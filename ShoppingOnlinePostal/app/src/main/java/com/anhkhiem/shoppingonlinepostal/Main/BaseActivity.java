package com.anhkhiem.shoppingonlinepostal.Main;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.anhkhiem.shoppingonlinepostal.Cache.SaveCache;
import com.anhkhiem.shoppingonlinepostal.Login.data_models.User;
import com.anhkhiem.shoppingonlinepostal.Navigation.Adapter_Icons;
import com.anhkhiem.shoppingonlinepostal.Navigation.data_model.MenuIcons;
import com.anhkhiem.shoppingonlinepostal.PermissionUtil.PermissionUserUtil;
import com.anhkhiem.shoppingonlinepostal.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BaseActivity extends AppCompatActivity {
    String imgUrl,email,name;
    Intent intent;
    DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    CircleImageView cImgUser;
    TextView tvEmail, tvName;
    ListView lvMenuIcon;
    private String logoutMessage = "Bạn muốn đăng xuất?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);

        intent = new Intent();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        anhXa();
        initMenu();

    }

    private void anhXa() {
        cImgUser = findViewById(R.id.imageNav);
        tvName = findViewById(R.id.txtNameNav);
        tvEmail = findViewById(R.id.txtEmailNav);
        lvMenuIcon = findViewById(R.id.lvMenu);
    }

    public void addContenView(int layoutID) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(layoutID, null, false);
        drawerLayout.addView(contentView, 0);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    void initMenu(){
        final List<MenuIcons> lstIconMenu = new ArrayList<>();
        PermissionUserUtil.with(this).checkPermission(new PermissionUserUtil.IPermissionUser() {
            @Override
            public void admin() {

            }

            @Override
            public void manager() {

            }

            @Override
            public void user() {

            }
        });

        Adapter_Icons adapterMenuIcon =new Adapter_Icons(this,lstIconMenu);
        lvMenuIcon.setAdapter(adapterMenuIcon);

        lvMenuIcon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MenuIcons menuIcons =lstIconMenu.get(i);
                switch (menuIcons.getTypeMenu()){
                    case HOME:
                        startActivity(new Intent(BaseActivity.this,MainActivity.class));
                        break;
                    case LOGOUT:
                        logout();
                        break;
                }
            }
        });
        if (!SaveCache.getInstance(this).isLogin()){
            lstIconMenu.add(new MenuIcons(MenuIcons.TypeMenu.LOGIN,R.drawable.ic_baseline_accessibility_24,"Đăng Nhập"));
        }else {
            lstIconMenu.add(new MenuIcons(MenuIcons.TypeMenu.LOGOUT,R.drawable.ic_launcher_foreground,"Đăng Xuất"));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkLogin(){
        if (SaveCache.getInstance(this).isLogin()){
            imgUrl = SaveCache.getInstance(this).getUser().getImage();
            name = SaveCache.getInstance(this).getUser().getName();
            email = SaveCache.getInstance(this).getUser().getEmail();
            tvName.setText(name);
            tvEmail.setText(email);
            Picasso.get().load(imgUrl).into(cImgUser);
        }else {
            tvName.setText("Shopping Online Postal Xin Chào");
            tvEmail.setText("");
            cImgUser.setImageDrawable(getDrawable(R.drawable.ic_launcher_foreground));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onStart() {
        super.onStart();
        checkLogin();
        initMenu();
    }

    private void logout() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage(logoutMessage)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvName.setText("Shopping Online Postal Xin chào");
                        tvEmail.setText("");
                        cImgUser.setImageDrawable(getDrawable(R.drawable.ic_launcher_background));
                        SaveCache.getInstance(BaseActivity.this).setUser(new User());
                        SaveCache.getInstance(BaseActivity.this).setLogin(false);
                        initMenu();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
}