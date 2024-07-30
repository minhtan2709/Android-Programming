package com.example.assigment2_22h1120116;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.assigment2_22h1120116.model.Cart;
import com.example.assigment2_22h1120116.model.SanPham;

import java.text.DecimalFormat;

public class Product_detail extends AppCompatActivity {
private Toolbar toolbar;
private ImageView ivdetail;
private TextView txtgia,txtten,txtmota;
private Spinner spinner;
private Button btndatmua;
private int id =0;
private  String tendetail = "";
private  int giadetail = 0;
private  String anhdetail = "";
private  String motadetail = "";
private int idsp =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);

        toolbar = findViewById(R.id.toolbardetailsp);
        ivdetail  =findViewById(R.id.ivdetailsp);
        txtten = findViewById(R.id.txtdetailsp);
        txtgia = findViewById(R.id.txtgiadetailsp);
        txtmota = findViewById(R.id.txtmotadetailsp);
        spinner = findViewById(R.id.spndetailsp);
        btndatmua =  findViewById(R.id.btndatmua);
        
        ActionBar();
        getInfomation();
        catchSpinner();
        EventButton();
        

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menucart) {

            Intent intent = new Intent(getApplicationContext(), com.example.assigment2_22h1120116.Cart.class);
            startActivity(intent);
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void EventButton() {
     btndatmua.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if(user_screen1.listcart.size()>0){
                 int s1 = Integer.parseInt(spinner.getSelectedItem().toString());
                 boolean exists = false;
                 for(int i =0; i<user_screen1.listcart.size();i++){
                     if(user_screen1.listcart.get(i).getIdsp() == id){
                        user_screen1.listcart.get(i).setSoluongsp(user_screen1.listcart.get(i).getSoluongsp() + s1);
                        if(user_screen1.listcart.get(i).getSoluongsp() >=10){
                            user_screen1.listcart.get(i).setSoluongsp(10);
                        }
                        user_screen1.listcart.get(i).setGiasp(giadetail * user_screen1.listcart.get(i).getSoluongsp());
                        exists = true;
                     }
                 }
                 if(exists == false){
                     int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                     long giamoi = sl * giadetail;
                     user_screen1.listcart.add(new Cart(id,tendetail,giamoi,anhdetail,sl));
                 }
             }
             else {
                 int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                 long giamoi = sl * giadetail;
                 user_screen1.listcart.add(new Cart(id,tendetail,giamoi,anhdetail,sl));

             }
             Intent   intent = new Intent(getApplicationContext(), com.example.assigment2_22h1120116.Cart.class);
             startActivity(intent);
         }
     });

    }

    private void catchSpinner() {
    Integer [] soluong = new Integer []{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);

    }

    private void getInfomation() {
        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanPham.getID();
        tendetail = sanPham.getProduct_name();
        giadetail = sanPham.getPrice();
        anhdetail = sanPham.getAnh_sp();
        idsp = sanPham.getProduct_ID();
        motadetail = sanPham.getDescription();

        txtten.setText(tendetail);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText("Giá: "+decimalFormat.format(giadetail)+"VND");
        txtmota.setText(motadetail);
        Glide.with(getApplicationContext()).load(sanPham.getAnh_sp())
                .placeholder(R.drawable.baseline_notification_important_24)
                .error(R.drawable.baseline_error_24)
                .into(ivdetail);

    }


    private void ActionBar() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_new_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();            }
        });
    }


}