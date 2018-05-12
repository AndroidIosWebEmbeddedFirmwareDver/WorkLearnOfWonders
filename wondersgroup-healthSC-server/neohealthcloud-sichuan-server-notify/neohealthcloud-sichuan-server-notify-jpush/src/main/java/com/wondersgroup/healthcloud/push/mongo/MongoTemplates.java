package com.wondersgroup.healthcloud.push.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * 封装mongo操作(不采用springboot 自动配置mongo, 因为何时初始化mongo不受控制)
 * Created by jialing.yao on 2017-5-11.
 */
public class MongoTemplates {
    private MongoClient mongoClient;
    private String dbName;

    public MongoTemplates(MongoClient mongoClient, String dbName) {
        this.mongoClient = mongoClient;
        this.dbName = dbName;
    }

    public boolean insertOne(String collectionName, Object data) {
        MongoDatabase db = mongoClient.getDatabase(dbName);
        MongoCollection dbCollection = db.getCollection(collectionName);
        long num = dbCollection.count();
        dbCollection.insertOne(data);
        if (dbCollection.count() - num > 0) {
            return true;
        }
        return false;
    }

}
