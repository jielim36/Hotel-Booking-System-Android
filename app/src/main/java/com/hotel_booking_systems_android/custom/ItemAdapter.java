package com.hotel_booking_systems_android.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hotel_booking_systems_android.R;
import com.hotel_booking_systems_android.bean.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends BaseAdapter {

    private Context context;
    private static List<Item> dataList;

    public ItemAdapter(Context context, ArrayList<Item> dataList) {
        this.context = context;
        this.dataList = dataList;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.template_item_list_amount_details, parent, false);
        }

        TextView itemId = convertView.findViewById(R.id.list_item_id);
        TextView itemName = convertView.findViewById(R.id.list_item_name);
        TextView itemQuantity = convertView.findViewById(R.id.list_item_quantity);
        TextView itemTotalAmount = convertView.findViewById(R.id.list_item_amount);

        itemId.setText(String.valueOf(position+1));//itemId is the list id, not the item object's id
        itemName.setText(dataList.get(position).getItemName());
        itemQuantity.setText(String.valueOf(dataList.get(position).getQuantity()));
        itemTotalAmount.setText(String.valueOf(dataList.get(position).getTotalAmount()));

        return convertView;
    }
}
