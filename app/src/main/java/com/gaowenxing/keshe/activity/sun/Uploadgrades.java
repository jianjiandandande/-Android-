package com.gaowenxing.keshe.activity.sun;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gaowenxing.keshe.R;
import com.gaowenxing.keshe.util.MySqlOpenHelper;
import com.gaowenxing.keshe.util.Operate;

/**
 * Created by Administrator on 2017/6/13 0013.
 */

public class Uploadgrades extends AppCompatActivity implements View.OnClickListener{

    private EditText input1, input2, input3, input4;
    private String st1, st2, st3, st4,intent_info;
    private MySqlOpenHelper mHelper;
    private Operate mOperate;
    private Button btn_ok;
    private TextView title;

    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_grades);

        Intent intent = getIntent();
        intent_info = intent.getStringExtra("SC");

        title = (TextView) this.findViewById(R.id.title);
        title.setText("上传成绩");
        if (mHelper == null) {
            mHelper = MySqlOpenHelper.newInstance(this);
        }
        if (mOperate == null) {
            mOperate = new Operate(this, mHelper);
        }

        initEdit();



        btn_ok = (Button) this.findViewById(R.id.upload_data_ok);
        btn_ok.setOnClickListener(this);
    }

    private void initEdit() {

        input1 = (EditText) this.findViewById(R.id.input1_upload);
        input2 = (EditText) this.findViewById(R.id.input2_upload);
        input3 = (EditText) this.findViewById(R.id.input3_upload);
        input4 = (EditText) this.findViewById(R.id.input4_upload);

        input1.setHint("请输入教师号");
        input2.setHint("请输入学号");
        input3.setHint("请输入课程号");
        input4.setHint("请输入成绩");
    }


    @Override
    public void onClick(View v) {

        st1 = input1.getText().toString();
        st2 = input2.getText().toString();
        st3 = input3.getText().toString();
        st4 = input4.getText().toString();
        if (st1.equals(intent_info)) {

            if ((!st2.equals("")) && (!st3.equals(""))) {
                if (queryFroExit() == true) {
                    mOperate.updateData("SC", "Grade = " + Double.valueOf(st4) + "", "Sno = " + "'" + st2 + "' AND " +
                            "Cno = " + "'" + st3 + "' AND " +
                            "Tno = " + "'" + st1 + "'");

                    Toast.makeText(this, "成绩上传成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "上传失败,请检查课程号与学号是否正确!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "请完善信息", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "每个老师只能上传自己所教课程的成绩", Toast.LENGTH_SHORT).show();
        }
    }


        /**
         * 查询SC表中是否存在这样的学生
         */
    private boolean queryFroExit(){

        boolean isExit = false;
        Cursor cursor = mOperate.selectData("SC","Grade",
                "Tno = "+ "'" + intent_info + "'"+
                        "AND"+" Sno="+"'" + st2+ "'"+
                        "AND"+" Cno="+"'" + st3 + "'");
        if (cursor.getCount()==0){
            isExit=false;
        }else{
            isExit = true;
        }
        return isExit;
    }


}
