package com.wonders.health.venus.open.user.module.home.search;

import android.content.Intent;

import com.wonders.health.venus.open.user.entity.SearchConfig;

/**
 * 首页搜索
 * Created by songzhen on 2016/11/8.
 */
public class HomeSearchActivity extends SearchActivity {
    @Override
    protected SearchConfig initConfig() {
        SearchConfig config = new SearchConfig();
        config.history_pref_name = "home_search_history";
        config.hint_text = "搜索医院、医生、文章";
        return config;
    }

    @Override
    protected void search(String key) {
        Intent intent = new Intent(this, HomeSearchResultActivity.class);
        intent.putExtra(HomeSearchResultActivity.EXTEA_KEY, key);
        intent.putExtra(HomeSearchResultActivity.EXTRA_FROM,SearchHospitalListActivity.FROM_HOME);
        startActivity(intent);
    }

}
