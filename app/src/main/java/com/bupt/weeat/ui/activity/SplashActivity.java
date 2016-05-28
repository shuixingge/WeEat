package com.bupt.weeat.ui.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.bupt.weeat.R;
import com.bupt.weeat.view.customView.CircleAnimView;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.Transition;

import butterknife.Bind;


public class SplashActivity extends BaseActivity {
    @Bind(R.id.iv_welcome)
    KenBurnsView welcomeImg;
    @Bind(R.id.skip_tv)
    TextView skipTv;
    @Bind(R.id.circle_anim_view)
    CircleAnimView circleAnimView;
    @Bind(R.id.app_tv)
    TextView appTv;
    private Handler mHandler=new Handler();
    @Override
    protected void setListener() {
        super.setListener();
        circleAnimView.setCallback(new CircleAnimView.Callback() {
            @Override
            public void onRevealEnd() {
                appTv.setVisibility(View.INVISIBLE);
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        });
        welcomeImg.setTransitionListener(new KenBurnsView.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                //动画结束后跳转

            }
        });
        skipTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public int getLayoutId() {
        return R.layout.splash_actvity;
    }

    private  void animAppText(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(appTv, "alpha", 1f, 0f, 1f);
        animator.setDuration(5000);
        animator.start();
    }


    @Override
    protected void onResume() {
        super.onResume();
        welcomeImg.resume();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                animAppText();
            }
        },2000L);
    }

    @Override
    protected void onPause() {
        super.onPause();
        welcomeImg.pause();
    }
}
