package com.bupt.weeat.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bupt.weeat.R;
import com.bupt.weeat.adapter.RecipeDetailAdapter;
import com.bupt.weeat.model.entity.RecipeBean;
import com.bupt.weeat.view.customView.SwipeLoadRefreshLayout;

import java.util.List;

import butterknife.Bind;

/**
 * Created by zhaoruolei1992 on 2016/5/25.
 */
public class RecipeDetailActivity extends BaseActivity {
    @Bind(R.id.recipe_detail_swipe)
    SwipeLoadRefreshLayout swipeRefreshLayout;
    @Bind( R.id.recipe_detail_recycler)
    RecyclerView recyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    RecipeBean recipe;
    private RecipeDetailAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_recipe_detail;
    }

    @Override
    protected void initData() {
        super.initData();
        initToolbar();
        recipe = (RecipeBean) getIntent().getSerializableExtra("recipe");
        List<RecipeBean.Step> steps = recipe.getSteps();
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorPrimary,
                R.color.colorAccent);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecipeDetailAdapter(context, steps);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);
        dynamicAddSkinEnableView(toolbar, "background", R.color.colorPrimary);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setTitle("详情");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ;onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
