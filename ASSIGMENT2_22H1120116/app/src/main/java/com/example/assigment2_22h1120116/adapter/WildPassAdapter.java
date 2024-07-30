package com.example.assigment2_22h1120116.adapter;

import android.content.Context;
import android.text.Layout;
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

public class WildPassAdapter extends BaseAdapter {
    private Context context;
    ArrayList<SanPham> listwildpass;

    public WildPassAdapter(Context context, ArrayList<SanPham> listwildpass) {
        this.context = context;
        this.listwildpass = listwildpass;
    }

    @Override
    public int getCount() {
        return listwildpass.size();
    }

    @Override
    public Object getItem(int position) {
        return listwildpass.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txtwildpass,txtgiawildpass,txtmotawildpass;
        public ImageView ivwildpass;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.line_wildpass,null);
            viewHolder.txtwildpass = view.findViewById(R.id.txtwildpass);
            viewHolder.txtgiawildpass = view.findViewById(R.id.txtgiawildpass);
            viewHolder.txtmotawildpass = view.findViewById(R.id.txtmotawildpass);
            viewHolder.ivwildpass = view.findViewById(R.id.ivwildpass);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }
        SanPham sanpham =(SanPham) getItem(position);
        viewHolder.txtwildpass.setText(sanpham.getProduct_name());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiawildpass.setText("Giá: "+ decimalFormat.format(sanpham.getPrice())+"VND");
        viewHolder.txtmotawildpass.setMaxLines(2);
        viewHolder.txtmotawildpass.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotawildpass.setText(sanpham.getDescription());
        Glide.with(context).load(sanpham.getAnh_sp())
                .placeholder(R.drawable.baseline_notification_important_24)
                .error(R.drawable.baseline_error_24)
                .into(viewHolder.ivwildpass);
        return view;
    }
}
