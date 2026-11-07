package com.example.mylesson2.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MySQLiteHelper(context: Context):SQLiteOpenHelper(context,"mydb.db",null,2) {


    companion object{
        const val TableName = "People"
        const val id = "_id"
        const val name = "name"
        const val age = "age"
        const val address = "address"
        const val phone = "phone"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table $TableName ($id integer primary key autoincrement, $name text,$age integer, $address text, $phone text);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("drop table if exists $TableName")
        onCreate(db)
    }
}