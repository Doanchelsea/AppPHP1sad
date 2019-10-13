package com.example.doannv.bandthoai.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doannv.bandthoai.R;
import com.example.doannv.bandthoai.adapter.DienThoaiAdapter;
import com.example.doannv.bandthoai.adapter.LaptopAdapter;
import com.example.doannv.bandthoai.model.Sanpham;
import com.example.doannv.bandthoai.ultil.Checkconnection;
import com.example.doannv.bandthoai.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaptopActivity extends AppCompatActivity {
    Toolbar toolbarLaptop;
    ListView listviewLaptop;
    LaptopAdapter laptopAdapter;
    ArrayList<Sanpham> mangdt;
    int idlt = 1;
    int page = 1;
    View footerview;
    boolean isloading = false;
    boolean limitdata = false;
    LaptopActivity.mHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        AnhXa();
        if (Checkconnection.haveNetworkConnection(getApplicationContext())){
            getIDLoaiSp();
            ActionToolbar();
            GetData(page);
            LoadMoreData();
        }else {
            Checkconnection.showToast_Short(getApplicationContext(),"Kiểm tra lại kết nối mạng");
            finish();
        }
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

    private void LoadMoreData() {
        listviewLaptop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ChiTietSpActivity.class);
                intent.putExtra("thongtinsanpham",mangdt.get(position));
                startActivity(intent);
            }
        });
        listviewLaptop.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount  == totalItemCount && totalItemCount!=0 && isloading == false && limitdata == false ){
                    isloading = true;
                    LaptopActivity.ThreadData threadData = new LaptopActivity.ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void GetData(int Page) {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.duongdandienthoai + String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String TenDT = "";
                int GiaDT = 0;
                String HinhAnhDT ="";
                String motadt = "";
                int idspdt = 0;
                if (response != null && response.length() != 2){
                    listviewLaptop.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i =0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            TenDT = jsonObject.getString("tensp");
                            GiaDT = jsonObject.getInt("giasp");
                            HinhAnhDT = jsonObject.getString("hinhanhsp");
                            motadt = jsonObject.getString("motasp");
                            idspdt = jsonObject.getInt("idsanpham");
                            mangdt.add(new Sanpham(id,TenDT,GiaDT,HinhAnhDT,motadt,idspdt));
                            laptopAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    limitdata = true;
                    listviewLaptop.removeFooterView(footerview);
                    Toast.makeText(LaptopActivity.this, "Đã Hết Dữ Liệu", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("idsanpham",String.valueOf(idlt));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarLaptop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLaptop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getIDLoaiSp() {
        idlt = getIntent().getIntExtra("idloaisanpham",-1);
        // kiểm tra giá trị
        Log.d("giatriloaisanpham",idlt+"");
    }

    private void AnhXa() {
        toolbarLaptop = (Toolbar) findViewById(R.id.toolbarlaptop);
        listviewLaptop = (ListView) findViewById(R.id.listviewlaptop);
        mangdt = new ArrayList<>();
        laptopAdapter = new LaptopAdapter(getApplicationContext(),mangdt);
        listviewLaptop.setAdapter(laptopAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar, null);
        mHandler = new LaptopActivity.mHandler();

    }
    public  class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    listviewLaptop.addFooterView(footerview);
                    break;
                case 1:
                    GetData(++page);
                    isloading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public  class  ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            // ngủ khoảng 3s
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}
