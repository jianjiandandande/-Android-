package com.gaowenxing.keshe.adapter.manager_update;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaowenxing.keshe.R;
import com.gaowenxing.keshe.adapter.manager_select.ManagerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 2017/6/11.
 */

public class ManagerUpdateAdapter extends RecyclerView.Adapter<ManagerUpdateViewHolder>{

    private List<Object[]> mDatas;

    private String mTableClass;

    private List<ManagerUpdateViewHolder> mHolders;

    public ManagerUpdateAdapter(List<Object[]> datas,String tableClass){
        mDatas = datas;
        mTableClass = tableClass;

        mHolders = new ArrayList<>();
    }

    @Override
    public ManagerUpdateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.update_item,parent,false);

        ManagerUpdateViewHolder viewHolder = new ManagerUpdateViewHolder(view,mTableClass);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ManagerUpdateViewHolder holder, int position) {
        onBind(holder,position);
        mHolders.add(holder);
    }

    private void onBind(ManagerUpdateViewHolder holder,int position) {

        if (mTableClass.equals("Student")) {
            bind_student(holder,position);
        }else if (mTableClass.equals("Course")) {
            bind_course(holder,position);
        } else if (mTableClass.equals("SC")) {
            bind_sc(holder,position);
        }

    }

    private void bind_student(ManagerUpdateViewHolder holder,int position) {

        Object[] object = mDatas.get(position);

        holder.mEditText1.setText(object[0].toString());
        holder.mEditText2.setText(object[1].toString());
        holder.mEditText3.setText(object[2].toString());
        holder.mEditText4.setText(object[3].toString());
        holder.mEditText5.setText(object[4].toString());
    }


    private void bind_course(ManagerUpdateViewHolder holder,int position) {

        Object[] object = mDatas.get(position);

        holder.mEditText1.setText(object[0].toString());
        holder.mEditText2.setText(object[1].toString());
        holder.mEditText3.setText(object[2].toString());
        holder.mEditText4.setText(object[3].toString());
    }

    private void bind_sc(ManagerUpdateViewHolder holder,int position) {
        Object[] object = mDatas.get(position);

        holder.mEditText1.setText(object[0].toString());
        holder.mEditText2.setText(object[1].toString());
        holder.mEditText3.setText(object[2].toString());
        holder.mEditText4.setText(object[3].toString());
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public List<ManagerUpdateViewHolder> getHolders(){

        return mHolders;
    }
}
