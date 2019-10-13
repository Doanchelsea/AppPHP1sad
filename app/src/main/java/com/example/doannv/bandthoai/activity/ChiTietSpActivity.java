package com.example.doannv.bandthoai.activity;

import android.content.Intent;
import android.support.design.internal.ThemeEnforcement;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doannv.bandthoai.R;
import com.example.doannv.bandthoai.model.Giohang;
import com.example.doannv.bandthoai.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietSpActivity extends AppCompatActivity {
    Toolbar toolbarchitietsp;
    ImageView imgchitietsp;
    TextView txtchitietsp;
    TextView txtgiachitietsp;
    Spinner spinner;
    Button btndatmua;
    TextView txtmotasp;
    int id = 0;
    String tenchitiet = "";
    int giachitiet = 0;
    String hinhanhchitiet = "";
    String motachitiet = "";
    int idsanpham = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sp);
        Anhxa();
        ActionToolbar();
        GetInformation();
        CatchEventSpinner();
        EventButton();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent  = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void EventButton() {
        btndatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.manggiohang.size()>0){
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for (int i=0; i<MainActivity.manggiohang.size();i++){
                        if (MainActivity.manggiohang.get(i).getIdsp() == id){
                            MainActivity.manggiohang.get(i).setSoluongsp(MainActivity.manggiohang.get(i).getSoluongsp() + sl);
                            if (MainActivity.manggiohang.get(i).getSoluongsp() >=10){
                                MainActivity.manggiohang.get(i).setSoluongsp(10);
                            }
                            MainActivity.manggiohang.get(i).setGiasp(giachitiet * MainActivity.manggiohang.get(i).getSoluongsp());
                            exists = true;
                        }
                    }
                    if (exists == false){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long Giamoi = soluong * giachitiet;
                        MainActivity.manggiohang.add(new Giohang(id,tenchitiet,Giamoi,hinhanhchitiet,soluong));
                    }
                }else {
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long Giamoi = soluong * giachitiet;
                    MainActivity.manggiohang.add(new Giohang(id,tenchitiet,Giamoi,hinhanhchitiet,soluong));
                }
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);

            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformation() {

        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
         id = sanpham.getID();
         tenchitiet = sanpham.getTenSanPham();
         giachitiet = sanpham.getGiaSanPham();
         hinhanhchitiet = sanpham.getHinhanhsanpham();
         motachitiet = sanpham.getMotasanpham();
         idsanpham = sanpham.getIDsanpham();
         txtchitietsp.setText(tenchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgiachitietsp.setText("Giá:" + decimalFormat.format(giachitiet)+" VNĐ");
        txtmotasp.setText(motachitiet);
        Picasso.get().load(hinhanhchitiet)
                .placeholder(R.drawable.ic_menu)
                .error(R.drawable.ic_menu2)
                .into(imgchitietsp);
    }

    private void ActionToolbar() {
         setSupportActionBar(toolbarchitietsp);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         toolbarchitietsp.setNavigationOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });
    }

    private void Anhxa() {
        toolbarchitietsp = (Toolbar) findViewById(R.id.toolbarchitietsp);
        imgchitietsp = (ImageView) findViewById(R.id.imgchitietsp);
        txtchitietsp = (TextView) findViewById(R.id.txtchitietsp);
        txtgiachitietsp = (TextView) findViewById(R.id.txtgiachitietsp);
        spinner = (Spinner) findViewById(R.id.spinner);
        btndatmua = (Button) findViewById(R.id.btndatmua);
        txtmotasp = (TextView) findViewById(R.id.txtmotasp);
    }

}
