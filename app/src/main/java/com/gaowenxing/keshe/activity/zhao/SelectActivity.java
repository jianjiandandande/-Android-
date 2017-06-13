package com.gaowenxing.keshe.activity.zhao;

import android.content.Intent;
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

import com.gaowenxing.keshe.Data;
import com.gaowenxing.keshe.R;
import com.gaowenxing.keshe.adapter.manager_select.ManagerAdapter;
import com.gaowenxing.keshe.adapter.manager_select.ManagerViewHolder;
import com.gaowenxing.keshe.util.MySqlOpenHelper;
import com.gaowenxing.keshe.util.Operate;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lx on 2017/6/11.
 */

public class SelectActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinner_class;

    private TextView title;

    private MySqlOpenHelper mHelper;
    private Operate mOperate;

    private ManagerAdapter mAdapter;

    private ArrayAdapter<String> adapter_class;

    private RecyclerView mRecyclerView;

    private List<Object[]> datas;
    private List<String> table;

    private List<TextView> texts;

    private String tableClass = "Student", intent_info;

    private TextView mTextView1, mTextView2, mTextView3, mTextView4, mTextView5, finish;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_layout);

        Intent intent = getIntent();
        intent_info = intent.getStringExtra(Data.INTENT);


        title = (TextView) this.findViewById(R.id.title);
        if (intent_info == null) {


            title.setText("查询数据");
        } else {
            title.setText("删除数据");

            finish = (TextView) this.findViewById(R.id.choose_ok);
            finish.setVisibility(View.VISIBLE);

            finish.setOnClickListener(this);
        }

        initTextView();

        datas = new ArrayList<>();

        if (mHelper == null) {
            mHelper = MySqlOpenHelper.newInstance(this);
        }
        if (mOperate == null) {
            mOperate = new Operate(this, mHelper);
        }

        spinner_class = (Spinner) this.findViewById(R.id.select_spinner);

        initSpinner();

        mRecyclerView = (RecyclerView) this.findViewById(R.id.select_recycle);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(layoutManager);

        spinner_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tableClass = table.get(position);
                update();//刷新数据
                if (tableClass.equals("Student")) {
                    changeLayout_student();
                } else if (tableClass.equals("Teacher")) {
                    changeLayout_teacher();
                } else if (tableClass.equals("Course")) {
                    changeLayout_course();
                } else if (tableClass.equals("SC")) {
                    changeLayout_sc();
                } else if (tableClass.equals("TC")) {
                    changeLayout_tc();
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
        mAdapter = new ManagerAdapter(datas, tableClass);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initTextView() {

        mTextView1 = (TextView) this.findViewById(R.id.text_view1);
        mTextView2 = (TextView) this.findViewById(R.id.text_view2);
        mTextView3 = (TextView) this.findViewById(R.id.text_view3);
        mTextView4 = (TextView) this.findViewById(R.id.text_view4);
        mTextView5 = (TextView) this.findViewById(R.id.text_view5);
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

    private void changeLayout_tc() {
        changeVisible();
        mTextView3.setVisibility(View.GONE);
        mTextView4.setVisibility(View.GONE);
        mTextView5.setVisibility(View.GONE);
        mTextView1.setText("教师号");
        mTextView2.setText("课程号");
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

    private void changeLayout_teacher() {
        changeVisible();
        mTextView5.setVisibility(View.GONE);
        mTextView1.setText("教师号");
        mTextView2.setText("姓名");
        mTextView3.setText("性别");
        mTextView4.setText("联系方式");
    }

    private void changeLayout_student() {
        changeVisible();
        mTextView1.setText("学号");
        mTextView2.setText("姓名");
        mTextView3.setText("性别");
        mTextView4.setText("出生年份");
        mTextView5.setText("专业");
    }

    private void initSpinner() {

        table = new ArrayList<>();
        table.add("Student");
        table.add("Teacher");
        table.add("Course");
        table.add("SC");
        table.add("TC");


        adapter_class = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, table);

        //设置样式
        adapter_class.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //加载适配器
        spinner_class.setAdapter(adapter_class);

    }

    private void initDatas() {

        if (tableClass.equals("Student")) {
            query_Student();
        } else if (tableClass.equals("Teacher")) {
            query_Teacher();
        } else if (tableClass.equals("Course")) {
            query_course();
        } else if (tableClass.equals("SC")) {
            query_sc();
        } else if (tableClass.equals("TC")) {
            query_tc();
        }

    }

    private void query_tc() {

        Cursor cursor = mOperate.selectData("TC", "*", null);
        if (cursor.moveToFirst()) {
            do {

                Object[] object = new Object[2];

                object[0] = cursor.getString(cursor.getColumnIndex("Tno"));
                object[1] = cursor.getString(cursor.getColumnIndex("Cno"));

                datas.add(object);
            } while (cursor.moveToNext());
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

    private void query_Teacher() {
        Cursor cursor = mOperate.selectData("Teacher", "*", null);
        if (cursor.moveToFirst()) {
            do {

                Object[] object = new Object[4];

                object[0] = cursor.getString(cursor.getColumnIndex("Tno"));
                object[1] = cursor.getString(cursor.getColumnIndex("Tname"));
                object[2] = cursor.getString(cursor.getColumnIndex("Tsex"));
                object[3] = cursor.getString(cursor.getColumnIndex("Ttel"));

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_ok:

                String text = finish.getText().toString();
                List<ManagerViewHolder> list = mAdapter.getHolders();
                if (text.equals("编辑")) {
                    setVisible(list);
                    finish.setText("完成");
                } else if (text.equals("完成")) {
                    check(list);
                    finish.setText("编辑");
                } else {

                }
                break;
        }
    }

    private void check(List<ManagerViewHolder> list) {

        int count = 0;

        Object[] object = new Object[5];


        for (ManagerViewHolder viewHolder : list) {
            if (((CheckBox) viewHolder.getView(R.id.text_item_choose)).isChecked()) {

                count++;

                object[0] = viewHolder.getText(R.id.text_item_view1);
                object[1] = viewHolder.getText(R.id.text_item_view2);
                object[2] = viewHolder.getText(R.id.text_item_view3);
                object[3] = viewHolder.getText(R.id.text_item_view4);
                object[4] = viewHolder.getText(R.id.text_item_view5);

                if (tableClass.equals("Student")) {

                    delete_student(object);
                } else if (tableClass.equals("Teacher")) {
                    delete_teacher(object);
                } else if (tableClass.equals("Course")) {
                    delete_course(object);
                } else if (tableClass.equals("SC")) {
                    delete_sc(object);
                } else if (tableClass.equals("TC")) {
                    delete_tc(object);
                }

            }
        }
        if (count == 0) {
            Toast.makeText(this, "您还未你选择要删除的对象!", Toast.LENGTH_SHORT).show();
        }
    }

    private void delete_student(Object[] object) {

        String Sno = object[0].toString();
        mOperate.deleteData("Student", "Sno = " + "'" + Sno + "'");
        if (queryFroExit("SC", "Sno = " + "'" + Sno + "'") == true) {
            mOperate.deleteData("SC", "Sno = " + "'" + Sno + "'");
        }

        Toast.makeText(this, "删除数据成功!", Toast.LENGTH_SHORT).show();
        update();
    }

    private void delete_teacher(Object[] object) {
        String Tno = object[0].toString();
        mOperate.deleteData("Teacher", "Tno = " + "'" + Tno + "'");
        if (queryFroExit("SC", "Tno = " + "'" + Tno + "'") == true) {
            mOperate.deleteData("SC", "Tno = " + "'" + Tno + "'");
        }
        if (queryFroExit("TC", "Tno = " + "'" + Tno + "'") == false) {
            mOperate.deleteData("TC", "Tno = " + "'" + Tno + "'");
        }
        Toast.makeText(this, "删除数据成功!", Toast.LENGTH_SHORT).show();
        update();

    }

    private void delete_course(Object[] object) {
        String Cno = object[0].toString();
        mOperate.deleteData("Course", "Cno = " + "'" + Cno + "'");
        if (queryFroExit("SC", "Cno = " + "'" + Cno + "'") == true) {
            mOperate.deleteData("SC", "Cno = " + "'" + Cno + "'");
        }
        if (queryFroExit("TC", "Cno = " + "'" + Cno + "'") == true) {
            mOperate.deleteData("TC", "Cno = " + "'" + Cno + "'");
        }
        Toast.makeText(this, "删除数据成功!", Toast.LENGTH_SHORT).show();
        update();
    }

    private void delete_sc(Object[] object) {
        String Sno = object[0].toString();
        String Cno = object[1].toString();
        String Tno = object[2].toString();
        mOperate.deleteData("SC", "Sno = " + "'" + Sno + "'" + " AND Cno = " + "'" + Cno + "'" + " AND Tno = " + "'" + Tno + "'");
        Toast.makeText(this, "删除数据成功!", Toast.LENGTH_SHORT).show();
        update();
    }

    private void delete_tc(Object[] object) {
        String Tno = object[0].toString();
        String Cno = object[1].toString();
        mOperate.deleteData("TC", "Tno = " + "'" + Tno + "'" + " AND Cno = " + "'" + Cno + "'");
        Toast.makeText(this, "删除数据成功!", Toast.LENGTH_SHORT).show();
        update();

    }

    private void setVisible(List<ManagerViewHolder> list) {

        for (ManagerViewHolder viewHolder : list) {

            ((CheckBox) viewHolder.getView(R.id.text_item_choose)).setVisibility(View.VISIBLE);
        }

    }

    private boolean queryFroExit(String table, String condition) {

        boolean isExit = false;

        Cursor cursor = mOperate.selectData(table, " * ", condition);

        if (cursor.getCount() == 0) {
            isExit = false;
        } else {
            isExit = true;
        }

        return false;
    }

}
