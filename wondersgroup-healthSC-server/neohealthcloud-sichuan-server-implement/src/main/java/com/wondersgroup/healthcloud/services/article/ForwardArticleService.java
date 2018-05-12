package com.wondersgroup.healthcloud.services.article;

import com.wondersgroup.healthcloud.jpa.entity.article.ForwardArticle;
import com.wondersgroup.healthcloud.jpa.repository.article.ForwardArticleRepository;
import com.wondersgroup.healthcloud.services.article.dto.ForwardArticleAPIEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dukuanxin on 2016/8/25.
 */
@Component
public class ForwardArticleService {

    @Autowired
    private ForwardArticleRepository repository;

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jt;


    public int updateForwardArticle(ForwardArticle forwardArticle) {
        forwardArticle.setCreate_time(new Date());
        return repository.saveAndFlush(forwardArticle).getId();
    }

    public List<ForwardArticleAPIEntity> queryById(String id, String areaCode) {
        String sql = "SELECT t2.id,t2.rank,t1.title,t2.article_id,t2.start_time,t2.end_time,t2.is_visable FROM app_tb_article t1 " +
                "LEFT JOIN app_tb_forward_article t2 ON t1.id=t2.article_id where t1.id=" + id + " and main_area='" + areaCode + "'";
        List<Map<String, Object>> maps = getJt().queryForList(sql);

        return mapTOforwardArticle(maps);

    }

    public List<ForwardArticleAPIEntity> queryPageForWardArticle(String status, int pageNo, int pageSize, String areaCode) {
        String sql = makeSql(status, 1, areaCode);
        sql += " order by t2.rank desc limit " + (pageNo - 1) * pageSize + "," + pageSize;
        List<Map<String, Object>> maps = getJt().queryForList(sql);
        return mapTOforwardArticle(maps);
    }

    public int getCount(String status, String areaCode) {
        String sql = makeSql(status, 2, areaCode);
        return this.getJt().queryForObject(sql, Integer.class);
    }

    public String makeSql(String status, int type, String areaCode) {
        StringBuffer sql = new StringBuffer();
        if (type == 1) {
            sql.append("SELECT t2.id,t2.rank,t1.title,t2.article_id,t2.start_time,t2.end_time,t2.is_visable FROM app_tb_article t1 " +
                    "LEFT JOIN app_tb_forward_article t2 ON t1.id=t2.article_id where main_area='" + areaCode + "'");
        } else {
            sql.append("SELECT count(1) FROM app_tb_article t1 LEFT JOIN app_tb_forward_article t2 ON t1.id=t2.article_id where  main_area='" + areaCode + "'");
        }
        if (status.equals("1")) {//未开始
            sql.append(" and start_time>NOW()");
        }
        if (status.equals("2")) {//进行中
            sql.append(" and start_time<=NOW() and end_time>=NOW()");
        }
        if (status.equals("3")) {//已结束
            sql.append(" and end_time<NOW()");
        }
        if (status.equals("4")) {//已下线
            sql.append(" and start_time<=NOW() and end_time>=NOW() and t2.is_visable=0");
        }
        return sql.toString();
    }

    public Object getHomePageArticle(int id) {
        String sql = "SELECT t1.id,t2.rank,t1.title,t1.thumb,t1.keyword,t1.brief,t1.content,t1.fake_pv,t2.article_id,t2.start_time,t2.end_time,t2.is_visable " +
                "FROM app_tb_article t1 LEFT JOIN app_tb_forward_article t2 ON t1.id=t2.article_id WHERE t2.id=" + id;
        return getJt().queryForList(sql);
    }

    public List<ForwardArticleAPIEntity> mapTOforwardArticle(List<Map<String, Object>> param) {
        List<ForwardArticleAPIEntity> list = new ArrayList<>();
        for (Map<String, Object> map : param) {
            list.add(new ForwardArticleAPIEntity(map));
        }
        return list;
    }

    private JdbcTemplate getJt() {
        if (jt == null) {
            jt = new JdbcTemplate(dataSource);
        }
        return jt;
    }

    //查询首页资讯存在进行中的记录数
    public int getExistCount(int articleId, String areaCode) {
        String sql = "SELECT count(1) FROM app_tb_article t1 LEFT JOIN app_tb_forward_article t2 ON t1.id=t2.article_id where t2.article_id=" + articleId + "  AND main_area='" + areaCode + "'";

        return this.getJt().queryForObject(sql, Integer.class);
    }
}