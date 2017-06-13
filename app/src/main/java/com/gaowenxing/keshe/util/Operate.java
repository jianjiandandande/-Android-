package com.gaowenxing.keshe.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.gaowenxing.keshe.Data;

import static android.content.ContentValues.TAG;

/**
 * Created by lx on 2017/6/4.
 */

public class Operate {

    private Context mContext;
    private SQLiteDatabase mDatabase;


    public Operate(Context context, MySqlOpenHelper helper) {
        mContext = context;
        mDatabase = helper.getWritableDatabase();
    }

    /**
     * 添加数据
     */
    public void addData(String insertName, String table, Object[] values) {
        String insert_values = null;
        if (table.equals("Student")) {
            insert_values = " values(?,?,?,?,?)";
        } else if (table.equals("SC") ||table.equals("Course")) {
            insert_values = " values(?,?,?,?)";
        } else if (table.equals("User")) {
            insert_values = " values(?,?,?)";
        } else if (table.equals("Teacher")) {
            insert_values = " values(?,?,?,?)";
        } else if (table.equals("TC")) {
            insert_values = " values(?,?)";
        }
        String INSERT = "insert into " + table + " " + insertName + " " +
                insert_values;
        mDatabase.execSQL(INSERT, values);
    }

    /**
     * 删除数据
     */

    public void deleteData(String table, String condition) {

        String DELETE = "delete from " + table + " where " + condition;
        mDatabase.execSQL(DELETE);
    }

    /**
     * 更新数据
     */

    public void updateData(String table, String values, String condition) {
        String UPDATE;
        if (condition == null) {
            UPDATE = "update " + table + " set " + values;
        }

        UPDATE = "update " + table + " set " + values + " where " + condition;
        Log.d(TAG, "updateData: "+UPDATE);
         mDatabase.execSQL(UPDATE);
    }

    /**
     * 查询数据
     */

    public Cursor selectData(String table, String values, String condition) {
        Cursor cursor;
        String SELECT;
        if (condition == null) {
            SELECT = "select " + values + " from " + table;
        } else {
            SELECT = "select " + values + " from " + table + " where " + condition;
        }
        Log.d(TAG, "selectData: -------"+SELECT);
        cursor = mDatabase.rawQuery(SELECT, null);
        return cursor;
    }

    /**
     * 创建表
     *
     * @param helper
     */
    public void createTable(MySqlOpenHelper helper) {
        helper.getWritableDatabase();
    }

}
