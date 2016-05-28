package com.bupt.weeat.ui.fragment;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bupt.weeat.R;
import com.bupt.weeat.skinloader.listener.ILoaderListener;
import com.bupt.weeat.skinloader.load.SkinManager;
import com.bupt.weeat.utils.FileUtils;

import java.io.File;

import butterknife.OnClick;

/**
 * Created by zhaoruolei1992 on 2016/5/25.
 */
public class ChangeSkinFragment extends BaseFragment {
    private static String TAG = "ChangeSkinFragment";
    private static String SKIN_BROWN_NAME = "skin_brown.skin";
    private static String SKIN_BLACK_NAME = "skin_black.skin";
    private static String SKIN_DIR;
    private String skinFullName;
    private File skin;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_change_skin;

    }

    @Override
    protected void initData() {
        super.initData();
        SKIN_DIR = FileUtils.getSkinDirPath(context);
    }

    @OnClick({R.id.ll_green, R.id.ll_brown, R.id.ll_black})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_green:
                SkinManager.getInstance().restoreDefaultTheme();
                break;
            case R.id.ll_brown:
                skinFullName = SKIN_DIR + File.separator + "skin_brown.skin";
                FileUtils.moveRawToDir(context, "skin_brown.skin", skinFullName);
                skin = new File(skinFullName);
                if (!skin.exists()) {
                    Toast.makeText(context, "请检查" + skinFullName + "是否存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                SkinManager.getInstance().load(skin.getAbsolutePath(),
                        new ILoaderListener() {
                            @Override
                            public void onStart() {
                                Log.e(TAG, "loadSkinStart");
                            }

                            @Override
                            public void onSuccess() {
                                Log.i(TAG, "loadSkinSuccess");
                                Toast.makeText(context, "切换成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailed() {
                                Log.i(TAG, "loadSkinFail");
                                Toast.makeText(context, "切换失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            case R.id.ll_black:
                skinFullName = SKIN_DIR + File.separator + "skin_black.skin";
                FileUtils.moveRawToDir(context, "skin_black.skin", skinFullName);
                skin = new File(skinFullName);
                if (!skin.exists()) {
                    Toast.makeText(context, "请检查" + skinFullName + "是否存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                SkinManager.getInstance().load(skin.getAbsolutePath(),
                        new ILoaderListener() {
                            @Override
                            public void onStart() {
                                Log.e(TAG, "loadSkinStart");
                            }

                            @Override
                            public void onSuccess() {
                                Log.e(TAG, "loadSkinSuccess");
                                Toast.makeText(context, "切换成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailed() {
                                Log.e(TAG, "loadSkinFail");
                                Toast.makeText(context, "切换失败", Toast.LENGTH_SHORT).show();
                            }
                        });
        }
    }
}
