package com.hotel_booking_systems_android.Activity.Tenant.Home_Part;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.hotel_booking_systems_android.Activity.Tenant.TenantMainPage_Part.ExtendStayActivity;
import com.hotel_booking_systems_android.Activity.Tenant.TenantMainPage_Part.OrderFoodActivity;
import com.hotel_booking_systems_android.Activity.Tenant.TenantMainPage_Part.RoomDetailsActivity;
import com.hotel_booking_systems_android.MainActivity;
import com.hotel_booking_systems_android.R;
import com.hotel_booking_systems_android.Activity.Tenant.TenantMainPage_Part.CheckoutActivity;
import com.hotel_booking_systems_android.Activity.Tenant.TenantMainPage_Part.FeedbackActivity;
import com.hotel_booking_systems_android.service.AccountSharedPreferences;

public class TenantMainFragment extends Fragment {

    private Button checkout_btn;
    private Button extendStay_btn;
    private Button feedback_btn;
    private Button orderFood_btn;
    private Button roomDetails_btn;
    private Button callService_btn;
    private TextView tenant_main_page_username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tenant_main, container, false);
        initializeComponent(view);
        initializeEvent();

        boolean isBooking = AccountSharedPreferences.getInstance(getContext()).isBooking();
        if(!isBooking){
            Toast.makeText(getContext(), "You are no booking any room, back to home...", Toast.LENGTH_SHORT).show();
            if (getActivity() instanceof MainActivity) {
                // 切换到 Fragment A
                ((MainActivity) getActivity()).replaceFragment(new TenantMainFragment());
            }
        }
        
        return view;
    }

    private void initializeComponent(View view) {
        checkout_btn = view.findViewById(R.id.tenant_checkout_btn);
        extendStay_btn = view.findViewById(R.id.tenant_extendStay_btn);
        feedback_btn = view.findViewById(R.id.tenant_feedback_btn);
        orderFood_btn = view.findViewById(R.id.tenant_orderFood_btn);
        roomDetails_btn = view.findViewById(R.id.tenant_roomDetails_btn);
        callService_btn = view.findViewById(R.id.tenant_callService_btn);
        tenant_main_page_username = view.findViewById(R.id.tenant_main_page_username);

        //set value
        AccountSharedPreferences sp = AccountSharedPreferences.getInstance(getContext());
        tenant_main_page_username.setText("Hi, " + sp.getUsername());
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
