package com.hotel_booking_systems_android.Activity.Tenant.TenantMainPage_Part;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.hotel_booking_systems_android.DB.TenantRoomDatabaseHelper;
import com.hotel_booking_systems_android.R;
import com.hotel_booking_systems_android.bean.TenantRoom;
import com.hotel_booking_systems_android.service.AccountSharedPreferences;

import java.util.Calendar;
import java.util.List;

public class ExtendStayActivity extends AppCompatActivity {

    ImageButton selectDate_btn;
    EditText inputDate_et;
    ImageButton previousPage_btn;
    TextView roomNo_tv;
    TextView customerName_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_stay);

        initializeComponent();
        initializeEvent();
    }

    private void initializeComponent() {
        selectDate_btn = findViewById(R.id.extend_stay_select_date_btn);
        inputDate_et = findViewById(R.id.extendStay_extendDate);
        previousPage_btn = findViewById(R.id.extend_stay_back_btn);
        roomNo_tv = findViewById(R.id.extendStay_roomNo);
        customerName_tv = findViewById(R.id.extendStay_customerName);

        //set value
        AccountSharedPreferences accSp = AccountSharedPreferences.getInstance(getApplicationContext());
        //set customer name
        customerName_tv.setText(accSp.getUsername());

        //list all rooms no
        Integer userId = accSp.getUserId();
        TenantRoomDatabaseHelper tenantRoomDatabaseHelper = new TenantRoomDatabaseHelper(getApplicationContext());
        List<TenantRoom> rooms = tenantRoomDatabaseHelper.getTenantRoomsByUserIdAndStatus(userId, TenantRoom.Status.CHECKED_IN);
        String roomsNoContent = "";
        for(int i = 0 ; i < rooms.size() ; i++){
            if(i > 0){
                roomsNoContent += "," + rooms.get(i).getRoomId();
            }else{
                roomsNoContent += String.valueOf(rooms.get(i).getRoomId());
            }
        }
        roomNo_tv.setText(roomsNoContent);
    }

    private void initializeEvent() {
        selectDate_btn.setOnClickListener(view -> openDateTimePicker());
        previousPage_btn.setOnClickListener(v -> onBackPressed());
    }

    private void openDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(ExtendStayActivity.this,
                (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
                    final Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(Calendar.YEAR, selectedYear);
                    selectedDate.set(Calendar.MONTH, selectedMonth);
                    selectedDate.set(Calendar.DAY_OF_MONTH, selectedDay);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(ExtendStayActivity.this,
                            (TimePicker timePicker, int selectedHour, int selectedMinute) -> {
                                selectedDate.set(Calendar.HOUR_OF_DAY, selectedHour);
                                selectedDate.set(Calendar.MINUTE, selectedMinute);

                                String formattedDateTime = android.text.format.DateFormat.format("yyyy-MM-dd HH:mm", selectedDate).toString();
                                inputDate_et.setText(formattedDateTime);
                            }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true);

                    timePickerDialog.show();
                }, year, month, day);

        datePickerDialog.show();
    }
}