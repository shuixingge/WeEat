package com.bupt.weeat.view.customView;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ViewAnimator;

import com.bupt.weeat.R;

public class SendCommentButton extends ViewAnimator implements View.OnClickListener {
    private static final String TAG = SendCommentButton.class.getSimpleName();
    public static final int STATE_SEND = 0;
    public static final int STATE_DONE = 1;
    private static final int RESET_STATE_DELAY_MILLS = 500;
    private int currentState;
    private onSendClickListener onSendClickListener;

    public void setOnSendClickListener(SendCommentButton.onSendClickListener onSendClickListener) {
        this.onSendClickListener = onSendClickListener;
    }

    private Runnable revertRunnable = new Runnable() {
        @Override
        public void run() {
            setCurrentState(STATE_SEND);
        }
    };

    public SendCommentButton(Context context) {
        super(context);
        init();
    }

    public SendCommentButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_send_comment_button, this, true);
        //
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        currentState = STATE_SEND;
        super.setOnClickListener(this);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(revertRunnable);
    }

    @Override
    public void onClick(View v) {
        if (onSendClickListener != null) {
            onSendClickListener.onSendClick(this);
        }
    }

    public void setCurrentState(int state) {
        if (currentState == state)
            return;
        currentState = state;
        if (state == STATE_DONE) {
            setEnabled(false);
            postDelayed(revertRunnable, RESET_STATE_DELAY_MILLS);
            setInAnimation(getContext(), R.anim.slide_in_done);
            setOutAnimation(getContext(), R.anim.slide_out_send);
        } else {
            setEnabled(true);
            setInAnimation(getContext(), R.anim.slide_in_send);
            setOutAnimation(getContext(), R.anim.slide_out_done);
        }
        showNext();
    }


    public interface onSendClickListener {
        void onSendClick(View view);
    }
}
