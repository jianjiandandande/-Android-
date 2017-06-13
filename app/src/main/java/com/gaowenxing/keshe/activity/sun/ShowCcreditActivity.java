package com.gaowenxing.keshe.activity.sun;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.gaowenxing.keshe.R;
import com.gaowenxing.keshe.adapter.showCredit.ShowCreditAdapter;
import com.gaowenxing.keshe.util.MySqlOpenHelper;
import com.gaowenxing.keshe.util.Operate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 2017/6/10.
 */


/**
 * 成绩查询
 */
public class ShowCcreditActivity extends AppCompatActivity {

    private TextView title;
    private String Sno,Cno,Tno;

    private RecyclerView mRecyclerView;

    private List<Object[]> datas;

    private MySqlOpenHelper mHelper;

    private Operate mOperate;

    private ShowCreditAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_credit);

        Intent intent = getIntent();
        Sno = intent.getStringExtra("Sno");

        title = (TextView) this.findViewById(R.id.title);
        title.setText("成绩查询");
        if (mHelper == null) {
            mHelper = MySqlOpenHelper.newInstance(this);
        }
        if (mOperate == null) {
            mOperate = new Operate(this, mHelper);
        }

        mRecyclerView = (RecyclerView) this.findViewById(R.id.credit_recycle);

        if (datas==null){
            datas = new ArrayList<>();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(layoutManager);

        Log.d("", "onCreate: chengjichaxun"+"----");
        initData();
        for (Object[] data : datas) {
            Log.d("", "onCreate: chengjichaxun"+data);
        }

        adapter = new ShowCreditAdapter(datas);

        mRecyclerView.setAdapter(adapter);

    }

    private void initData() {

        Cursor cursor = mOperate.selectData("SC", "Cno,Tno,Grade", "Sno = "+"'"+Sno+"'");
        if (cursor.moveToFirst()) {
            do {
                Object[] object = new Object[3];
                Cno = cursor.getString(cursor.getColumnIndex("Cno"));
                Tno = cursor.getString(cursor.getColumnIndex("Tno"));
                object[2] = cursor.getString(cursor.getColumnIndex("Grade"));
                Cursor cursor_course = mOperate.selectData("Course", "Cname", "Cno = " + "'" + Cno + "'");
                if (cursor_course.moveToFirst()) {
                    do {
                        object[0] = cursor_course.getString(cursor_course.getColumnIndex("Cname"));
                    } while (cursor_course.moveToNext());

                } else {
                    Toast.makeText(this, "选课状态异常!", Toast.LENGTH_SHORT).show();
                }
                Cursor cursor_teacher = mOperate.selectData("Teacher", "Tname", "Tno = "+"'"+Tno+"'");
                if (cursor_teacher.moveToFirst()){
                    do {
                        object[1] = cursor_teacher.getString(cursor_teacher.getColumnIndex("Tname"));
                    }while(cursor_teacher.moveToNext());
                }else{
                    Toast.makeText(this, "选课状态异常", Toast.LENGTH_SHORT).show();
                }
                datas.add(object);
            } while (cursor.moveToNext());
        }else{
            Toast.makeText(this, "获取数据异常", Toast.LENGTH_SHORT).show();
        }
    }
}
