package com.example.assigment2_22h1120116;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.assigment2_22h1120116.fragment.AboutFragment;
import com.example.assigment2_22h1120116.fragment.ProductFragment;
import com.google.android.material.navigation.NavigationView;

public class screen_admin extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_admin);
         // anhxa
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationview);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_reorder_24);


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.linearlayout,new ProductFragment())
                .commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if(item.getItemId() == R.id.mQLSP){
                    fragment = new ProductFragment();
                } else if (item.getItemId() == R.id.mgioithieu) {
                    fragment = new AboutFragment();

                } else if (item.getItemId() == R.id.mdangxuat) {
                    new AlertDialog.Builder(screen_admin.this)
                            .setTitle("Xác nhận đăng xuất")
                            .setMessage("Bạn có muốn đăng xuất không?")
                            .setIcon(R.drawable.baseline_notification_important_24)
                            .setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Nếu người dùng đồng ý đăng xuất, thực hiện chuyển sang màn hình đăng nhập (screen1)
                                    Intent intent = new Intent(screen_admin.this, screen1.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("Hủy", null)
                            .show();
                    // Không cần chuyển Fragment khi hiển thị AlertDialog
                    return false;
                }

                else{
                    fragment = new ProductFragment();
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.linearlayout,fragment)
                        .commit();
                getSupportActionBar().setTitle(item.getTitle());

                drawerLayout.closeDrawer(GravityCompat.START);

                return false;
            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }






}