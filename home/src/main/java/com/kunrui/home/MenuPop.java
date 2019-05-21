package com.kunrui.home;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuPopupHelper;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.lang.reflect.Field;

public class MenuPop extends AppCompatActivity {

    private PopupMenu menu;
    private ImageButton imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pop);

        imageView = findViewById(R.id.barAdd);
        init(imageView);
        imageView.setOnClickListener(new myListen());
        findViewById(R.id.barSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(v);
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPop.this, Menu_two.class);
                startActivity(intent);
            }
        });
    }

    private class myListen implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            //menu生效
            menu.show();
            Log.e("v:", String.valueOf(v));
        }
    }

    @SuppressLint("RestrictedApi")
    public void init(View v) {
        menu = new PopupMenu(MenuPop.this, v);
        //inflate加载布局文件
        menu.inflate(R.menu.pop_menu);
        menu.getMenu().findItem(R.id.rename_file).setVisible(false);

        //设置字体颜色
        MenuItem item = menu.getMenu().findItem(R.id.open_file);
        SpannableString spannableString = new SpannableString(item.getTitle());
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FDE500")), 0, spannableString.length(), 0);
        item.setTitle(spannableString);
        Log.e("spannableString", String.valueOf(spannableString));


        //设置弹框按钮内部按钮的点击事件, 固定写法， 一般上面加@Override都是自动生成的
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                int i = item.getItemId();
                if (i == R.id.open_file) {
                    Intent intent;
                    intent = new Intent(MenuPop.this, MainActivity.class);
                    startActivity(intent);
                } else if (i == R.id.save_file) {
                    Intent intent;
                    intent = new Intent(MenuPop.this, MainActivity.class);
                    startActivity(intent);
                } else if (i == R.id.save_file) {
                } else if (i == R.id.rename_file) {
                }
                return false;
            }
        });

        //按钮消失事件
        menu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
            }
        });

        try {
            Field field = menu.getClass().getDeclaredField("pop_menu");
            field.setAccessible(true);
            MenuPopupHelper helper = (MenuPopupHelper) field.get(menu);
            helper.setForceShowIcon(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        @SuppressLint("InflateParams") View contentView = LayoutInflater.from(MenuPop.this).inflate(
                R.layout.pop_window, null);


        final PopupWindow popupWindow = new PopupWindow(contentView,
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.ic_action_globe));
        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);
    }
}