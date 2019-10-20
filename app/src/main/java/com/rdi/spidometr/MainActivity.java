package com.rdi.spidometr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import com.rdi.spidometr.Animation.AnimationDrawableActivity;
import com.rdi.spidometr.Animation.CustomViewAnimationActivity;
import com.rdi.spidometr.Animation.ObjectAnimationsActivity;
import com.rdi.spidometr.Animation.ValueAnimationsActivity;
import com.rdi.spidometr.Animation.ViewAnimationsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_for_animation);

        handlClick(R.id.animation_drawable, AnimationDrawableActivity.class);
        handlClick(R.id.animation_view_animations, ViewAnimationsActivity.class);
        handlClick(R.id.animation_value_animations, ValueAnimationsActivity.class);
        handlClick(R.id.animation_object_animations, ObjectAnimationsActivity.class);
        handlClick(R.id.animation_custom_view_animation, CustomViewAnimationActivity.class);
    }

    private void handlClick(@IdRes int viewId, Class<? extends Activity> activityClass) {
        findViewById(viewId).setOnClickListener(
                v -> startActivity(new Intent(MainActivity.this,
                        activityClass)));
    }
}
