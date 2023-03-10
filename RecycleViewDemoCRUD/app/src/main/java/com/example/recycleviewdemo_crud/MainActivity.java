package com.example.recycleviewdemo_crud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.recycleviewdemo_crud.model.Cat;
import com.example.recycleviewdemo_crud.model.CatAdapter;
import com.example.recycleviewdemo_crud.model.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements CatAdapter.CatItemListener,SearchView.OnQueryTextListener {
    private Spinner sp;
    private RecyclerView recyclerView;
    private CatAdapter catAdapter;
    private EditText eName, eDesc, ePrice;
    private Button btAdd, btUpdate;

    private SearchView searchView;
    private int posCurrent;
    private int[] imgs = {
            R.drawable.cat_1,
            R.drawable.cat_2,
            R.drawable.cat_3,
            R.drawable.cat_4,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        catAdapter = new CatAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(catAdapter);
        catAdapter.setCatItemListener(this);
        searchView.setOnQueryTextListener(this);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cat cat = new Cat();
                String img = sp.getSelectedItem().toString();
                String name = eName.getText().toString();
                String desc = eDesc.getText().toString();
                String p = ePrice.getText().toString();
                int i_img = R.drawable.cat_1;
                double price = 0;
                try {
                    i_img = imgs[Integer.parseInt(img)];
                    price = Double.parseDouble(p);
                    cat.setImg(i_img);
                    cat.setName(name);
                    cat.setDesc(desc);
                    cat.setPrice(price);
                    catAdapter.add(cat);
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Nhap lai", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cat cat = new Cat();
                String img = sp.getSelectedItem().toString();
                String name = eName.getText().toString();
                String desc = eDesc.getText().toString();
                String p = ePrice.getText().toString();
                int i_img = R.drawable.cat_1;
                double price = 0;
                try {
                    i_img = imgs[Integer.parseInt(img)];
                    price = Double.parseDouble(p);
                    cat.setImg(i_img);
                    cat.setName(name);
                    cat.setDesc(desc);
                    cat.setPrice(price);
                    catAdapter.update(posCurrent, cat);
                    btAdd.setEnabled(true);
                    btUpdate.setEnabled(false);
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Nhap lai", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        sp = findViewById(R.id.sp_img);
        SpinnerAdapter adapter = new SpinnerAdapter(this);
        sp.setAdapter(adapter);
        recyclerView = findViewById(R.id.rView);
        eName = findViewById(R.id.eName);
        eDesc = findViewById(R.id.eDesc);
        ePrice = findViewById(R.id.ePrice);
        btAdd = findViewById(R.id.btAdd);
        btUpdate = findViewById(R.id.btUpdate);
        btUpdate.setEnabled(false);
        searchView = findViewById(R.id.search);
    }

    @Override
    public void onItemClick(View view, int position) {
        btAdd.setEnabled(false);
        btUpdate.setEnabled(true);
        posCurrent = position;
        Cat cat = catAdapter.getItem(posCurrent);
        int img = cat.getImg();
        int p = 0;
        for (int i = 0; i < imgs.length; i++) {
            if (img == imgs[i]) {
                p = i;
                break;
            }
        }
        sp.setSelection(p);
        eName.setText(cat.getName());
        eDesc.setText(cat.getDesc());
        ePrice.setText(cat.getPrice() + "");

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        filter(s);
        return false;
    }

    private void filter(String s) {
        List<Cat> filterList = new ArrayList<>();
        for(Cat i:catAdapter.getBackup()){
            if (i.getName().toLowerCase().contains(s.toLowerCase())){
                filterList.add(i);
            }
        }
        if(filterList.isEmpty()){
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }
        else{
            catAdapter.filterList(filterList);
        }
    }
}