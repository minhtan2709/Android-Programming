package com.example.assigment2_22h1120116.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.assigment2_22h1120116.R;
import com.example.assigment2_22h1120116.adapter.ProductAdapter;
import com.example.assigment2_22h1120116.dao.SanPhamDao;
import com.example.assigment2_22h1120116.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductFragment extends Fragment {
    private RecyclerView recyclerProduct;
    private FloatingActionButton floatAdd;
    private SanPhamDao sanPhamDao;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product,container,false);

        //
        recyclerProduct = view.findViewById(R.id.recycleviewProduct);
        floatAdd = view.findViewById(R.id.floatadd);



        sanPhamDao = new SanPhamDao(getContext());

        loadData();

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdd();
            }
        });

        return view;
    }
    private void  loadData(){
        ArrayList<Product> list = sanPhamDao.getDS();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerProduct.setLayoutManager(linearLayoutManager);
        ProductAdapter adapter = new ProductAdapter(getContext(),list,sanPhamDao);
        recyclerProduct.setAdapter(adapter);
    }
    private void showDialogAdd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_screen3,null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //xu ly chuc nang
        EditText edttensp = view.findViewById(R.id.edttensp);
        EditText edtgiasp = view.findViewById(R.id.edtgia);
        EditText edtmota = view.findViewById(R.id.edtdes);
        EditText edtanh = view.findViewById(R.id.edtanh);



        Button btnadd = view.findViewById(R.id.btnadd);
        Button btnback = view.findViewById(R.id.btnback);


        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tensp = edttensp.getText().toString();
                int giasp = Integer.parseInt(edtgiasp.getText().toString());
                String mota = edtmota.getText().toString();
                String anhsp = edtanh.getText().toString();



                if(tensp.length() == 0 || String.valueOf(giasp).length()  == 0||mota.length() == 0||anhsp.length() == 0){
                    Toast.makeText(getContext(),"Nhập đầy đủ thông tin!",Toast.LENGTH_SHORT).show();

                }
                else {
                    Product product = new Product(tensp,mota,giasp,anhsp);
                    boolean check = sanPhamDao.themSPmoi(product);
                    if(check){
                        Toast.makeText(getContext(),"Thêm sản phẩm thành công",Toast.LENGTH_SHORT).show();
                        loadData();
                        alertDialog.dismiss();
                    }
                    else {
                        Toast.makeText(getContext(),"Thêm sản phẩm thất bại",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


    }


}
