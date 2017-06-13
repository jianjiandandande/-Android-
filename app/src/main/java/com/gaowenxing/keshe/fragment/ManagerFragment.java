package com.gaowenxing.keshe.fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gaowenxing.keshe.Data;
import com.gaowenxing.keshe.R;

import com.gaowenxing.keshe.activity.su.AddActivity;
import com.gaowenxing.keshe.activity.zhao.SelectActivity;
import com.gaowenxing.keshe.activity.sun.UpdateActivity;
import com.gaowenxing.keshe.util.MySqlOpenHelper;
import com.gaowenxing.keshe.util.Operate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 2017/6/3.
 */

public class ManagerFragment extends Fragment implements View.OnClickListener {

    private List<String> tables = new ArrayList<>();
    private MySqlOpenHelper mHelper;
    private Operate mOperate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.manager, container, false);
        if (mHelper == null) {
            mHelper = MySqlOpenHelper.newInstance(getContext());
        }
        if (mOperate == null) {
            mOperate = new Operate(getContext(), mHelper);
        }
        Button add_data = (Button) view.findViewById(R.id.add_data);
        Button update_data = (Button) view.findViewById(R.id.update_data);
        Button delete_data = (Button) view.findViewById(R.id.delete_data);
        Button select_data = (Button) view.findViewById(R.id.select_data);

        add_data.setOnClickListener(this);
        update_data.setOnClickListener(this);
        delete_data.setOnClickListener(this);
        select_data.setOnClickListener(this);
        initTables();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.add_data:
                addData();
                break;
            case R.id.update_data:
                updateData();
                break;
            case R.id.delete_data:
                deleteData();
                break;
            case R.id.select_data:
                selectData();
                break;
        }
    }

    private void initTables() {
        tables.add("Student");
        tables.add("Course");
        tables.add("SC");
        tables.add("User");
    }

    /**
     * 添加数据
     */
    private SQLiteDatabase mDatabase;

    private void addData() {
        Intent intent = new Intent(getContext(), AddActivity.class);
        startActivity(intent);
    }

    /**
     * 删除数据
     */

    private void deleteData() {

        Intent intent  = new Intent(getContext(),SelectActivity.class);
        intent.putExtra(Data.INTENT,"delete");
        startActivity(intent);

    }

    /**
     * 更新数据
     */

    private void updateData() {
        Intent intent = new Intent(getContext(), UpdateActivity.class);
        startActivity(intent);
    }

    /**
     * 查询数据
     */

    private void selectData() {

        Intent intent = new Intent(getContext(), SelectActivity.class);
        startActivity(intent);

    }
}
