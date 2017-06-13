package com.gaowenxing.keshe.adapter.choose;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.gaowenxing.keshe.R;

/**
 * Created by lx on 2017/6/10.
 */

public class ChooseViewHolder extends RecyclerView.ViewHolder {

    public TextView mTextView1, mTextView2, mTextView3, mTextView4;
    public CheckBox mCheckBox;

    private SparseArray<View> mSparseArray;


    public ChooseViewHolder(View itemView) {
        super(itemView);

        mSparseArray = new SparseArray();
        mTextView1 = (TextView) itemView.findViewById(R.id.course_no);
        mTextView2 = (TextView) itemView.findViewById(R.id.course_name);
        mTextView3 = (TextView) itemView.findViewById(R.id.teacher_no);
        mTextView4 = (TextView) itemView.findViewById(R.id.teacher_name);
        mCheckBox = (CheckBox) itemView.findViewById(R.id.choose);

    }

    public View getView(@IdRes int id) {
        View child = mSparseArray.get(id);
        if (null == child) {
            child = itemView.findViewById(id);
            mSparseArray.put(id, child);
        }
        return mSparseArray.get(id);
    }

    public String getText(@IdRes int id) {
        TextView tv = (TextView) getView(id);
        return tv.getText().toString();
    }

}
