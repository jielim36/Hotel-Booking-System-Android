package com.hotel_booking_systems_android.Activity.Employee.Room;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.hotel_booking_systems_android.DB.MyDatabaseHelper;
import com.hotel_booking_systems_android.Activity.Employee.Room.AddRoom.AddRoomStep1Activity;
import com.hotel_booking_systems_android.Activity.Employee.Tenant.TenantMainActivity;
import com.hotel_booking_systems_android.R;
import com.hotel_booking_systems_android.bean.Room;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class RoomListActivity extends AppCompatActivity {
    private ArrayAdapter arrayAdapter;
    private ArrayList<Room> Rooms;
    private ListView room_list;
    private MyDatabaseHelper databaseHelper;

    // Views
    View btn_add_room, filter_img, sch_img, tenant_list, profile;
    EditText search_bar;

    // Value of search bar
    String search_content;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        // initialize
        room_list = findViewById(R.id.room_list); // get room list
        Rooms = new ArrayList<>();  // Initialize the rooms ArrayList
        arrayAdapter = new ArrayAdapter(this, R.layout.room_list_items);

        // set room list adapter
        room_list.setAdapter(arrayAdapter);

        // Retrieve conditions from Intent
        Intent intent = getIntent();
        String typeCondition = intent.getStringExtra("typeCondition");
//        String fromPrice = intent.getStringExtra("fromPrice");
//        String toPrice = intent.getStringExtra("toPrice");
        String statusCondition = intent.getStringExtra("statusCondition"); // get only, not use yet
        String floorCondition = intent.getStringExtra("floorCondition");
        String rules = intent.getStringExtra("rules");
//        Toast.makeText(RoomListActivity.this, String.valueOf(statusCondition), Toast.LENGTH_SHORT).show();

        // null
        if (typeCondition == null
                || statusCondition == null
                || floorCondition == null) {
            queryDataFromDatabase();
        } else {
            // query Data
            if (typeCondition.equals("'', '', '', ''")
                    && statusCondition.equals("'', '', '', ''")
                    && floorCondition.equals("'', '', '', ''")) {
                queryDataFromDatabase();

            } else { // filter有任何条件都apply
                applyFilter(typeCondition, statusCondition, floorCondition);
            }
        }

        // set room list items adapter
        RoomListAdapter adapter = new RoomListAdapter(this, Rooms);
        room_list.setAdapter(adapter);


        // find Views
        btn_add_room = findViewById(R.id.btn_add_room);
        filter_img = findViewById(R.id.filter_img);
        search_bar = findViewById(R.id.search_bar);
        sch_img = findViewById(R.id.sch_img);
        tenant_list = findViewById(R.id.tenant_list);
        profile = findViewById(R.id.profile);

        // go add room
        btn_add_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RoomListActivity.this, "go add room", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RoomListActivity.this, AddRoomStep1Activity.class);
                startActivity(intent);
            }
        });


        // go filter
        filter_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RoomListActivity.this, "go filter", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RoomListActivity.this, RoomListFilterActivity.class);
                startActivity(intent);
                finish();
            }
        });


        // get search bar value
        sch_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_content = String.valueOf(search_bar.getText());

                if (!search_content.isEmpty()) {
                    // select * from rooms where room_no LIKE '%search_content%'
                    searchRooms(search_content);
                } else {
                    // 如果搜索内容为空，重新查询所有记录
                    queryDataFromDatabase();
                }
            }
        });


        // click room list item
        room_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 获取点击的房间对象
                Room selectedRoom = Rooms.get(position);

                // 启动 RoomDetailsActivity，并传递房间信息
                Intent intent = new Intent(RoomListActivity.this, RoomDetailsActivity.class);

                //set room image
                selectedRoom.setImages(selectedRoom.getImagesByType(selectedRoom.getType()));

                // 传递房间ID或其他信息
                intent.putExtra("room_no", selectedRoom.getRoom_no());
                intent.putExtra("price", selectedRoom.getPrice());
                intent.putExtra("type", selectedRoom.getType());
                intent.putExtra("floor_no", selectedRoom.getFloor_no());
                intent.putExtra("max_people", selectedRoom.getMax_people());
                intent.putExtra("describe", selectedRoom.getDescribe());
                intent.putExtra("status", selectedRoom.getStatus());
                intent.putExtra("images", selectedRoom.getImages());

                startActivity(intent);
            }
        });

        // baseline
