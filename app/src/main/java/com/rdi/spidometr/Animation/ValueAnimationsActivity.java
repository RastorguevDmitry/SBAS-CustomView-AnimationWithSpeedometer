package com.rdi.spidometr.Animation;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat;

import com.rdi.spidometr.R;

public class ValueAnimationsActivity extends AppCompatActivity {
    private static final String ALPHA = "alpha";
    private static final String SCALE = "scale";

    ValueAnimator alphaAnimator;
    ValueAnimator translationAnimator;
    ImageView imageView;
    ImageView secondImageView;
    ImageView xmlImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_animations);
        setTitle(this.getClass().getSimpleName());

        imageView = findViewById(R.id.image_view);
        imageView.setOnClickListener(v -> {
            animationFirstView();
        });

        secondImageView = findViewById(R.id.image_view_animate_programmatically);
        secondImageView.setOnClickListener(v -> {
            animateSecondView();
        });

        xmlImageView = findViewById(R.id.image_view_animate_from_xml);
        xmlImageView.setOnClickListener(v -> {
            animateViewFromXml();
        });
    }

    private void animationFirstView() {
        alphaAnimator = ValueAnimator.ofFloat(0f, 1f);
        configurateAnimation(alphaAnimator);
        alphaAnimator.addUpdateListener(animation -> {
            imageView.setAlpha((Float) animation.getAnimatedValue());
        });
        alphaAnimator.start();

        translationAnimator = ValueAnimator.ofFloat(0f, -500);
        configurateAnimation(translationAnimator);
        translationAnimator.addUpdateListener(animation -> {
            imageView.setTranslationY((Float) animation.getAnimatedValue());
        });
        translationAnimator.start();
    }

    private void animateSecondView() {
        PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofFloat(ALPHA, 0f, 1f);
        PropertyValuesHolder scaleHolder = PropertyValuesHolder.ofFloat(SCALE, 0.5f, 1f);

        ValueAnimator animator = ValueAnimator.ofPropertyValuesHolder(alphaHolder, scaleHolder);
        configurateAnimation(animator);
        animator.addUpdateListener(animation -> {
            secondImageView.setAlpha((Float) animation.getAnimatedValue(ALPHA));
            Float scale = (Float) animation.getAnimatedValue(SCALE);
            secondImageView.setScaleX(scale);
            secondImageView.setScaleY(scale);
        });
        animator.start();
    }


    private void animateViewFromXml() {
        ValueAnimator animatorXml = (ValueAnimator) AnimatorInflaterCompat.loadAnimator(this, R.animator.value_animator);
        animatorXml.addUpdateListener(animator -> xmlImageView.setAlpha((Float) animator.getAnimatedValue()));
        animatorXml.start();
    }


    private ValueAnimator configurateAnimation(ValueAnimator animator) {
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(4);
        animator.setDuration(800);
        return animator;
    }
}
