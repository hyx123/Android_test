package com.kunrui.android_test.Recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kunrui.android_test.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHolder> {
    private Context context;
    private List<ArrayList> list;

    public RecyclerAdapter(Context context, List<ArrayList> list) {
        this.context = context;
        this.list = list;
    }

    public void update(List<ArrayList> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    //创建ViewHolder
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler, viewGroup, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    //操作item
    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, int id) {
        myHolder.id.setText(String.valueOf(list.get(id).get(0)));
        myHolder.context.setText(String.valueOf(list.get(id).get(1)));
        myHolder.message.setText(String.valueOf(list.get(id).get(2)));
        myHolder.remark.setText(String.valueOf(list.get(id).get(3)));

        myHolder.id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, myHolder.context.getText(), Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //初始化控件
    class MyHolder extends RecyclerView.ViewHolder{
        TextView id, context, message, remark;
        MyHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            context = itemView.findViewById(R.id.context);
            message = itemView.findViewById(R.id.message);
            remark = itemView.findViewById(R.id.remark);
        }
    }

    /** 一开始, 表格制作方法
    //dp单位转换成px单位
    public static int dp2px(Context context, float dpValue){
        return (int)(dpValue * (context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    @TargetApi(Build.VERSION_CODES.N)
    public void setEditAdd(int width_t, final ArrayList Msg, final int status)
    {
        String value = String.valueOf(Msg.get(status));
        int height=dp2px(context, 40);
        int margin=dp2px(context, 0.5f);
        int padding=dp2px(context, 2);
        int width = dp2px(context, width_t);

        editText=new EditText(context);
        editText.setSingleLine();
        editText.setBackgroundColor(Color.parseColor("#0d122e"));
        android.widget.TableRow.LayoutParams params=new android.widget.TableRow.LayoutParams(width, height);
        params.weight=1;
        editText.setGravity(Gravity.CENTER);
        params.setMargins(margin,margin,margin,margin);
        editText.setLayoutParams(params);
        editText.setPadding(padding,padding,padding,padding);
        editText.setTextSize(14);

        //设置不可编辑但有点击
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
        editText.setTextColor(Color.parseColor("#FFFFFFFF"));
        editText.setText(value);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
    */
}
