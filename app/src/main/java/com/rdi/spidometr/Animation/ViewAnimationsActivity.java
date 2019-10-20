package com.rdi.spidometr.Animation;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import androidx.appcompat.app.AppCompatActivity;

import com.rdi.spidometr.R;

public class ViewAnimationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animations);
        setTitle(this.getClass().getSimpleName());

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate_scale_alpha);
        View imageView = findViewById(R.id.image_view);
        imageView.setOnClickListener(v -> {
            imageView.clearAnimation();
            imageView.startAnimation(animation);
        });

        animateProgrammatically();
    }

    private void animateProgrammatically() {
        View view = findViewById(R.id.image_view_animate_programmatically);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 1f, 0.5f, 1f);
        configure(scaleAnimation);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        configure(alphaAnimation);

        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        configure(translateAnimation);

        AnimationSet animations = new AnimationSet(false);
        animations.addAnimation(scaleAnimation);
        animations.addAnimation(alphaAnimation);
        animations.addAnimation(translateAnimation);

        view.setOnClickListener(v -> {
            view.clearAnimation();
            view.startAnimation(animations);
        });

    }

    private Animation configure(Animation animation) {
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(4);
        animation.setDuration(500);
        return animation;
    }


}
