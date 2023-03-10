package com.example.worker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.worker.model.CongViec;
import com.example.worker.model.CongViecAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CongViecAdapter.CongViecItemListioner
,SearchView.OnQueryTextListener{
    private int[] imgs = {R.drawable.male, R.drawable.female};
    private EditText txtTencv, txtDesc;
    private RadioGroup radioGroup;
    private EditText eDate;
    private Button btAdd, btUpdate, btDel;
    private SearchView searchView;
    private RecyclerView recyclerView;

    private CongViecAdapter adapter;

    private List<CongViec> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        onInit();
        eDate.setOnClickListener(this);
        btAdd.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        adapter.setCongViecItemListioner(this);
        searchView.setOnQueryTextListener(this);
    }

    private void initView() {
        txtTencv = findViewById(R.id.txtTencv);
        txtDesc = findViewById(R.id.txtDesc);
        radioGroup = findViewById(R.id.ggioitinh);
        eDate = findViewById(R.id.txtDate);
        btAdd = findViewById(R.id.btAdd);
        btUpdate = findViewById(R.id.btUpdate);
        searchView = findViewById(R.id.search);
        recyclerView = findViewById(R.id.rView);
        btUpdate.setEnabled(false);
    }

    private void onInit() {
        mList = new ArrayList<>();
        mList.add(new CongViec("Lau nha", "Mieu ta Lau nha", "Nam", "22/03/2022"));
        mList.add(new CongViec("Quet nha", "Mieu ta Quet nha", "Nu", "23/04/2022"));
        adapter = new CongViecAdapter(this, mList);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public void onClick(View view) {
        if (view == eDate) {
            Calendar calendar = Calendar.getInstance();
            int y = calendar.get(Calendar.YEAR);
            int m = calendar.get(Calendar.MONTH);
            int d = calendar.get(Calendar.DATE);
            DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                    eDate.setText(dd + "/ " + (mm + 1) + "/ " + yy);
                }
            }, y, m, d);
            dialog.show();
        }
        if (btAdd == view) {

            String ten = txtTencv.getText().toString();
            String desc = txtDesc.getText().toString();
            int selectedId = radioGroup.getCheckedRadioButtonId();
            RadioButton gioitinhRadio = findViewById(selectedId);
            String gioitinh = gioitinhRadio.getText().toString();
            String date = eDate.getText().toString();
            if (ten.isEmpty() || desc.isEmpty() || selectedId == -1 || date.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Nhap day du thong tin", Toast.LENGTH_SHORT).show();
            } else {
                CongViec cv = new CongViec();
                cv.setTen(ten);
                cv.setDesc(desc);
                cv.setGioitinh(gioitinh);
                cv.setDate(date);

                adapter.add(cv);

                txtTencv.setText("");
                txtDesc.setText("");
                radioGroup.check(-1);
                eDate.setText("");
            }
        }
        if(btUpdate == view){
            String ten = txtTencv.getText().toString();
            String desc = txtDesc.getText().toString();
            int selectedId = radioGroup.getCheckedRadioButtonId();
            RadioButton gioitinhRadio = findViewById(selectedId);
            String gioitinh = gioitinhRadio.getText().toString();
            String date = eDate.getText().toString();
            if (ten.isEmpty() || desc.isEmpty() || selectedId == -1 || date.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Nhap day du thong tin", Toast.LENGTH_SHORT).show();
            } else {
                CongViec cv = new CongViec();
                cv.setTen(ten);
                cv.setDesc(desc);
                cv.setGioitinh(gioitinh);
                cv.setDate(date);

                adapter.update(cv,currentPos);
                btAdd.setEnabled(true);
                btUpdate.setEnabled(false);
                txtTencv.setText("");
                txtDesc.setText("");
                radioGroup.check(-1);
                eDate.setText("");
            }
        }
    }

    int currentPos;

    @Override
    public void onItemClick(View view, int pos) {
        btAdd.setEnabled(false);
        btUpdate.setEnabled(true);
        currentPos = pos;
        CongViec cv = adapter.getItem(pos);
        String ten = cv.getTen();
        String desc = cv.getDesc();
        String gt = cv.getGioitinh();
        String date = cv.getDate();
        txtTencv.setText(ten);
        txtDesc.setText(desc);
        eDate.setText(date);
        if(gt.equals("Nam")){
            RadioButton radioButton = findViewById(R.id.gnam);
            radioButton.setChecked(true);
        }else{
            RadioButton radioButton = findViewById(R.id.gnu);
            radioButton.setChecked(true);
        }
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

    private void filter(String s){
        List<CongViec> filterList = new ArrayList<>();
        for(CongViec i : adapter.getBackup()){
            if(i.getTen().toLowerCase().contains(s.toLowerCase())){
                filterList.add(i);
            }
        }
        if(filterList.isEmpty()){
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }
        else{
            adapter.filterList(filterList);
        }
    }
}