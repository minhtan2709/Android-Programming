package com.example.assigment2_22h1120116;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;

import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.assigment2_22h1120116.ultil.CheckConnection;
import com.example.assigment2_22h1120116.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InfoUser extends AppCompatActivity {
private EditText edtusername,edtphonenumber,edtemail;
private Button btnxacnhan;
private ImageView ivback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);
        edtusername = findViewById(R.id.edtuser_name);
        edtemail = findViewById(R.id.edtuseremail);
        edtphonenumber= findViewById(R.id.edtusersdt);

        btnxacnhan = findViewById(R.id.btnxacnhan);
        ivback = findViewById(R.id.ivback);
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(CheckConnection.isNetworkAvailable(getApplicationContext())){
            EventButton();

        }
        else{
            CheckConnection.showToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
        }


    }

    private void EventButton() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ten = edtusername.getText().toString();
                final String sdt = edtphonenumber.getText().toString();
                final String email = edtemail.getText().toString();
                if (ten.length()>0 && sdt.length()>0 && email.length()>0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.linkdonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Log.d("madonhang",madonhang);
                            if(Integer.parseInt(madonhang)>0){
                                RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                                StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Server.linkdetailcart, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("1")){
                                            user_screen1.listcart.clear();
                                            CheckConnection.showToast_Short(getApplicationContext(),"Ban đã thêm dữu liệu vào giỏ hàng thành công");
                                            startActivity(new Intent(getApplicationContext(), Bank.class));
                                            CheckConnection.showToast_Short(getApplicationContext(),"Mời bạn thanh toán các gói nạp");


                                        }
                                        else{
                                            CheckConnection.showToast_Short(getApplicationContext(),"Dữ liệu đã bị lỗi");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Nullable
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for(int i =0; i<user_screen1.listcart.size();i++){
                                            JSONObject jsonObject = new JSONObject();

                                            try {
                                                jsonObject.put("madonhang", madonhang);
                                                jsonObject.put("masanpham", user_screen1.listcart.get(i).getIdsp());
                                                jsonObject.put("tensanpham", user_screen1.listcart.get(i).getTensp());
                                                jsonObject.put("giasanpham", user_screen1.listcart.get(i).getGiasp());
                                                jsonObject.put("soluongsanpham", user_screen1.listcart.get(i).getSoluongsp());

                                            }
                                            catch(JSONException e){
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }

                                        HashMap<String,String> hashMap = new HashMap<>();
                                        hashMap.put("json",jsonArray.toString());

                                        return hashMap;
                                    }
                                };
                                requestQueue1.add(stringRequest1);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put("username",ten);
                            hashMap.put("phonenumber",sdt);
                            hashMap.put("email",email);

                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
                else{
                    CheckConnection.showToast_Short(getApplicationContext(),"Hãy kiểm tra lai dữ liệu");
                }

            }
        });
    }


}