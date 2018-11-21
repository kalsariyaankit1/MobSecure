package com.cenet.mobsecure;

import android.content.Context;
import android.provider.Telephony;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CodeAdapter extends RecyclerView.Adapter<CodeAdapter.MyViewHolder> {
    Context context;
    List<Code> code;
    RecyclerView recyclerView;
    CodeAdapter(Context context, List<Code> code, RecyclerView recyclerView) {
        this.context = context;
        this.code = code;
        this.recyclerView = recyclerView;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.code_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.txttitle.setText(code.get(position).getTitle());
        holder.txtcode.setText(code.get(position).getCode());
        holder.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(code.get(position).getDestNumber(),null,"Hello",null,null);
                Toast.makeText(context,"Message Sent Successfully.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return code.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txttitle;
        public TextView txtcode;
        public ImageView btnSend;
        public MyViewHolder(View itemView) {
            super(itemView);
            txttitle = (TextView)itemView.findViewById(R.id.txttitle);
            txtcode = (TextView)itemView.findViewById(R.id.txtCode);
            btnSend = (ImageView) itemView.findViewById(R.id.btnSend);
        }
    }
}

