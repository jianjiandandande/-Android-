package com.gaowenxing.keshe.activity.su;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gaowenxing.keshe.Data;
import com.gaowenxing.keshe.R;
import com.gaowenxing.keshe.util.MySqlOpenHelper;
import com.gaowenxing.keshe.util.Operate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 2017/6/8.
 */

/**
 * 更新个人信息，以及老师上传成绩
 */

public class UpdateInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private String intent_info, intent_choose,select_sno,select_cno;
    private TextView title;
    private EditText input1, input2, input3, input4;
    private List<EditText> edits;
    private Spinner spinner_sex;
    private List<String> sexs;
    private ArrayAdapter<String>  adapter_sex;
    private String input_sex;
    private LinearLayout linearLayout;
    private Button btn_ok;
    private String st1, st2, st3, st4;
    private MySqlOpenHelper mHelper;
    private Operate mOperate;
    private String values;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_layout);

        Intent intent = getIntent();
        intent_choose = intent.getStringExtra(Data.INTENT);
        intent_info = intent.getStringExtra(intent_choose);

        if (mHelper == null) {
            mHelper = MySqlOpenHelper.newInstance(this);
        }
        if (mOperate == null) {
            mOperate = new Operate(this, mHelper);
        }
        title = (TextView) this.findViewById(R.id.title);
        input1 = (EditText) this.findViewById(R.id.input1_update);
        input2 = (EditText) this.findViewById(R.id.input2_update);
        input3 = (EditText) this.findViewById(R.id.input3_update);
        input4 = (EditText) this.findViewById(R.id.input4_update);

        edits = new ArrayList<>();
        edits.add(input1);
        edits.add(input2);
        edits.add(input3);
        edits.add(input4);

        spinner_sex = (Spinner) this.findViewById(R.id.spinner_sex_update);
        initSpinner();

        linearLayout = (LinearLayout) this.findViewById(R.id.update_choose_sex);
        btn_ok = (Button) this.findViewById(R.id.update_data_ok);
        title.setText("更新数据" + "(" + getTableName() + ")");
        query_data();
        btn_ok.setOnClickListener(this);

    }

    /**
     * 初始化Spinner
     */
    private void initSpinner() {
        sexs = new ArrayList<>();
        sexs.add("男");
        sexs.add("女");
        adapter_sex = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sexs);
        adapter_sex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sex.setAdapter(adapter_sex);
        spinner_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                input_sex = sexs.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        if (intent_choose.equals("Sno")) {
            st1 = input1.getText().toString();
            st2 = input2.getText().toString();
            st3 = input3.getText().toString();
            st4 = input4.getText().toString();
            if (st1.equals("") || st2.equals("") || st3.equals("")) {
                Toast.makeText(this, "请完善信息!", Toast.LENGTH_SHORT).show();
            } else {
                if (intent_choose.equals("Sno") && st1.equals(intent_info)) {
                    values = "Sname = " +"'"+ st2 +"'"+ "," + "Sdate =" +"'"+ st3+"'" + "," + "Sdept = " +"'"+st4+"'"+","+"Ssex = "+"'"+input_sex+"'";
                    mOperate.updateData("Student", values, "Sno = " + "'" + intent_info + "'");
                    Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();
                } else {
                    input1.setText(intent_info);
                    Toast.makeText(this, "学号只能由管理员来更改!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (intent_choose.equals("Tno")) {
            st1 = input1.getText().toString();
            st2 = input2.getText().toString();
            st3 = input3.getText().toString();
            if (st1.equals("")) {
                Toast.makeText(this, "主码不能为空", Toast.LENGTH_SHORT).show();
            } else if (st2.equals("") || st3.equals("")) {
                Toast.makeText(this, "请完善信息!", Toast.LENGTH_SHORT).show();
            } else {
                if (st1.equals(intent_info)) {
                    values ="Tname = "+"'"+ st2+"'"+","+"Ttel = "+"'"+st3+"'"+","+"Tsex = "+"'"+input_sex+"'";
                    mOperate.updateData("Teacher", values, "Tno = " + "'" + intent_info + "'");
                    Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "教师号只能由管理员来更改!", Toast.LENGTH_SHORT).show();
                    input1.setText(intent_info);
                }
            }
        }else if(intent_choose.equals("Uname")) {
            st1 = input1.getText().toString();
            st2 = input2.getText().toString();
            if ((!st1.equals("")) && (!st2.equals(""))) {
                if (st1.equals(st2)) {
                    mOperate.updateData("User", "Upassword = " + st1, "Uname =" + intent_info);
                    Toast.makeText(this, "更改密码成功!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "两次密码输入不相同", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "请完善信息", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void query_data() {
        if (intent_choose.equals(Data.CHANGE_STUDENT_INFO)) {
            Cursor cursor = mOperate.selectData("Student", "*", "Sno=" + "'" + intent_info + "'");
            if (cursor.moveToFirst()) {
                do {
                    input1.setText(cursor.getString(cursor.getColumnIndex("Sno")));
                    input2.setText(cursor.getString(cursor.getColumnIndex("Sname")));
                    input3.setText(cursor.getString(cursor.getColumnIndex("Sdate")));
                    input4.setText(cursor.getString(cursor.getColumnIndex("Sdept")));

                } while (cursor.moveToNext());
            }
        } else if (intent_choose.equals(Data.CHANGE_teacher_INFO)) {

            Cursor cursor = mOperate.selectData("Teacher", "*", "Tno=" + "'" + intent_info + "'");
            if (cursor.moveToFirst()) {
                do {
                    input1.setText(cursor.getString(cursor.getColumnIndex("Tno")));
                    input2.setText(cursor.getString(cursor.getColumnIndex("Tname")));
                    input3.setText(cursor.getString(cursor.getColumnIndex("Ttel")));

                } while (cursor.moveToNext());
            }

        }else if (intent_choose.equals("SC")){

        }
    }

    /**
     * 获取表名,并更改布局
     *
     * @return
     */
    private String getTableName() {

        String tableName = null;

        if (intent_choose.equals(Data.CHANGE_STUDENT_INFO)) {
            tableName = "Student";
            changeLayout_student();
        } else if (intent_choose.equals(Data.CHANGE_teacher_INFO)) {
            tableName = "Teacher";
            changeLayout_teacher();
        } else if (intent_choose.equals(Data.CHANGE_USER_INFO)) {
            tableName = "User";
            changeLayout_user();
        }
        return tableName;
    }


    /**
     * 设置EditText的显示
     */
    private void changeVisible() {
        for (EditText editText : edits) {
            if (editText.getVisibility() == View.GONE) {
                editText.setVisibility(View.VISIBLE);
            }
        }
    }

    private void changeLayout_teacher() {
        changeVisible();
        if (linearLayout.getVisibility() == View.GONE) {
            linearLayout.setVisibility(View.VISIBLE);
        }
        input1.setHint("请输入教师号");
        input2.setHint("请输入姓名");
        input3.setHint("请输入联系方式");
        input4.setVisibility(View.GONE);
    }

    private void changeLayout_student() {
        if (linearLayout.getVisibility() == View.GONE) {
            linearLayout.setVisibility(View.VISIBLE);
        }
        changeVisible();
        input1.setHint("请输入学号");
        input2.setHint("请输入姓名");
        input3.setHint("请输入出生年份");
        input4.setHint("请输入专业");
    }

    private void changeLayout_user() {

        input1.setHint("请输入密码");
        input2.setHint("请重新输入密码");
        input3.setVisibility(View.GONE);
        input4.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
    }

}
