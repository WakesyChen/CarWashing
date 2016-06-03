package com.example.carwashing.fragments.aroundStore_fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carwashing.R;

import java.util.List;

/**
 * Created by Wakesy on 2016/5/22.
 */
public class StoreAdapter extends BaseAdapter {
    private Context context;
    private List<List_ItemViews> datalist;
    private LayoutInflater minflater;

    public StoreAdapter(Context context, List<List_ItemViews> datalist) {
        this.context = context;
        this.datalist = datalist;
        minflater = LayoutInflater.from(context);
    }

    /*当数据源发生更新时*/
    public void onDateChange(List<List_ItemViews> datalist) {
        this.datalist = datalist;
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return datalist.size();
    }

    @Override
    public Object getItem(int position) {
        return datalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final int Itemposition = position;//传给Button用
        //如果缓冲池中没有当前的视图
        if (convertView == null) {
            holder = new ViewHolder();
            //初始化ViewHolder中的控件
            convertView = minflater.inflate(R.layout.aroundstore_listview_item, null);
            holder.item_ll= (LinearLayout) convertView.findViewById(R.id.ListItem_wholeItem);
            holder.item_store = (TextView) convertView.findViewById(R.id.ListItem_store);
            holder.item_Img = (ImageView) convertView.findViewById(R.id.ListItem_img);
            holder.item_address = (TextView) convertView.findViewById(R.id.ListItem_address);
            holder.item_ratingbar = (RatingBar) convertView.findViewById(R.id.ListItem_ratingBar);
            holder.item_score = (TextView) convertView.findViewById(R.id.ListItem_Score);
            holder.item_comments = (TextView) convertView.findViewById(R.id.ListItem_comments);
            holder.item_distance = (TextView) convertView.findViewById(R.id.ListItem_distance);
            holder.item_type = (TextView) convertView.findViewById(R.id.ListItem_type);
            holder.item_price = (TextView) convertView.findViewById(R.id.ListItem_price);
            holder.item_buy = (Button) convertView.findViewById(R.id.ListItem_buy);

            //把绑定好控件的holder装进Tag中
            convertView.setTag(holder);
        }
        //缓冲池中已经存在当前Item
        else {
            //取出holder
            holder = (ViewHolder) convertView.getTag();
        }
            //给每个控件赋值,从当前的List_ItemViews对象中取值
            List_ItemViews ItemViews = datalist.get(position);
            holder.item_store.setText(ItemViews.ItemStore);
//            background设置背景（适应控件大小）,src设置资源（基于本身大小）
           holder.item_Img.setBackgroundResource(ItemViews.ItemImg);
//            holder.item_Img.setImageResource(ItemViews.ItemImg);
            holder.item_address.setText(ItemViews.ItemAddress);
            holder.item_ratingbar.setRating(ItemViews.ItemStars);
            holder.item_score.setText(ItemViews.ItemScore);
            holder.item_comments.setText(ItemViews.ItemComments);
            holder.item_distance.setText(ItemViews.ItemDistance);
            holder.item_type.setText(ItemViews.ItemType);
            holder.item_price.setText(ItemViews.ItemPrice);

        //给整个Item设置点击事件
        holder.item_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "你点击了第" + (Itemposition + 1) + "个门店", Toast.LENGTH_SHORT).show();
            }
        });
        //给购买按键设置点击事件
            holder.item_buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "恭喜你买下第" + (Itemposition + 1) + "笔单子", Toast.LENGTH_SHORT).show();
                }
            });



        return convertView;
    }

    //装控件的ViewHolder类
    static class ViewHolder {
        LinearLayout item_ll;//单个Item布局的View
        TextView item_store;
        ImageView item_Img;
        TextView item_address;
        RatingBar item_ratingbar;
        TextView item_score;
        TextView item_comments;
        TextView item_distance;
        TextView item_type;
        TextView item_price;
        Button item_buy;
    }
}
