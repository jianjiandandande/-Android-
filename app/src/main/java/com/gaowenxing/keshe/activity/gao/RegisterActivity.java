package com.gaowenxing.keshe.activity.gao;

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
 * Created by lx on 2017/6/3.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private EditText username,password,again_password;
    private Button register;
    private Spinner mSpinner;
    private TextView title;
    private List<String> users;
    private ArrayAdapter<String> adapter;
    private MySqlOpenHelper mHelper;
    private Operate mOperate;
    private String Uclass;
    private boolean isExist_User = false;
    private boolean isExist_Student = false;

    private static final String TAG = "RegisterActivity";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        username=(EditText)this.findViewById(R.id.username_register);
        password=(EditText)this.findViewById(R.id.password_register);
        again_password=(EditText)this.findViewById(R.id.again_password);

        title = (TextView) this.findViewById(R.id.title);
        title.setText("用户注册");

        register=(Button) this.findViewById(R.id.register);
        register.setOnClickListener(this);

        mSpinner = (Spinner) this.findViewById(R.id.spinner);
        initSpinner();
        mSpinner.setOnItemSelectedListener(this);
    }


    private void initSpinner(){
        users = new ArrayList<>();
        users.add("Student");
        users.add("Teacher");
        users.add("Manager");
        //适配器
        adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, users);
        //设置样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        mSpinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Uclass = users.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void add_data_Manager(){
        if(!query_data("User","Uname",null)) {
            mOperate.addData(Data.INSERT_USER, "User", new String[]{username.getText().toString(), Uclass, password.getText().toString()});
            Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "用户名已被注册!", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * 添加数据
     */
    private void add_data(String table,String values,String condition){

        if (query_data(table,values,condition)) {
            if(!query_data("User","Uname",condition)) {
                mOperate.addData(Data.INSERT_USER, "User", new Object[]{username.getText().toString(), Uclass, password.getText().toString()});
                Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(this, "用户名已被注册!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "请检查学号是否正确!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 查询数据表
     */
    private boolean query_data(String table,String values,String condition){
        boolean isExist = false;
        Cursor cursor = mOperate.selectData(table,values,condition);
        if (cursor!=null) {
            if (cursor.moveToFirst()) {
                do {
                    if (cursor.getString(cursor.getColumnIndex(values)).equals(username.getText().toString())) {
                        isExist = true;
                    }
                } while (cursor.moveToNext());

            }
            cursor.close();
        }else{
            Toast.makeText(this, "没有获取到数据!", Toast.LENGTH_SHORT).show();
        }
        return isExist;
    }




    @Override
    public void onClick(View v) {
        if (mHelper==null) {
            mHelper = MySqlOpenHelper.newInstance(this);
        }
        mOperate = new Operate(this,mHelper);
        if ((!username.getText().toString().equals(""))&&
                (!password.getText().toString().equals(""))&&
                (!again_password.getText().toString().equals(""))){
            if (password.getText().toString().equals(again_password.getText().toString())){
                if(Uclass.toString().equals("Student")) {
                    add_data("Student","Sno",null);
                }else if (Uclass.toString().equals("Teacher")){
                    add_data("Teacher","Tno",null);
                }else if (Uclass.toString().equals("Manager")){
                    add_data_Manager();
                }
            }else{
                Toast.makeText(this, "两次密码输入不同!", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "请完善注册信息!", Toast.LENGTH_SHORT).show();
        }
    }
}
