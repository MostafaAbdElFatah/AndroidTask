package com.mostafa.fci.androidtask.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class User {



    @PrimaryKey
    @NonNull
    @ColumnInfo
    private String email;

    @NonNull
    @ColumnInfo
    private String username;

    @NonNull
    @ColumnInfo
    private String pass;

    public User() {
    }

    public User(String email, String name, String pass) {
        this.email = email;
        this.username = name;
        this.pass = pass;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
