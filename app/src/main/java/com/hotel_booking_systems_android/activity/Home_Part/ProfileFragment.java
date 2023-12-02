package com.hotel_booking_systems_android.activity.Home_Part;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hotel_booking_systems_android.R;

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
    private SharedPreferences sp;
    private TextView profileUsername_tv;
    private TextView profileRoomUnit_tv;
    private ImageView profileAvatar_iv;
    private TextView profileState_tv;

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
        return view;
    }

    public void initialize(){
        sp = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
        profileUsername_tv = view.findViewById(R.id.profile_username_tv);
        profileRoomUnit_tv = view.findViewById(R.id.profile_roomUnit_tv);
        profileAvatar_iv = view.findViewById(R.id.profile_avatar_iv);
        profileState_tv = view.findViewById(R.id.profile_state_tv);

        if (sp.getBoolean("isLogin",false)){
            String username = sp.getString("username","-");
            profileUsername_tv.setText(username);
            Drawable avatarDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.avatar_labixiaoxin);
            profileAvatar_iv.setImageDrawable(avatarDrawable);
            profileState_tv.setText("State:");
            //TODO: update user's room information from BookingTable
            profileRoomUnit_tv.setText("None");//default state is None, after user booked a room, change to room unit
        }
    }
}