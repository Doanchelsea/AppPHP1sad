package com.example.doannv.bandthoai.activity;

import android.content.Intent;
import android.media.Image;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.doannv.bandthoai.R;
import com.example.doannv.bandthoai.adapter.LoaispAdapter;
import com.example.doannv.bandthoai.adapter.SanphamAdapter;
import com.example.doannv.bandthoai.model.Giohang;
import com.example.doannv.bandthoai.model.Loaisp;
import com.example.doannv.bandthoai.model.Sanpham;
import com.example.doannv.bandthoai.ultil.Checkconnection;
import com.example.doannv.bandthoai.ultil.Server;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbarmanhinhchinh;
    ViewFlipper viewlipper;
    RecyclerView recyclerview;
    NavigationView navigationview;
    ListView listviewmanhinhchinh;
    DrawerLayout drawerlayout;
    ArrayList<Loaisp> mangloaisp;
    LoaispAdapter loaispAdapter;
    int id = 0;
    String tenloaisp = "";
    String hinhanhloaisp = "";
    ArrayList<Sanpham> mangsanpham;
    SanphamAdapter sanphamAdapter;
    public static ArrayList<Giohang> manggiohang;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        if (Checkconnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFlipper();
            GetDulieuloaisp();
            GetDuLieuSanphamMoiNhat();
            ItemListView();
        }else {
            Checkconnection.showToast_Short(getApplicationContext(),"");
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

    private void ItemListView() {
        listviewmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        // kiểm tra thông báo có mạng hay ko ?
                        if(Checkconnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else {
                            Checkconnection.showToast_Short(getApplicationContext(),"");
                        }
                        // sự kiện đóng
                        drawerlayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        // kiểm tra thông báo có mạng hay ko ?
                        if(Checkconnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,DienThoaiActivity.class);
                            // gọi id loại sản phẩm
                            intent.putExtra("idloaisanpham",mangloaisp.get(position).getId());
                            startActivity(intent);
                        }else {
                            Checkconnection.showToast_Short(getApplicationContext(),"");
                        }
                        // sự kiện đóng
                        drawerlayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        // kiểm tra thông báo có mạng hay ko ?
                        if(Checkconnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LaptopActivity.class);
                            // gọi id loại sản phẩm
                            intent.putExtra("idloaisanpham",mangloaisp.get(position).getId());
                            startActivity(intent);
                        }else {
                            Checkconnection.showToast_Short(getApplicationContext(),"");
                        }
                        // sự kiện đóng
                        drawerlayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        // kiểm tra thông báo có mạng hay ko ?
                        if(Checkconnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LienHeActivity.class);

                            startActivity(intent);
                        }else {
                            Checkconnection.showToast_Short(getApplicationContext(),"");
                        }
                        // sự kiện đóng
                        drawerlayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        // kiểm tra thông báo có mạng hay ko ?
                        if(Checkconnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,ThongTinActivity.class);
                            startActivity(intent);
                        }else {
                            Checkconnection.showToast_Short(getApplicationContext(),"");
                        }
                        // sự kiện đóng
                        drawerlayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void GetDuLieuSanphamMoiNhat() {
        // đọc nội dung json
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdansanphammoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                 if(response != null){
                         int ID= 0;
                         String Tensanpham = "";
                         Integer Giasanpham = 0;
                         String Hinhanhsanpham = "";
                         String Motasanpham = "";
                         int IDsanpham = 0;
                         for (int i=0 ; i< response.length();i++){
                             try {
                                 JSONObject jsonObject = response.getJSONObject(i);
                                 ID = jsonObject.getInt("id");
                                 Tensanpham = jsonObject.getString("tensp");
                                 Giasanpham = jsonObject.getInt("giasp");
                                 Hinhanhsanpham = jsonObject.getString("hinhanhsp");
                                 Motasanpham = jsonObject.getString("motasp");
                                 IDsanpham = jsonObject.getInt("idsanpham");
                              mangsanpham.add(new Sanpham(ID,Tensanpham,Giasanpham,Hinhanhsanpham,Motasanpham,IDsanpham));
                              sanphamAdapter.notifyDataSetChanged();
                             } catch (JSONException e) {
                                 e.printStackTrace();
                             }
                         }

                 }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDulieuloaisp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdanloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                 if (response != null){
                   for (int i=0;i<response.length();i++){
                       try {
                           JSONObject jsonObject = response.getJSONObject(i);
                           id = jsonObject.getInt("id");
                           tenloaisp = jsonObject.getString("tenloaisp");
                           hinhanhloaisp = jsonObject.getString("hinhanhloaisp");
                           mangloaisp.add(new Loaisp(id,tenloaisp,hinhanhloaisp));
                           loaispAdapter.notifyDataSetChanged();

                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                   }
                   mangloaisp.add(3,new Loaisp(0,"Liên Hệ","http://caygiongvietnam.com/wp-content/uploads/2016/09/telephone-icon-1.png"));
                   mangloaisp.add(4,new Loaisp(0,"Thông Tin","https://vidiashop.net/wp-content/uploads/2017/08/icon-cai-dat-3.png"));
                 }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Checkconnection.showToast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);

    }

    // bắt sự kiện cho Hình ảnh ở flipper
    private void ActionViewFlipper() {
        //Khai báo mảng quảng cáo
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://image.anninhthudo.vn/h500/uploaded/83/2017_05_22/che3.jpg");
        mangquangcao.add("https://cdnmedia.thethaovanhoa.vn/2012/05/20/05/58/Chelseavodich.jpg");
        mangquangcao.add("https://cdnmedia.thethaovanhoa.vn/2012/05/19/22/57/bayernmunichchelsea2.jpg");
        mangquangcao.add("https://cdnmedia.thethaovanhoa.vn/2012/05/22/10/06/chelseavd.jpg");
        for (int i=0; i<mangquangcao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
             Picasso.get().load(mangquangcao.get(i)).into(imageView);
             imageView.setScaleType(ImageView.ScaleType.FIT_XY);
             viewlipper.addView(imageView);
        }
        viewlipper.setFlipInterval(5000);
        viewlipper.setAutoStart(true);
        // hiệu ứng chuyển ảnh
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewlipper.setInAnimation(animation_slide_in);
        viewlipper.setOutAnimation(animation_slide_out);


    }

    // bấm nút menu chuyển sang navigion
    private void ActionBar(){
        setSupportActionBar(toolbarmanhinhchinh);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarmanhinhchinh.setNavigationIcon(R.drawable.ic_menu2);
        toolbarmanhinhchinh.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerlayout.openDrawer(GravityCompat.START);
            }
        });

    }
//Khai báo khởi tạo
  private void Anhxa(){
        drawerlayout = findViewById(R.id.drawerlayout);
        toolbarmanhinhchinh =  findViewById(R.id.toolbarmanhinhchinh);
        viewlipper =  findViewById(R.id.viewlipper);
        recyclerview =  findViewById(R.id.recyclerview);
        navigationview =  findViewById(R.id.navigationview);
        listviewmanhinhchinh =  findViewById(R.id.listviewmanhinhchinh);
        mangloaisp = new ArrayList<>();
        mangloaisp.add(0,new Loaisp(0,"Trang Chính","http://caygiongvietnam.com/wp-content/uploads/2016/09/telephone-icon-1.png"));
        loaispAdapter = new LoaispAdapter(mangloaisp,getApplicationContext());
        listviewmanhinhchinh.setAdapter(loaispAdapter);

        mangsanpham = new ArrayList<>();
        sanphamAdapter = new SanphamAdapter(getApplicationContext(),mangsanpham);
      // số cột
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerview.setAdapter(sanphamAdapter);
        if (manggiohang != null){

        }else {
            manggiohang = new ArrayList<>();
        }
    }
}
