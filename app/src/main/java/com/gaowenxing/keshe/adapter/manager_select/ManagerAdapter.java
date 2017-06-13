package com.gaowenxing.keshe.adapter.manager_select;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaowenxing.keshe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 2017/6/11.
 */

public class ManagerAdapter extends RecyclerView.Adapter<ManagerViewHolder> {

    private List<Object[]> mDatas;

    private String mTableClass;

    private List<ManagerViewHolder> mHolders;

    public ManagerAdapter(List<Object[]> datas,String tableClass){
        mDatas = datas;
        mTableClass = tableClass;

        mHolders = new ArrayList<>();
    }

    @Override
    public ManagerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_item,parent,false);

        ManagerViewHolder viewHolder = new ManagerViewHolder(view,mTableClass);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ManagerViewHolder holder, int position) {

        onBind(holder,position);
        mHolders.add(holder);

    }

    private void onBind(ManagerViewHolder holder,int position) {

        if (mTableClass.equals("Student")) {
            bind_student(holder,position);
        } else if (mTableClass.equals("Teacher")) {
            bind_teacher(holder,position);
        } else if (mTableClass.equals("Course")) {
            bind_course(holder,position);
        } else if (mTableClass.equals("SC")) {
            bind_sc(holder,position);
        } else if (mTableClass.equals("TC")) {
            bind_tc(holder,position);
        }

    }

    private void bind_student(ManagerViewHolder holder,int position) {

        Object[] object = mDatas.get(position);

        holder.mTextView1.setText(object[0].toString());
        holder.mTextView2.setText(object[1].toString());
        holder.mTextView3.setText(object[2].toString());
        holder.mTextView4.setText(object[3].toString());
        holder.mTextView5.setText(object[4].toString());
    }

    private void bind_teacher(ManagerViewHolder holder,int position) {

        Object[] object = mDatas.get(position);

        holder.mTextView1.setText(object[0].toString());
        holder.mTextView2.setText(object[1].toString());
        holder.mTextView3.setText(object[2].toString());
        holder.mTextView4.setText(object[3].toString());

    }

    private void bind_course(ManagerViewHolder holder,int position) {

        Object[] object = mDatas.get(position);

        holder.mTextView1.setText(object[0].toString());
        holder.mTextView2.setText(object[1].toString());
        holder.mTextView3.setText(object[2].toString());
        holder.mTextView4.setText(object[3].toString());
    }

    private void bind_sc(ManagerViewHolder holder,int position) {
        Object[] object = mDatas.get(position);

        holder.mTextView1.setText(object[0].toString());
        holder.mTextView2.setText(object[1].toString());
        holder.mTextView3.setText(object[2].toString());
        holder.mTextView4.setText(object[3].toString());
    }

    private void bind_tc(ManagerViewHolder holder,int position) {

        Object[] object = mDatas.get(position);

        holder.mTextView1.setText(object[0].toString());
        holder.mTextView2.setText(object[1].toString());

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public List<ManagerViewHolder> getHolders(){

        return mHolders;
    }
}
