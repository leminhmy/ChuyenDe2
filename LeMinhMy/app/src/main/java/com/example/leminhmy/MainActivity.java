package com.example.leminhmy;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {

                    case R.id.nav_trangchu:
                        Toast.makeText(getApplicationContext(),"Màn Hình Home",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_danhsachcuahang:
                        Toast.makeText(getApplicationContext(),"Màn Hình Danh Sách Cửa hàng",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_tinnhan:
                        Toast.makeText(getApplicationContext(),"Màn Hình Tin Nhắn",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_follower:
                        Toast.makeText(getApplicationContext(),"Màn Hình Follower",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_giohang:
                        Toast.makeText(getApplicationContext(),"Màn Hình Giỏ Hàng",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_lichsu:
                        Toast.makeText(getApplicationContext(),"Màn Hình Lịch Sử",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_caidat:
                        Toast.makeText(getApplicationContext(),"Màn Hình Cài Đặt",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_login:
                        Toast.makeText(getApplicationContext(),"Màn Hình Login",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_logout:
                        Toast.makeText(getApplicationContext(),"Màn Hình Logout",Toast.LENGTH_LONG).show();
                        break;
                }
                return false;
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}