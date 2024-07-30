
package com.example.assigment2_22h1120116;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.ActionBar;
        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.Toolbar;
        import androidx.core.app.ActivityCompat;
        import androidx.core.content.ContextCompat;
        import androidx.core.view.GravityCompat;
        import androidx.drawerlayout.widget.DrawerLayout;
        import androidx.recyclerview.widget.GridLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.app.DownloadManager;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Bundle;

        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.AdapterView;
        import android.widget.ImageView;
        import android.widget.ListView;

        import android.widget.ViewFlipper;

        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.JsonArrayRequest;
        import com.android.volley.toolbox.Volley;
        import com.bumptech.glide.Glide;
        import com.cloudinary.android.download.picasso.PicassoDownloadRequestBuilderStrategy;
        import com.example.assigment2_22h1120116.adapter.CategoryAdapter;
        import com.example.assigment2_22h1120116.adapter.SanPhamAdapter;
        import com.example.assigment2_22h1120116.model.Cart;
        import com.example.assigment2_22h1120116.model.Category;
        import com.example.assigment2_22h1120116.model.SanPham;
        import com.example.assigment2_22h1120116.ultil.CheckConnection;
        import com.example.assigment2_22h1120116.ultil.Server;
        import com.google.android.material.navigation.NavigationView;


        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;

public class user_screen1 extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewFlipper viewFlipper;
    private RecyclerView recyclerView;
    private NavigationView navigationView;
    private ListView listView;
    private DrawerLayout drawerLayout;
    private  ArrayList<Category> categories;

    private CategoryAdapter categoryAdapter;
    private int id =0;
    private String category_name = "";
    private String anh ="";
    private  ArrayList<SanPham> sanPhams;
    private SanPhamAdapter sanPhamAdapter;
    public static  ArrayList<Cart> listcart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_screen1);

        toolbar =  findViewById(R.id.toolbaruser_screen1);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerView = findViewById(R.id.recycleview);
        navigationView = findViewById(R.id.navigation_user);
        listView = findViewById(R.id.listviewuser_screen1);
        drawerLayout = findViewById(R.id.drawerLayout);
        categories = new ArrayList<>();
        categories.add(0,new Category(0,"Gói Nạp","https://res.cloudinary.com/dmkvyvdir/image/upload/v1712740636/ayjff9drygftrlyhsijw.jpg"));
        categoryAdapter = new CategoryAdapter(categories,getApplicationContext());
        listView.setAdapter(categoryAdapter);
        sanPhams = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(getApplicationContext(),sanPhams);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(sanPhamAdapter);

        if(listcart != null){

        }
        else{
            listcart =new ArrayList<>();
        }

        if(CheckConnection.isNetworkAvailable(getApplicationContext())){
            ActionBar();
            ActionViewFlipper();
            GetDataCategory();
            GetDataProduct();
            CatchOnItem();

        }
        else {
            CheckConnection.showToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
            finish();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
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


    // lay du lieu xuat ra spham
    private  void GetDataProduct(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.linkproduct, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int ID = 0;
                String product_name = "";
                Integer pricesp =0;
                String ivsp = "";
                String description ="";
                int product_ID = 0;
                if(response != null){
                    for(int i =0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID =jsonObject.getInt("id");
                            product_name =jsonObject.getString("product_name");
                            pricesp = jsonObject.getInt("price");
                            ivsp =jsonObject.getString("anh");
                            description = jsonObject.getString("description");

                            product_ID = jsonObject.getInt("product_id");
                           sanPhams.add(new SanPham(ID,product_name,pricesp,ivsp,description,product_ID));
                           sanPhamAdapter.notifyDataSetChanged();
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }



                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    private void GetDataCategory(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.linkcategory, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    for(int i =0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id =jsonObject.getInt("id");
                            category_name =jsonObject.getString("category_name");
                            anh =jsonObject.getString("anh");

                             categories.add(new Category(id,category_name,anh));

                             categoryAdapter.notifyDataSetChanged();

                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    categories.add(3,new Category(0,"Đăng xuất","https://res.cloudinary.com/dmkvyvdir/image/upload/v1712927036/p1fhuns2r6iowszdf8xp.jpg"));

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    private void ActionBar(){
        //set toolbar

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_reorder_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    private void ActionViewFlipper(){
        //set viewflipper
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://res.cloudinary.com/dnwkflkwu/image/upload/v1713832759/p43bpnnoeklyphi6vc6s.jpg");
        mangquangcao.add("https://res.cloudinary.com/dnwkflkwu/image/upload/v1713832760/uggf3jn38gbjhhgotnqk.jpg");

        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setAnimation(animation_slide_in);
        viewFlipper.setAnimation(animation_slide_out);

    }
    private void CatchOnItem(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if(CheckConnection.isNetworkAvailable(getApplicationContext())){
                            Intent intent = new Intent(user_screen1.this,user_screen1.class);
                            startActivity(intent);

                        }
                        else {
                            CheckConnection.showToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnection.isNetworkAvailable(getApplicationContext())){
                            Intent intent = new Intent(user_screen1.this,user_screen2.class);
                            intent.putExtra("category_id",categories.get(position).getId());
                            startActivity(intent);

                        }
                        else {
                            CheckConnection.showToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(CheckConnection.isNetworkAvailable(getApplicationContext())){
                            Intent intent = new Intent(user_screen1.this,user_screen3.class);
                            intent.putExtra("category_id",categories.get(position).getId());
                            startActivity(intent);

                        }
                        else {
                            CheckConnection.showToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 3:
                        if(CheckConnection.isNetworkAvailable(getApplicationContext())){
                            new AlertDialog.Builder(user_screen1.this)
                                    .setTitle("Xác nhận đăng xuất")
                                    .setMessage("Bạn có muốn đăng xuất không?")
                                    .setIcon(R.drawable.baseline_notification_important_24)
                                    .setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(user_screen1.this, screen1.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("Hủy", null)
                                    .show();
                        }
                        else {
                            CheckConnection.showToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }
            }
        });
    }




}