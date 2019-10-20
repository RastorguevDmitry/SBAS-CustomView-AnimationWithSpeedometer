package com.rdi.spidometr.Animation;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.rdi.spidometr.R;
import com.rdi.spidometr.SpeedometerView;

public class CustomViewAnimationActivity extends AppCompatActivity {
    private ObjectAnimator mAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_animation);
        setTitle(this.getClass().getSimpleName());

        SpeedometerView speedometerView = findViewById(R.id.speedometer_view);
        speedometerView.setCurentVeloсity(240);
        speedometerView.setMaxVeloсity(500);

        mAnimator = ObjectAnimator.ofInt(speedometerView,
                "curentVeloсity", 0, speedometerView.getCurentVeloсity());
        configure(mAnimator);

//        mAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.custom_view_animator);
//        mAnimator.setTarget(speedometerView);
        mAnimator.start();
    }

    private ObjectAnimator configure(ObjectAnimator animator) {
        animator.setDuration(3000);
        animator.setRepeatCount(0);
        return animator;
    }
}

