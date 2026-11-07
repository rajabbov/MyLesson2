package com.example.mylesson2.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class MyDbManager(val context: Context) {

    private lateinit var sqLiteHelper: MySQLiteHelper
    private lateinit var database: SQLiteDatabase

    fun onCreate() {
        sqLiteHelper = MySQLiteHelper(context)
        database = sqLiteHelper.writableDatabase
    }

    fun insert(contactModel: ContactModel) {
        val contentValues = ContentValues()
        contentValues.put(MySQLiteHelper.name, contactModel.name)
        contentValues.put(MySQLiteHelper.age, contactModel.age)
        contentValues.put(MySQLiteHelper.address, contactModel.address)
        contentValues.put(MySQLiteHelper.phone, contactModel.phoneNumber)
        database.insert(MySQLiteHelper.TableName, null, contentValues)
    }

    fun fetch(): Cursor? {
        val cursor = database.query(
            MySQLiteHelper.TableName,
            arrayOf(
                MySQLiteHelper.id,
                MySQLiteHelper.name,
                MySQLiteHelper.age,
                MySQLiteHelper.address,
                MySQLiteHelper.phone
            ),
            null,
            null,
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            return cursor

        } else {
            return null
        }
    }

    fun update(contactModel: ContactModel): Int {
        val contentValues = ContentValues()
        contentValues.put(MySQLiteHelper.name, contactModel.name)
        contentValues.put(MySQLiteHelper.age, contactModel.age)
        contentValues.put(MySQLiteHelper.address, contactModel.address)
        contentValues.put(MySQLiteHelper.phone, contactModel.phoneNumber)
        return database.update(
            MySQLiteHelper.TableName,
            contentValues,
            "${MySQLiteHelper.id} = ${contactModel.id}",
            null
        )
    }
    fun delete(id: Int){
        database.delete(MySQLiteHelper.TableName,"${MySQLiteHelper.id} = $id",null)
    }
}