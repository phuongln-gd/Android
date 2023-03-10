package com.example.worker.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worker.R;

import java.util.ArrayList;
import java.util.List;

public class CongViecAdapter extends RecyclerView.Adapter<CongViecAdapter.CongViecViewHolder>{

    private Context context;
    private List<CongViec> mList;

    private List<CongViec> listBackup;

    private CongViecItemListioner congViecItemListioner;
    private int[] imgs = {R.drawable.male,R.drawable.female};

    public CongViecAdapter(Context context) {
        this.context = context;
        mList = new ArrayList<>();
        listBackup = new ArrayList<>();
    }
    public List<CongViec> getBackup(){
        return listBackup;
    }
    public CongViecAdapter(Context context, List<CongViec> mList) {
        this.context = context;
        this.mList = mList;
        listBackup = mList;
    }

    public void setCongViecItemListioner(CongViecItemListioner congViecItemListioner) {
        this.congViecItemListioner = congViecItemListioner;
    }

    public void add(CongViec cv){
        mList.add(cv);
        listBackup.add(cv);
        notifyDataSetChanged();
    }

    public CongViec getItem(int pos){
        return mList.get(pos);
    }
    public void update(CongViec cv, int position){
        mList.set(position,cv);
        listBackup.set(position,cv);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CongViecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new CongViecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CongViecViewHolder holder, int position) {
        CongViec congViec = mList.get(position);
        if(congViec == null)
            return;
        System.out.println(congViec.getGioitinh());
        if (congViec.getGioitinh().equals("Nam")){
            holder.img.setImageResource(imgs[0]);
        }
        else{
            holder.img.setImageResource(imgs[1]);
        }
        holder.tv.setText(congViec.getTen() +" - "+ congViec.getDate());
        holder.btDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mList.remove(position);
                listBackup.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mList != null)
            return mList.size();
        return 0;
    }

    public class CongViecViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView img;
        private TextView tv;

        private Button btDel;
        public CongViecViewHolder(@NonNull View view) {
            super(view);
            img = view.findViewById(R.id.img);
            tv = view.findViewById(R.id.txtCongViec);
            btDel = view.findViewById(R.id.btnDel);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (congViecItemListioner != null){
                congViecItemListioner.onItemClick(view,getAdapterPosition());
            }
        }
    }

    public interface CongViecItemListioner{
        void onItemClick(View view, int pos);
    }

    public void filterList(List<CongViec> filterList){
        mList = filterList;
        notifyDataSetChanged();
    }
}
