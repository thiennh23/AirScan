package com.example.airscan;

import static com.example.airscan.Others.APIClient.Usertoken;

import androidx.annotation.NonNull;
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

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;


    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Bottom NAV
        bottomNavigationView = findViewById(R.id.bottomNavView);
        frameLayout = findViewById(R.id.FrameLayout);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemID = item.getItemId();
                if (itemID == R.id.navHome){
                    loadFragment(new HomeFragment(), false);
                } else if (itemID == R.id.navSearch){
                    loadFragment(new HomeFragment(), false);
                } else if (itemID == R.id.navNotification){
                    loadFragment(new HomeFragment(), false);
                } else {//NavProfile
                    loadFragment(new HomeFragment(), false);
                }
                return true;
            }
        });
        loadFragment(new HomeFragment(), true);

        TextView text = findViewById(R.id.text);
        //text.setText(Usertoken);
    }

    private void loadFragment(Fragment fragment, boolean isAppInitialized){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (isAppInitialized){
            fragmentTransaction.add(R.id.FrameLayout, fragment);
        } else fragmentTransaction.replace(R.id.FrameLayout, fragment);
        fragmentTransaction.commit();
    }

}