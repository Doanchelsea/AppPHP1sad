package com.example.doannv.bandthoai.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doannv.bandthoai.R;
import com.example.doannv.bandthoai.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DienThoaiAdapter extends BaseAdapter {
        Context context;
        ArrayList<Sanpham> arraydienthoai;

    public DienThoaiAdapter(Context context, ArrayList<Sanpham> arraydienthoai) {
        this.context = context;
        this.arraydienthoai = arraydienthoai;
    }

    @Override
    public int getCount() {
        // trả về bằng kích thước của mảng
        return arraydienthoai.size();
    }

    @Override
    public Object getItem(int position) {
        return arraydienthoai.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txtTendienthoai,txtGiadienthoai,txtMoTaDienThoai;
        public ImageView imgDienthoai;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView  == null){
         viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_dienthoai,null);
            viewHolder.txtTendienthoai = convertView.findViewById(R.id.txttendienthoai);
            viewHolder.txtGiadienthoai = convertView.findViewById(R.id.txtgiadienthoai);
            viewHolder.txtMoTaDienThoai = convertView.findViewById(R.id.txtmotadienthoai);
            viewHolder.imgDienthoai = convertView.findViewById(R.id.imgdienthoai);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(position);
        viewHolder.txtTendienthoai.setText(sanpham.getTenSanPham());
        // phương thức giá tiền, định dạng tiền tiền
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiadienthoai.setText("Giá:" + decimalFormat.format(sanpham.getGiaSanPham())+" VNĐ");
        // số lượng dòng
        viewHolder.txtMoTaDienThoai.setMaxLines(2);
        // dấu 3 chấm ở phía sau
        viewHolder.txtMoTaDienThoai.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMoTaDienThoai.setText(sanpham.getMotasanpham());
        Picasso.get().load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.ic_menu)
                .error(R.drawable.ic_menu2)
                .into(viewHolder.imgDienthoai);

        return convertView;
    }
}
