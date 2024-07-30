package com.example.assigment2_22h1120116.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.assigment2_22h1120116.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<com.example.assigment2_22h1120116.model.Cart> listcart;

    public CartAdapter(Context context, ArrayList<com.example.assigment2_22h1120116.model.Cart> listcart) {
        this.context = context;
        this.listcart = listcart;
    }




    @Override
    public int getCount() {
        return listcart.size();
    }

    @Override
    public Object getItem(int position) {
        return listcart.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txttencart,txtgiacart;
        public ImageView ivcart;
        public Button btnminus,btnvalue,btnadd;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder= new ViewHolder();
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.line_cart,null);

            viewHolder.txttencart = view.findViewById(R.id.txttencart);
            viewHolder.txtgiacart = view.findViewById(R.id.txtgiacart);
            viewHolder.ivcart = view.findViewById(R.id.ivcart);

            viewHolder.btnvalue = view.findViewById(R.id.btnvalue);

            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();

        }
        com.example.assigment2_22h1120116.model.Cart cart = (com.example.assigment2_22h1120116.model.Cart)getItem(position);
        viewHolder.txttencart.setText(cart.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiacart.setText("Giá: "+ decimalFormat.format(cart.getGiasp())+"VND");
        Glide.with(context).load(cart.getHinhsp())
                .placeholder(R.drawable.baseline_notification_important_24)
                .error(R.drawable.baseline_error_24)
                .into(viewHolder.ivcart);

        viewHolder.btnvalue.setText(cart.getSoluongsp()+" ");

        return  view;



    }


}
