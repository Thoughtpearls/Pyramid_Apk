// Generated by view binder compiler. Do not edit!
package com.thoughtpearl.conveyance.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.thoughtpearl.conveyance.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentAttendanceBinding implements ViewBinding {
  @NonNull
  private final SwipeRefreshLayout rootView;

  @NonNull
  public final MaterialButton applyLeaveBtn;

  @NonNull
  public final LinearLayout applyLeaveLayout;

  @NonNull
  public final RelativeLayout attendanceLayout;

  @NonNull
  public final CardView calendarLayoutTest;

  @NonNull
  public final LinearLayout calenderContainer;

  @NonNull
  public final MaterialButton checkInBtn;

  @NonNull
  public final LinearLayout checkInLayout;

  @NonNull
  public final MaterialButton checkOutBtn;

  @NonNull
  public final TextView compoffTextView;

  @NonNull
  public final LottieAnimationView errorAnimation;

  @NonNull
  public final CardView leaveLayout;

  @NonNull
  public final TextView leaveRemainingTextView;

  @NonNull
  public final TextView leaveTakenTextView;

  @NonNull
  public final SwipeRefreshLayout swipeRefreshLayout;

  @NonNull
  public final ScrollView test;

  private FragmentAttendanceBinding(@NonNull SwipeRefreshLayout rootView,
      @NonNull MaterialButton applyLeaveBtn, @NonNull LinearLayout applyLeaveLayout,
      @NonNull RelativeLayout attendanceLayout, @NonNull CardView calendarLayoutTest,
      @NonNull LinearLayout calenderContainer, @NonNull MaterialButton checkInBtn,
      @NonNull LinearLayout checkInLayout, @NonNull MaterialButton checkOutBtn,
      @NonNull TextView compoffTextView, @NonNull LottieAnimationView errorAnimation,
      @NonNull CardView leaveLayout, @NonNull TextView leaveRemainingTextView,
      @NonNull TextView leaveTakenTextView, @NonNull SwipeRefreshLayout swipeRefreshLayout,
      @NonNull ScrollView test) {
    this.rootView = rootView;
    this.applyLeaveBtn = applyLeaveBtn;
    this.applyLeaveLayout = applyLeaveLayout;
    this.attendanceLayout = attendanceLayout;
    this.calendarLayoutTest = calendarLayoutTest;
    this.calenderContainer = calenderContainer;
    this.checkInBtn = checkInBtn;
    this.checkInLayout = checkInLayout;
    this.checkOutBtn = checkOutBtn;
    this.compoffTextView = compoffTextView;
    this.errorAnimation = errorAnimation;
    this.leaveLayout = leaveLayout;
    this.leaveRemainingTextView = leaveRemainingTextView;
    this.leaveTakenTextView = leaveTakenTextView;
    this.swipeRefreshLayout = swipeRefreshLayout;
    this.test = test;
  }

  @Override
  @NonNull
  public SwipeRefreshLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentAttendanceBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentAttendanceBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_attendance, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentAttendanceBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.applyLeaveBtn;
      MaterialButton applyLeaveBtn = ViewBindings.findChildViewById(rootView, id);
      if (applyLeaveBtn == null) {
        break missingId;
      }

      id = R.id.applyLeaveLayout;
      LinearLayout applyLeaveLayout = ViewBindings.findChildViewById(rootView, id);
      if (applyLeaveLayout == null) {
        break missingId;
      }

      id = R.id.attendanceLayout;
      RelativeLayout attendanceLayout = ViewBindings.findChildViewById(rootView, id);
      if (attendanceLayout == null) {
        break missingId;
      }

      id = R.id.calendarLayout_test;
      CardView calendarLayoutTest = ViewBindings.findChildViewById(rootView, id);
      if (calendarLayoutTest == null) {
        break missingId;
      }

      id = R.id.calender_container;
      LinearLayout calenderContainer = ViewBindings.findChildViewById(rootView, id);
      if (calenderContainer == null) {
        break missingId;
      }

      id = R.id.checkInBtn;
      MaterialButton checkInBtn = ViewBindings.findChildViewById(rootView, id);
      if (checkInBtn == null) {
        break missingId;
      }

      id = R.id.checkInLayout;
      LinearLayout checkInLayout = ViewBindings.findChildViewById(rootView, id);
      if (checkInLayout == null) {
        break missingId;
      }

      id = R.id.checkOutBtn;
      MaterialButton checkOutBtn = ViewBindings.findChildViewById(rootView, id);
      if (checkOutBtn == null) {
        break missingId;
      }

      id = R.id.compoffTextView;
      TextView compoffTextView = ViewBindings.findChildViewById(rootView, id);
      if (compoffTextView == null) {
        break missingId;
      }

      id = R.id.errorAnimation;
      LottieAnimationView errorAnimation = ViewBindings.findChildViewById(rootView, id);
      if (errorAnimation == null) {
        break missingId;
      }

      id = R.id.leave_layout;
      CardView leaveLayout = ViewBindings.findChildViewById(rootView, id);
      if (leaveLayout == null) {
        break missingId;
      }

      id = R.id.leaveRemainingTextView;
      TextView leaveRemainingTextView = ViewBindings.findChildViewById(rootView, id);
      if (leaveRemainingTextView == null) {
        break missingId;
      }

      id = R.id.leaveTakenTextView;
      TextView leaveTakenTextView = ViewBindings.findChildViewById(rootView, id);
      if (leaveTakenTextView == null) {
        break missingId;
      }

      SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) rootView;

      id = R.id.test;
      ScrollView test = ViewBindings.findChildViewById(rootView, id);
      if (test == null) {
        break missingId;
      }

      return new FragmentAttendanceBinding((SwipeRefreshLayout) rootView, applyLeaveBtn,
          applyLeaveLayout, attendanceLayout, calendarLayoutTest, calenderContainer, checkInBtn,
          checkInLayout, checkOutBtn, compoffTextView, errorAnimation, leaveLayout,
          leaveRemainingTextView, leaveTakenTextView, swipeRefreshLayout, test);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}