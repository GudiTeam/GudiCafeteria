package com.duzi.gudicafeteria_a.ui.custom.tablayout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.duzi.gudicafeteria_a.R;
import com.duzi.gudicafeteria_a.ui.custom.tablayout.listener.LoadHeaderImagesListener;
import com.duzi.gudicafeteria_a.ui.custom.tablayout.listener.OnTabSelectedListener;

import java.util.Objects;

/**
 * https://github.com/hugeterry/CoordinatorTabLayout
 */
public class CoordinatorTabLayout extends CoordinatorLayout {
    private int[] imageArray, colorArray;

    private Context context;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private TabLayout tabLayout;
    private ImageView imageView;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private LoadHeaderImagesListener loadHeaderImagesListener;
    private OnTabSelectedListener onTabSelectedListener;

    public CoordinatorTabLayout(Context context) {
        super(context);
        this.context = context;
    }

    public CoordinatorTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        if (!isInEditMode()) {
            initView(context);
        }
    }

    public CoordinatorTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        if (!isInEditMode()) {
            initView(context);
        }
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_coordinatortablayout, this, true);
        initToolbar();
        collapsingToolbarLayout = findViewById(R.id.collapsingtoolbarlayout);
        tabLayout = findViewById(R.id.tabLayout);
        imageView = findViewById(R.id.imageview);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        ((AppCompatActivity) context).setSupportActionBar(toolbar);
        actionBar = ((AppCompatActivity) context).getSupportActionBar();
    }

    private void setTabLayoutDefault() {

        if (imageArray != null) {
            imageView.setImageResource(imageArray[0]);
        }
        if (colorArray != null) {
            collapsingToolbarLayout.setContentScrimColor(
                    ContextCompat.getColor(context, colorArray[0]));
        }
    }


    public CoordinatorTabLayout setTitle(String title) {
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
        return this;
    }

    public CoordinatorTabLayout setBackEnable(Boolean canBack) {
        if (canBack && actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
        }
        return this;
    }


    public CoordinatorTabLayout setImageArray(@NonNull int[] imageArray) {
        this.imageArray = imageArray;
        return this;
    }


    public CoordinatorTabLayout setImageArray(@NonNull int[] imageArray, @NonNull int[] colorArray) {
        this.imageArray = imageArray;
        this.colorArray = colorArray;
        return this;
    }


    public CoordinatorTabLayout setContentScrimColorArray(@NonNull int[] colorArray) {
        this.colorArray = colorArray;
        return this;
    }

    private void setupTabLayout(ViewPager viewPager) {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                imageView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_dismiss));
                if (loadHeaderImagesListener == null) {
                    if (imageArray != null) {
                        imageView.setImageResource(imageArray[tab.getPosition()]);
                    }
                } else {
                    loadHeaderImagesListener.loadHeaderImages(imageView, tab);
                }
                if (colorArray != null) {
                    collapsingToolbarLayout.setContentScrimColor(
                            ContextCompat.getColor(context, colorArray[tab.getPosition()]));
                }
                imageView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_show));

                if (onTabSelectedListener != null) {
                    onTabSelectedListener.onTabSelected(tab);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (onTabSelectedListener != null) {
                    onTabSelectedListener.onTabUnselected(tab);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (onTabSelectedListener != null) {
                    onTabSelectedListener.onTabReselected(tab);
                }
            }
        });
    }


    public CoordinatorTabLayout setTabMode(@TabLayout.Mode int mode) {
        tabLayout.setTabMode(mode);
        return this;
    }


    public CoordinatorTabLayout setupWithViewpager(ViewPager viewPager) {
        setupTabLayout(viewPager);
        setTabLayoutDefault();
        return this;
    }


    public ActionBar getActionBar() {
        return actionBar;
    }


    public TabLayout getTabLayout() {
        return tabLayout;
    }


    public ImageView getImageView() {
        return imageView;
    }


    public CoordinatorTabLayout setLoadHeaderImagesListener(LoadHeaderImagesListener loadHeaderImagesListener) {
        this.loadHeaderImagesListener = loadHeaderImagesListener;
        return this;
    }


    public CoordinatorTabLayout addOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.onTabSelectedListener = onTabSelectedListener;
        return this;
    }


    public CoordinatorTabLayout setTranslucentStatusBar(@NonNull Activity activity) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return this;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            activity.getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow()
                    .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (toolbar != null) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
            layoutParams.setMargins(
                    layoutParams.leftMargin,
                    layoutParams.topMargin + SystemView.getStatusBarHeight(activity),
                    layoutParams.rightMargin,
                    layoutParams.bottomMargin);
        }

        return this;
    }


    public CoordinatorTabLayout setTranslucentNavigationBar(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return this;
        } else {
            toolbar.setPadding(0, SystemView.getStatusBarHeight(activity) >> 1, 0, 0);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        return this;
    }

}
