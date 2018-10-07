package com.cenet.mobsecure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by ANKIT on 9/7/2018.
 */

public class CustomGridAdapter extends BaseAdapter {
    Context context;
    String[] modList;
    LayoutInflater inflater;
    CustomGridAdapter(Context context,String[] modList){
        this.context = context;
        this.modList = modList;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return modList.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class Holder {
        TextView txtTitle;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView = inflater.inflate(R.layout.grid_item,null);
        holder.txtTitle = (TextView) rowView.findViewById(R.id.txtTitle);

        holder.txtTitle.setText(modList[position].toString());
        return rowView;
    }
}
