package com.hotel_booking_systems_android.room.manager;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.hotel_booking_systems_android.room.User.User;
import com.hotel_booking_systems_android.room.User.UserDao;
import com.hotel_booking_systems_android.room.User.UserDatabase;

import java.util.List;

public class DBEngine {

    private UserDao userDao;

    public DBEngine(Context context){
        UserDatabase userDatabase = UserDatabase.getInstance(context);
        userDao = userDatabase.getUserDao();
    }

    //Dao CRUD

    //insert
    public void insertUsers(User ...users){
        new InsertAsyncTask(userDao).execute(users);
    }

    public void updateUsers(User ...users){
        new UpdateAsyncTask(userDao).execute(users);
    }

    public void deleteUsers(User ...users){
        new DeleteAsyncTask(userDao).execute(users);
    }

    public List<User> queryAllUsers(){
        return userDao.getAllUsers();
    }

    public User getUser(String username, String password){
        return userDao.getUser(username,password);
    }

    static class InsertAsyncTask extends AsyncTask<User,Void,Void>{
        private UserDao userDao;
        public InsertAsyncTask(UserDao userDao){
            this.userDao = userDao;
        }
        @Override
        protected Void doInBackground(User... users) {
            userDao.insertUsers(users);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<User,Void,Void>{
        private UserDao userDao;
        public UpdateAsyncTask(UserDao userDao){
            this.userDao = userDao;
        }
        @Override
        protected Void doInBackground(User... users) {
            userDao.updateStudents(users);
            return null;
        }
    }

    static class DeleteAsyncTask extends AsyncTask<User,Void,Void>{
        private UserDao userDao;
        public DeleteAsyncTask(UserDao userDao){
            this.userDao = userDao;
        }
        @Override
        protected Void doInBackground(User... users) {
            userDao.deleteUsers(users);
            return null;
        }
    }

    static class QueryAllAsyncTask extends AsyncTask<Void,Void,Void>{
        private UserDao userDao;
        public QueryAllAsyncTask(UserDao userDao){
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<User> allUsers = userDao.getAllUsers();
            for (User user : allUsers){
                Log.e("DBEngine","doInBackground: Query all:"+user);
            }
            return null;
        }
    }

}
