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
import android.widget.EditText;
import android.widget.TextView;

import com.gaowenxing.keshe.Data;
import com.gaowenxing.keshe.R;
import com.gaowenxing.keshe.activity.zhao.ChooseCourseActivity;
import com.gaowenxing.keshe.activity.sun.ShowCcreditActivity;
import com.gaowenxing.keshe.activity.su.UpdateInfoActivity;
import com.gaowenxing.keshe.util.MySqlOpenHelper;
import com.gaowenxing.keshe.util.Operate;

import java.util.List;

/**
 * Created by lx on 2017/6/3.
 */

public class StudentFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "StudentFragment";

    private TextView no, name, sex, date, dept;
    private String intent_no, str_no, str_name, str_sex, str_date, str_dept;
    private List<EditText> edits;
    private Button update_info, update_password,choose_course,select_grade;

    private MySqlOpenHelper mHelper;
    private Operate mOperate;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.student, container, false);

        if (mHelper == null) {
            mHelper = MySqlOpenHelper.newInstance(getContext());
        }
        if (mOperate == null) {
            mOperate = new Operate(getContext(), mHelper);
        }

        Intent intent = getActivity().getIntent();
        intent_no = intent.getStringExtra("Sno");

        initText(view);

        query_data();
        setValue();

        initButton(view);

        return view;
    }

    private void initButton(View view) {
        update_info = (Button) view.findViewById(R.id.student_update_info);
        update_password = (Button) view.findViewById(R.id.student_update_password);
        choose_course = (Button) view.findViewById(R.id.student_choose_course);
        select_grade = (Button) view.findViewById(R.id.student_select_grade);

        update_info.setOnClickListener(this);
        update_password.setOnClickListener(this);
        choose_course.setOnClickListener(this);
        select_grade.setOnClickListener(this);
    }

    /**
     * 实例化TextView
     */
    private void initText(View view) {
        no = (TextView) view.findViewById(R.id.text_num_student);
        name = (TextView) view.findViewById(R.id.text_name_student);
        sex = (TextView) view.findViewById(R.id.text_sex_student);
        date = (TextView) view.findViewById(R.id.text_date_student);
        dept = (TextView) view.findViewById(R.id.text_dept_student);
    }

    /**
     * 加载内容
     */
    private void setValue(){
        no.setText(intent_no);name.setText(str_name);sex.setText(str_sex);
        date.setText(str_date);dept.setText(str_dept);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.student_update_info:
                change_info();
                break;
            case R.id.student_update_password:
                change_password();
                break;
            case R.id.student_choose_course:
                chooseCourse();
                break;
            case R.id.student_select_grade:
                get_credit();
                break;
        }
    }

    private void chooseCourse() {

        Intent intent = new Intent(getContext(), ChooseCourseActivity.class);
        intent.putExtra("Sno",no.getText().toString());
        startActivity(intent);
    }

    private void change_password() {

        Intent intent = new Intent(getContext(),UpdateInfoActivity.class);
        intent.putExtra(Data.INTENT,Data.CHANGE_USER_INFO);
        intent.putExtra(Data.CHANGE_USER_INFO,no.getText().toString());
        startActivity(intent);
    }

    /**
     * 实现更改用户信息的功能
     */
    private void change_info() {
        Intent intent = new Intent(getContext(), UpdateInfoActivity.class);
        intent.putExtra(Data.INTENT, Data.CHANGE_STUDENT_INFO);
        intent.putExtra(Data.CHANGE_STUDENT_INFO, no.getText().toString());
        startActivity(intent);
    }


    private void query_data() {

        Cursor cursor = mOperate.selectData("Student", "Sname,Ssex,Sdate,Sdept", "Sno ="+"'"+intent_no+"'");
        if (cursor.moveToFirst()){
            do {
                str_name = cursor.getString(cursor.getColumnIndex("Sname"));
                str_sex = cursor.getString(cursor.getColumnIndex("Ssex"));
                str_date = cursor.getString(cursor.getColumnIndex("Sdate"));
                str_dept = cursor.getString(cursor.getColumnIndex("Sdept"));

            }while (cursor.moveToNext());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        query_data();
        setValue();
    }

    public void  get_credit() {
        Intent intent = new Intent(getContext(), ShowCcreditActivity.class);
        intent.putExtra("Sno",no.getText().toString());
        startActivity(intent);
    }
}
