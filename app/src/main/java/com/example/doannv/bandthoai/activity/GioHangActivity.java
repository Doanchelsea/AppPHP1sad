package com.example.doannv.bandthoai.activity;

import android.app.AlertDialog;
import android.app.usage.UsageEvents;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doannv.bandthoai.R;
import com.example.doannv.bandthoai.adapter.GioHangAdapter;
import com.example.doannv.bandthoai.model.Giohang;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {
     Toolbar toolbargiohang;
     TextView txtthongbao;
    static TextView txttongtien;
     ListView lvgiohang;
     Button btnthanhtoan;
     Button btnmuahang;
     GioHangAdapter gioHangAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        AnhXa();
        ActionToolbar();
        CheckData();
        EvenUltil();
        ItemListView();
        EventButton();
    }

    private void EventButton() {
        btnmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.manggiohang.size()>0){
                    Intent intent = new Intent(getApplicationContext(),ThongTinKhachHangActivity.class);
                    startActivity(intent);

                }else {
                    Toast.makeText(GioHangActivity.this, "Giỏ Hàng chưa có sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void ItemListView() {
        // bấm lâu mới nhận sk onclick
        lvgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
                  builder.setTitle("Thông Báo");
                  builder.setMessage("Bạn chắc chắn muốn xóa sản phẩm");
                  builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                       if (MainActivity.manggiohang.size()<=0){
                           txtthongbao.setVisibility(View.VISIBLE);
                       }else {
                           MainActivity.manggiohang.remove(position);
                           gioHangAdapter.notifyDataSetChanged();
                           EvenUltil();
                           if (MainActivity.manggiohang.size()<=0){
                               txtthongbao.setVisibility(View.VISIBLE);
                           }else {
                               txtthongbao.setVisibility(View.INVISIBLE);
                               gioHangAdapter.notifyDataSetChanged();
                               EvenUltil();
                           }
                       }
                      }
                  });
                  builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          gioHangAdapter.notifyDataSetChanged();
                          EvenUltil();
                      }
                  });
                    builder.show();
                return true;
            }
        });
    }

    public static void EvenUltil() {
        long tongtien = 0;
        for (int i = 0;i<MainActivity.manggiohang.size();i++){
             tongtien += MainActivity.manggiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txttongtien.setText( decimalFormat.format(tongtien)+" VNĐ");
        txttongtien.setMaxLines(1);
        txttongtien.setEllipsize(TextUtils.TruncateAt.END);
    }

    private void CheckData() {
        if (MainActivity.manggiohang.size()<=0){
            gioHangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE); // HIỆN
            lvgiohang.setVisibility(View.INVISIBLE); // ẨN

        } else {
            gioHangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            lvgiohang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXa() {
        toolbargiohang = (Toolbar) findViewById(R.id.toolbargiohang);
        txtthongbao = (TextView) findViewById(R.id.txtthongbao);
        lvgiohang = (ListView) findViewById(R.id.lvgiohang);
        btnthanhtoan = (Button) findViewById(R.id.btnthanhtoan);
        btnmuahang = (Button) findViewById(R.id.btnmuahang);
        txttongtien = (TextView) findViewById(R.id.txttongtien);
        gioHangAdapter = new GioHangAdapter(GioHangActivity.this,MainActivity.manggiohang);
        lvgiohang.setAdapter(gioHangAdapter);

    }
}
