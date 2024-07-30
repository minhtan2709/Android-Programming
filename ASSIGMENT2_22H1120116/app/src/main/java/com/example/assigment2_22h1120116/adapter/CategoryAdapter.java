package com.example.assigment2_22h1120116.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.assigment2_22h1120116.R;
import com.example.assigment2_22h1120116.model.Category;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {
    ArrayList<Category> listcategory;
    Context context;

    public CategoryAdapter(ArrayList<Category> listcategory, Context context) {
        this.listcategory = listcategory;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listcategory.size();
    }

    @Override
    public Object getItem(int position) {
        return listcategory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        TextView txtcategory;
        ImageView ivcategory;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_category, null);
            viewHolder = new ViewHolder();
            viewHolder.txtcategory = (TextView) view.findViewById(R.id.txtcategory);
            viewHolder.ivcategory = (ImageView) view.findViewById(R.id.ivcategory);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Category category = listcategory.get(position);
        viewHolder.txtcategory.setText(category.getCategory_name());
        Glide.with(context).load(category.getAnh())
                .placeholder(R.drawable.baseline_notification_important_24)
                .error(R.drawable.baseline_error_24)
                .into(viewHolder.ivcategory);


        return view;
    }


}
