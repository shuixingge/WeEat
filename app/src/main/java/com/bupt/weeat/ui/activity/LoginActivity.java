package com.bupt.weeat.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bupt.weeat.Constant;
import com.bupt.weeat.R;
import com.bupt.weeat.model.entity.User;
import com.bupt.weeat.utils.LogUtils;
import com.bupt.weeat.utils.ToastUtils;
import com.bupt.weeat.utils.UserUtils;

import butterknife.Bind;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;


public class LoginActivity extends BaseActivity implements View.OnClickListener, UserUtils.SignUpListener, UserUtils.LoginListener {
    @Bind(R.id.login_user_mail)
    AppCompatEditText userMail;
    @Bind(R.id.login_user_name)
    AppCompatEditText  userName;
    @Bind(R.id.login_user_password)
    AppCompatEditText userPassword;
    @Bind(R.id.register_tv)
    TextView registerText;
    @Bind(R.id.back_login)
    TextView backLoginText;
    @Bind(R.id.find_lost_password)
    TextView findPasswordText;
    @Bind(R.id.register_button)
    Button registerButton;
    @Bind(R.id.login_progress_bar)
    ProgressBar loginProgress;
    @Bind(R.id.float_label_mail)
    TextInputLayout floatUserMail;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private final int MODE_LOGIN = 1;
    private final int MODE_REGISTER = 2;
    private int operation = MODE_LOGIN;
    private UserUtils userUtils;


    private static final String TAG = LoginActivity.class.getSimpleName();


    @Override
    public int getLayoutId() {
        return R.layout.login_activity;
    }

    @Override
    protected void initData() {
        super.initData();
        initToolbar();
        userUtils = new UserUtils(this);
        dynamicAddSkinEnableView(toolbar, "background", R.color.colorPrimary);
        dynamicAddSkinEnableView(registerButton, "background", R.color.colorPrimary);
        dynamicAddSkinEnableView(registerText, "textColor", R.color.colorPrimary);
        dynamicAddSkinEnableView(backLoginText,"textColor",R.color.colorPrimary);
        dynamicAddSkinEnableView(findPasswordText,"textColor",R.color.colorPrimary);
    }


    private void initToolbar() {
        setSupportActionBar(toolbar);
        setTitle("登陆");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void setListener() {
        registerText.setOnClickListener(this);
        backLoginText.setOnClickListener(this);
        findPasswordText.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                switch (operation) {
                    case MODE_REGISTER:
                        if (!TextUtils.isEmpty(userName.getText()) && !TextUtils.isEmpty(userPassword.getText())
                                && !TextUtils.isEmpty(userMail.getText())) {
                            registerButton.setEnabled(true);
                            registerButton.setAlpha(1.0F);
                        } else {
                            registerButton.setEnabled(false);
                            registerButton.setAlpha(0.7F);
                        }
                    case MODE_LOGIN:
                        if (!TextUtils.isEmpty(userName.getText()) && !TextUtils.isEmpty(userPassword.getText())) {
                            registerButton.setEnabled(true);
                            registerButton.setAlpha(1.0F);
                        } else {
                            registerButton.setEnabled(false);
                            registerButton.setAlpha(0.7F);
                        }
                        break;
                    default:
                        break;
                }
            }
        };
        userMail.addTextChangedListener(textWatcher);
        userName.addTextChangedListener(textWatcher);
        userPassword.addTextChangedListener(textWatcher);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_tv:
                if (operation == MODE_LOGIN) {
                    try {
                        SwitchUI(MODE_REGISTER);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    operation = MODE_REGISTER;
                }
                break;
            case R.id.back_login:
                if (operation == MODE_REGISTER) {
                    SwitchUI(MODE_LOGIN);
                    operation = MODE_LOGIN;
                }
                break;
            case R.id.register_button:
                hideSoftKeyBoard();
                if (operation == MODE_LOGIN) {
                    if (!TextUtils.isEmpty(userName.getText()) && !TextUtils.isEmpty(userPassword.getText()))
                        loginProgress.setVisibility(View.VISIBLE);
                    userUtils.setLoginListener(this);
                    userUtils.Login(userName.getText().toString().trim(),
                            userPassword.getText().toString().trim());
                    System.out.println(userUtils.toString());

                } else if (operation == MODE_REGISTER) {
                    if (!TextUtils.isEmpty(userName.getText()) && !TextUtils.isEmpty(userPassword.getText())
                            && !TextUtils.isEmpty(userMail.getText()))
                        loginProgress.setVisibility(View.VISIBLE);
                    userUtils.setSignUpListener(this);
                    userUtils.SignUp(userMail.getText().toString().trim(),
                            userName.getText().toString().trim(),
                            userPassword.getText().toString().trim());
                }
                break;
            default:
                break;
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        LogUtils.i(TAG, "onBackPressed");

    }

    public void SwitchUI(int mode) {
        switch (mode) {
            case MODE_LOGIN:
                LogUtils.i(TAG, "MODE_LOGIN");
                floatUserMail.setVisibility(View.GONE);
                registerText.setVisibility(View.VISIBLE);
                backLoginText.setVisibility(View.GONE);
                registerButton.setText(R.string.login);
                break;
            case MODE_REGISTER:
                LogUtils.i(TAG, "MODE_REGISTER");
                floatUserMail.setVisibility(View.VISIBLE);
                floatUserMail.requestFocus();
                backLoginText.setVisibility(View.VISIBLE);
                registerText.setVisibility(View.GONE);
                registerButton.setText(R.string.register);
                toolbar.setTitle("注册");
                break;
            default:
                break;
        }


    }

    @Override
    public void onSignUpSuccess() {
        LogUtils.i(TAG, " onSignUpSuccess()");
        SwitchUI(MODE_LOGIN);
        ToastUtils.showToast(getApplicationContext(), R.string.SignUp_success, Toast.LENGTH_SHORT);
    }

    @Override
    public void onSignUpFailure(String s) {
        ToastUtils.showToast(this, s, Toast.LENGTH_SHORT);
    }

    @Override
    public void onLoginSuccess() {
        LogUtils.i(TAG, " onLoginSuccess()");
        ToastUtils.showToast(this, R.string.login_success, Toast.LENGTH_SHORT);
        loginProgress.setVisibility(View.VISIBLE);
        User user = BmobUser.getCurrentUser(context, User.class);
        user.setSex(Constant.MALE_SEX);
        user.update(getApplicationContext(), new UpdateListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
        Intent intent = new Intent();
        setResult(2, intent);
        finish();
    }


    @Override
    public void onLoginFailure(String s) {
        ToastUtils.showToast(getApplicationContext(), s, Toast.LENGTH_SHORT);
    }


    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(userPassword.getWindowToken(), 0);

    }

}

