package com.kalakunjmandir.newgunatitallinone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView menu,menu_txt,setting;
    LinearLayout menubar_icon,menubar_txt;
    FrameLayout frameLayout;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Animation rotate_btn_forward,rotate_btn_back,menu_bar_anim_exit,menu_bar_txt_anim_exit,menu_bar_anim_enter,menu_bar_txt_anim_enter;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menu=findViewById(R.id.menu);
        menu_txt=findViewById(R.id.menu_txt);
        setting=findViewById(R.id.setting);
        menubar_icon=findViewById(R.id.menubar_icon);
        menubar_txt=findViewById(R.id.menubar_txt);
        frameLayout=findViewById(R.id.frameLayout);
        preferences =getSharedPreferences("kalakunj_mandir",MODE_PRIVATE);
        editor= preferences.edit();

        rotate_btn_forward= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.back_rotate_0_to_180_deg_img);
        rotate_btn_back=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.back_rotate_180_to_0_deg_img);
        menu_bar_anim_exit= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.menu_bar_anim_exit);
        menu_bar_txt_anim_exit= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.menu_bar_txt_anim_exit);
        menu_bar_anim_enter= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.menu_bar_anim_enter);
        menu_bar_txt_anim_enter= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.menu_bar_txt_anim_enter);

        setTheme();
        setVisibility();

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("DarkMode",true);
                    editor.apply();
                }else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("DarkMode",false);
                    editor.apply();
                }

            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menubar_icon.getVisibility()==View.GONE){
                    menubar_icon.startAnimation(menu_bar_anim_enter);
                    menubar_icon.setVisibility(View.VISIBLE);
                    editor.putBoolean("menubar_icon",true);
                    editor.apply();
                }else {
                    if (menubar_txt.getVisibility()==View.VISIBLE){
                        menubar_txt.setVisibility(View.GONE);
                        menubar_txt.startAnimation(menu_bar_txt_anim_exit);
                        menu_txt.startAnimation(rotate_btn_back);
                        editor.putBoolean("menubar_txt",false);
                        editor.apply();
                    }
                    menubar_icon.startAnimation(menu_bar_anim_exit);
                    menubar_icon.setVisibility(View.GONE);
                    editor.putBoolean("menubar_icon",false);
                    editor.apply();
                }
            }
        });

        menu_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menubar_txt.getVisibility()==View.VISIBLE){
                    menubar_txt.setVisibility(View.GONE);
                    menubar_txt.startAnimation(menu_bar_txt_anim_exit);
                    menu_txt.startAnimation(rotate_btn_back);
                    editor.putBoolean("menubar_txt",false);
                    editor.apply();
                }else {
                    menubar_txt.setVisibility(View.VISIBLE);
                    menubar_txt.startAnimation(menu_bar_txt_anim_enter);
                    menu_txt.startAnimation(rotate_btn_forward);
                    editor.putBoolean("menubar_txt",true);
                    editor.apply();
                }

            }
        });

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Frame Layout", Toast.LENGTH_SHORT).show();
                if (menubar_txt.getVisibility()==View.VISIBLE){
                    menubar_txt.setVisibility(View.GONE);
                    editor.putBoolean("menubar_txt",false);
                    editor.apply();
                }
            }
        });
    }

    public void setTheme(){
        boolean darkTheme=preferences.getBoolean("DarkMode",false);
        if (darkTheme){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
    public void setVisibility(){
        boolean v_menubar_ic=preferences.getBoolean("menubar_icon",true);
        boolean v_menubar_txt=preferences.getBoolean("menubar_txt",false);
        if (v_menubar_ic){
            menubar_icon.setVisibility(View.VISIBLE);
            menubar_icon.startAnimation(menu_bar_anim_enter);
        }else {
            menubar_icon.setVisibility(View.GONE);
            menubar_icon.startAnimation(menu_bar_anim_exit);
        }
        if (v_menubar_txt){
            menubar_txt.setVisibility(View.VISIBLE);
            menubar_txt.startAnimation(menu_bar_txt_anim_enter);
            menu_txt.startAnimation(rotate_btn_forward);
        }else {
            menubar_txt.setVisibility(View.GONE);
            menubar_txt.startAnimation(menu_bar_txt_anim_exit);
            menu_txt.startAnimation(rotate_btn_back);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        editor.putBoolean("menubar_icon",true);
        editor.apply();
        editor.putBoolean("menubar_txt",false);
        editor.apply();
    }
}