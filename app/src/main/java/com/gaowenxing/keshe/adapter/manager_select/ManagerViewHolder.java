package com.gaowenxing.keshe.adapter.manager_select;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.gaowenxing.keshe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 2017/6/11.
 */

public class ManagerViewHolder extends RecyclerView.ViewHolder {

    private String mTableClass;

    public TextView mTextView1, mTextView2, mTextView3, mTextView4, mTextView5;

    public CheckBox mCheckBox;

    private List<TextView> texts;

    private SparseArray<View> mSparseArray;

    public ManagerViewHolder(View itemView, String tableClass) {
        super(itemView);
        mTableClass = tableClass;
        mSparseArray = new SparseArray<>();
        mTextView1 = (TextView) itemView.findViewById(R.id.text_item_view1);
        mTextView2 = (TextView) itemView.findViewById(R.id.text_item_view2);
        mTextView3 = (TextView) itemView.findViewById(R.id.text_item_view3);
        mTextView4 = (TextView) itemView.findViewById(R.id.text_item_view4);
        mTextView5 = (TextView) itemView.findViewById(R.id.text_item_view5);
        mCheckBox = (CheckBox) itemView.findViewById(R.id.text_item_choose);
        texts = new ArrayList<>();
        texts.add(mTextView1);
        texts.add(mTextView2);
        texts.add(mTextView3);
        texts.add(mTextView4);
        texts.add(mTextView5);
        changeItemVisible();

    }

    private void changeItemVisible() {

        if (mTableClass.equals("Student")) {
            changeLayout_student();
        } else if (mTableClass.equals("Teacher")) {
            changeLayout_teacher();
        } else if (mTableClass.equals("Course")) {
            changeLayout_course();
        } else if (mTableClass.equals("SC")) {
            changeLayout_sc();
        } else if (mTableClass.equals("TC")) {
            changeLayout_tc();
        }
    }

    private void changeVisible() {
        for (TextView textview : texts) {
            if (textview.getVisibility() == View.GONE) {
                textview.setVisibility(View.VISIBLE);
            }
        }
    }

    private void changeLayout_tc() {
        changeVisible();
        mTextView3.setVisibility(View.GONE);
        mTextView4.setVisibility(View.GONE);
        mTextView5.setVisibility(View.GONE);
    }

    private void changeLayout_sc() {
        changeVisible();
        mTextView5.setVisibility(View.GONE);
    }

    private void changeLayout_course() {
        changeVisible();
        mTextView5.setVisibility(View.GONE);

    }

    private void changeLayout_teacher() {
        changeVisible();
        mTextView5.setVisibility(View.GONE);

    }

    private void changeLayout_student() {
        changeVisible();

    }

    public View getView(int id) {

        View child = mSparseArray.get(id);
        if (null == child) {
            child = itemView.findViewById(id);
            mSparseArray.put(id, child);
        }
        return mSparseArray.get(id);
    }

    public String getText(int id) {

        TextView tv = (TextView) getView(id);
        return tv.getText().toString();

    }
}
