package com.bwie.searchview.search.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.searchview.R;
import com.bwie.searchview.search.adapter.SearchAdapter;
import com.bwie.searchview.search.bean.SearchContentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * date:2019/1/13
 * author:张自力(DELL)
 * function:
 */

public class SearchViewUtils extends Fragment implements View.OnClickListener {

    private SearchView searchview;
    private TextView txtSearch;
    private RecyclerView rvLiushi;
    private SharedPreferences mSearchSp;
    private List<SearchContentBean>  mList;
    private SearchAdapter mSearchAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.item_searchview_utils, null);
        //初始化布局
        initView(view);
        //List And Adapter
        initListAndAdapter();
        //initShareadPrefrence
        initShareadPrefrence();
        //searchView的搜索内容
        initSearchView();
        return view;
    }

    /**
     * //searchView的搜索内容
     * */
    String serachContent="";
    private void initSearchView() {
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            //输入完成后，提交时触发的方法，一般情况是点击输入法中的搜索按钮才会触发，表示现在正式提交了
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }


            //在输入时触发的方法，当字符真正显示到searchView中才触发，像是拼音，在输入法组词的时候不会触发
            @Override
            public boolean onQueryTextChange(String newText) {

                serachContent = newText;

                return true;
            }
        });
    }


    /**
     * //initShareadPrefrence
     * */
    private void initShareadPrefrence() {
        mSearchSp =getActivity().getSharedPreferences("search", Context.MODE_PRIVATE);

        String string = mSearchSp.getString("searchContent", "");
        //将数据加入集合中
        mList.add(new SearchContentBean(string));
        mSearchAdapter.notifyDataSetChanged();
    }

    /**
     * //List And Adapter
     * */
    private void initListAndAdapter() {
        //搜索
        mList = new ArrayList<>();
        mSearchAdapter = new SearchAdapter(getActivity(), mList);
        rvLiushi.setAdapter(mSearchAdapter);
    }


    /**
     * 初始化控件
     */
    private void initView(View view) {
        searchview = (SearchView) view.findViewById(R.id.searchview);
        txtSearch = (TextView) view.findViewById(R.id.txt_search);
        rvLiushi = (RecyclerView) view.findViewById(R.id.rv_liushi);
        rvLiushi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_search://点击搜索按钮
                //判断非空
                if(serachContent.equals("")){
                    //提示
                    Toast.makeText(getActivity(),"请输入搜索内容",Toast.LENGTH_SHORT).show();
                    break;
                }
                boolean searchContent = mSearchSp.edit()
                        .putString("searchContent", serachContent)
                        .commit();
                //将数据加入集合中
                mList.add(new SearchContentBean(serachContent));
                mSearchAdapter.notifyDataSetChanged();
                break;
        }
    }


    //销毁

}
