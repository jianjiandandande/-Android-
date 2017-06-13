package com.gaowenxing.keshe.activity.gao;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gaowenxing.keshe.R;
import com.gaowenxing.keshe.activity.gao.other.ManagerActivity;
import com.gaowenxing.keshe.activity.gao.other.StudentActivity;
import com.gaowenxing.keshe.activity.gao.other.TeacherActivity;
import com.gaowenxing.keshe.util.MySqlOpenHelper;
import com.gaowenxing.keshe.util.Operate;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lx on 2017/6/3.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private CheckBox student_check, teacher_check, manager_check;
    private EditText username, password;
    private Button login;
    private TextView forgetPassword, register;
    private List<CheckBox> mCheckBoxes;
    private MySqlOpenHelper mHelper;
    private Operate mOperate ;
    private int ERROR_STUDENT;
    private int ERROR_TEACHER;
    private int ERROR_MANAGER;

    private LinearLayout linerLayout;
    private CircleImageView circleImage;

    private static boolean isFirst = false;

    private static final String TAG = "LoginActivity";

    private static final int UPDATE_BACKGROUND = 1;

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_BACKGROUND:

                    linerLayout.setBackgroundResource(R.mipmap.login);
                    circleImage.setImageDrawable(getResources().getDrawable(R.mipmap.zhongbei));
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_layout);
        linerLayout= (LinearLayout) this.findViewById(R.id.login);
        circleImage = (CircleImageView) this.findViewById(R.id.image_login);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = UPDATE_BACKGROUND;
                handler.sendMessage(message);
            }
        }).start();

        if (mHelper==null){
            mHelper = MySqlOpenHelper.newInstance(this);
        }
        if (mOperate==null){
            mOperate = new Operate(this,mHelper);
        }

        if (!isFirst) {
            isFirst = true;
            mOperate.createTable(mHelper);
        }
        student_check = (CheckBox) this.findViewById(R.id.student_check);
        teacher_check = (CheckBox) this.findViewById(R.id.teacher_check);
        manager_check = (CheckBox) this.findViewById(R.id.manager_check);
        mCheckBoxes = new ArrayList<>();
        mCheckBoxes.add(student_check);
        mCheckBoxes.add(teacher_check);
        mCheckBoxes.add(manager_check);

        username = (EditText) this.findViewById(R.id.username);
        password = (EditText) this.findViewById(R.id.password);

        login = (Button) this.findViewById(R.id.btn_login);
        login.setOnClickListener(this);

        forgetPassword = (TextView) this.findViewById(R.id.forgetPassword);
        register = (TextView) this.findViewById(R.id.register);
        forgetPassword.setOnClickListener(this);
        register.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.forgetPassword:
                forgetPassword();
                break;
            case R.id.register:
                register();
                break;
            case R.id.student_check:
                student_check.setChecked(true);
                break;
            case R.id.teacher_check:
                teacher_check.setChecked(true);
                break;
            case R.id.manager_check:
                manager_check.setChecked(true);
                break;
        }

    }

    private int getCount() {

        int check_count = 0;

        for (CheckBox checkBox : mCheckBoxes) {
            if (checkBox.isChecked()) {
                check_count++;
            }
        }
        return check_count;
    }

    /**
     * 用户登录
     */
    private void login() {
        if ((!username.getText().toString().equals(""))&&
                (!password.getText().toString().equals(""))) {
            if (getCount() == 1) {

                if (student_check.isChecked()) {
                    ERROR_STUDENT = query_User("User", "Uname,Upassword", "UClass = 'Student'");
                    if (ERROR_STUDENT == 0) {
                        Intent intent = new Intent(LoginActivity.this, StudentActivity.class);
                        intent.putExtra("Sno",username.getText().toString());
                        startActivity(intent);
                        finish();
                    } else if (ERROR_STUDENT == 1 ) {
                        Toast.makeText(this, "密码错误!", Toast.LENGTH_SHORT).show();
                    } else if (ERROR_STUDENT == -1) {
                        Toast.makeText(this, "该用户还未注册!", Toast.LENGTH_SHORT).show();
                    }
                } else if (teacher_check.isChecked()) {
                    ERROR_TEACHER = query_User("User", "Uname,Upassword", "UClass = 'Teacher'");
                    if (ERROR_TEACHER == 0) {
                        Intent intent = new Intent(LoginActivity.this, TeacherActivity.class);
                        intent.putExtra("Tno",username.getText().toString());
                        startActivity(intent);
                        finish();
                    } else if (ERROR_TEACHER == 1 ) {
                        Toast.makeText(this, "密码错误!", Toast.LENGTH_SHORT).show();
                    } else if (ERROR_TEACHER == -1) {
                        Toast.makeText(this, "该用户还未注册!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    ERROR_MANAGER = query_User("User", "Uname,Upassword", "UClass = 'Manager'");
                    if (ERROR_MANAGER == 0) {
                        Intent intent = new Intent(LoginActivity.this, ManagerActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (ERROR_MANAGER == 1) {
                        Toast.makeText(this, "密码错误!", Toast.LENGTH_SHORT).show();
                    } else if (ERROR_MANAGER == -1) {

                        Toast.makeText(this, "该用户还未注册!", Toast.LENGTH_SHORT).show();
                    }
                }

            } else if (getCount() > 1) {
                Toast.makeText(this, "只能以一个身份进入此系统!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "您还未选择身份!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "请输入完整信息!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 用户注册
     */
    private void register() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * 忘记密码
     */
    private void forgetPassword() {

        if (username.getText().toString().equals("")) {

            Toast.makeText(this, "请输入用户名再点击忘记密码", Toast.LENGTH_SHORT).show();
        }else {
            mOperate.updateData("User", "Upassword = '123'", "Uname = " + "'" + username.getText().toString() + "'");

            Toast.makeText(this, "密码已设置为默认密码：123", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 查询登录信息
     */
    private int query_User(String table, String values, String condition) {
        int error = -1;
        Cursor cursor = mOperate.selectData(table, values, condition);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(cursor.getColumnIndex("Uname")).equals(username.getText().toString())) {
                    if (cursor.getString(cursor.getColumnIndex("Upassword")).equals(password.getText().toString())) {
                        error = 0;
                    } else {
                        error = 1;
                    }
                }
            } while (cursor.moveToNext());

        }
        return error;
    }
}
