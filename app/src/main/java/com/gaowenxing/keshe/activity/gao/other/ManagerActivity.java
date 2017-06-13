package com.gaowenxing.keshe.activity.gao.other;

import android.support.v4.app.Fragment;

import com.gaowenxing.keshe.activity.SingleFragmentActivity;
import com.gaowenxing.keshe.fragment.ManagerFragment;

/**
 * Created by lx on 2017/6/3.
 */

public class ManagerActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ManagerFragment();
    }

    @Override
    public String getName() {
        return "Manager";
    }

}
