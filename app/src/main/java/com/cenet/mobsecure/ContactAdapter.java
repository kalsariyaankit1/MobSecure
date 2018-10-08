package com.cenet.mobsecure;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by ANKIT on 10/4/2018.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {
    Context context;
    List<Contacts> contactsList;
    RecyclerView recyclerView;
    ContactAdapter(Context context, List<Contacts> contactsList, RecyclerView recyclerView) {
        this.context = context;
        this.contactsList = contactsList;
        this.recyclerView = recyclerView;
    }
    @Override
    public ContactAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contactitem,parent,false);
        return new ContactAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactAdapter.MyViewHolder holder, int position) {
        holder.txtContact.setText(contactsList.get(position).getContact());
        holder.txtName.setText(contactsList.get(position).getCName());
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtContact;
        public TextView txtName;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtContact = (TextView)itemView.findViewById(R.id.txtContact);
            txtName = (TextView)itemView.findViewById(R.id.txtName);
        }
    }
}
