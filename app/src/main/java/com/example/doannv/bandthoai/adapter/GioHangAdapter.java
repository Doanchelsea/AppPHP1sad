package com.example.doannv.bandthoai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doannv.bandthoai.R;
import com.example.doannv.bandthoai.activity.GioHangActivity;
import com.example.doannv.bandthoai.activity.MainActivity;
import com.example.doannv.bandthoai.model.Giohang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<Giohang> arrayGioHang;

    public GioHangAdapter(Context context, ArrayList<Giohang> arrayGioHang) {
        this.context = context;
        this.arrayGioHang = arrayGioHang;
    }

    @Override
    public int getCount() {
        return arrayGioHang.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayGioHang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
      public TextView txttengiohang,txtgiagiohang;
      public ImageView imggiohang;
      public Button btnminus,btnvales,btnplus;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_giohang,null);
            viewHolder.txttengiohang = convertView.findViewById(R.id.txttengiohang);
            viewHolder.txtgiagiohang = convertView.findViewById(R.id.txtgiagiohang);
            viewHolder.imggiohang = convertView.findViewById(R.id.imggiohang);
            viewHolder.btnminus = convertView.findViewById(R.id.btnminus);
            viewHolder.btnplus = convertView.findViewById(R.id.btnplus);
            viewHolder.btnvales = convertView.findViewById(R.id.btnvalues);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Giohang giohang = (Giohang) getItem(position);
        viewHolder.txttengiohang.setText(giohang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiagiohang.setText(decimalFormat.format(giohang.getGiasp())+" VNĐ");
        Picasso.get().load(giohang.getHinhanhsp())
                .placeholder(R.drawable.ic_menu)
                .error(R.drawable.ic_menu2)
                .into(viewHolder.imggiohang);
        viewHolder.btnvales.setText(giohang.getSoluongsp()+"");
        int sl =Integer.parseInt( viewHolder.btnvales.getText().toString());
        if (sl >=10){
            viewHolder.btnplus.setVisibility(View.INVISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }else if (sl <=1){
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
        }else if (sl >=1){
            viewHolder.btnplus.setVisibility(View.VISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;

        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnvales.getText().toString()) + 1;
                int slhientai = MainActivity.manggiohang.get(position).getSoluongsp();
                long giaht = MainActivity.manggiohang.get(position).getGiasp();
                MainActivity.manggiohang.get(position).setSoluongsp(slmoinhat);
                long giamoinhat = (giaht * slmoinhat)/ slhientai;
                MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtgiagiohang.setText(decimalFormat.format(giamoinhat)+" VNĐ");
                // update lại tổng tiền
                GioHangActivity.EvenUltil();
                if (slmoinhat > 9){
                    finalViewHolder.btnplus.setVisibility(View.INVISIBLE);
                        finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                        finalViewHolder.btnvales.setText(String.valueOf(slmoinhat));
                }else {
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvales.setText(String.valueOf(slmoinhat));

                }
            }
        });
        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnvales.getText().toString()) - 1;
                int slhientai = MainActivity.manggiohang.get(position).getSoluongsp();
                long giaht = MainActivity.manggiohang.get(position).getGiasp();
                MainActivity.manggiohang.get(position).setSoluongsp(slmoinhat);
                long giamoinhat = (giaht * slmoinhat)/ slhientai;
                MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtgiagiohang.setText(decimalFormat.format(giamoinhat)+" VNĐ");
                // update lại tổng tiền
                GioHangActivity.EvenUltil();
                if (slmoinhat < 2){
                    finalViewHolder.btnminus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvales.setText(String.valueOf(slmoinhat));
                }else {
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvales.setText(String.valueOf(slmoinhat));

                }
            }
        });
        return convertView;
    }
}
