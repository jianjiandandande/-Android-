package com.gaowenxing.keshe.activity.sun;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
public class ShowCcreditActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView title;
    private String Sno,Cno,Tno;

    private RecyclerView mRecyclerView,condition_recycleView;

    private List<Object[]> datas,condition_datas;

    private MySqlOpenHelper mHelper;

    private Operate mOperate;

    private ShowCreditAdapter adapter,condition_adapter;

    private EditText edit_condition;

    private Button btn_query;

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

        //初始化RecycleView

        mRecyclerView = (RecyclerView) this.findViewById(R.id.credit_recycle);
        condition_recycleView = (RecyclerView) this.findViewById(R.id.recycle_select);


        edit_condition = (EditText) this.findViewById(R.id.edit_select);

        //初始化按钮，并设置监听
        btn_query = (Button) this.findViewById(R.id.btn_select);
        btn_query.setOnClickListener(this);


        //初始化List集合，用来存储从数据库中获取到的数据
        if (datas==null){
            datas = new ArrayList<>();
        }

        if (condition_datas==null){
            condition_datas = new ArrayList<>();
        }

        //设置RecycleView的布局排列(不懂没关系，到时候不用说)
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager1);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        condition_recycleView.setLayoutManager(layoutManager2);

        initData();//给Data填充数据--上边的那个大查询的数据

        adapter = new ShowCreditAdapter(datas);//初始化适配器

        mRecyclerView.setAdapter(adapter);//绑定

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


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_select:

                Object[] object = new Object[3];//这个数组用于填充按条件查询的List集合，即 condition_datas

                String course_name = edit_condition.getText().toString();



                Cursor cursor_cno = mOperate.selectData("Course","Cno","Cname = "+ "'"+course_name+"'");

                if (cursor_cno.getCount()==0){
                    //如果课程名不存在，就将数组的每个值都赋为null
                    object[0] = "null";
                    object[1] = "null";
                    object[2] = "null";
                    Toast.makeText(this, "请检查课程名!", Toast.LENGTH_SHORT).show();
                }else {

                    object[0] = course_name;//课程名

                    if (cursor_cno.moveToFirst()) {
                        do {

                            Cno = cursor_cno.getString(cursor_cno.getColumnIndex("Cno"));

                        } while (cursor_cno.moveToNext());
                    }

                    Cursor cursor_grade = mOperate.selectData("SC", "Tno,Grade", "Sno = " + "'" + Sno + "' and " + "Cno = " + "'" + Cno + "'");
                    if (cursor_grade.moveToFirst()) {
                        do {

                            Tno = cursor_grade.getString(cursor_grade.getColumnIndex("Tno"));
                            object[2] = cursor_grade.getString(cursor_grade.getColumnIndex("Grade"));//成绩

                        } while (cursor_grade.moveToNext());
                    }

                    Cursor cursor_tname = mOperate.selectData("Teacher", "Tname", "Tno = " + "'" + Tno + "'");
                    if (cursor_tname.moveToFirst()) {
                        do {

                            object[1] = cursor_tname.getString(cursor_tname.getColumnIndex("Tname"));//教师名

                        } while (cursor_tname.moveToNext());
                    }
                    
                }
                if (condition_datas.size() > 0) {
                     condition_datas.clear();//如果你用这个查询了好几次，那么这个集合中的数据会
                     // 被累加起来，但我们按条件查询，最终的成绩只有一个，所以在每次查询到新数据时，
                     // 要把之前的旧数据清除掉
                }
             
                condition_datas.add(object);//加入新数据
                condition_adapter = new ShowCreditAdapter(condition_datas);//初始化适配器
                condition_recycleView.setAdapter(condition_adapter);//绑定
                break;
    }
}
