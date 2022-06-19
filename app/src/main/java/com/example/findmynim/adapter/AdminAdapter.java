package com.example.findmynim.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmynim.R;
import com.example.findmynim.model.AdminModel;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminViewHolder> {

    private Context context;
    private List<AdminModel> list;
    private Dialog dialog;

    public interface Dialog{
        void onClick(int pos);
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public AdminAdapter(Context context, List<AdminModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowdata,parent,false);
        return new AdminViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder holder, int position) {
        holder.namamhs.setTextSize(20);
        holder.nimmhs.setTextSize(15);

        holder.namamhs.setTextColor(Color.BLACK);
        holder.nimmhs.setTextColor(Color.BLACK);

        holder.namamhs.setText(list.get(position).getNama());
        holder.nimmhs.setText(list.get(position).getNim());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AdminViewHolder  extends RecyclerView.ViewHolder{
        private CardView cardView;
        private TextView namamhs, nimmhs;

        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardinsertadmin);
            namamhs = itemView.findViewById(R.id.namamhs);
            nimmhs = itemView.findViewById(R.id.nimmhsi);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null){
                        dialog.onClick(getLayoutPosition());
                    }
                }
            });

        }
    }
}
