package com.gaowenxing.keshe.activity.sun;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gaowenxing.keshe.R;
import com.gaowenxing.keshe.adapter.manager_update.ManagerUpdateAdapter;
import com.gaowenxing.keshe.adapter.manager_update.ManagerUpdateViewHolder;
import com.gaowenxing.keshe.util.MySqlOpenHelper;
import com.gaowenxing.keshe.util.Operate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 2017/6/11.
 */

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView title;

    private List<Object[]> datas;

    private ManagerUpdateAdapter mAdapter;

    private Spinner mSpinner;
    private ArrayAdapter<String> adapter_class;

    private MySqlOpenHelper mHelper;
    private Operate mOperate;

    private RecyclerView mRecyclerView;

    private List<TextView> texts;
    private List<String> table;

    private String tableClass = "Student", intent_info;

    private TextView mTextView1, mTextView2, mTextView3, mTextView4, mTextView5, finish;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_update);

        title = (TextView) this.findViewById(R.id.title);

        title.setText("更新数据");

        finish = (TextView) this.findViewById(R.id.choose_ok);
        finish.setVisibility(View.VISIBLE);

        finish.setOnClickListener(this);

        initTextView();

        datas = new ArrayList<>();

        if (mHelper == null) {
            mHelper = MySqlOpenHelper.newInstance(this);
        }
        if (mOperate == null) {
            mOperate = new Operate(this, mHelper);
        }

        mSpinner = (Spinner) this.findViewById(R.id.update_spinner);

        initSpinner();

        mRecyclerView = (RecyclerView) this.findViewById(R.id.update_recycle);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(layoutManager);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tableClass = table.get(position);
                update();//刷新数据
                if (tableClass.equals("Student")) {
                    changeLayout_student();
                } else if (tableClass.equals("Course")) {
                    changeLayout_course();
                } else if (tableClass.equals("SC")) {
                    changeLayout_sc();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void update() {

        if (datas.size() > 0) {

            datas.clear();
        }
        initDatas();
        mAdapter = new ManagerUpdateAdapter(datas, tableClass);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void changeLayout_sc() {
        changeVisible();
        mTextView5.setVisibility(View.GONE);
        mTextView1.setText("学号");
        mTextView2.setText("课程号");
        mTextView3.setText("教师号");
        mTextView4.setText("成绩");
    }

    private void changeLayout_course() {
        changeVisible();
        mTextView5.setVisibility(View.GONE);
        mTextView1.setText("课程号");
        mTextView2.setText("课程名");
        mTextView3.setText("先行课");
        mTextView4.setText("学分");
    }

    private void changeLayout_student() {
        changeVisible();
        mTextView1.setText("学号");
        mTextView2.setText("姓名");
        mTextView3.setText("性别");
        mTextView4.setText("出生年份");
        mTextView5.setText("专业");
    }

    private void initDatas() {

        if (tableClass.equals("Student")) {
            query_Student();
        } else if (tableClass.equals("Course")) {
            query_course();
        } else if (tableClass.equals("SC")) {
            query_sc();
        }
    }


    private void query_sc() {

        Cursor cursor = mOperate.selectData("SC", "*", null);
        if (cursor.moveToFirst()) {
            do {

                Object[] object = new Object[4];

                object[0] = cursor.getString(cursor.getColumnIndex("Sno"));
                object[1] = cursor.getString(cursor.getColumnIndex("Cno"));
                object[2] = cursor.getString(cursor.getColumnIndex("Tno"));
                object[3] = cursor.getString(cursor.getColumnIndex("Grade"));

                datas.add(object);
            } while (cursor.moveToNext());
        }

    }

    private void query_course() {

        Cursor cursor = mOperate.selectData("Course", "*", null);
        if (cursor.moveToFirst()) {
            do {

                Object[] object = new Object[4];

                object[0] = cursor.getString(cursor.getColumnIndex("Cno"));
                object[1] = cursor.getString(cursor.getColumnIndex("Cname"));
                object[2] = cursor.getString(cursor.getColumnIndex("Cpno"));
                object[3] = cursor.getString(cursor.getColumnIndex("Ccredit"));

                datas.add(object);
            } while (cursor.moveToNext());
        }

    }

    private void query_Student() {

        Cursor cursor = mOperate.selectData("Student", "*", null);
        if (cursor.moveToFirst()) {
            do {

                Object[] object = new Object[5];

                object[0] = cursor.getString(cursor.getColumnIndex("Sno"));
                object[1] = cursor.getString(cursor.getColumnIndex("Sname"));
                object[2] = cursor.getString(cursor.getColumnIndex("Ssex"));
                object[3] = cursor.getString(cursor.getColumnIndex("Sdate"));
                object[4] = cursor.getString(cursor.getColumnIndex("Sdept"));

                datas.add(object);
            } while (cursor.moveToNext());
        }
    }

    private void initSpinner() {

        table = new ArrayList<>();
        table.add("Student");
        table.add("Course");
        table.add("SC");

        adapter_class = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, table);

        //设置样式
        adapter_class.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //加载适配器
        mSpinner.setAdapter(adapter_class);
    }

    private void initTextView() {

        mTextView1 = (TextView) this.findViewById(R.id.edit_view1);
        mTextView2 = (TextView) this.findViewById(R.id.edit_view2);
        mTextView3 = (TextView) this.findViewById(R.id.edit_view3);
        mTextView4 = (TextView) this.findViewById(R.id.edit_view4);
        mTextView5 = (TextView) this.findViewById(R.id.edit_view5);
        texts = new ArrayList<>();
        texts.add(mTextView1);
        texts.add(mTextView2);
        texts.add(mTextView3);
        texts.add(mTextView4);
        texts.add(mTextView5);
    }

    private void changeVisible() {
        for (TextView textview : texts) {
            if (textview.getVisibility() == View.GONE) {
                textview.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.choose_ok:

                String text = finish.getText().toString();
                List<ManagerUpdateViewHolder> list = mAdapter.getHolders();
                if (text.equals("编辑")) {
                    setVisible(list);
                    finish.setText("完成");
                } else if (text.equals("完成")) {
                    check(list);
                    finish.setText("编辑");
                } else {

                }
        }
    }

    private void setVisible(List<ManagerUpdateViewHolder> list) {

        for (ManagerUpdateViewHolder viewHolder : list) {

            ((CheckBox) viewHolder.getView(R.id.edit_item_choose)).setVisibility(View.VISIBLE);
        }

    }

    private void check(List<ManagerUpdateViewHolder> list){

        int count = 0;

        Object[] object = new Object[5];


        for (ManagerUpdateViewHolder viewHolder : list) {
            if (((CheckBox) viewHolder.getView(R.id.edit_item_choose)).isChecked()) {

                count++;

                object[0] = viewHolder.getText(R.id.edit_item_view1);
                object[1] = viewHolder.getText(R.id.edit_item_view2);
                object[2] = viewHolder.getText(R.id.edit_item_view3);
                object[3] = viewHolder.getText(R.id.edit_item_view4);
                object[4] = viewHolder.getText(R.id.edit_item_view5);

                if (tableClass.equals("Student")) {

                    update_student(object);

                } else if (tableClass.equals("Course")) {

                    update_course(object);

                } else if (tableClass.equals("SC")) {

                    update_sc(object);

                }

            }
        }
        if (count == 0) {
            Toast.makeText(this, "您还未确认修改!", Toast.LENGTH_SHORT).show();
        }
    }

    private void update_sc(Object[] object) {

        String Sno = object[0].toString();
        String Cno = object[1].toString();
        String Tno = object[2].toString();
        String Grade = object[3].toString();
        mOperate.updateData("SC","Grade = "+Grade,"Sno = "+"'"+Sno+"' and "+
                "Cno ="+"'"+Cno+"' and "+"Tno ="+"'"+Tno+"'");
        Toast.makeText(this, "已修改", Toast.LENGTH_SHORT).show();

    }

    private void update_course(Object[] object) {

        String Cno = object[0].toString();
        String Cname = object[1].toString();
        String Cpno = object[2].toString();
        String Ccredit = object[3].toString();
        mOperate.updateData("Course","Cname = "+"'"+Cname+"',"+" Cpno = "+"'"+Cpno+"',"+
                " Ccredit = "+Ccredit,"Cno = "+"'"+Cno+"'");

        Toast.makeText(this, "已修改", Toast.LENGTH_SHORT).show();



    }

    private void update_student(Object[] object) {

        String Sno = object[0].toString();

        String Sdept = object[4].toString();

        mOperate.updateData("Student","Sdept = "+ "'"+Sdept+"'","Sno = "+"'"+Sno+"'");
        Toast.makeText(this, "已修改", Toast.LENGTH_SHORT).show();
    }
}
