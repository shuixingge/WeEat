package com.bupt.weeat.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bupt.weeat.R;
import com.bupt.weeat.model.entity.User;
import com.bupt.weeat.ui.fragment.ChangeSkinFragment;
import com.bupt.weeat.ui.fragment.FindFragment;
import com.bupt.weeat.ui.fragment.HeathFragment;
import com.bupt.weeat.ui.fragment.RecipeFragment;
import com.bupt.weeat.ui.fragment.SquareFragment;
import com.bupt.weeat.utils.ActivityUtils;
import com.bupt.weeat.utils.BaseUtils;
import com.bupt.weeat.utils.LogUtils;
import com.bupt.weeat.utils.NetUtils;
import com.bupt.weeat.utils.ToastUtils;
import com.bupt.weeat.utils.UserUtils;
import com.bupt.weeat.utils.ViewUtils;
import com.bupt.weeat.view.customView.CircleTransformation;
import com.bupt.weeat.view.customView.RoundImageView;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import cn.bmob.v3.BmobUser;


public class MainActivity extends BaseActivity {

    private ActionBarDrawerToggle mDrawerToggle;
    private User user;
    public static final String TAG = MainActivity.class.getSimpleName();
    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;
    private MenuItem mPreMenuItem;

    public static final int ANIM_DURATION_TOOLBAR = 500;
    private static final int USER_ACTIVITY = 3;
    private static final int TIME_INTERVAL = 2000;
    private static final int REQUEST_LOGIN_CODE = 0;
    private static final int REQUEST_USER_CODE = 1;
    private long mBackPressed;
    private boolean pendingIntroAnim;
    private MenuItem menuItem;
    int avatarSize;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.nav)
    NavigationView nav;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    RoundImageView navUserAvatar;
    TextView navUserName;


    public int getLayoutId() {
        return R.layout.main;
    }


    @Override
    protected void initData() {
        super.initData();
        mFragmentManager = getSupportFragmentManager();
        View headerLayout = nav.getHeaderView(0);
        navUserAvatar = (RoundImageView) headerLayout.findViewById(R.id.user_avatar_iv);
        navUserName = (TextView) headerLayout.findViewById(R.id.user_name_tv);
        pendingIntroAnim = true;
        avatarSize = getResources().getDimensionPixelSize(R.dimen.global_menu_avatar_size);
        toolbar.setTitle("首页");
        setSupportActionBar(toolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        initDefaultFragment();
        setNavContent(nav);
        updateUserHeader();
        dynamicAddSkinEnableView(toolbar, "background", R.color.colorPrimary);
        dynamicAddSkinEnableView(nav.getHeaderView(0), "background", R.color.colorPrimary);
        dynamicAddSkinEnableView(nav, "navigationViewMenu", R.color.colorPrimary);
    }

    private void initDefaultFragment() {
        Log.i(TAG, "initDefaultFragment");
        try {
            mCurrentFragment = ViewUtils.createFragment(FindFragment.class);
            mFragmentManager.beginTransaction().add(R.id.drawer_frame, mCurrentFragment).commit();
            mPreMenuItem = nav.getMenu().getItem(0);
            mPreMenuItem.setChecked(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        navUserAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserUtils.isLogin(context)) {
                    Intent userIntent = new Intent(MainActivity.this, UserActivity.class);
                    startActivityForResult(userIntent, REQUEST_USER_CODE);
                } else {
                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(loginIntent, REQUEST_LOGIN_CODE);
                }
            }
        });
    }

    private void setNavContent(NavigationView nav) {
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (null != mPreMenuItem) {
                    mPreMenuItem.setChecked(false);
                }
                switch (menuItem.getItemId()) {
                    case R.id.nav_item_home:
                        toolbar.setTitle(R.string.drawer_home);
                        switchFragment(FindFragment.class);
                        break;
                    case R.id.nav_item_search:
                        toolbar.setTitle(R.string.drawer_search);
                        switchFragment(RecipeFragment.class);
                        break;
                    case R.id.nav_item_square:
                        toolbar.setTitle(R.string.drawer_square);
                        switchFragment(SquareFragment.class);
                        break;
                    case R.id.nav_item_person:
                        if (!UserUtils.isLogin(context)) {
                            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                                mDrawerLayout.closeDrawer(GravityCompat.START);
                            }
                            ToastUtils.showToast(context, R.string.please_login_first, Toast.LENGTH_SHORT);
                            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivityForResult(loginIntent, REQUEST_LOGIN_CODE);
                        } else {
                            Intent intent = new Intent(MainActivity.this, UserActivity.class);
                            startActivityForResult(intent, REQUEST_USER_CODE);
                        }
                        toolbar.setTitle(R.string.drawer_person);
                        break;
                    case R.id.nav_item_heath:
                        toolbar.setTitle(R.string.drawer_heath);
                        switchFragment(HeathFragment.class);
                        break;
                    case R.id.menu_settings:
                        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_change_theme:
                        toolbar.setTitle(R.string.change_theme);
                        switchFragment(ChangeSkinFragment.class);
                    default:
                        break;

                }
                menuItem.setChecked(true);
                mPreMenuItem = menuItem;
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.i(TAG, "onActivityResult");
        if (resultCode == 2 || requestCode == 1) {
            switch (requestCode) {
                case REQUEST_LOGIN_CODE:
                    LogUtils.i(TAG, "onActivityResult" + REQUEST_LOGIN_CODE);
                    updateUserHeader();
                    switchFragment(FindFragment.class);
                    break;
                case REQUEST_USER_CODE:
                    LogUtils.i(TAG, "onActivityResult" + REQUEST_USER_CODE);
                    updateUserHeader();
                    switchFragment(FindFragment.class);
                    break;
                default:
                    break;
            }
        }
    }


    public void updateUserHeader() {
        user = BmobUser.getCurrentUser(this, User.class);
        LogUtils.i(TAG, user + "updateUserHeader()");
        if (user != null && user.getUserImage() != null) {
            String imageUrl = user.getUserImage().getFileUrl(this);
            Picasso.with(this)
                    .load(imageUrl)
                    .centerCrop()
                    .resize(avatarSize, avatarSize)
                    .placeholder(R.drawable.tou_xiang)
                    .transform(new CircleTransformation())
                    .into(navUserAvatar);
            LogUtils.i(TAG, user + "");
            if (user.getUsername() != null) {
                navUserName.setText(user.getUsername());
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        boolean connectedNet = NetUtils.checkNet(this);
        if (!connectedNet) {
            ToastUtils.showToast(this, R.string.check_net, Toast.LENGTH_SHORT);
        }
        mBackPressed = 0L;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }

        if (System.currentTimeMillis() - mBackPressed > TIME_INTERVAL) {
            mBackPressed = System.currentTimeMillis();
            ToastUtils.showToast(this, R.string.one_more_press_exit, Toast.LENGTH_SHORT);
        } else {
            ToastUtils.clearToast();
            ActivityUtils.finishAll();
        }
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        menuItem = menu.findItem(R.id.actionbar_take_picture);
        menuItem.setActionView(R.layout.menu_item_view);
        if (pendingIntroAnim) {
            //startToolbarAnim();
            pendingIntroAnim = false;
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void startToolbarAnim() {
        int actionBarSize = BaseUtils.dpToPx(56);
        toolbar.setTranslationY(-actionBarSize);
        menuItem.getActionView().setTranslationY(-actionBarSize);
        toolbar.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
        menuItem.getActionView().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400);
    }

    private void switchFragment(Class<?> clazz) {
        Fragment to = ViewUtils.createFragment(clazz);
        if (to.isAdded()) {
            Log.i(TAG, "Added");
            mFragmentManager.beginTransaction().hide(mCurrentFragment).show(to).commitAllowingStateLoss();
        } else {
            Log.i(TAG, "Not Added");
            mFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.drawer_frame, to).commitAllowingStateLoss();
        }
        mCurrentFragment = to;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
