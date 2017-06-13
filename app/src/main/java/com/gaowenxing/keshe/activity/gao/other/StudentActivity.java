package com.gaowenxing.keshe.activity.gao.other;

import android.support.v4.app.Fragment;

import com.gaowenxing.keshe.activity.SingleFragmentActivity;
import com.gaowenxing.keshe.fragment.StudentFragment;

/**
 * Created by lx on 2017/6/3.
 */

public class StudentActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new StudentFragment();
    }

    @Override
    public String getName() {
        return "Student";
    }

}
