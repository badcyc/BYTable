package com.bingyan.bytable.viewHolder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bingyan.R;
import com.bingyan.bytable.adapter.SelectWeekAdapter;
import com.bingyan.bytable.widget.SelectWeekLayout;
import com.bingyan.common.ClickGuard;
import com.bingyan.common.DimenUtils;


/**
 * Created by apple on 16/7/15.
 */
public class SelectWeekViewHolder extends ZeroAlphaDialogHolder {

    private LinearLayout mLinearBackGround;
    private ListView mListView;
    private int mCurrentWeek;
    private String[] mListViewString;
    private SelectWeekAdapter.SelectWeekCallback mCallback;
    private SelectWeekLayout mSelecLayout;
    private int mSelectWeek;

    private static final String TAG = "SelectWeekViewHolder";

    public SelectWeekViewHolder(Context context, int weekCount, int currentWeek,int selectWeek, SelectWeekAdapter.SelectWeekCallback callback) {
        super(R.layout.classtable_dialog_select_week, context);

        Log.d(TAG, "SelectWeekViewHolder: "+"onCreate");
        mCurrentWeek = currentWeek;
        mSelectWeek = selectWeek;
        mListViewString = new String[weekCount];
        for (int i = 0; i < weekCount; i++)
            if ((i + 1) == mCurrentWeek)
                mListViewString[i] = "第" + (i + 1) + "周(本周)";
            else
                mListViewString[i] = "第"+(i+1)+"周";
        mCallback = callback;
        init();
    }

    public SelectWeekViewHolder(int layoutId, Context context) {
        super(layoutId, context);
    }

    private void init(){
        mListView.setAdapter(new SelectWeekAdapter(mListViewString, mSelectWeek, new SelectWeekAdapter.SelectWeekCallback() {
            @Override
            public void selectWeek(int weekIndex) {
                back();
                if(mCallback!=null)
                    mCallback.selectWeek(weekIndex);
            }
        }, getContext()));
        mListView.setDivider(null);
        mListView.setSelection(mSelectWeek-1);
    }

    @Override
    public void initView() {
        mLinearBackGround = findL(R.id.classtable_dialog_select_week_linear);
        mListView = (ListView) findViewById(R.id.classtable_dialog_select_week_listview);
        mSelecLayout = (SelectWeekLayout) findViewById(R.id.classtable_select_week_seleclayout);
    }

    @Override
    public void Guard() {
        ClickGuard.guard(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        },mLinearBackGround);
    }

    @Override
    public void unGurad() {
        ClickGuard.unGuard(mLinearBackGround);
    }

    @Override
    public boolean backTwice(){
        return false;
    }

    @Override
    public void in(long duration, final AnimationEndCallBack callBack) {
        int cardViewHeight = DimenUtils.getDimensionPixelSize(getContext(), R.dimen.classtable_dialog_select_week_listview_height);
        final ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) mSelecLayout.getLayoutParams();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(-cardViewHeight,0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentTopMargin = (int) animation.getAnimatedValue();
                marginLayoutParams.topMargin = currentTopMargin;
                mSelecLayout.setLayoutParams(marginLayoutParams);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (callBack != null)
                    callBack.end();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        valueAnimator.start();

    }

    @Override
    public void leave(long duration, final AnimationEndCallBack callBack) {
        int toolbarHeight = DimenUtils.getDimensionPixelSize(getContext(), R.dimen.classtable_class_page_toolbar_height);
        int cardViewHeight = DimenUtils.getDimensionPixelSize(getContext(), R.dimen.classtable_dialog_select_week_listview_height);
        final ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) mSelecLayout.getLayoutParams();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0,-cardViewHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentTopMargin = (int) animation.getAnimatedValue();
                marginLayoutParams.topMargin = currentTopMargin;
                mSelecLayout.setLayoutParams(marginLayoutParams);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (callBack != null)
                    callBack.end();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        valueAnimator.start();
    }


}
