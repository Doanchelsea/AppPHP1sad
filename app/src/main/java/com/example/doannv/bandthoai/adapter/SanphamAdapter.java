package com.example.doannv.bandthoai.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doannv.bandthoai.R;
import com.example.doannv.bandthoai.activity.ChiTietSpActivity;
import com.example.doannv.bandthoai.model.Sanpham;
import com.example.doannv.bandthoai.ultil.Checkconnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.ItemHolder> {
       Context context;
       ArrayList<Sanpham> arraySanpham;

    public SanphamAdapter(Context context, ArrayList<Sanpham> arraySanpham) {
        this.context = context;
        this.arraySanpham = arraySanpham;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dong_sanphammoinhat,null);
        ItemHolder itemHolder = new ItemHolder(view);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
      Sanpham sanpham = arraySanpham.get(i);
      itemHolder.txttensanpham.setText(sanpham.getTenSanPham());
      // phương thức giá tiền, định dạng tiền tiền
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        itemHolder.txtgiasanpham.setText("Giá:" + decimalFormat.format(sanpham.getGiaSanPham())+" VNĐ");
        // phương thức lấy ảnh dùng picasso
        Picasso.get().load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.ic_menu)
                .error(R.drawable.ic_menu2)
                .into(itemHolder.imghinhanhsanpham);
    }

    @Override
    public int getItemCount() {
        return arraySanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imghinhanhsanpham;
        public TextView txttensanpham, txtgiasanpham;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imghinhanhsanpham =  itemView.findViewById(R.id.imageviewsanpham);
            txttensanpham =  itemView.findViewById(R.id.textviewtensanpham);
            txtgiasanpham =  itemView.findViewById(R.id.textviewgiasanpham);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietSpActivity.class);
                    intent.putExtra("thongtinsanpham", arraySanpham.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

        }
    }
}
