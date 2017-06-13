package com.gaowenxing.keshe.adapter.chooseResult;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaowenxing.keshe.R;
import com.gaowenxing.keshe.adapter.choose.ChooseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 2017/6/10.
 */

public class ChooseResultAdapter extends RecyclerView.Adapter<ChooseResultViewHolder> {

    private List<Object[]> mDatas;

    private List<ChooseViewHolder> mHolders;

    public ChooseResultAdapter(List<Object[]> datas){
        if (mDatas==null){
            mDatas = new ArrayList<>();
        }
        mDatas = datas;

        mHolders = new ArrayList<>();
    }

    @Override
    public ChooseResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_result_item,parent,false);

        ChooseResultViewHolder viewHolder = new ChooseResultViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChooseResultViewHolder holder, int position) {
        Object[] object= mDatas.get(position);
        holder.mTextView1.setText(object[0].toString());
        holder.mTextView2.setText(object[1].toString());
        holder.mTextView3.setText(object[2].toString());
    }



    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    public List<ChooseViewHolder> getHolders() {
        return mHolders;
    }
}
