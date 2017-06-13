package com.gaowenxing.keshe.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.gaowenxing.keshe.Data;

/**
 * Created by lx on 2017/6/3.
 */

public class MySqlOpenHelper extends SQLiteOpenHelper {

    private Context mContext;

    private static MySqlOpenHelper mHelper ;

    public static int version=1;

    public static MySqlOpenHelper newInstance(Context context){
        mHelper = new MySqlOpenHelper(context,"XSCJ.db",null,version);
        return mHelper;
    }

    private static String CREATE_STUDENT = "create table Student (" +
            Data.Sno+" primary key NOT NULL," +
            Data.Sname+" NOT NULL ,"+
            Data.Sdate+" NOT NULL,"+
            Data.Ssex+" NOT NULL CHECK(Ssex IN('男','女')) default('男'),"+
            Data.Sdept+" NOT NULL )";

    private static String CREATE_COURSE = "create table Course (" +
            Data.Cno+" primary key NOT NULL ," +
            Data.Cname+" NOT NULL ,"+
            Data.Cpno+" NULL ,"+
            Data.Ccredit+" NOT NULL )";

    private static String CREATE_SC = "create table SC (" +
            Data.Sno+" NOT NULL,"+
            Data.Cno+" NOT NULL,"+
            Data.Tno+"NOT NULL,"+
            Data.Grade+","+
            " PRIMARY KEY(Cno,Sno,Tno)," +
            " FOREIGN KEY (Sno) REFERENCES Student(Sno)," +
            " FOREIGN KEY (Tno) REFERENCES Teacher(Tno)," +
            " FOREIGN KEY (Cno) REFERENCES Course(Cno))";

    private static String CREATE_TEACHER = "create table Teacher (" +
            Data.Tno+" primary key NOT NULL," +
            Data.Tname+" NOT NULL," +
            Data.Tsex+" NOT NULL CHECK(Tsex IN('男','女')) default('男')," +
            Data.Ttel+" NOT NULL )";

    private static String CREATE_USER = "create table User (" +
            Data.Uname + " primary key," +
            Data.Uclass + "NOT NULL," +
            Data.Upassword + " NOT NULL )";

    private static String CREATE_TC = "create table TC (" +
            Data.Tno+" NOT NULL," +
            Data.Cno+"NOT NULL," +
            " FOREIGN KEY (Tno) REFERENCES Teacher(Tno)," +
            " FOREIGN KEY (Cno) REFERENCES Course(Cno))";





    private MySqlOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENT);
        db.execSQL(CREATE_COURSE);
        db.execSQL(CREATE_SC);
        db.execSQL(CREATE_TEACHER);
        db.execSQL(CREATE_TC);
        db.execSQL(CREATE_USER);
        Toast.makeText(mContext, "创建表成功!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Student");
        db.execSQL("drop table if exists Course");
        db.execSQL("drop table if exists SC");
        db.execSQL("drop table if exists Teacher");
        db.execSQL("drop table if exists TC");
        db.execSQL("drop table if exists User");
        onCreate(db);
    }

}
