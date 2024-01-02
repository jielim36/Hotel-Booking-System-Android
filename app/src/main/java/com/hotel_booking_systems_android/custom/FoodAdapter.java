package com.hotel_booking_systems_android.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.core.content.ContextCompat;

import com.hotel_booking_systems_android.R;
import com.hotel_booking_systems_android.bean.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends BaseAdapter {
    private Context context;
    private static List<Food> dataList = Food.getFoodListInstance();

    public FoodAdapter(Context context, ArrayList<Food> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public FoodAdapter(Context context) {//using default itemList
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.template_food_list, parent, false);
        }

        ImageView foodImg = convertView.findViewById(R.id.food_list_img);
        TextView itemName = convertView.findViewById(R.id.food_list_name);
        TextView itemDesc = convertView.findViewById(R.id.food_list_description);
        TextView itemPrice = convertView.findViewById(R.id.food_list_price);

        itemName.setText(dataList.get(position).getName());
        itemDesc.setText(dataList.get(position).getDescription());
        itemPrice.setText("Price: "+String.valueOf(dataList.get(position).getPrice()));
        foodImg.setBackground(ContextCompat.getDrawable(context, dataList.get(position).getImg()));

        return convertView;
    }
}
