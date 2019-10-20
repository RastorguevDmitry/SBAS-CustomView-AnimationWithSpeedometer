package com.rdi.spidometr.Animation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.rdi.spidometr.R;

public class AnimationDrawableActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_drawable);
        setTitle(this.getClass().getSimpleName());
        imageView = findViewById(R.id.image_view);

    }

    @Override
    protected void onStart() {
        super.onStart();
        ((AnimationDrawable) imageView.getDrawable()).start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ((AnimationDrawable) imageView.getDrawable()).stop();
    }
}
