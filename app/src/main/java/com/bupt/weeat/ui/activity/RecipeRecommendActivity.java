package com.bupt.weeat.ui.activity;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bupt.weeat.R;
import com.bupt.weeat.adapter.RecipeCategoryAdapter;
import com.bupt.weeat.view.customView.RecyclerItemClickListener;

import butterknife.Bind;

public class RecipeRecommendActivity extends BaseActivity {
    @Bind(R.id.recipe_recycler)
    RecyclerView recyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Override
    public int getLayoutId() {
        return R.layout.activity_recipe_recommend;
    }

    @Override
    protected void initData() {
        super.initData();
        setSupportActionBar(toolbar);
        setTitle("食谱推荐");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        RecipeCategoryAdapter adapter=new RecipeCategoryAdapter(this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        dynamicAddSkinEnableView(toolbar, "background", R.color.colorPrimary);
    }

    @Override
    protected void setListener() {
        super.setListener();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }
}
