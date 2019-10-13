package com.example.doannv.bandthoai.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doannv.bandthoai.R;
import com.example.doannv.bandthoai.ultil.Checkconnection;
import com.example.doannv.bandthoai.ultil.Server;

import java.util.HashMap;
import java.util.Map;

public class ThongTinKhachHangActivity extends AppCompatActivity {
     EditText edtenkh;
     EditText edSDT;
    EditText edEmail;
     Button btnxacnhan;
    Button btntrove;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);
        AnhXa();
        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // kiểm tra trước khi đẩy lên sever
        if (Checkconnection.haveNetworkConnection(getApplicationContext())){
            EventButton();
        }else {
            Toast.makeText(this, "Bạn Hãy Kiểm Tra Mạng", Toast.LENGTH_SHORT).show();
        }
    }

    private void EventButton() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ten = edtenkh.getText().toString().trim();
                final String sdt = edSDT.getText().toString().trim();
                final String email = edEmail.getText().toString().trim();
                if (ten.length()>0 && sdt.length()>0 && email.length()>0 ){
                    // đưa dữ liệu lên sever
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdandonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("iddonhang",response);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                           HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put("tenkhachhang",ten);
                            hashMap.put("sodienthoai",sdt);
                            hashMap.put("email",email);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else {
                    Toast.makeText(ThongTinKhachHangActivity.this, "Yêu Cầu Nhập Dữ Liệu", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    private void AnhXa() {
        edtenkh = (EditText) findViewById(R.id.edtenkh);
        edSDT = (EditText) findViewById(R.id.edSDT);
        edEmail = (EditText) findViewById(R.id.edEmail);
        btnxacnhan = (Button) findViewById(R.id.btnxacnhan);
        btntrove = (Button) findViewById(R.id.btntrove);

    }
}
