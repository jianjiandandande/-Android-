package com.gaowenxing.keshe.activity.zhao;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.gaowenxing.keshe.Data;
import com.gaowenxing.keshe.R;
import com.gaowenxing.keshe.adapter.choose.ChooseAdapter;
import com.gaowenxing.keshe.adapter.choose.ChooseViewHolder;
import com.gaowenxing.keshe.adapter.chooseResult.ChooseResultAdapter;
import com.gaowenxing.keshe.util.MySqlOpenHelper;
import com.gaowenxing.keshe.util.Operate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 2017/6/10.
 */


/**
 * 选课系统
 */
public class ChooseCourseActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView title, finish;

    private RecyclerView mChoose, mChoose_result;

    private ChooseAdapter mAdapter_choose;
    private ChooseResultAdapter mAdapter_choose_result;

    private List<Object[]> choose_datas, choose_result_datas;

    private String Cno, Tno, Sno;

    private MySqlOpenHelper mHelper;

    private Operate mOperate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_course_layout);

        Intent intent = getIntent();
        Sno = intent.getStringExtra("Sno");

        if (mHelper == null) {
            mHelper = MySqlOpenHelper.newInstance(this);
        }
        if (mOperate == null) {
            mOperate = new Operate(this, mHelper);
        }

        title = (TextView) this.findViewById(R.id.title);
        title.setText("选课系统");
        finish = (TextView) this.findViewById(R.id.choose_ok);
        finish.setOnClickListener(this);//设置监听，使得点击事件生效
        finish.setVisibility(View.VISIBLE);

        mChoose = (RecyclerView) this.findViewById(R.id.choose_recycle);
        mChoose_result = (RecyclerView) this.findViewById(R.id.choose_result_recycle);

        LinearLayoutManager layoutManager_choose = new LinearLayoutManager(this);
        LinearLayoutManager layoutManager_choose_result = new LinearLayoutManager(this);

        mChoose.setLayoutManager(layoutManager_choose);
        mChoose_result.setLayoutManager(layoutManager_choose_result);

        choose_datas = new ArrayList<>();
        choose_result_datas = new ArrayList<>();

        init_choose_datas();
        init_choose_result_datas();

        mAdapter_choose = new ChooseAdapter(choose_datas);
        mAdapter_choose_result = new ChooseResultAdapter(choose_result_datas);

        mChoose.setAdapter(mAdapter_choose);
        mChoose_result.setAdapter(mAdapter_choose_result);

    }

    private void init_choose_datas() {

        Cursor cursor = mOperate.selectData("TC", "*", null);

        if (cursor.moveToFirst()) {
            do {
                Object[] object = new Object[4];
                object[0] = cursor.getString(cursor.getColumnIndex("Cno"));
                object[2] = cursor.getString(cursor.getColumnIndex("Tno"));
                Cursor cursor_course = mOperate.selectData("Course", "Cname", "Cno = " + "'" + object[0] + "'");
                if (cursor_course.moveToFirst()) {
                    do {
                        object[1] = cursor_course.getString(cursor_course.getColumnIndex("Cname"));

                    } while (cursor_course.moveToNext());

                } else {
                    Toast.makeText(this, "选课状态异常!", Toast.LENGTH_SHORT).show();
                }
                Cursor cursor_teacher = mOperate.selectData("Teacher", "Tname", "Tno = " + "'" + object[2] + "'");
                if (cursor_teacher.moveToFirst()) {
                    do {
                        object[3] = cursor_teacher.getString(cursor_teacher.getColumnIndex("Tname"));
                    } while (cursor_teacher.moveToNext());
                } else {
                    Toast.makeText(this, "选课状态异常", Toast.LENGTH_SHORT).show();
                }

                choose_datas.add(object);
            } while (cursor.moveToNext());
        }
    }

    private void init_choose_result_datas() {

        Cursor cursor = mOperate.selectData("SC", "Cno,Tno,Grade", "Sno = " + "'" + Sno + "'");
        if (cursor.moveToFirst()) {
            do {
                Object[] object = new Object[3];
                Cno = cursor.getString(cursor.getColumnIndex("Cno"));
                Tno = cursor.getString(cursor.getColumnIndex("Tno"));
                Cursor cursor_course = mOperate.selectData("Course", "Cname,Ccredit", "Cno = " + "'" + Cno + "'");
                if (cursor_course.moveToFirst()) {
                    do {
                        object[0] = cursor_course.getString(cursor_course.getColumnIndex("Cname"));
                        object[2] = cursor_course.getString(cursor_course.getColumnIndex("Ccredit"));
                    } while (cursor_course.moveToNext());

                } else {
                    Toast.makeText(this, "选课状态异常!", Toast.LENGTH_SHORT).show();
                }
                Cursor cursor_teacher = mOperate.selectData("Teacher", "Tname", "Tno = " + "'" + Tno + "'");
                if (cursor_teacher.moveToFirst()) {
                    do {
                        object[1] = cursor_teacher.getString(cursor_teacher.getColumnIndex("Tname"));
                    } while (cursor_teacher.moveToNext());
                } else {
                    Toast.makeText(this, "选课状态异常", Toast.LENGTH_SHORT).show();
                }
                choose_result_datas.add(object);
            } while (cursor.moveToNext());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_ok:

                String text = finish.getText().toString();
                List<ChooseViewHolder> list = mAdapter_choose.getHolders();
                if (text.equals("编辑")) {
                    setVisible(list);

                    finish.setText("完成");
                } else if (text.equals("完成")) {
                    check(list);
                    mAdapter_choose_result.notifyDataSetChanged();
                } else {

                }
                break;
        }
    }

    private void check(List<ChooseViewHolder> list) {

        int count = 0;

        Object[] values = new Object[4];
        for (ChooseViewHolder viewHolder : list) {
            //获取每一行的CheckBox（view），检测它是否被选中
            if (((CheckBox) viewHolder.getView(R.id.choose)).isChecked()) {

                count++;

                values[0] = Sno;
                values[1] = viewHolder.getText(R.id.course_no);//获取课程号的那内容(文字)
                values[2] = viewHolder.getText(R.id.teacher_no);
                values[3] = 0;
                if (queryFroExist(values) == false) {
                    mOperate.addData(Data.INSERT_SC, "SC", values);//添加数据
                    Toast.makeText(this, "选课成功!", Toast.LENGTH_SHORT).show();
                    update();
                } else {
                    Toast.makeText(this, "不能重复进行选课!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (count == 0) {
            Toast.makeText(this, "您还未你选择任何课程!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 把每一行的CheckBox显示出来
     *
     * @param list
     */

    private void setVisible(List<ChooseViewHolder> list) {

        for (ChooseViewHolder viewHolder : list) {

            ((CheckBox) viewHolder.getView(R.id.choose)).setVisibility(View.VISIBLE);
        }

    }

    /**
     * 查询SC表中是否存在选课结果
     */
    private boolean queryFroExist(Object[] values) {

        boolean isExist = false;
        Cursor cursor = mOperate.selectData("SC", "*",
                "Tno = " + "'" + values[2] + "'" +
                        "AND" + " Sno=" + "'" + Sno + "'" +
                        "AND" + " Cno=" + "'" + values[1] + "'");
        if (cursor.getCount() == 0) {
            isExist = false;
        } else {
            isExist = true;
        }
        return isExist;
    }

    private void update() {

        if (choose_result_datas.size() > 0) {
            choose_result_datas.clear();
            init_choose_result_datas();
            mAdapter_choose_result = new ChooseResultAdapter(choose_result_datas);
            mChoose_result.setAdapter(mAdapter_choose_result);
        }
    }
}

