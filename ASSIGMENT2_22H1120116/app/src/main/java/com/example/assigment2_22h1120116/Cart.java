package com.example.assigment2_22h1120116;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.assigment2_22h1120116.adapter.CartAdapter;
import com.example.assigment2_22h1120116.model.SanPham;
import com.example.assigment2_22h1120116.ultil.CheckConnection;
import com.example.assigment2_22h1120116.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Cart extends AppCompatActivity {
private ListView lvcart;
private TextView txtthongbao;
private static TextView txttongtien;
private Button btnthanhtoan,btnttmua;
private Toolbar toolbarcart;
private CartAdapter cartAdapter;
private SanPham sanPham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        lvcart = findViewById(R.id.lvcart);
        txtthongbao = findViewById(R.id.txtthongbao);
        txttongtien = findViewById(R.id.txttongtien);
        btnthanhtoan = findViewById(R.id.btnthanhtoancart);
        btnttmua = findViewById(R.id.btnttmua);
        toolbarcart = findViewById(R.id.toolbarcart);

        cartAdapter = new CartAdapter(Cart.this,user_screen1.listcart);
        lvcart.setAdapter(cartAdapter);
        if(CheckConnection.isNetworkAvailable(getApplicationContext())) {
            ActionToolbar();
            CheckData();
            EvenUltil();
            CatchOnItemList();


        }
        else {
            CheckConnection.showToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
            finish();
        }
        btnttmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),user_screen1.class));
            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_screen1.listcart.size()>0){
                    Intent intent = new Intent(getApplicationContext(), InfoUser.class);
                    startActivity(intent);
                }
                else {
                    CheckConnection.showToast_Short(getApplicationContext(),"Bạn có sản phẩm để thanh toán");
                }


            }
        });



    }

    private void CatchOnItemList() {
        lvcart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder   builder  =new AlertDialog.Builder(Cart.this);
                builder.setTitle("Xác nhận xóa gói nạp");
                builder.setIcon(R.drawable.baseline_notification_important_24);
                ///
                builder.setMessage("Bạn có muốn xóa gói nạp\""+user_screen1.listcart.get(position).getTensp()+"\"không?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      if(user_screen1.listcart.size() <=0){
                          txtthongbao.setVisibility(View.VISIBLE);
                      }
                      else{
                          user_screen1.listcart.remove(position);
                          cartAdapter.notifyDataSetChanged();
                          EvenUltil();
                          if(user_screen1.listcart.size()<=0){
                              txtthongbao.setVisibility(View.VISIBLE);
                          }
                          else {
                              txtthongbao.setVisibility(View.INVISIBLE);
                              cartAdapter.notifyDataSetChanged();
                              EvenUltil();
                          }
                      }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      cartAdapter.notifyDataSetChanged();
                      EvenUltil();
                    }
                });
                builder.show();

                return true;
            }
        });

    }

    public static void EvenUltil(){
        long tongtien = 0;
        for(int i =0;i<user_screen1.listcart.size();i++){
            tongtien += user_screen1.listcart.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txttongtien.setText(decimalFormat.format(tongtien)+" VND");
    }

    private void CheckData() {
            if (user_screen1.listcart.size() <= 0) {
                cartAdapter.notifyDataSetChanged();
                txtthongbao.setVisibility(View.VISIBLE);
                lvcart.setVisibility((View.INVISIBLE));
            } else {
                cartAdapter.notifyDataSetChanged();
                txtthongbao.setVisibility(View.INVISIBLE);
                lvcart.setVisibility((View.VISIBLE));
            }

    }


private void ActionToolbar(){

    setSupportActionBar(toolbarcart);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_new_24);
    toolbarcart.setNavigationOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();            }
    });
}








}
