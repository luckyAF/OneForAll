package com.luckyaf.oneforall.module.test.ui.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.luckyaf.oneforall.module.test.R;
import com.luckyaf.smartandroid.lib.base.app.BaseFragment;
import com.luckyaf.smartandroid.lib.base.widget.recyclerview.BaseAdapter;
import com.luckyaf.smartandroid.lib.base.widget.recyclerview.CommonViewHolder;
import com.luckyaf.smartandroid.lib.base.widget.recyclerview.HeaderFooterAdapter;
import com.luckyaf.smartandroid.lib.base.widget.recyclerview.ItemHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2018/7/16
 */
public class RecyclerViewFragment extends BaseFragment{
    private RecyclerView recyclerView;
    private BaseAdapter mInnerAdapter;
    private List<String> stringList = new ArrayList<>();
    private HeaderFooterAdapter mHeaderFooterAdapter;


    public static RecyclerViewFragment newInstance(){
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.test_fragment_recyclerview;
    }

    @Override
    protected void initParams(Bundle bundle) {

    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        recyclerView = findView(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mInnerAdapter = new BaseAdapter<String>(mContext,stringList,R.layout.test_item_string) {
            @Override
            public void convert(CommonViewHolder holder, String data, int position) {
                holder.setText(R.id.txt_content,data);
            }
        };
        mHeaderFooterAdapter = new HeaderFooterAdapter(mInnerAdapter);
        mHeaderFooterAdapter.setHeaderView(0, new ItemHelper() {
            @Override
            public int getItemLayoutId() {
                return R.layout.test_item_header;
            }

            @Override
            public void onBind(CommonViewHolder holder) {
                holder.setText(R.id.txt_header,"头部");
            }
        });
        recyclerView.setAdapter(mHeaderFooterAdapter);
    }

    @Override
    protected void initData() {
        //2-1 异步数据，不借助实体类
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mHeaderFooterAdapter.addHeaderView( new ItemHelper() {
                    @Override
                    public int getItemLayoutId() {
                        return R.layout.test_item_header;
                    }

                    @Override
                    public void onBind(CommonViewHolder holder) {
                        //假装从网络拿到了数据
                         holder.setText(R.id.txt_header, "new header");
                    }
                });
                stringList.add("A");
                stringList.add("B");
                stringList.add("C");
                stringList.add("D");
                stringList.add("E");

                mHeaderFooterAdapter.notifyDataSetChanged();
            }
        }, 4000);
    }
}
