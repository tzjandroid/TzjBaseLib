package com.tzj.baselib;

import android.content.Intent;
import android.os.Handler;
import android.view.View;

import com.tzj.baselib.chain.fragment.BaseLibFragment;
import com.tzj.baselib.demo.R;
import com.tzj.recyclerview.TzjRecyclerView;
import com.tzj.recyclerview.adapter.TzjAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends BaseLibFragment {

    private List<String> list = new ArrayList<>();
    private TzjRecyclerView mRecyclerView;

    @Override
    protected int layoutId() {
        return R.layout.view_refresh_recycler;
    }

    @Override
    public void initView() {
        super.initView();
        mRecyclerView = findViewById(R.id.swipe_target);
        mRecyclerView.setLineLayoutManager();
        mRecyclerView.setViewType(R.layout.item_main,StringHolder.class);
        mRecyclerView.setList(list);
        mRecyclerView.setClickListener(new TzjAdapter.OnClickIndexListener() {
            @Override
            public void onClick(View view, int i) {
                startActivity(new Intent(getActivity(),MainActivity.class));
            }
        });
    }

    @Override
    public void onRefresh() {
        for (int i = 0; i < 100; i++) {
            list.add("this is "+ i);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainFragment.super.onRefresh();
            }
        },3000);
    }

    @Override
    public void loadFinish() {
        super.loadFinish();
        mRecyclerView.notifyDataSetChanged();
    }


}
