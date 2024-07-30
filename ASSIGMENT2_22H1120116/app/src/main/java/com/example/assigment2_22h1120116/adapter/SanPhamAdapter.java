package com.example.assigment2_22h1120116.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assigment2_22h1120116.Product_detail;
import com.example.assigment2_22h1120116.R;
import com.example.assigment2_22h1120116.model.SanPham;
import com.example.assigment2_22h1120116.ultil.CheckConnection;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ItemHolder> {
    private Context context;
    private ArrayList<SanPham> listsp;

    public SanPhamAdapter(Context context, ArrayList<SanPham> listsp) {
        this.context = context;
        this.listsp = listsp;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<SanPham> getListsp() {
        return listsp;
    }

    public void setListsp(ArrayList<SanPham> listsp) {
        this.listsp = listsp;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_sanpham,null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        SanPham sanPham = listsp.get(position);
        holder.txttensp.setText(sanPham.getProduct_name());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiasp.setText("Giá: "+ decimalFormat.format(sanPham.getPrice())+"VND");
        Glide.with(context).load(sanPham.getAnh_sp())
                .placeholder(R.drawable.baseline_notification_important_24)
                .error(R.drawable.baseline_error_24)
                .into(holder.imsp);
    }

    @Override
    public int getItemCount() {
        return listsp.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imsp;
        public TextView txttensp,txtgiasp;

        public ItemHolder(View itemView){
            super(itemView);
            imsp  =itemView.findViewById(R.id.ivsp);
            txttensp = itemView.findViewById(R.id.txttensp);
            txtgiasp = itemView.findViewById(R.id.txtpricesp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, Product_detail.class);
                        intent.putExtra("thongtinsanpham", listsp.get(position));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        CheckConnection.showToast_Short(context, listsp.get(position).getProduct_name());
                        context.startActivity(intent);
                    }
                }
            });

        }
    }

}
