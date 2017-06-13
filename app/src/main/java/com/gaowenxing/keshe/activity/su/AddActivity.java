package com.gaowenxing.keshe.activity.su;

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
 * Created by lx on 2017/6/7.
 */

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText input1, input2, input3, input4;
    private List<EditText> edits;
    private TextView title;
    private Spinner spinner_class, spinner_sex;
    private List<String> classs, sexs;
    private ArrayAdapter<String> adapter_class, adapter_sex;
    private String input_class, input_sex;
    private LinearLayout linearLayout;
    private Button btn_ok;
    private String st1, st2, st3, st4;
    private MySqlOpenHelper mHelper;
    private Operate mOperate;
    private Object[] values;
    private static int ERROR_STUDENT = 0, ERROR_COURSE = 0, ERROR_SC1 = 0, ERROR_SC = 0,
            ERROR_SC2 = 0, ERROR_SC3 = 0, ERROR_SC4 = 0, ERROR_TEACHER = 0, ERROR_TC1 = 0, ERROR_TC2 = 0, ERROR_TC = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);

        title = (TextView) this.findViewById(R.id.title);
        title.setText("添加数据");

        if (mHelper == null) {
            mHelper = MySqlOpenHelper.newInstance(this);
        }
        if (mOperate == null) {
            mOperate = new Operate(this, mHelper);
        }
        input1 = (EditText) this.findViewById(R.id.input1);
        input2 = (EditText) this.findViewById(R.id.input2);
        input3 = (EditText) this.findViewById(R.id.input3);
        input4 = (EditText) this.findViewById(R.id.input4);

        edits = new ArrayList<>();//实例化集合
        //填充数据
        edits.add(input1);
        edits.add(input2);
        edits.add(input3);
        edits.add(input4);

        linearLayout = (LinearLayout) this.findViewById(R.id.choose_sex);
        btn_ok = (Button) this.findViewById(R.id.add_data_ok);
        btn_ok.setOnClickListener(this);
        spinner_class = (Spinner) this.findViewById(R.id.spinner_add);
        spinner_sex = (Spinner) this.findViewById(R.id.spinner_sex);
        initSpinner();

        spinner_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                input_class = classs.get(position);
                if (input_class.equals("Student")) {
                    changeLayout_student();
                } else if (input_class.equals("Teacher")) {
                    changeLayout_teacher();
                } else if (input_class.equals("Course")) {
                    changeLayout_course();
                } else if (input_class.equals("SC")) {
                    changeLayout_sc();
                } else if (input_class.equals("TC")) {
                    changeLayout_tc();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    private void changeVisible() {
        for (EditText editText : edits) {
            if (editText.getVisibility() == View.GONE) {
                editText.setVisibility(View.VISIBLE);
            }
        }
    }


    private void changeLayout_tc() {
        changeVisible();
        input1.setHint("请输入教师号");
        input2.setHint("请输入课程号");
        linearLayout.setVisibility(View.GONE);
        input3.setVisibility(View.GONE);
        input4.setVisibility(View.GONE);
    }

    private void changeLayout_sc() {
        changeVisible();
        input1.setHint("请输入学号");
        input2.setHint("请输课程号");
        input3.setHint("请输教师号");
        input4.setHint("请输入成绩");
        linearLayout.setVisibility(View.GONE);
    }

    private void changeLayout_course() {
        changeVisible();
        input1.setHint("请输入课程号");
        input2.setHint("请输入课程名");
        input3.setHint("请输入先行课的课程号");
        input4.setHint("请输入该课程所占的学分");
        linearLayout.setVisibility(View.GONE);
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

    private void initSpinner() {
        classs = new ArrayList<>();
        classs.add("Student");
        classs.add("Teacher");
        classs.add("Course");
        classs.add("SC");
        classs.add("TC");
        sexs = new ArrayList<>();
        sexs.add("男");
        sexs.add("女");
        //适配器
        adapter_class = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, classs);
        adapter_sex = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sexs);
        //设置样式
        adapter_class.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_sex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner_class.setAdapter(adapter_class);
        spinner_sex.setAdapter(adapter_sex);

    }


    @Override
    public void onClick(View v) {
        if (input_class.equals("Student")) {
            st1 = input1.getText().toString();
            st2 = input2.getText().toString();
            st3 = input3.getText().toString();
            st4 = input4.getText().toString();
            if (st1.equals("")) {
                Toast.makeText(this, "主码不能为空!", Toast.LENGTH_SHORT).show();
            } else if (st2.equals("") || st3.equals("") || st4.equals("")) {
                Toast.makeText(this, "请完善信息!", Toast.LENGTH_SHORT).show();
            } else {
                ERROR_STUDENT = queryFromTable("Student", "Sno", null, null);
                if (ERROR_STUDENT == 1) {
                    Toast.makeText(this, "不能重复插入", Toast.LENGTH_SHORT).show();
                } else {
                    values = new Object[]{st1, st2, input_sex, st3, st4};
                    mOperate.addData(Data.INSERT_STUDENT, "Student", values);
                    Toast.makeText(this, "插入成功", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (input_class.equals("Course")) {
            st1 = input1.getText().toString();
            st2 = input2.getText().toString();
            st3 = input3.getText().toString();
            st4 = input4.getText().toString();
            if (st1.equals("")) {
                Toast.makeText(this, "主码不能为空!", Toast.LENGTH_SHORT).show();
            } else if (st2.equals("") || st4.equals("")) {
                Toast.makeText(this, "请完整信息，没有先行课的先行课一栏可以不填!", Toast.LENGTH_SHORT).show();
            } else {
                ERROR_COURSE = queryFromTable("Course", "Cno", null, null);
                if (ERROR_COURSE == 1) {
                    Toast.makeText(this, "不能重复插入", Toast.LENGTH_SHORT).show();
                } else {
                    values = new Object[]{st1, st2, st3, Double.valueOf(st4)};
                    mOperate.addData(Data.INSERT_COURSE, "Course", values);
                    Toast.makeText(this, "插入成功", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (input_class.equals("SC")) {

            st1 = input1.getText().toString();
            st2 = input2.getText().toString();
            st3 = input3.getText().toString();
            st4 = input4.getText().toString();
            if (st1.equals("") && st2.equals("") && st3.equals("")) {
                Toast.makeText(this, "主码不能为空!", Toast.LENGTH_SHORT).show();
            } else {
                ERROR_SC1 = queryFromTable("Student", "Sno", null, null);
                ERROR_SC2 = queryFromTable("Course", "Cno", null, st2);
                ERROR_SC3 = queryFromTable("Teacher", "Tno", null, st3);

                if (ERROR_SC1 == 1 && ERROR_SC2 == 1 && ERROR_SC3 == 1) {

                    if (queryFromTC("Tno,Cno", "Tno = " + "'" + st3 + "' AND " + "Cno = " + "'" + st2 + "'")) {

                        ERROR_SC = queryFromTable("SC", "Sno,Cno,Tno", null, null);
                        if (ERROR_SC == 1) {
                            Toast.makeText(this, "不能重复插入", Toast.LENGTH_SHORT).show();
                        } else {
                            if (st4.equals("")) {
                                values = new Object[]{st1, st2, st3, 0};
                            } else {
                                values = new Object[]{st1, st2, st3, Double.valueOf(st4)};
                            }
                            mOperate.addData(Data.INSERT_SC, "SC", values);
                            Toast.makeText(this, "插入成功", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(this, "不满足参照完整性!", Toast.LENGTH_SHORT).show();
                    }
                    //插入逻辑
                } else if (ERROR_SC1 == 0 || ERROR_SC2 == 0 || ERROR_SC3 == 0) {
                    Toast.makeText(this, "不满足参照完整性!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (input_class.equals("Teacher")) {
            st1 = input1.getText().toString();
            st2 = input2.getText().toString();
            st3 = input3.getText().toString();
            if (st1.equals("")) {
                Toast.makeText(this, "主码不能为空", Toast.LENGTH_SHORT).show();
            } else if (st2.equals("") || st3.equals("")) {
                Toast.makeText(this, "请完善信息!", Toast.LENGTH_SHORT).show();
            } else {
                ERROR_TEACHER = queryFromTable("Teacher", "Tno", null, null);
                if (ERROR_TEACHER == 1) {
                    Toast.makeText(this, "不能重复插入!", Toast.LENGTH_SHORT).show();
                } else {
                    values = new Object[]{st1, st2, input_sex, st3};
                    mOperate.addData(Data.INSERT_TEACHER, "Teacher", values);
                    Toast.makeText(this, "插入成功", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (input_class.equals("TC")) {
            st1 = input1.getText().toString();
            st2 = input2.getText().toString();
            if (st1.equals("") && st2.equals("")) {
                Toast.makeText(this, "主码不能为空!", Toast.LENGTH_SHORT).show();
            } else {
                ERROR_TC1 = queryFromTable("Teacher", "Tno", null, null);
                ERROR_TC2 = queryFromTable("Course", "Cno", null, st2);
                if (ERROR_TC1 == 1 && ERROR_TC2 == 1) {

                    ERROR_TC = queryFromTable("TC", "Tno,Cno", null, null);
                    if (ERROR_TC == 1) {
                        Toast.makeText(this, "不能重复插入", Toast.LENGTH_SHORT).show();
                    } else {
                        values = new Object[]{st1, st2};
                        mOperate.addData(Data.INSERT_TC, "TC", values);
                        Toast.makeText(this, "插入成功", Toast.LENGTH_SHORT).show();
                    }
                    //插入逻辑
                } else if (ERROR_TC1 == 0 || ERROR_TC2 == 0) {
                    Toast.makeText(this, "不满足参照完整性", Toast.LENGTH_SHORT).show();
                }
            }
        }
        st1 = "";
        st2 = "";
        st3 = "";
        st4 = "";
    }

    private int queryFromTable(String table, String value, String condition, String editText) {
        int error = 0;
        Cursor cursor = mOperate.selectData(table, value, condition);
        if (cursor.moveToFirst()) {
            do {
                if (value.contains(",")) {
                    if (cursor.getString(cursor.getColumnIndex(value.substring(0, 3)))
                            .equals(st1) &&
                            cursor.getString(cursor.getColumnIndex(value.substring(4, 7)))
                                    .equals(st2) &&
                            cursor.getString(cursor.getColumnIndex(value.substring(8, 11)))
                                    .equals(st3)) {
                        error = 1;
                    }
                } else {
                    if (editText == null) {
                        if (cursor.getString(cursor.getColumnIndex(value)).equals(st1)) {
                            error = 1;
                        }
                    } else {
                        if (cursor.getString(cursor.getColumnIndex(value)).equals(editText)) {
                            error = 1;
                        }
                    }
                }

            } while (cursor.moveToNext());
        }
        return error;
    }

    private boolean queryFromTC(String values, String condition) {

        boolean isExist = false;

        Cursor cursor = mOperate.selectData("TC", values, condition);

        if (cursor.getCount() == 0) {
            isExist = false;
        } else {
            isExist = true;
        }

        return isExist;

    }

}
