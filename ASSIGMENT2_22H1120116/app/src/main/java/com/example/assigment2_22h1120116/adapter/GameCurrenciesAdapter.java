package com.example.assigment2_22h1120116.adapter;

import android.content.Context;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.assigment2_22h1120116.R;
import com.example.assigment2_22h1120116.model.SanPham;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GameCurrenciesAdapter extends BaseAdapter {
    private Context context;
    ArrayList<SanPham> listgc;

    public GameCurrenciesAdapter(Context context, ArrayList<SanPham> listgc) {
        this.context = context;
        this.listgc = listgc;
    }

    @Override
    public int getCount() {
        return listgc.size();
    }

    @Override
    public Object getItem(int position) {
        return listgc.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txtgc,txtgiagc,txtmotagc;
        public ImageView ivgc;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.line_gamecurrencies,null);
            viewHolder.txtgc = view.findViewById(R.id.txtgc);
            viewHolder.txtgiagc = view.findViewById(R.id.txtgiagc);
            viewHolder.txtmotagc = view.findViewById(R.id.txtmotagc);
            viewHolder.ivgc = view.findViewById(R.id.ivgc);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }
        SanPham sanpham =(SanPham) getItem(position);
        viewHolder.txtgc.setText(sanpham.getProduct_name());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiagc.setText("Giá: "+ decimalFormat.format(sanpham.getPrice())+"VND");
        viewHolder.txtmotagc.setMaxLines(2);
        viewHolder.txtmotagc.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotagc.setText(sanpham.getDescription());
        Glide.with(context).load(sanpham.getAnh_sp())
                .placeholder(R.drawable.baseline_notification_important_24)
                .error(R.drawable.baseline_error_24)
                .into(viewHolder.ivgc);
        return view;
    }
}
