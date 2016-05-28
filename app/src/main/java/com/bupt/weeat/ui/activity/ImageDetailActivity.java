package com.bupt.weeat.ui.activity;


import com.bupt.weeat.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoView;

public class ImageDetailActivity extends BaseActivity {



    @Bind(value = R.id.iv_img)
    PhotoView iv_img;
    String path;
    @Override
    public int getLayoutId() {
        return R.layout.activity_image_detail;
    }

    @Override
    protected void initData() {
        super.initData();
        path=getIntent().getStringExtra("image_url");
        Picasso.with(context).load(path).into(iv_img);

    }
}
