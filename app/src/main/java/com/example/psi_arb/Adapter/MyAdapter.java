package com.example.recyclerview2.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.psi_arb.Model.ListItem;
import com.example.psi_arb.R;


import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private List<ListItem> listItems;

    public MyAdapter(Context context, List listItem) {
        this.context = context;
        this.listItems = listItem;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {


        ListItem item = listItems.get(position);
        holder.name.setText(item.getName());
        holder.content.setText(item.getContent());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name;
        public TextView content;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.titleTxt);
            content = (TextView) itemView.findViewById(R.id.contentTxt);

            //Event Listener
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            //Get Position
            int position = getAdapterPosition();
            ListItem item = listItems.get(position);

        }
    }
}
