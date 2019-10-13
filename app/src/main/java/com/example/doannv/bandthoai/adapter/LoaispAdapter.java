package com.example.doannv.bandthoai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doannv.bandthoai.R;
import com.example.doannv.bandthoai.model.Loaisp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaispAdapter extends BaseAdapter {
    ArrayList<Loaisp> arrayListLoaisp;
    Context context;

    public LoaispAdapter(ArrayList<Loaisp> arrayListLoaisp, Context context) {
        this.arrayListLoaisp = arrayListLoaisp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListLoaisp.size();
        // đưa ra các giá trị trong mảng
    }

    @Override
    public Object getItem(int position) {
        return arrayListLoaisp.get(position);
        // lấy ra từng thuộc tính ở mảng
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
// hỗ trợ load dữ liệu nhanh hơn
    public class  ViewHolder{
   TextView txttenloaisp;
   ImageView imgloaisp;

}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
       viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_listview_loaisp,null);
            // gán id cho các thuộc tính cho layout
            viewHolder.txttenloaisp = convertView.findViewById(R.id.textviewloaisp);
            viewHolder.imgloaisp = convertView.findViewById(R.id.imageviewloaisp);
            convertView.setTag(viewHolder);



        }else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        Loaisp loaisp = (Loaisp) getItem(position);
        viewHolder.txttenloaisp.setText(loaisp.getTenloaisp());
        Picasso.get().load(loaisp.hinhanhloaisp)
                .placeholder(R.drawable.ic_menu)
                .error(R.drawable.ic_menu2)
                .into(viewHolder.imgloaisp);
        return convertView;
    }
}
