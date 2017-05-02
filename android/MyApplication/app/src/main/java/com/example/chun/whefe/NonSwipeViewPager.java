package com.example.chun.whefe;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Chun on 2017-04-26.
 */

public class NonSwipeViewPager extends ViewPager {
    public NonSwipeViewPager(Context context) {super(context);}
    public NonSwipeViewPager(Context context, AttributeSet attrs) {super(context,attrs);}

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {return false;}

    @Override
    public boolean onTouchEvent(MotionEvent ev) {return false;}
}
