package com.example.assigment2_22h1120116.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cloudinary.android.download.glide.GlideDownloadRequestBuilderFactory;
import com.example.assigment2_22h1120116.R;
import com.example.assigment2_22h1120116.dao.SanPhamDao;
import com.example.assigment2_22h1120116.model.Product;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class ProductAdapter extends  RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Product> list;
    private SanPhamDao sanPhamDao;

    public ProductAdapter(Context context, ArrayList<Product> list, SanPhamDao sanPhamDao) {
        this.context = context;
        this.list = list;
        this.sanPhamDao = sanPhamDao;
    }

    public ProductAdapter(Context context, ArrayList<Product> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_product,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.txtname.setText(list.get(position).getTensp());

    NumberFormat format = new DecimalFormat("#,###");
    double myNumber = list.get(position).getGiaban();
    String formattedNumber = format .format(myNumber);

    holder.txtprice.setText(formattedNumber +"VND");
    holder.txtmota.setText(list.get(position).getDessp());

    Glide.with(context).load(list.get(position).getAnhsp()).into(holder.ivanh);



    holder.ivedit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showDialogUpdate(list.get(holder.getAdapterPosition()));

        }
    });
    holder.ivdelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        showDialogDelete(
                list.get(holder.getAdapterPosition()).getTensp(),
                list.get(holder.getAdapterPosition()).getMasp());
        }
    });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
    TextView txtname;
    TextView txtprice;
    TextView txtmota;
    ImageView ivanh;
    ImageView ivedit;
    ImageView ivdelete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtname = itemView.findViewById(R.id.txtname);
            txtprice = itemView.findViewById(R.id.txtprice);
            txtmota = itemView.findViewById(R.id.txtmota);
            ivanh = itemView.findViewById(R.id.ivanh);
            ivedit = itemView.findViewById(R.id.ivedit);
            ivdelete = itemView.findViewById(R.id.ivdelete);

        }
    }
    private void showDialogUpdate(Product product){
        AlertDialog.Builder  builder =new AlertDialog.Builder(context);
        LayoutInflater layoutInflater  =((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.edit_screen3,null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText edttensp = view.findViewById(R.id.edttensp);
        EditText edtgiaban = view.findViewById(R.id.edtgia);
        EditText edtmota = view.findViewById(R.id.edtdes);
        EditText edtanh = view.findViewById(R.id.edtanh);
        Button btnsua  = view.findViewById(R.id.btnedit);
        Button btnback = view.findViewById(R.id.btnback);

        // dua du lieu san pham vao tren edt
        edttensp.setText(product.getTensp());
        edtgiaban.setText(String.valueOf(product.getGiaban()));
        edtmota.setText(product.getDessp());
        edtanh.setText(product.getAnhsp());

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int masp = product.getMasp();
                String tensp = edttensp.getText().toString();
                String giasp = edtgiaban.getText().toString();
                String dessp = edtmota.getText().toString();
                String anhsp = edtanh.getText().toString();



                if(tensp.length() ==0|| giasp.length() ==0||dessp.length()==0||anhsp.length()==0){
                    Toast.makeText(context,"Nhập đầy đủ thông tin!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Product chinhsuasp = new Product(masp,tensp,dessp,Integer.parseInt(giasp),anhsp);
                    boolean check = sanPhamDao.chinhsuaSP(chinhsuasp);
                    if(check){
                        Toast.makeText(context,"Chỉnh sửa thành công",Toast.LENGTH_SHORT).show();
                        list.clear();
                        list = sanPhamDao.getDS();
                        notifyDataSetChanged();
                        alertDialog.dismiss();
                    }
                    else{
                        Toast.makeText(context,"Chỉnh sửa thất bại!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private void showDialogDelete(String tensp,int masp){
        AlertDialog.Builder   builder  =new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setIcon(R.drawable.baseline_notification_important_24);
        ///
        builder.setMessage("Bạn có muốn xóa sản phẩm \""+tensp+"\"không?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean check = sanPhamDao.XoaSp(masp);
                if(check){
                    Toast.makeText(context,"Xóa thành công",Toast.LENGTH_SHORT).show();

                    list.clear();
                    list =sanPhamDao.getDS();
                    notifyDataSetChanged();

                }
                else {
                    Toast.makeText(context,"Xóa thất bại",Toast.LENGTH_SHORT).show();

                }
            }
        });
        builder.setNegativeButton("Hủy",null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }

}
