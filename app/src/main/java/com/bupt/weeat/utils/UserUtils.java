package com.bupt.weeat.utils;

import android.content.Context;

import com.bupt.weeat.model.entity.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class UserUtils  {
    private static final String TAG="UserUtils";
    private Context mContext;

    public UserUtils(Context mContext) {
        this.mContext = mContext;
    }

    public void SignUp(String userMail,String userName,String Password){
        User user=new User();
        user.setUsername(userName);
        user.setEmail(userMail);
        user.setPassword(Password);
        user.signUp(mContext, new SaveListener() {
            @Override
            public void onSuccess() {
                mSignUpListener.onSignUpSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                 mSignUpListener.onSignUpFailure(s);
            }
        });
    }
    private SignUpListener mSignUpListener;
    public interface SignUpListener{
        void onSignUpSuccess();
        void onSignUpFailure(String s);

    }
    public void setSignUpListener(SignUpListener mSignUpListener){
        this.mSignUpListener=mSignUpListener;

    }
    public void Login(String userName,String Password){
        User user=new User();
        user.setPassword(Password);
        user.setUsername(userName);
        user.login(mContext, new SaveListener() {
            @Override
            public void onSuccess() {
              mLoginListener.onLoginSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                mLoginListener.onLoginFailure(s);
            }
        });

    }
   private LoginListener mLoginListener;
    public interface LoginListener{
       void onLoginSuccess();
       void onLoginFailure(String s);
   }
    public void setLoginListener(LoginListener mLoginListener){
              this.mLoginListener=mLoginListener;

    }
    public static boolean isLogin(Context context) {
        BmobUser user = BmobUser.getCurrentUser(context, User.class);
        if (user != null) {
            return true;
        }
        return false;
    }
}
