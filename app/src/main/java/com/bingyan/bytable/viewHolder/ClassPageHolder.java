package com.bingyan.bytable.viewHolder;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bingyan.DataTest;
import com.bingyan.R;
import com.bingyan.bytable.Store;
import com.bingyan.bytable.adapter.ClassViewPagerAdapter2;
import com.bingyan.bytable.adapter.SelectWeekAdapter;
import com.bingyan.bytable.adapter.ShowManyClassesAdapter;
import com.bingyan.bytable.dialog.ShowManyClassesDialog;
import com.bingyan.bytable.model.AllClasses;
import com.bingyan.bytable.model.ClassUnit;
import com.bingyan.bytable.model.Common;
import com.bingyan.bytable.model.OneWeekClasses;
import com.bingyan.bytable.query.HubBean;
import com.bingyan.bytable.widget.ClassBoxLayout;
import com.bingyan.common.ClickGuard;
import com.bingyan.common.DateUtils;
import com.bingyan.utils.ActivityUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;


/**
 * 课程表首页的Holder
 * Created by apple on 16/7/11.
 */
public class ClassPageHolder extends ClasstableBaseHolder implements View.OnClickListener, ClassBoxLayout.OnItemClickListener, ShowManyClassesAdapter.onItemClickListener {

    private RelativeLayout mRelaBack;//返回按钮
    private RelativeLayout mRelaWeek;//选择当前周的按钮
    private TextView mTVCurrentWeek;//显示当前周的TextView
    private RelativeLayout mRelaSetting;//设置按钮
    private ImageView mAddButton;
    private ViewPager mViewPager;
    private PagerAdapter mAdapter;
    private AllClasses mAllClasses;
    private int mCurrentWeek;//当前周
    private int mSelectWeek;

    public static String data;

