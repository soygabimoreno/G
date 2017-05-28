package com.gabrielmorenoibarra.g;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * Touch Listener.
 * Created by Gabriel Moreno on 2017-05-28.
 */
public class GOnClickAndDoubleClickListener implements OnTouchListener {

    private final GestureDetector gestureDetector;

    public GOnClickAndDoubleClickListener(Context context) {
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            onClick();
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            onDoubleClick();
            return super.onDoubleTap(e);
        }
    }

    public void onClick() {
    }

    public void onDoubleClick() {
    }
}