package com.bupt.weeat.ui.fragment;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bupt.weeat.R;
import com.bupt.weeat.model.entity.DishBean;
import com.bupt.weeat.presenter.RecommendationPresenter;
import com.bupt.weeat.ui.activity.ImageDetailActivity;
import com.bupt.weeat.view.mvpView.DishBeanListView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class RecommendationFragment extends BaseFragment implements DishBeanListView {

    @Bind(R.id.random_food_iv)
    ImageView randomDishImage;
    @Bind(R.id.anim_love_iv)
    ImageView animLoveIv;
    @Bind(R.id.recommendation_dish_name)
    TextView dishName;
    @Bind(R.id.anim_love_bg)
    View animLoveBg;
    @Bind(R.id.recommendation_dish_tastes)
    TextView dishTaste;
    @Bind(R.id.recommendation_dish_praise_num)
    TextView dishPraiseNum;
    @Bind(R.id.recommendation_dish_location)
    TextView dishLocation;
    @Bind(R.id.recommendation_dish_praise_image)
    ImageView dishPraiseImage;
    private DishBean dish;
    private String path;
    private RecommendationPresenter presenter;
    private static final String TAG = RecommendationFragment.class.getSimpleName();
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final DecelerateInterpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();

    public static RecommendationFragment newInstance() {
        return new RecommendationFragment();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.hot_recom_fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        presenter=new RecommendationPresenter(context);
        presenter.attachView(this);
        presenter.loadList();
        Log.i(TAG,"initData");
        showLoading("加载中...");
        dynamicAddSkinView(dishTaste,"background",R.color.colorPrimary);
    }

    @Override
    public void refresh(List<DishBean> data) {
        dish = data.get(0);
        path = dish.getDish_Cover().getPath();
        if (!path.isEmpty()) {
            Picasso.with(context)
                    .load(path)
                    .placeholder(R.drawable.loading_placeholer)
                    .resize(300, 200)
                    .centerCrop()
                    .into(randomDishImage);
            dishLocation.setText(dish.getDish_Window().getLocation());
            dishName.setText(dish.getName());
            dishTaste.setText(dish.getTastes());
            dishPraiseNum.setText(dish.getPraise());
        }
    }

    @Override
    public void loadMore(List<DishBean> data) {

    }
    @OnClick({R.id.recommendation_dish_praise_image, R.id.random_food_iv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recommendation_dish_praise_image:
                String praise = (String) dishPraiseNum.getText();
                int currentPraise = Integer.parseInt(praise);
                dishPraiseNum.setText((++currentPraise) + "");
                dish.setPraise((++currentPraise) + "");
                animPictureLove();
                break;
            case R.id.random_food_iv:
                Intent intent = new Intent(getActivity(), ImageDetailActivity.class);
                intent.putExtra("image_url", path);
                startActivity(intent);
            default:
                break;
        }
    }

    private void animPictureLove() {
        animLoveBg.setVisibility(View.VISIBLE);
        animLoveIv.setVisibility(View.VISIBLE);

        animLoveIv.setScaleX(0.1f);
        animLoveIv.setScaleY(0.1f);

        animLoveBg.setScaleX(0.1f);
        animLoveBg.setScaleY(0.1f);
        animLoveBg.setAlpha(1f);

        AnimatorSet set = new AnimatorSet();
        ObjectAnimator bgScaleUpX = ObjectAnimator.ofFloat(animLoveBg, "ScaleX", 0.1f, 1f);
        bgScaleUpX.setDuration(200);
        bgScaleUpX.setInterpolator(DECELERATE_INTERPOLATOR);


        ObjectAnimator bgScaleUpY = ObjectAnimator.ofFloat(animLoveBg, "ScaleY", 0.1f, 1f);
        bgScaleUpY.setDuration(200);
        bgScaleUpY.setInterpolator(DECELERATE_INTERPOLATOR);

        ObjectAnimator bgAlpha = ObjectAnimator.ofFloat(animLoveBg, "alpha", 1f, 0f);
        bgAlpha.setDuration(200);
        bgAlpha.setStartDelay(150);
        bgAlpha.setInterpolator(DECELERATE_INTERPOLATOR);

        ObjectAnimator ivScaleUpX = ObjectAnimator.ofFloat(animLoveIv, "ScaleX", 0.1f, 1f);
        ivScaleUpX.setDuration(300);
        ivScaleUpX.setInterpolator(DECELERATE_INTERPOLATOR);

        ObjectAnimator ivScaleUpY = ObjectAnimator.ofFloat(animLoveIv, "ScaleY", 0.1f, 1f);
        ivScaleUpY.setDuration(300);
        ivScaleUpY.setInterpolator(DECELERATE_INTERPOLATOR);

        ObjectAnimator ivScaleDownX = ObjectAnimator.ofFloat(animLoveIv, "ScaleX", 1f, 0f);
        ivScaleDownX.setDuration(300);
        ivScaleDownX.setInterpolator(ACCELERATE_INTERPOLATOR);

        ObjectAnimator ivScaleDownY = ObjectAnimator.ofFloat(animLoveIv, "ScaleY", 1f, 0f);
        ivScaleDownY.setDuration(300);
        ivScaleDownY.setInterpolator(ACCELERATE_INTERPOLATOR);

        set.playTogether(bgScaleUpX, bgScaleUpY, bgAlpha, ivScaleUpX, ivScaleUpY);
        set.play(ivScaleDownX).with(ivScaleDownY).after(ivScaleUpY);
        set.start();
    }
}