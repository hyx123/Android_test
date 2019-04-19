/* Created in 2019.3.27 10:59
 * Created by hyx
 * Use in RecyclerView
 * Refer to https://www.jianshu.com/p/bb6b029de04f 创建
 * https://www.jianshu.com/p/bde672af4e11 滑动
 */

package com.kunrui.android_test.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kunrui.android_test.R;
import com.kunrui.android_test.Recycler.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity{
    List<ArrayList> list = new ArrayList<>();
    private EditText editText;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.recycler_activity);

        setRecyclerView();

        editText = findViewById(R.id.addText);
        Button button = findViewById(R.id.addView);
        button.setOnClickListener(new myListen());
    }

    public void setRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerAdapter = new RecyclerAdapter(this, list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);   //设置样式， 水平、垂直等
        //添加Android自带的分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);
    }

    public class myListen implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.addView:
                    String s = String.valueOf(editText.getText());
                    ArrayList<java.io.Serializable> arrayList = new ArrayList<>();
                    if(!s.equals("")) {
                        arrayList.add(list.size() + 1);
                        arrayList.add(" " + s + " ");
                        arrayList.add(" hello ");
                        arrayList.add(" Nothing ");
                        list.add(arrayList);
                        recyclerAdapter.update(list);
                        System.out.println("list: --->>>: " + list);
                    } else
                        break;

                    recyclerView.smoothScrollToPosition(list.size() - 1);//平滑滚动
                    //recyclerView.scrollToPosition(list.size() - 1); //非平滑滚动
                    break;
            }
        }
    }
}
