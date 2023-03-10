package com.example.recycleviewdemo_crud.model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleviewdemo_crud.R;

import java.util.ArrayList;
import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatViewHolder> {
    private Context context;
    private List<Cat> mList;
    private List<Cat> listBackup;
    private CatItemListener catItemListener;

    public void setCatItemListener(CatItemListener catItemListener) {
        this.catItemListener = catItemListener;
    }

    public CatAdapter(Context context) {
        this.context = context;
        mList = new ArrayList<>();
        listBackup = new ArrayList<>();
    }
    public List<Cat> getBackup(){
        return listBackup;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int pos) {
        Cat cat = mList.get(pos);
        if(cat == null){
            return;
        }
        holder.img.setImageResource(cat.getImg());
        holder.name.setText(cat.getName());
        holder.desc.setText(cat.getDesc());
        holder.price.setText(cat.getPrice()+"$");
        holder.btDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thong bao xoa");
                builder.setMessage("Ban co chac muon xoa "+cat.getName()+" nay khong");
                builder.setIcon(R.drawable.baseline_delete_forever_24);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listBackup.remove(pos);
                        mList.remove(pos);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mList != null){
            return mList.size();
        }
        return 0;
    }

    public void add(Cat cat) {
        listBackup.add(cat);
        mList.add(cat);
        notifyDataSetChanged();
    }



    public class CatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView img;
        private TextView name,desc,price;
        private Button btDel;
        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.txtName);
            desc = itemView.findViewById(R.id.txtDescription);
            price = itemView.findViewById(R.id.txtPrice);
            btDel = itemView.findViewById(R.id.btDel);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(catItemListener != null){
                catItemListener.onItemClick(view,getAdapterPosition());
            }
        }
    }

    public Cat getItem(int pos){
        return mList.get(pos);
    }
    public interface CatItemListener{
        public void onItemClick(View view,int position);
    }
    public void filterList(List<Cat> filterList){
        mList = filterList;
        notifyDataSetChanged();
    }
    public void update(int pos,Cat cat){
        listBackup.set(pos,cat);
        mList.set(pos,cat);
        notifyDataSetChanged();
    }
}
