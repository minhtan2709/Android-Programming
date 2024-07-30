package com.example.assigment2_22h1120116;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import android.content.Intent;

import android.content.SearchRecentSuggestionsProvider;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.assigment2_22h1120116.adapter.WildPassAdapter;
import com.example.assigment2_22h1120116.model.SanPham;
import com.example.assigment2_22h1120116.ultil.CheckConnection;
import com.example.assigment2_22h1120116.ultil.Server;
import com.google.android.material.internal.TouchObserverFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class user_screen3 extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;
    private WildPassAdapter wildPassAdapter;
    ArrayList<SanPham> listwildpass;
    int idwp = 0;
    int page =1;
    private View footerview;
    private boolean isloading =false;
    private mHandler mHandler ;
    private boolean limitdata =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wildpass);

        toolbar = findViewById(R.id.toolbarwildpass);
        listView = findViewById(R.id.lvwildpass);
        listwildpass = new ArrayList<>();
        wildPassAdapter = new WildPassAdapter(getApplicationContext(),listwildpass);
        listView.setAdapter(wildPassAdapter);
        mHandler = new mHandler();

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.grogressbar, null);
        if(CheckConnection.isNetworkAvailable(getApplicationContext())){
            GetCategoryId();
            ActionToolbar();
            loadMoreData();
            getData(page);
        }
        else {
            CheckConnection.showToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
            finish();
        }

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
    private void loadMoreData() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),Product_detail.class);
                intent.putExtra("thongtinsanpham",listwildpass.get(position));
                startActivity(intent);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int i) {

            }

            @Override
            public void onScroll(AbsListView view, int FirstItem, int VisibleItem, int TotalItem) {
                if(FirstItem + VisibleItem == TotalItem && TotalItem !=0&& isloading == false && limitdata  == false){
                    isloading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void ActionToolbar(){

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_new_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();            }
        });
    }
    private void getData(int Page){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String link = Server.linkgc+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int ID = 0;
                String product_name ="";
                String description = "";
                String anh  = "";
                int price = 0;
                int product_ID =0;
                if(response != null && response.length()!= 2){
                    listView.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            product_name = jsonObject.getString("product_name");
                            price =jsonObject.getInt("price");
                            anh = jsonObject.getString("anh");
                            description  =jsonObject.getString("description");
                            product_ID = jsonObject.getInt("product_id");
                            listwildpass.add(new SanPham(ID,product_name,price,anh,description,product_ID));
                            wildPassAdapter.notifyDataSetChanged();
                        }
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                else{
                    limitdata =true;
                    listView.removeFooterView(footerview);

                    CheckConnection.showToast_Short(getApplicationContext(),"Đã hết dữ liệu ");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast_Short(getApplicationContext(),error.toString());


            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("idsanpham",String.valueOf(idwp));

                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void GetCategoryId(){
        idwp = getIntent().getIntExtra("category_id",-1);
        Log.d("giatricategory",idwp+"");
    }
    public  class mHandler extends Handler{
        @Override
        public void handleMessage( Message msg) {
            switch (msg.what){
                case 0:
                    listView.addFooterView(footerview);
                    break;
                case 1:

                    getData(++page);
                    isloading =false;
                    break;

            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends  Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try{
                Thread.sleep(3000);

            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }




}