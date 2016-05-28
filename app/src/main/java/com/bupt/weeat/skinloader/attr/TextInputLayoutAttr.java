package com.bupt.weeat.skinloader.attr;

import android.support.design.widget.TextInputLayout;
import android.view.View;


/**
 * Created by zhaoruolei1992 on 2016/5/28.
 */
public class TextInputLayoutAttr extends SkinAttr {
    @Override
    public void apply(View view) {
        if (view instanceof TextInputLayout) {
            TextInputLayout textInputLayout = (TextInputLayout) view;
            //textInputLayout.setHintTextAppearance(android.support.design.R.styleable);
            //textInputLayout.s

        }
    }

}
