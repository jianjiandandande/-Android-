package com.gaowenxing.keshe.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaowenxing.keshe.R;
import com.gaowenxing.keshe.adapter.choose.ChooseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 2017/6/13.
 */

public  abstract class BaseAdapter <T,H extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {

    private List<T> mDatas;

    private int mLayoutId;

    private List<BaseViewHolder> mHolders;

    public BaseAdapter(List<T> datas,int layoutId){

        mHolders = new ArrayList<>();
        mDatas = new ArrayList<>();
        mDatas = datas;
        mLayoutId = layoutId;

    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId,parent,false);

        BaseViewHolder viewHolder = new BaseViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        onBind(((H)holder),position);
        mHolders.add(holder);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public List<BaseViewHolder> getHolders() {
        return mHolders;
    }

    public abstract void onBind(H holder, int position);
}
