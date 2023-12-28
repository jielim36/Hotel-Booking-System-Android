package com.hotel_booking_systems_android.Activity.Tenant.Home_Part;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hotel_booking_systems_android.Activity.Tenant.Authentication_Part.EditUserAccActivity;
import com.hotel_booking_systems_android.Activity.Tenant.Authentication_Part.PaymentHistoryActivity;
import com.hotel_booking_systems_android.Activity.Tenant.TenantMainPage_Part.CallServiceActivity;
import com.hotel_booking_systems_android.MainActivity;
import com.hotel_booking_systems_android.R;
import com.hotel_booking_systems_android.service.AccountSharedPreferences;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View view;
    private String mParam1;
    private String mParam2;
    private AccountSharedPreferences accSp;
    private TextView profileUsername_tv;
    private TextView profileRoomUnit_tv;
    private ImageView profileAvatar_iv;
    private TextView profileState_tv;
    LinearLayout editAccBtn;
    LinearLayout historyBtn;
    LinearLayout callServiceBtn ;
    LinearLayout logoutBtn;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        initialize();
        initEvent();
        return view;
    }

    public void initialize(){
        accSp = AccountSharedPreferences.getInstance(getContext());
        profileUsername_tv = view.findViewById(R.id.profile_username_tv);
        profileRoomUnit_tv = view.findViewById(R.id.profile_roomUnit_tv);
        profileAvatar_iv = view.findViewById(R.id.profile_avatar_iv);
        profileState_tv = view.findViewById(R.id.profile_state_tv);
        editAccBtn = view.findViewById(R.id.profile_editAcc_btn);
        historyBtn = view.findViewById(R.id.profile_history_btn);
        callServiceBtn = view.findViewById(R.id.profile_callService_btn);
        logoutBtn = view.findViewById(R.id.profile_logout_btn);

        if(accSp.isStaff()){
            editAccBtn.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_disabled_shape));
        }

        if (accSp.isLogin()){
            String username = accSp.getUsername();
            profileUsername_tv.setText(username);
            Drawable avatarDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.avatar_labixiaoxin);
            profileAvatar_iv.setImageDrawable(avatarDrawable);
            profileState_tv.setText("State:");
            if (accSp.isStaff()){
                profileRoomUnit_tv.setText("Employee");
            }else if(accSp.isBooking()){
                profileRoomUnit_tv.setText("Booked");
            }else{
                profileRoomUnit_tv.setText("No Booking");
            }

        }
    }

    private void initEvent(){
        editAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(accSp.isStaff()){
                    Toast.makeText(getContext(), "Admin is not allowed to edit account", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getContext(), EditUserAccActivity.class);
                startActivity(intent);
            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext() , PaymentHistoryActivity.class);
                startActivity(intent);
            }
        });

        callServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!accSp.isBooking()){
                    Toast.makeText(getContext(), "Please Booking Room First...", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getContext(), CallServiceActivity.class);
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutProcess();
            }
        });
    }

    public void logoutProcess(){
        AccountSharedPreferences accSp = AccountSharedPreferences.getInstance(getContext());
        accSp.setLogin(false);
        accSp.setAutoLogin(false);
        accSp.setStaff(false);
//        initializeLoginState();//reload login state
        Intent intent = new Intent(getContext() , MainActivity.class);
        startActivity(intent);
    }
}