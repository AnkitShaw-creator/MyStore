package com.example.mystore.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mystore.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import fragments.HOME.HomeFragment;
import fragments.ORDERS.OrderFragment;
import fragments.SETTINGS.SettingsFragment;

public class ContentActivity extends AppCompatActivity {


    private BottomNavigationView mBottomNavigation;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        mAuth = FirebaseAuth.getInstance();
        mBottomNavigation = findViewById(R.id.bottom_navigation);

        mBottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_home:{
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragmentContainerView, new HomeFragment(),null)
                                .commit();
                        Toast.makeText(ContentActivity.this, "Selected home", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    case R.id.nav_orders:{
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragmentContainerView, OrderFragment.class,null)
                                .commit();
                        Toast.makeText(ContentActivity.this, "Selected orders", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    case R.id.nav_settings:{
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragmentContainerView, SettingsFragment.class,null)
                                .commit();
                        Toast.makeText(ContentActivity.this, "Selected Settings", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    default:
                        return false;
                }
            }

        });
    }
}