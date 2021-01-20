package com.example.contact.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class},version = 1,exportSchema = false)
public abstract class ContactDB extends RoomDatabase {
    //Create database instance
    private static ContactDB database;

    //Define database name
    private static String DB_NAME = "contact_db";

    public static synchronized ContactDB getInstance(Context context){

        if(database == null){
            database = Room.databaseBuilder(context.getApplicationContext()
                    , ContactDB.class,DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
    //Create Dao
    public abstract ContactDao contactDao();

}