//         tenant list
        tenant_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tenant_list = findViewById(R.id.tenant_list);
                tenant_list.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RoomListActivity.this, TenantMainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
            }
        });

        // profile
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RoomListActivity.this, "GO PROFILE", Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void applyFilter(String typeCondition, String statusCondition, String floorCondition) {
        // 查询数据的代码...
        Rooms.clear();

        //
        databaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        final Cursor c = db.rawQuery("SELECT * FROM Rooms " +
                "WHERE type IN (" + typeCondition + ") " +
                "OR status IN (" + statusCondition + ") " +
                "OR floor_no IN (" + floorCondition + ") " +
                "ORDER BY room_no ASC", null);

        int room_id = c.getColumnIndex("room_id");
        int room_no = c.getColumnIndex("room_no");
        int price = c.getColumnIndex("price");
        int type = c.getColumnIndex("type");
        int floor_no = c.getColumnIndex("floor_no");
        int max_people = c.getColumnIndex("max_people");
        int descibe = c.getColumnIndex("describe");
        int status = c.getColumnIndex("status");


        if (c.moveToFirst()) {
            do {
                // new a room and set the attribute
                Room room = new Room();
                room.setRoom_no(c.getString(room_no));
                room.setPrice(c.getString(price));
                room.setType(c.getString(type));
                room.setFloor_no(c.getString(floor_no));
                room.setMax_people(c.getString(max_people));
                room.setDescribe(c.getString(descibe));
                room.setStatus(RoomStatus.valueOf(c.getString(status)));
//                room.images =

                // add into rooms vector
                Rooms.add(room);

            } while (c.moveToNext());

            // 更新UI
            arrayAdapter.notifyDataSetChanged();
            room_list.invalidateViews();
            c.close();

        } else {
            Toast.makeText(this, "No data in the database", Toast.LENGTH_LONG).show();
        }
    }

    private void searchRooms(String searchTerm) {
        // 查询数据的代码...
        Rooms.clear();

        databaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        final Cursor c = db.rawQuery("SELECT * FROM Rooms " +
                "WHERE room_no LIKE '%" + searchTerm + "%' " +
                "OR type LIKE '%" + searchTerm + "%' " +
                "OR floor_no LIKE '%" + searchTerm + "%' " +
                "ORDER BY room_no ASC", null);

        int room_id = c.getColumnIndex("room_id");
        int room_no = c.getColumnIndex("room_no");
        int price = c.getColumnIndex("price");
        int type = c.getColumnIndex("type");
        int floor_no = c.getColumnIndex("floor_no");
        int max_people = c.getColumnIndex("max_people");
        int descibe = c.getColumnIndex("describe");
        int status = c.getColumnIndex("status");

        if (c.moveToFirst()) {
            do {
                // new a room and set the attribute
                Room room = new Room();
                room.setRoom_no(c.getString(room_no));
                room.setPrice(c.getString(price));
                room.setType(c.getString(type));
                room.setFloor_no(c.getString(floor_no));
                room.setMax_people(c.getString(max_people));
                room.setDescribe(c.getString(descibe));
                room.setStatus(RoomStatus.valueOf(c.getString(status)));
//                room.images =

                // add into rooms vector
                Rooms.add(room);

            } while (c.moveToNext());

            // 更新UI
            arrayAdapter.notifyDataSetChanged();
            room_list.invalidateViews();
            c.close();

        } else {
            Toast.makeText(this, "No data in the database", Toast.LENGTH_LONG).show();
        }
    }

    private void queryDataFromDatabase() {
        // 查询数据的代码...
        Rooms.clear();

        databaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        final Cursor c = db.rawQuery("SELECT * FROM Rooms ORDER BY room_no ASC", null);

        int room_id = c.getColumnIndex("room_id");
        int room_no = c.getColumnIndex("room_no");
        int price = c.getColumnIndex("price");
        int type = c.getColumnIndex("type");
        int floor_no = c.getColumnIndex("floor_no");
        int max_people = c.getColumnIndex("max_people");
        int descibe = c.getColumnIndex("describe");
        int status = c.getColumnIndex("status");

        if (c.moveToFirst()) {
            do {
                // new a room and set the attribute
                Room room = new Room();
                room.setRoom_no(c.getString(room_no));
                room.setPrice(c.getString(price));
                room.setType(c.getString(type));
                room.setFloor_no(c.getString(floor_no));
                room.setMax_people(c.getString(max_people));
                room.setDescribe(c.getString(descibe));
                room.setStatus(RoomStatus.valueOf(c.getString(status)));
//                room.images =

                // add into rooms vector
                Rooms.add(room);

            } while (c.moveToNext());

            // 更新UI
            arrayAdapter.notifyDataSetChanged();
            room_list.invalidateViews();
            c.close();

        } else {
            Toast.makeText(this, "No data in the database", Toast.LENGTH_LONG).show();
        }
    }
}

class RoomListAdapter extends ArrayAdapter<Room> {
    private LayoutInflater inflater;
    private Context context;
    TextView tv_room_no, tv_room_type;
    ImageView tv_room_status;

    public RoomListAdapter(Context context, List<Room> Rooms) {
        super(context, R.layout.room_list_items, Rooms);
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.room_list_items, parent, false);
        }

        final Room room = getItem(position);
        if (room != null) {
            tv_room_no = view.findViewById(R.id.room_no);
            tv_room_type = view.findViewById(R.id.room_type);
            tv_room_status = view.findViewById(R.id.room_status);

            tv_room_no.setText(room.getRoom_no());
            tv_room_type.setText(room.getType());

            // get status
            RoomStatus status = room.getStatus();
            tv_room_status.setImageResource(status.getImgId());
        }

        return view;
    }
}
