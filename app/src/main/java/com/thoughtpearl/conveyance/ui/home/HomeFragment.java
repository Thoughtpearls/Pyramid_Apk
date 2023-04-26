package com.thoughtpearl.conveyance.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.thoughtpearl.conveyance.databinding.FragmentHomeBinding;
import com.thoughtpearl.conveyance.ui.recordride.RecordRideActivity;
import com.thoughtpearl.conveyance.LocationApp;
import com.thoughtpearl.conveyance.R;
import com.thoughtpearl.conveyance.utility.TrackerUtility;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private boolean isClockedIn;
    private boolean isClockedOut;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(LocationApp.APP_NAME, Context.MODE_PRIVATE);
        AtomicReference<String> checkInDate = new AtomicReference<>(sharedPreferences.getString(LocationApp.CLOCK_IN, ""));
        AtomicReference<String> checkOutDate = new AtomicReference<>(sharedPreferences.getString(LocationApp.CLOCK_OUT, ""));
        AtomicReference<Boolean> isRideDisabled = new AtomicReference<>(sharedPreferences.getBoolean("rideDisabled", false));
       if (checkInDate.get().trim().length() > 0 &&  checkInDate.get().equalsIgnoreCase(TrackerUtility.getDateString(new Date()))) {
            isClockedIn = true;
        } else {
            isClockedIn = false;
        }
        if (checkOutDate.get().equalsIgnoreCase(TrackerUtility.getDateString(new Date()))) {
            isClockedOut = true;
        } else {
            isClockedOut = false;
        }


        View riderRecord = binding.recordRideBtn;
        riderRecord.setVisibility(isRideDisabled.get() ? View.GONE : View.VISIBLE);
        binding.statisticsBtn.setVisibility(isRideDisabled.get() ? View.GONE : View.VISIBLE);
        if (!(isClockedIn && isClockedOut)) {
            riderRecord.setBackgroundColor(Color.WHITE);
            riderRecord.setOnClickListener(view -> {
                if (!TrackerUtility.checkConnection(getActivity())) {
                    Toast.makeText(getActivity(), "Please check your network connection", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getContext(), RecordRideActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            riderRecord.setBackgroundColor(Color.GRAY);
            riderRecord.setOnClickListener(view -> {
                Toast.makeText(getActivity(), "You can not record rides once you are checkout for the day.", Toast.LENGTH_LONG).show();
            });
        }

        binding.statisticsBtn.setOnClickListener(view -> {
            if (!TrackerUtility.checkConnection(getActivity())) {
                Toast.makeText(getActivity(), "Please check your network connection", Toast.LENGTH_LONG).show();
            } else {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_bottom_navigation);
                navController.navigate(R.id.navigation_statistics);
            }
        });

        binding.attendanceBtn.setOnClickListener(view -> {
            if (!TrackerUtility.checkConnection(getActivity())) {
                Toast.makeText(getActivity(), "Please check your network connection", Toast.LENGTH_LONG).show();
            } else {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_bottom_navigation);
                navController.navigate(R.id.navigation_attendance);
            }
        });

        binding.calendarView.setFirstDayOfWeek(Calendar.MONDAY);

        if (!isRideDisabled.get()) {
            binding.calendarView.setOnDateChangeListener((calendarView, year, month, day) -> {
                String date = year + "-" + (month < 9 ? "0" + (month + 1) : (month + 1)) + "-" + day;
                showLogoutAlertDialog("Do you want to check ride details of selected date (" + date + ")", date);

                //Toast.makeText(getContext(), "i :" + year + " i1 :" +  month + " i2 :" + day, Toast.LENGTH_LONG).show();
            });
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void showLogoutAlertDialog(String message, String date) {
        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setTitle("Alert");
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("OK", (dialogInterface, i) -> {
            Bundle bundle = new Bundle();
            bundle.putString("selectedDate", date);
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_bottom_navigation);
            navController.navigate(R.id.navigation_statistics, bundle);
        });
        alertDialogBuilder.setNegativeButton("CANCEL", (dialogInterface, i) -> dialogInterface.dismiss());
        alertDialogBuilder.show();
    }
}