package com.rdi.spidometr.Animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.rdi.spidometr.R;

public class ObjectAnimationsActivity extends AppCompatActivity {

    ImageView imageView;
    ImageView secondImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_animations);
        setTitle(this.getClass().getSimpleName());

        imageView = findViewById(R.id.image_view);

        secondImageView = findViewById(R.id.image_view_animate_programmatically);
        imageView.setOnClickListener(v -> {
            animateProgrammatically();
        });
        secondImageView.setOnClickListener(v -> {
            animateSecondView();
        });
    }

    private void animateProgrammatically() {
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(imageView,
                "scaleX", 0.5f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(imageView,
                "scaleY", 0.5f, 1f);
        configure(scaleXAnimator).start();
        configure(scaleYAnimator).start();

        configure(scaleXAnimator).start();
        configure(scaleYAnimator).start();

    }

    private Animator configure(ObjectAnimator animator) {
        animator.setDuration(800);
        animator.setRepeatCount(4);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        return animator;
    }

    private void animateSecondView() {
        ObjectAnimator mAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.object_animator);
        mAnimator.setTarget(secondImageView);
        mAnimator.start();
    }
}
