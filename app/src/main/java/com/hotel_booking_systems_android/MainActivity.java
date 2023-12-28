package com.hotel_booking_systems_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.hotel_booking_systems_android.Activity.Tenant.Home_Part.TenantMainFragment;
import com.hotel_booking_systems_android.Activity.Tenant.Home_Part.HomeFragment;
import com.hotel_booking_systems_android.Activity.Tenant.Home_Part.ProfileFragment;
import com.hotel_booking_systems_android.databinding.ActivityMainBinding;
import com.hotel_booking_systems_android.service.AccountSharedPreferences;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    SharedPreferences sp;
//    private RoomDBEngine roomDBEngine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialize();
//        initializeDatabase();

        //After booking will jump to main page, verify which fragment other page want to go
        if(getIntent().getStringExtra("fragmentToShow") != null){
            jumpTargetFragment(getIntent().getStringExtra("fragmentToShow"));
        }

    }

    public void initialize(){
        //default page
        replaceFragment(new HomeFragment());

        //member initialize
        sp = getSharedPreferences("account", Context.MODE_PRIVATE);
        AccountSharedPreferences accSp = AccountSharedPreferences.getInstance(getApplicationContext());

        //event initialize
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home){
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.nav_profile) {
                if(accSp.isLogin()){
                    replaceFragment(new ProfileFragment());
                }else {
                    Toast.makeText(this, "Please Login First", Toast.LENGTH_SHORT).show();
                }
            }else if(itemId == R.id.nav_tenantMainPage){
                boolean isLogin = sp.getBoolean("isLogin", false);
                boolean isBooking = sp.getBoolean("isBooking", false);
                if(isLogin && isBooking){
                    replaceFragment(new TenantMainFragment());
                }else{
                    Toast.makeText(MainActivity.this , "Please login and booking room first..." , Toast.LENGTH_SHORT).show();
                }
            }

            return true;
        });


        //alert message
        if (sp.getBoolean("autoLogin",false)) {
            Toast.makeText(MainActivity.this, "Auto Login!", Toast.LENGTH_SHORT).show();
        }
    }

    private void jumpTargetFragment(String fragment){
        if (fragment.equals("home")){
            replaceFragment(new HomeFragment());
        } else if (fragment.equals("profile")) {
            replaceFragment(new ProfileFragment());
        }else if(fragment.equals("tenant")){
            boolean isLogin = sp.getBoolean("isLogin", false);
            boolean isBooking = sp.getBoolean("isBooking", false);
            if(isLogin && isBooking){
                replaceFragment(new TenantMainFragment());
            }else{
                Toast.makeText(MainActivity.this , "Please login and booking room first..." , Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout , fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        //关闭应用时自动退出
        super.onDestroy();
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("isLogin",false);
        edit.apply();
    }

//    public void initializeDatabase(){
//        roomDBEngine = new RoomDBEngine(MainActivity.this);
//
//        Room getFirstRoom = roomDBEngine.getRoomByFloorAndRoomNumber(1, 1);
//        if (getFirstRoom == null){
//            //room data
//            Room room1 = new Room( 1, "1", Room.ROOM_TYPE_SINGLE, 100.00, Room.AVAILABILITY_AVAILABLE);
//            Room room2 = new Room( 1, "2", Room.ROOM_TYPE_SINGLE, 100.00, Room.AVAILABILITY_AVAILABLE);
//            Room room3 = new Room(1, "3", Room.ROOM_TYPE_SINGLE, 100.00, Room.AVAILABILITY_AVAILABLE);
//            Room room4 = new Room(1, "4", Room.ROOM_TYPE_SINGLE, 100.00, Room.AVAILABILITY_AVAILABLE);
//
//            Room room5 = new Room( 2, "1", Room.ROOM_TYPE_DOUBLE, 280.00, Room.AVAILABILITY_AVAILABLE);
//            Room room6 = new Room( 2, "2", Room.ROOM_TYPE_DOUBLE, 280.00, Room.AVAILABILITY_AVAILABLE);
//            Room room7 = new Room(2, "3", Room.ROOM_TYPE_DOUBLE, 280.00, Room.AVAILABILITY_AVAILABLE);
//            Room room8 = new Room(2, "4", Room.ROOM_TYPE_DOUBLE, 280.00, Room.AVAILABILITY_AVAILABLE);
//
//            Room room9 = new Room( 3, "1", Room.ROOM_TYPE_FAMILY, 340.00, Room.AVAILABILITY_AVAILABLE);
//            Room room10 = new Room( 3, "2", Room.ROOM_TYPE_FAMILY, 340.00, Room.AVAILABILITY_AVAILABLE);
//            Room room11 = new Room(3, "3", Room.ROOM_TYPE_FAMILY, 340.00, Room.AVAILABILITY_AVAILABLE);
//            Room room12 = new Room(3, "4", Room.ROOM_TYPE_FAMILY, 340.00, Room.AVAILABILITY_AVAILABLE);
//
//            Room room13 = new Room( 4, "1", Room.ROOM_TYPE_SUITE, 550.00, Room.AVAILABILITY_AVAILABLE);
//            Room room14 = new Room( 4, "2", Room.ROOM_TYPE_SUITE, 550.00, Room.AVAILABILITY_AVAILABLE);
//            Room room15 = new Room(4, "3", Room.ROOM_TYPE_SUITE, 550.00, Room.AVAILABILITY_AVAILABLE);
//            Room room16 = new Room(4, "4", Room.ROOM_TYPE_SUITE, 550.00, Room.AVAILABILITY_AVAILABLE);
//
//            roomDBEngine.insertRooms(room1,room2,room3,room4,room5,room6,room7,room8,room9,room10,room11,room12,room13,room14,room15,room16);
//        }
//
//    }

}
