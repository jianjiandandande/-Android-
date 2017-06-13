package com.gaowenxing.keshe.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gaowenxing.keshe.Data;
import com.gaowenxing.keshe.R;
import com.gaowenxing.keshe.activity.su.UpdateInfoActivity;
import com.gaowenxing.keshe.activity.sun.Uploadgrades;
import com.gaowenxing.keshe.util.MySqlOpenHelper;
import com.gaowenxing.keshe.util.Operate;

/**
 * Created by lx on 2017/6/3.
 */

public class TeacherFragment extends Fragment implements View.OnClickListener{

    private MySqlOpenHelper mHelper;
    private Operate mOperate;

    private TextView no, name, sex, tel;

    private Button update_info,update_password,update_grade;

    private String str_name,str_sex,str_tel,intent_no;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.teacher,container,false);

        if (mHelper == null) {
            mHelper = MySqlOpenHelper.newInstance(getContext());
        }
        if (mOperate == null) {
            mOperate = new Operate(getContext(), mHelper);
        }

        Intent intent = getActivity().getIntent();
        intent_no = intent.getStringExtra("Tno");

        initText(view);
        initButton(view);
        query_data();
        setValue();

        update_info.setOnClickListener(this);
        update_password.setOnClickListener(this);
        update_grade.setOnClickListener(this);

        return view;
    }

    private void initButton(View view) {
        update_info = (Button) view.findViewById(R.id.teacher_update_info);
        update_password = (Button) view.findViewById(R.id.teacher_update_password);
        update_grade = (Button) view.findViewById(R.id.teacher_update_grade);
    }

    private void initText(View view) {
        no = (TextView) view.findViewById(R.id.text_num_teacher);
        name = (TextView) view.findViewById(R.id.text_name_teacher);
        sex = (TextView) view.findViewById(R.id.text_sex_teacher);
        tel = (TextView) view.findViewById(R.id.text_tel_teacher);
    }

    private void setValue(){
        no.setText(intent_no);
        name.setText(str_name);
        sex.setText(str_sex);
        tel.setText(str_tel);
    }


    /**
     * 查询信息
     */
    private void query_data() {

        Cursor cursor = mOperate.selectData("Teacher", "Tname,Tsex,Ttel", "Tno ="+"'"+intent_no+"'");
        if (cursor.moveToFirst()){
            do {
                str_name = cursor.getString(cursor.getColumnIndex("Tname"));
                str_sex = cursor.getString(cursor.getColumnIndex("Tsex"));
                str_tel = cursor.getString(cursor.getColumnIndex("Ttel"));
            }while (cursor.moveToNext());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.teacher_update_info:
                //跳转页面并传递数据
                Intent intent = new Intent(getContext(), UpdateInfoActivity.class);
                intent.putExtra(Data.INTENT,Data.CHANGE_teacher_INFO);
                intent.putExtra(Data.CHANGE_teacher_INFO,intent_no);
                startActivity(intent);
                break;
            case R.id.teacher_update_password:
                Intent intent1 = new Intent(getContext(),UpdateInfoActivity.class);
                intent1.putExtra(Data.INTENT,Data.CHANGE_USER_INFO);
                intent1.putExtra(Data.CHANGE_USER_INFO,intent_no);
                startActivity(intent1);
                break;
            case R.id.teacher_update_grade:
                Intent intent2 = new Intent(getContext(),Uploadgrades.class);
                intent2.putExtra(Data.INTENT,"SC");
                intent2.putExtra("SC",intent_no);
                startActivity(intent2);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        query_data();
        setValue();
    }
}
