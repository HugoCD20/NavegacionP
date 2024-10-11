package com.example.navegacion

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class OrganizatDatabase : RoomDatabase() {
    abstract fun userDao(): DAOdb
}


