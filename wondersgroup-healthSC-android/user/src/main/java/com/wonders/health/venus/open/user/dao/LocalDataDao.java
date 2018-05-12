package com.wonders.health.venus.open.user.dao;

import com.wonders.health.venus.open.user.BaseApp;
import com.wondersgroup.hs.healthcloud.common.util.DbUtils;
import com.wondersgroup.hs.healthcloud.common.util.FileUtil;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

/**
 * 类描述：
 * 创建人：hhw
 * 创建时间：2016/11/3 9:42
 */
public class LocalDataDao {
    public static String LOCAL_DATA_DB_NAME = "local_data.db";
    public static final String ASSERT_DB_NAME = "data.db";
    protected DbUtils dbUtils;

    private static Object lock = new Object();

    private int dbVersion = 2;

    public LocalDataDao() {
        LOCAL_DATA_DB_NAME = "local_data_patient" + dbVersion + ".db";
        File file = BaseApp.getApp().getDatabasePath(LOCAL_DATA_DB_NAME);
        if (file.exists()) {
            dbUtils = DbUtils.create(BaseApp.getApp(), file.getParent(), file.getName());
        }
    }

    public void init() {
        new Thread() {
            @Override
            public void run() {
                synchronized (lock) {
                    final File file = BaseApp.getApp().getDatabasePath(LOCAL_DATA_DB_NAME);
                    if (!file.exists()) {
                        FileUtil.deleteDirectory(file.getParent(), new FileFilter() {
                            @Override
                            public boolean accept(File pathname) {
                                return pathname.getName().startsWith("local_data");
                            }
                        });
                        try {
                            FileUtil.copyAssets(BaseApp.getApp(), ASSERT_DB_NAME, file.getAbsolutePath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        dbUtils = DbUtils.create(BaseApp.getApp(), file.getParent(), file.getName());
                    }
                }

            }
        }.start();
    }
}
