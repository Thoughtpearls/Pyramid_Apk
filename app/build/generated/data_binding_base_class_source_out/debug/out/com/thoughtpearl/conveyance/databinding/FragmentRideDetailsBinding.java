// Generated by view binder compiler. Do not edit!
package com.thoughtpearl.conveyance.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.thoughtpearl.conveyance.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentRideDetailsBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView approvedAmount;

  @NonNull
  public final TextView approvedAmountIcon;

  @NonNull
  public final ImageView incompleteIcon;

  @NonNull
  public final TextView rideDate;

  @NonNull
  public final TextView totalDistance;

  @NonNull
  public final TextView totaldurationTextView;

  private FragmentRideDetailsBinding(@NonNull LinearLayout rootView,
      @NonNull TextView approvedAmount, @NonNull TextView approvedAmountIcon,
      @NonNull ImageView incompleteIcon, @NonNull TextView rideDate,
      @NonNull TextView totalDistance, @NonNull TextView totaldurationTextView) {
    this.rootView = rootView;
    this.approvedAmount = approvedAmount;
    this.approvedAmountIcon = approvedAmountIcon;
    this.incompleteIcon = incompleteIcon;
    this.rideDate = rideDate;
    this.totalDistance = totalDistance;
    this.totaldurationTextView = totaldurationTextView;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentRideDetailsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentRideDetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_ride_details, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentRideDetailsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.approvedAmount;
      TextView approvedAmount = ViewBindings.findChildViewById(rootView, id);
      if (approvedAmount == null) {
        break missingId;
      }

      id = R.id.approvedAmountIcon;
      TextView approvedAmountIcon = ViewBindings.findChildViewById(rootView, id);
      if (approvedAmountIcon == null) {
        break missingId;
      }

      id = R.id.incompleteIcon;
      ImageView incompleteIcon = ViewBindings.findChildViewById(rootView, id);
      if (incompleteIcon == null) {
        break missingId;
      }

      id = R.id.rideDate;
      TextView rideDate = ViewBindings.findChildViewById(rootView, id);
      if (rideDate == null) {
        break missingId;
      }

      id = R.id.totalDistance;
      TextView totalDistance = ViewBindings.findChildViewById(rootView, id);
      if (totalDistance == null) {
        break missingId;
      }

      id = R.id.totaldurationTextView;
      TextView totaldurationTextView = ViewBindings.findChildViewById(rootView, id);
      if (totaldurationTextView == null) {
        break missingId;
      }

      return new FragmentRideDetailsBinding((LinearLayout) rootView, approvedAmount,
          approvedAmountIcon, incompleteIcon, rideDate, totalDistance, totaldurationTextView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
