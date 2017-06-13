package com.gaowenxing.keshe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gaowenxing.keshe.R;
import com.gaowenxing.keshe.activity.gao.LoginActivity;


/**
 * Created by lx on 2017/4/12.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity implements View.OnClickListener{
    private DrawerLayout mDrawerLayout;

    private TextView titleText;

    private Button menu_btn;


    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        menu_btn = (Button) this.findViewById(R.id.menu_button);

        menu_btn.setOnClickListener(this);

        titleText = (TextView) this.findViewById(R.id.titleText);
        titleText.setText(titleText.getText().toString()+"("+getName()+")");

        mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);

        NavigationView navView = (NavigationView) this.findViewById(R.id.nav_view);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_task:
                        Intent intent = new Intent(SingleFragmentActivity.this,LoginActivity.class);
                        startActivity(intent);

                        break;
                }
                return true;

            }
        });

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment, fragment)
                    .commit();
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menu_button:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
    }

    public abstract String getName();

}
