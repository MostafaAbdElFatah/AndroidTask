package com.mostafa.fci.androidtask.Model.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.graphics.Movie;

import com.mostafa.fci.androidtask.Model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void saveUser(User user);

    @Insert
    void saveUsers(List<User> users);

    @Query("SELECT * FROM User WHERE email = :id")
    User getUser(String id);

    @Query ("SELECT * FROM User")
    List<User> getUsers();

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("DELETE FROM user")
    void deleteAllUsers();

}
