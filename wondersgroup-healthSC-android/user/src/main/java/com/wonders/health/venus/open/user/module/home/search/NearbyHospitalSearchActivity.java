package com.wonders.health.venus.open.user.module.home.search;

import android.content.Intent;

import com.wonders.health.venus.open.user.entity.SearchConfig;

/**
 * 附近就医搜索
 * Created by songzhen on 2016/11/11.
 */
public class NearbyHospitalSearchActivity extends SearchActivity {
    @Override
    protected SearchConfig initConfig() {
        SearchConfig config = new SearchConfig();
        config.history_pref_name = "nearby_search_history";
        config.hint_text = "搜索医院";
        return config;
    }

    @Override
    protected void search(String key) {
        Intent intent = new Intent(this, SearchHospitalListActivity.class);
        intent.putExtra(SearchHospitalListActivity.EXTRA_KEY, key);
        intent.putExtra(SearchHospitalListActivity.EXTRA_FROM,SearchHospitalListActivity.FROM_NEARBY);
        startActivity(intent);
    }
}
