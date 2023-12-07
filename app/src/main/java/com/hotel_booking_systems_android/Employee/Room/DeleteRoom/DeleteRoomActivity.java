package com.hotel_booking_systems_android.Employee.Room.DeleteRoom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hotel_booking_systems_android.Employee.MyDatabaseHelper;
import com.hotel_booking_systems_android.Employee.Room.RoomDetailsActivity;
import com.hotel_booking_systems_android.R;

@SuppressWarnings("all")
public class DeleteRoomActivity extends AppCompatActivity {
    MyDatabaseHelper roomListDatabaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_room);

        // 获取传递的房间ID
        Intent intent = getIntent();
        String room_no = intent.getStringExtra("room_no");

        // 初始化数据库帮助类
        roomListDatabaseHelper = new MyDatabaseHelper(this);

        // back
        View btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DeleteRoomActivity.this, "back", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DeleteRoomActivity.this, RoomDetailsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        View btn_delete = findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteRoomActivity.this);
                builder.setMessage("Are you sure to delete the room?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 用户点击了"Yes"按钮，执行退出操作
                                SQLiteDatabase db = roomListDatabaseHelper.getWritableDatabase();

                                // 执行删除语句
                                String deleteQuery = "DELETE FROM Rooms WHERE room_no = ?";
                                db.execSQL(deleteQuery, new String[]{room_no});

                                // 提示用户删除成功
                                Toast.makeText(DeleteRoomActivity.this, "(yes) delete room", Toast.LENGTH_LONG).show();

                                // 去到成功界面
                                Intent intent = new Intent(DeleteRoomActivity.this, DeleteRoomSuccessfulActivity.class);
                                startActivity(intent);
                                finish(); // 关闭当前Activity
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 用户点击了"No"按钮，取消对话框，不执行任何操作
                                dialog.dismiss();
                                Toast.makeText(DeleteRoomActivity.this, "cancel delete room", Toast.LENGTH_LONG).show();

                                // 去到失败界面
                                Intent intent = new Intent(DeleteRoomActivity.this, DeleteRoomFailedActivity.class);
                                startActivity(intent);
                                finish(); // 关闭当前Activity
                            }
                        });
                // 创建并显示对话框
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
}
