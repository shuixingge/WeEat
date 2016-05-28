package com.bupt.weeat.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bupt.weeat.Constant;
import com.bupt.weeat.R;
import com.bupt.weeat.model.entity.User;
import com.bupt.weeat.utils.LogUtils;
import com.bupt.weeat.utils.ToastUtils;
import com.bupt.weeat.view.customView.CircleTransformation;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.Bind;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = UserActivity.class.getSimpleName();
    private static final int REQUEST_CODE_ALBUM = 1;
    private static final int REQUEST_LOGIN = 0;
    private User user;
    @Bind(R.id.user_nick_name)
    TextView userName;
    @Bind(R.id.head_user_name)
    TextView headUserName;
    @Bind(R.id.user_avatar_layout)
    RelativeLayout avatarLayout;
    @Bind(R.id.user_gender_layout)
    RelativeLayout userGenderLayout;
    @Bind(R.id.user_activity_avatar_iv)
    CircleImageView userAvatar;
    @Bind(R.id.user_activity_header_avatar_iv)
    CircleImageView  headAvatar;
    @Bind(R.id.user_gender_switch)
    Switch genderSwitch;
    @Bind(R.id.logout)
    Button logout;
    @Bind(R.id.user_profile_tabs)
    TabLayout tabLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.user_collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    private static final int RESULT_CODE__USER = 1;

    @Override
    protected void initData() {
        super.initData();
        initToolbar();
        user = BmobUser.getCurrentUser(this, User.class);
        initPersonalInfo();
        dynamicAddSkinEnableView(toolbar, "background", R.color.colorPrimary);
        dynamicAddSkinEnableView(collapsingToolbarLayout, "contentScrimColor", R.color.colorPrimary);
        dynamicAddSkinEnableView(logout, "background", R.color.colorPrimary);
        dynamicAddSkinEnableView(tabLayout, "tabIndicatorColor", R.color.colorAccent);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        collapsingToolbarLayout.setTitle("个人");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                BmobFile imageFile = user.getUserImage();
                LogUtils.i(TAG, "onClick" + imageFile.getFileUrl(UserActivity.this));
                setResult(RESULT_CODE__USER, intent);
                finish();
            }
        });
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_user;
    }

    private void initPersonalInfo() {
        LogUtils.i(TAG, "initPersonalInfo()");
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.
                ic_drawer_person).setText("个人"));
        tabLayout.addTab(tabLayout.newTab().setText("消息").setIcon(R.drawable.ic_message));
        if (user != null) {
            LogUtils.i(TAG, user.getUsername() + "");
            userName.setText(user.getUsername());
            headUserName.setText(user.getUsername());
            if (user.getSex() != null && user.getSex().equals(Constant.FEMALE_SEX)) {
                genderSwitch.setChecked(false);
            } else {
                genderSwitch.setChecked(true);
            }
            BmobFile imageFile = user.getUserImage();
            if (imageFile != null) {
                String avatarUrl = imageFile.getFileUrl(this);
                LogUtils.i(TAG, avatarUrl + " avatarUrl");
                Glide.clear(userAvatar);

                Picasso.with(this)
                        .load(avatarUrl)
                        .centerCrop()
                        .resize(90, 90)
                        .placeholder(R.drawable.tou_xiang)
                        .transform(new CircleTransformation())
                        .into(headAvatar);

                Picasso.with(this)
                        .load(avatarUrl)
                        .centerCrop()
                        .resize(48, 48)
                        .placeholder(R.drawable.tou_xiang)
                        .into(userAvatar);
            }
            logout.setText(R.string.logout);
        } else {
            logout.setText(R.string.login);
        }

    }

    @Override
    protected void setListener() {
        avatarLayout.setOnClickListener(this);
        userGenderLayout.setOnClickListener(this);
        genderSwitch.setOnCheckedChangeListener(this);
        logout.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout:
                if (isLogin()) {
                    BmobUser.logOut(this);
                    ToastUtils.showToast(this, R.string.already_logout, Toast.LENGTH_SHORT);
                    skipLogin();
                }
                break;
            case R.id.user_avatar_layout:
                LogUtils.i(TAG, "click image");

                Intent selectPictureIntent = new Intent(Intent.ACTION_PICK);
                selectPictureIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(selectPictureIntent,
                        "Select Picture"), REQUEST_CODE_ALBUM);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.i(TAG, "onActivityResult");
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_ALBUM:
                    if (data != null) {
                        Uri originalUri = data.getData();
                        String avatarUrl = getRealPathFromUri(originalUri);
                        Picasso.with(this)
                                .load(Uri.parse("file://" + avatarUrl))
                                .centerCrop()
                                .resize(90, 90)
                                .placeholder(R.drawable.tou_xiang)
                                .transform(new CircleTransformation())
                                .into(headAvatar);
                        Picasso.with(this)
                                .load(Uri.parse("file://" + avatarUrl))
                                .centerCrop()
                                .resize(48, 48)
                                .placeholder(R.drawable.tou_xiang)
                                .into(userAvatar);
                        final BmobFile file = new BmobFile(new File(avatarUrl));
                        user.setUserImage(file);
                        file.uploadblock(this, new UploadFileListener() {
                            @Override
                            public void onSuccess() {
                                user.update(UserActivity.this, new UpdateListener() {
                                    @Override
                                    public void onSuccess() {
                                        LogUtils.i(TAG, "update user image success");
                                    }

                                    @Override
                                    public void onFailure(int i, String s) {
                                        LogUtils.i(TAG, s);
                                    }
                                });

                            }

                            @Override
                            public void onFailure(int i, String s) {
                                LogUtils.i(TAG, s);
                            }
                        });
                    }

                    break;
                default:
                    break;
            }
        }
    }

    public String getRealPathFromUri(Uri contentUri) {

        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContentResolver().query(contentUri, projection, null, null, null);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String realPath = cursor.getString(columnIndex);
        cursor.close();
        return realPath;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.user_gender_switch:
                if (isChecked) {
                    updateSex(0);
                } else {
                    updateSex(1);
                }
                break;
            default:
                break;
        }

    }

    private void updateSex(int sex) {
        if (isLogin() && user != null) {
            if (sex == 0) {
                user.setSex(Constant.MALE_SEX);
            } else {
                user.setSex(Constant.FEMALE_SEX);
            }
            user.update(this, new UpdateListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(int i, String s) {

                }
            });
        } else {
            skipLogin();
        }

    }

    private void skipLogin() {
        ToastUtils.showToast(this, R.string.please_login_first, Toast.LENGTH_SHORT);
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivityForResult(loginIntent, REQUEST_LOGIN);
    }

    public boolean isLogin() {
        BmobUser user = BmobUser.getCurrentUser(this, User.class);
        if (user != null) {
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG, "onDestroy()");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
