package com.gaowenxing.keshe.adapter.showCredit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaowenxing.keshe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 2017/6/10.
 */

public class ShowCreditAdapter extends RecyclerView.Adapter<ShowCreditViewHolder> {

    private List<Object[]> mDatas;

    public ShowCreditAdapter(List<Object[]> datas){
        if (mDatas==null){
            mDatas = new ArrayList<>();
        }
        mDatas = datas;

    }


    @Override
    public ShowCreditViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.credit_item,parent,false);

        ShowCreditViewHolder viewHolder = new ShowCreditViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ShowCreditViewHolder holder, int position) {

        Object[] object= mDatas.get(position);
        holder.mTextView1.setText(object[0].toString());
        holder.mTextView2.setText(object[1].toString());
        holder.mTextView3.setText(object[2].toString());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

}
