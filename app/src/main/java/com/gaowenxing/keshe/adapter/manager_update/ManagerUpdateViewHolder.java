package com.gaowenxing.keshe.adapter.manager_update;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.gaowenxing.keshe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 2017/6/11.
 */

public class ManagerUpdateViewHolder extends RecyclerView.ViewHolder{

    private String mTableClass;

    public EditText mEditText1, mEditText2, mEditText3, mEditText4, mEditText5;

    public CheckBox mCheckBox;

    private List<EditText> edits;

    private SparseArray<View> mSparseArray;

    public ManagerUpdateViewHolder(View itemView,String tableClass) {
        super(itemView);
        mTableClass = tableClass;
        mSparseArray = new SparseArray<>();
        mEditText1 = (EditText) itemView.findViewById(R.id.edit_item_view1);
        mEditText2 = (EditText) itemView.findViewById(R.id.edit_item_view2);
        mEditText3 = (EditText) itemView.findViewById(R.id.edit_item_view3);
        mEditText4 = (EditText) itemView.findViewById(R.id.edit_item_view4);
        mEditText5 = (EditText) itemView.findViewById(R.id.edit_item_view5);
        mCheckBox = (CheckBox) itemView.findViewById(R.id.edit_item_choose);
        edits = new ArrayList<>();
        edits.add(mEditText1);
        edits.add(mEditText2);
        edits.add(mEditText3);
        edits.add(mEditText4);
        edits.add(mEditText5);
        changeItemVisible();

    }

    private void changeItemVisible() {

        if (mTableClass.equals("Student")) {
            changeLayout_student();
        } else if (mTableClass.equals("Course")) {
            changeLayout_course();
        } else if (mTableClass.equals("SC")) {
            changeLayout_sc();
        }
    }

    /**
     * 控件是或否可见
     */
    private void changeVisible() {
        for (EditText editview : edits) {
            if (editview.getVisibility() == View.GONE) {
                editview.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Edit是否可以修改内容
     */
    private void changeEnabled() {
        for (EditText editview : edits) {
            editview.setEnabled(true);
        }
    }

    private void changeLayout_sc() {
        changeVisible();
        changeEnabled();
        mEditText5.setVisibility(View.GONE);
        mEditText1.setEnabled(false);
        mEditText2.setEnabled(false);
        mEditText3.setEnabled(false);
    }

    private void changeLayout_course() {
        changeVisible();
        changeEnabled();
        mEditText5.setVisibility(View.GONE);
        mEditText1.setEnabled(false);
    }



    private void changeLayout_student() {
        changeVisible();
        changeEnabled();
        mEditText1.setEnabled(false);
        mEditText2.setEnabled(false);
        mEditText3.setEnabled(false);
        mEditText4.setEnabled(false);

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

        EditText et = (EditText) getView(id);
        return et.getText().toString();

    }
}
