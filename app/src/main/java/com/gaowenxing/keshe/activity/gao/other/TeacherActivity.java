package com.gaowenxing.keshe.activity.gao.other;

import android.support.v4.app.Fragment;

import com.gaowenxing.keshe.activity.SingleFragmentActivity;
import com.gaowenxing.keshe.fragment.TeacherFragment;

/**
 * Created by lx on 2017/6/3.
 */

public class TeacherActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new TeacherFragment();
    }

    @Override
    public String getName() {
        return "Teacher";
    }
}
