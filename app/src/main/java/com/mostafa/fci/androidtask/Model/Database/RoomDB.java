package com.mostafa.fci.androidtask.Model.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Movie;

import com.mostafa.fci.androidtask.Model.User;
import java.util.List;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends android.arch.persistence.room.RoomDatabase {

    abstract UserDao userDao();
    private static RoomDB mRoomManager;

    private static RoomDB getDatabase(final Context context) {
        if (mRoomManager == null) {
            synchronized (RoomDB.class) {
                if (mRoomManager == null) {
                    mRoomManager = Room.databaseBuilder(context,
                            RoomDB.class, "users_database")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return mRoomManager;
    }
    ///

  public static class RoomManager {
        private Context context;

        public RoomManager(Context context){
            this.context = context;
            mRoomManager = getDatabase(context);
        }

         public void saveUser(User user) {
             mRoomManager.userDao().saveUser(user);
         }

         public void saveUsers(List<User> userList) {
             mRoomManager.userDao().saveUsers(userList);
         }

         public User getUser(String id) {
             return mRoomManager.userDao().getUser(id);
         }

         public List<User> getUsers() {
             return mRoomManager.userDao().getUsers();
         }

         public boolean isHasRows() {
             List<User> movies = mRoomManager.userDao().getUsers();
             if (movies == null)
                 return false;
             else if (movies.size() <= 0)
                 return false;
             else
                 return true;
         }

         public void updateUser(User user) {
             mRoomManager.userDao().updateUser(user);
         }

         public void deleteUser(User user) {
             mRoomManager.userDao().deleteUser(user);
         }

         public void deleteAllUsers() {
             mRoomManager.userDao().deleteAllUsers();
         }
    }
}