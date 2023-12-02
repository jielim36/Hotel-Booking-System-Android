package com.hotel_booking_systems_android.fragments.Home_Part;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.hotel_booking_systems_android.R;
import com.hotel_booking_systems_android.activity.TenantMainPage_Part.CheckoutActivity;
import com.hotel_booking_systems_android.activity.TenantMainPage_Part.ExtendStayActivity;
import com.hotel_booking_systems_android.activity.TenantMainPage_Part.FeedbackActivity;
import com.hotel_booking_systems_android.activity.TenantMainPage_Part.OrderFoodActivity;
import com.hotel_booking_systems_android.activity.TenantMainPage_Part.RoomDetailsActivity;

public class TenantMainFragment extends Fragment {

    private Button checkout_btn;
    private Button extendStay_btn;
    private Button feedback_btn;
    private Button orderFood_btn;
    private Button roomDetails_btn;
    private Button callService_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tenant_main, container, false);
        initializeComponent(view);
        initializeEvent();
        return view;
    }

    private void initializeComponent(View view) {
        checkout_btn = view.findViewById(R.id.tenant_checkout_btn);
        extendStay_btn = view.findViewById(R.id.tenant_extendStay_btn);
        feedback_btn = view.findViewById(R.id.tenant_feedback_btn);
        orderFood_btn = view.findViewById(R.id.tenant_orderFood_btn);
        roomDetails_btn = view.findViewById(R.id.tenant_roomDetails_btn);
        callService_btn = view.findViewById(R.id.tenant_callService_btn);
    }

    private void initializeEvent() {
        checkout_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CheckoutActivity.class);
            startActivity(intent);
        });

        extendStay_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ExtendStayActivity.class);
            startActivity(intent);
        });

        feedback_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), FeedbackActivity.class);
            startActivity(intent);
        });

        roomDetails_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), RoomDetailsActivity.class);
            startActivity(intent);
        });

        orderFood_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), OrderFoodActivity.class);
            startActivity(intent);
        });
    }
}
