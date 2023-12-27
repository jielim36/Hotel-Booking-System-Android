package com.hotel_booking_systems_android.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.hotel_booking_systems_android.Activity.Employee.Room.Room;
import com.hotel_booking_systems_android.Activity.Employee.Room.RoomStatus;
import com.hotel_booking_systems_android.R;

import java.util.List;

public class RoomAdapter extends BaseAdapter {
    private Context context;
    private List<Room> roomList;

    public RoomAdapter(Context context, List<Room> roomList) {
        this.context = context;
        this.roomList = roomList;
    }

    @Override
    public int getCount() {
        return roomList.size();
    }

    @Override
    public Object getItem(int position) {
        return roomList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.template_room_list, parent, false);
        }

        TextView roomNoTextView = convertView.findViewById(R.id.room_list_room_no);
        TextView priceTextView = convertView.findViewById(R.id.room_list_price);
        TextView maxPeopleTextView = convertView.findViewById(R.id.room_list_max_people);
        TextView statusTextView = convertView.findViewById(R.id.room_list_status);

        Room room = roomList.get(position);

        roomNoTextView.setText("Room No: " + room.getRoom_no());
        priceTextView.setText("Price: " + room.getPrice());
        maxPeopleTextView.setText("Max People: " + room.getMax_people());
        statusTextView.setText("Status: " + room.getStatus());

        if(room.getStatus() == RoomStatus.BOOKED){
            convertView.setBackgroundColor(ContextCompat.getColor(context,R.color.gray));
        }

        return convertView;
    }
}
