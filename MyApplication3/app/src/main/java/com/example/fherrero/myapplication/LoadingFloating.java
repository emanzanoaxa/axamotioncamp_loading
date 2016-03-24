package com.example.fherrero.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;

/**
 * Created by fherrero on 25/02/16.
 */
public class LoadingFloating extends FrameLayout implements View.OnTouchListener {

    private static final int ANIM_DURATION = 1500;
    private static final int REPEAT = 2;
    private FloatingActionButton fab1;
    private FloatingActionButton fab2;

    private RotateAnimation rightRotate;
    private RotateAnimation leftRotate;
    private Animation pulse;
    private ObjectAnimator scaleDown;
    private ObjectAnimator scaleDown1;
    private FloatingActionButton fab3;
    private EndListener listener;

    public LoadingFloating(Context context) {
        super(context);
        initView();
    }

    public LoadingFloating(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoadingFloating(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public LoadingFloating(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();

    }

    public void setListener(EndListener listener){
        this.listener = listener;
    }

    private void initView() {
        setClipToPadding(false);
        setClipChildren(false);

        setPadding(25, 25, 25, 25);

        inflate(getContext(), R.layout.loading_floating, this);
        AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        rightRotate = new RotateAnimation(0, 360 * 4,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rightRotate.setRepeatCount(1);
        rightRotate.setDuration(ANIM_DURATION);
        leftRotate = new RotateAnimation(360 * 4, 0,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        leftRotate.setRepeatCount(Animation.INFINITE);
        leftRotate.setDuration(ANIM_DURATION);
        rightRotate.setInterpolator(interpolator);
        leftRotate.setInterpolator(interpolator);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startAnimation();
                return true;
            case MotionEvent.ACTION_UP:
                return false;
        }
        return false;
    }

    public void startAnimation() {

        scaleDown = ObjectAnimator.ofPropertyValuesHolder(fab2,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        scaleDown.setDuration(ANIM_DURATION/2);

        scaleDown.setRepeatCount(3);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);



        scaleDown1 = ObjectAnimator.ofPropertyValuesHolder(fab1,
                PropertyValuesHolder.ofFloat("scaleX", 1.3f),
                PropertyValuesHolder.ofFloat("scaleY", 1.3f));
        scaleDown1.setDuration(ANIM_DURATION/2);

        scaleDown1.setRepeatCount(3);
        scaleDown1.setRepeatMode(ObjectAnimator.REVERSE);
        scaleDown1.setStartDelay(ANIM_DURATION / 4);

        fab3.startAnimation(rightRotate);
        scaleDown.start();
        scaleDown1.start();

        scaleDown1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (listener != null) {
                    listener.endAnim();
                }
            }
        });
    }
}
