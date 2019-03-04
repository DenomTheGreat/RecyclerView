package com.example.naveen.recyclerview.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.naveen.recyclerview.R;
import com.example.naveen.recyclerview.Utility.ListItem;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    @NonNull

    private List<ListItem> listItems;

    public Adapter(@NonNull List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    private Context context;
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ListItem listItem = listItems.get(position);
        viewHolder.textViewTitle.setText(listItem.getTitle());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewTitle;
        public ImageView imageViewImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = (TextView)itemView.findViewById(R.id.title);
            imageViewImage = (ImageView)itemView.findViewById(R.id.image);
        }
    }
}