    public ClassPageHolder(Context context, AllClasses allClasses) {
        super(R.layout.classtable_class_page, context);
        if (allClasses == null) {
            data = ActivityUtil.getSharePreferencesByKey(context, "js", "js");
            Gson gson = new Gson();
            ArrayList<HubBean.Data> datas = gson.fromJson(data, new TypeToken<ArrayList<HubBean.Data>>() {
            }.getType());
            final HubBean hubBean = new HubBean();
            hubBean.datas = datas;
            for (HubBean.Data data : datas) {
                data.formattedTxt = gson.fromJson(data.txt, HubBean.Txt.class);
            }
            try {
                mAllClasses = AllClasses.parserHubBean(hubBean);
                Store.saveClassData(getContext(), mAllClasses);
                Store.getLocalData(getContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                sleep(1000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mAllClasses = allClasses;
        }
        //  mAllClasses = allClasses;
        mAllClasses.addEmptyWeekUntil24();
        mCurrentWeek = getCurrentWeek();
        mSelectWeek = mCurrentWeek;
        mAdapter = new ClassViewPagerAdapter2(getContext(), mAllClasses, this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setPageData(position + 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setPageData(mCurrentWeek);
        mViewPager.setCurrentItem(mCurrentWeek - 1, true);
    }

    @Override
    public void show(long duration, AnimationEndCallBack callBack) {
        super.show(duration, callBack);
        if (mAdapter != null && mAdapter instanceof ClassViewPagerAdapter2)
            ((ClassViewPagerAdapter2) mAdapter).checkIfShowAll();
    }

    private void setPageData(int weekth) {
        mSelectWeek = weekth;
        OneWeekClasses oneWeekClasses = mAllClasses.getOneWeek(weekth);
        if (oneWeekClasses == null)
            throw new IllegalArgumentException("ClassPageHolder->setPageData->weekth is " + weekth);
        if (oneWeekClasses.getSEASON() == Common.SEASON.SUMMER)
            useSummer();
        else
            useWinter();
        setMonthAndDateText(oneWeekClasses);
        if (weekth == mCurrentWeek) {
            mTVCurrentWeek.setText("第" + weekth + "周");
            mTVCurrentWeek.setTextColor(Color.WHITE);

        } else {
            mTVCurrentWeek.setText("第" + weekth + "周(非本周)");
            mTVCurrentWeek.setTextColor(getContext().getResources().getColor(R.color.not_the_week_text_color));
        }

        if (oneWeekClasses.isContainToday()) {
            LinearLayout father = findL(R.id.classtable_day_week_father_linear);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                LinearLayout lx = (LinearLayout) father.getChildAt(DateUtils.getWeekday() - 1);
                TextView bottom = (TextView) lx.getChildAt(1);
                bottom.setTextColor(getContext().getResources().getColor(R.color.classtable_theme_color));
                bottom.setBackground(getContext().getResources().getDrawable(R.drawable.day_week_select_day_shape));
                // father.getChildAt(DateUtils.getWeekday() - 1).setBackground(getContext().getResources().getDrawable(R.drawable.day_week_select_day_shape));
            } else {
                LinearLayout lx = (LinearLayout) father.getChildAt(DateUtils.getWeekday() - 1);
                TextView bottom = (TextView) lx.getChildAt(1);
                bottom.setTextColor(getContext().getResources().getColor(R.color.classtable_theme_color));
                bottom.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.day_week_select_day_shape));
                //father.getChildAt(DateUtils.getWeekday() - 1).setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.day_week_select_day_shape));
            }
        } else {
            LinearLayout father = findL(R.id.classtable_day_week_father_linear);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                LinearLayout lx = (LinearLayout) father.getChildAt(DateUtils.getWeekday() - 1);
                TextView bottom = (TextView) lx.getChildAt(1);
                bottom.setTextColor(getContext().getResources().getColor(R.color.classtable_theme_color));
                bottom.setTextColor(Color.WHITE);
                bottom.setBackground(null);
                //father.getChildAt(DateUtils.getWeekday() - 1).setBackground(null);
            } else {
                LinearLayout lx = (LinearLayout) father.getChildAt(DateUtils.getWeekday() - 1);
                TextView bottom = (TextView) lx.getChildAt(1);
                bottom.setTextColor(getContext().getResources().getColor(R.color.classtable_theme_color));
                bottom.setTextColor(Color.WHITE);
                bottom.setBackgroundDrawable(null);
                //father.getChildAt(DateUtils.getWeekday() - 1).setBackgroundDrawable(null);
            }
        }
    }


    private void setMonthAndDateText(OneWeekClasses oneWeekClasses) {
        String firstDay[] = oneWeekClasses.getMonthAndDateText(0).split("\\.");
        int[] tvId = {R.id.classtable_day_week_tv_day1, R.id.classtable_day_week_tv_day2, R.id.classtable_day_week_tv_day3, R.id.classtable_day_week_tv_day4,
                R.id.classtable_day_week_tv_day5, R.id.classtable_day_week_tv_day6, R.id.classtable_day_week_tv_day7};
        findT(R.id.classtable_day_week_tv_month).setText(firstDay[0] + "");
        for (int i = 0; i < 7; i++) {
            String[] monthDay = oneWeekClasses.getMonthAndDateText(i).split("\\.");
            if (monthDay[0].equals(firstDay[0]))
                findT(tvId[i]).setText(monthDay[1]);
            else {
                findT(tvId[i]).setText(monthDay[0] + "月");
                firstDay[0] = monthDay[0];
            }
        }
    }

    /**
     * 根据mAllClasses中的数据获取当前周
     *
     * @return
     */
    private int getCurrentWeek() {
        return mAllClasses.getCurrentWeek();
    }


    public ClassPageHolder(int layoutId, Context context) {
        super(layoutId, context);
    }

    @Override
    public void initView() {
        this.mRelaBack = findR(R.id.classtable_toolbar_rela_back);
        this.mRelaWeek = findR(R.id.classtable_toolbar_week_rela);
        this.mRelaSetting = findR(R.id.classtable_toolbar_setting_rela);
        this.mViewPager = (ViewPager) findViewById(R.id.classtable_class_page_viewpager);
        this.mTVCurrentWeek = findT(R.id.classtable_toolbar_tv);
        this.mAddButton = (ImageView) findViewById(R.id.classtable_class_page_float_button);
    }

    @Override
    public void onClick(View v) {

        final int id = v.getId();
        if (id == R.id.classtable_toolbar_rela_back) {
            back();
        } else if (id == R.id.classtable_toolbar_setting_rela) {
            holderIn(new SettingPageViewHolder(getContext()));
        } else if (id == R.id.classtable_toolbar_week_rela) {
            holderIn(new SelectWeekViewHolder(getContext(), mAllClasses.getAllWeekCount(), mCurrentWeek, mSelectWeek, new SelectWeekAdapter.SelectWeekCallback() {
                @Override
                public void selectWeek(int weekIndex) {
                    setPageData(weekIndex);
                    mViewPager.setCurrentItem(weekIndex - 1, true);
                }
            }));
        } else if (id == R.id.classtable_class_page_float_button) {
            holderIn(new AddButtonHolder(getContext()));
        }
    }

    @Override
    public void Guard() {
        ClickGuard.guard(this, mAddButton, mRelaBack, mRelaWeek, mRelaSetting);
    }

    @Override
    public void unGurad() {
        ClickGuard.unGuard(mRelaBack, mRelaWeek, mRelaSetting, mAddButton);
        if (mAdapter != null && mAdapter instanceof ClassViewPagerAdapter2)
            ((ClassViewPagerAdapter2) mAdapter).finish();
    }


    /**
     * 使用夏令时
     */
    public void useSummer() {
        findT(R.id.classtable_left_time_tv_5).setText("14:30");
        findT(R.id.classtable_left_time_tv_6).setText("15:20");
        findT(R.id.classtable_left_time_tv_7).setText("16:25");
        findT(R.id.classtable_left_time_tv_8).setText("17:15");
        findT(R.id.classtable_left_time_tv_9).setText("19:00");
        findT(R.id.classtable_left_time_tv_10).setText("19:50");
        findT(R.id.classtable_left_time_tv_11).setText("20:45");
        findT(R.id.classtable_left_time_tv_12).setText("21:35");
    }

    /**
     * 使用冬令时
     */
    public void useWinter() {
        findT(R.id.classtable_left_time_tv_5).setText("14:00");
        findT(R.id.classtable_left_time_tv_6).setText("14:50");
        findT(R.id.classtable_left_time_tv_7).setText("15:55");
        findT(R.id.classtable_left_time_tv_8).setText("16:45");
        findT(R.id.classtable_left_time_tv_9).setText("18:30");
        findT(R.id.classtable_left_time_tv_10).setText("19:20");
        findT(R.id.classtable_left_time_tv_11).setText("20:15");
        findT(R.id.classtable_left_time_tv_12).setText("21:05");
    }

    @Override
    public void onItemClick(@NonNull ClassUnit topLayer, List<ClassUnit> bottomLayer) {
        if (bottomLayer != null)
            dialogIn(new ShowManyClassesDialog(getContext(), topLayer, bottomLayer, this));
        else
            holderIn(new ClassDetailViewHolder(getContext(), topLayer.mClassName + "@" + topLayer.mClassAddress));
    }

    @Override
    public void onItemClick(String s) {
        holderIn(new ClassDetailViewHolder(getContext(), s));
    }
}
