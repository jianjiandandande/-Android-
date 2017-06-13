package com.gaowenxing.keshe.adapter.showCredit;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.gaowenxing.keshe.R;

/**
 * Created by lx on 2017/6/10.
 */

public class ShowCreditViewHolder extends RecyclerView.ViewHolder {

    public TextView mTextView1,mTextView2,mTextView3,mTextView4;
    public CheckBox mCheckBox;

    public ShowCreditViewHolder(View itemView) {
        super(itemView);

        mTextView1 = (TextView) itemView.findViewById(R.id.credit_Cname);
        mTextView2 = (TextView) itemView.findViewById(R.id.credit_Tname);
        mTextView3 = (TextView) itemView.findViewById(R.id.credit);

    }
}
