package com.gaowenxing.keshe.adapter.chooseResult;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.gaowenxing.keshe.R;

/**
 * Created by lx on 2017/6/10.
 */

public class ChooseResultViewHolder extends RecyclerView.ViewHolder {

    public TextView mTextView1, mTextView2, mTextView3;

    private SparseArray<View> mSparseArray;

    public ChooseResultViewHolder(View itemView) {
        super(itemView);

        mSparseArray = new SparseArray();
        mTextView1 = (TextView) itemView.findViewById(R.id.course_result_name);
        mTextView2 = (TextView) itemView.findViewById(R.id.teacher_result_name);
        mTextView3 = (TextView) itemView.findViewById(R.id.result_credit);

    }

}
