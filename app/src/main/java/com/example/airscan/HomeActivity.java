package com.example.airscan;

import static com.example.airscan.Others.APIClient.Usertoken;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.example.airscan.Interfaces.APIInterface;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class HomeActivity extends AppCompatActivity {

    private AnimatedBottomBar bottomNavigationView;
    private FrameLayout frameLayout;


    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Bottom NAV
        bottomNavigationView = findViewById(R.id.bottom_bar);
        frameLayout = findViewById(R.id.frameLayout);
        //SETUP DEFAULT TAB/FRAGMENT
        replace(new HomeFragment());

        //CLICK TO THE BOTTOM NAV ITEMS
        bottomNavigationView.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int i, @Nullable AnimatedBottomBar.Tab tab, int i1, @NonNull AnimatedBottomBar.Tab tab1) {
                if (tab1.getId() == R.id.navHome){
                    replace(new HomeFragment());
                } else if (tab1.getId() == R.id.navMap){
                    replace(new MapFragment());
                } else if (tab1.getId() == R.id.navDevice){
                    replace(new DeviceFragment());
                } else if (tab1.getId() == R.id.navProfile){
                    replace(new ProfileFragment());
                }
            }

            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {

            }
        });




    }

    private void replace(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }
}