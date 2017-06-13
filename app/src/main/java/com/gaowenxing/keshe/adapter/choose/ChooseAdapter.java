package com.gaowenxing.keshe.adapter.choose;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaowenxing.keshe.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by lx on 2017/6/10.
 */

public class ChooseAdapter extends RecyclerView.Adapter<ChooseViewHolder> {

    private List<Object[]> mDatas;

    private List<ChooseViewHolder> mHolders;

    public ChooseAdapter(List<Object[]> datas){
        if (mDatas==null){
            mDatas = new ArrayList<>();
        }
        mDatas = datas;

        mHolders = new ArrayList<>();
    }

    @Override
    public ChooseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_item,parent,false);

        ChooseViewHolder viewHolder = new ChooseViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChooseViewHolder holder, int position) {
        Object[] object= mDatas.get(position);
        holder.mTextView1.setText(object[0].toString()==null?"":object[0].toString());
        holder.mTextView2.setText(object[1].toString()==null?"":object[1].toString());
        holder.mTextView3.setText(object[2].toString()==null?"":object[2].toString());
        holder.mTextView4.setText(object[3].toString()==null?"":object[3].toString());
        holder.mCheckBox.setChecked(false);

        mHolders.add(holder);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    public List<ChooseViewHolder> getHolders() {
        return mHolders;
    }
}
