package com.hotel_booking_systems_android.Activity.Tenant.Home_Part;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hotel_booking_systems_android.Activity.Employee.Tenant.Tenant;
import com.hotel_booking_systems_android.Activity.Employee.Tenant.TenantMainActivity;
import com.hotel_booking_systems_android.Activity.Tenant.AboutUs_Part.HotelDescriptionActivity;
import com.hotel_booking_systems_android.R;
import com.hotel_booking_systems_android.Activity.Tenant.AboutUs_Part.ServicesAndFacilitiesActivity;
import com.hotel_booking_systems_android.Activity.Tenant.Start_Booking_Part.BookingRoomActivity;
import com.hotel_booking_systems_android.Activity.Tenant.Authentication_Part.LoginActivity;
import com.hotel_booking_systems_android.service.AccountSharedPreferences;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView loginAndLogout_btn;//include logout function
    private TextView username_tv;
    private Intent loginIntent;
    private SharedPreferences sp;
    private View view;
    private LinearLayout startBooking_btn;
    private LinearLayout hotelDescription_btn;
    private LinearLayout servicesAndFacilities_btn;
    private LinearLayout goEmployeePage_btn;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initializeMember();
        initializeLoginState();
        initializeEvent();

        return view;
    }

    public void initializeLoginState(){

        if (sp.getBoolean("autoLogin",false)){
            //Toast.makeText(getActivity(),"Auto Login!",Toast.LENGTH_SHORT).show();
            //set username
            String username = sp.getString("username", "-");
            username_tv.setText(username);
            SharedPreferences.Editor edit = sp.edit();
            edit.putBoolean("isLogin",true);
            edit.apply();
        }
        //login_btn content and function is depending by isLogin boolean value
        if (sp.getBoolean("isLogin",false)){
            loginAndLogout_btn.setText("Logout");
            String username = sp.getString("username", "-");
            username_tv.setText(username);
        }else {
            loginAndLogout_btn.setText("Login");
        }

    }

    public void initializeMember(){
        loginAndLogout_btn = view.findViewById(R.id.loginAndLogout_btn);
        loginIntent = new Intent(getActivity() , LoginActivity.class);
        username_tv = view.findViewById(R.id.username_tv);
        sp = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
        startBooking_btn = view.findViewById(R.id.home_startBooking_btn);
        hotelDescription_btn = view.findViewById(R.id.home_hotelDescription_btn);
        servicesAndFacilities_btn = view.findViewById(R.id.home_servicesAndFacilities_btn);
        goEmployeePage_btn = view.findViewById(R.id.home_goEmployeePage_btn);

        //set username
        if(sp.getBoolean("isLogin",false)){
            String username = AccountSharedPreferences.getInstance(getContext()).getUsername();
            username_tv.setText(username);
        }else{
            username_tv.setText("-");
        }

        //set employee entry button background color
        AccountSharedPreferences accSp = AccountSharedPreferences.getInstance(getContext());
        if(!accSp.isStaff()){
            goEmployeePage_btn.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.btn_disabled_shape));
        }else{
            startBooking_btn.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.btn_disabled_shape));
        }
    }

    public void initializeEvent(){
        loginAndLogout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sp.getBoolean("isLogin",false)){
                    logoutProcess();
                }else {
                    startActivity(loginIntent);
                }
            }
        });

        startBooking_btn.setOnClickListener(v ->{
            if (sp.getBoolean("isLogin",false)){
                AccountSharedPreferences accSp = AccountSharedPreferences.getInstance(getContext());
                if(accSp.isStaff()){
                    Toast.makeText(getContext(), "Staff is not allowed to booking room...", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent startBookingPage = new Intent(getActivity(), BookingRoomActivity.class);
                startActivity(startBookingPage);
            }else {
                startActivity(loginIntent);
            }
        });

        hotelDescription_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), HotelDescriptionActivity.class);
            startActivity(intent);
        });

        servicesAndFacilities_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ServicesAndFacilitiesActivity.class);
            startActivity(intent);
        });

        goEmployeePage_btn.setOnClickListener(v -> {
            AccountSharedPreferences accSp = AccountSharedPreferences.getInstance(getContext());
            if(accSp.isStaff()){
                Intent intent = new Intent(getActivity(), TenantMainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(getActivity(), "You are not a Admin", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void logoutProcess(){
        AccountSharedPreferences accSp = AccountSharedPreferences.getInstance(getContext());
        username_tv.setText("-");
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isLogin",false);
        editor.putBoolean("autoLogin",false);
        editor.putBoolean("isStaff", false);
        editor.apply();
        initializeLoginState();//reload login state
    }

}