package com.wondersgroup.healthcloud.solr.service.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wondersgroup.healthcloud.common.utils.AppUrlH5Utils;
import com.wondersgroup.healthcloud.jpa.entity.article.NewsArticle;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
import com.wondersgroup.healthcloud.jpa.repository.article.NewsArticleRepo;
import com.wondersgroup.healthcloud.jpa.repository.hospital.HospitalRepository;
import com.wondersgroup.healthcloud.solr.dto.DoctorDto;
import com.wondersgroup.healthcloud.solr.dto.HospitalDto;
import com.wondersgroup.healthcloud.solr.dto.NewsArticleDto;
import com.wondersgroup.healthcloud.solr.dto.PageResult;
import com.wondersgroup.healthcloud.solr.dto.SearchResult;

@Service
@Transactional
public class SolrSearchService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    HospitalRepository hospitalRepository;

    @Autowired
    NewsArticleRepo newsArticleRepo;

    @Autowired
    AppUrlH5Utils appUrlH5Utils;

    public SearchResult search(String keyword) {
        Pageable pageable = new PageRequest(0, 2);

        SearchResult result = new SearchResult();

        PageResult<HospitalDto> hospitals = searchHospital(keyword, pageable);
        result.setHospitals(hospitals);

        PageResult<DoctorDto> doctors = searchDoctor(keyword, pageable);
        result.setDoctors(doctors);

        PageResult<NewsArticleDto> article = searchArticle(keyword, pageable);
        result.setArticles(article);
        return result;
    }

    public PageResult<HospitalDto> searchHospital(String keyword, Pageable pageable) {
        List<HospitalDto> list = new ArrayList<>();
        Page<Hospital> page = hospitalRepository.findByStatusAndDelFlagAndHospitalNameLike("1", "0", "%" + keyword + "%", pageable);
        List<Hospital> content = page.getContent();
        if (content != null && !content.isEmpty()) {
            for (Hospital hospital : content) {
                list.add(new HospitalDto(hospital));
            }
        }
        return new PageResult<>(list, page.hasNext());
    }

    public PageResult<DoctorDto> searchDoctor(String keyword, Pageable pageable) {

        int size = pageable.getPageSize() + 1;
        int page = pageable.getPageNumber() * pageable.getPageSize();

        String sql = "select a.id, "
                + "a.gender,"
                + "a.hospital_code as hosOrgCode, "
                + "a.dept_code as hosDeptCode, "
                + "a.doctor_code as hosDoctCode, "
                + "a.doctor_name as doctorName, "
                + "a.doctor_title as doctorTitle, "
                + "a.expertin as expertin, "
                + "a.order_count as orderCount,"
                + "a.headphoto as headphoto,"
                + "b.hospital_name as hosName, "
                + "c.dept_name as deptName "
                + "from tb_doctor_info a "
                + "left join tb_hospital_info b on a.hospital_code = b.hospital_code "
                + "left join tb_department_info c on c.dept_code = a.dept_code where b.status= '1' AND  a.doctor_name like ?"
                + "group by a.id "
                + "limit ?,?";
        Object[] params = new Object[]{"%" + keyword + "%", page, size};
        RowMapper<DoctorDto> rowMapper = new BeanPropertyRowMapper<DoctorDto>(DoctorDto.class);

        List<DoctorDto> list = jdbcTemplate.query(sql, params, rowMapper);
        boolean flag = false;
        if (list.size() == size) {
            flag = true;
            list = list.subList(0, size - 1);
        }
        return new PageResult<>(list, flag);
    }

    public PageResult<NewsArticleDto> searchArticle(String keyword, Pageable pageable) {
        String title = "%" + keyword + "%";
        String keyword1 = keyword + ",%";
        String keyword2 = "%," + keyword + ",%";

        List<NewsArticleDto> list = new ArrayList<>();
        Page<NewsArticle> page = newsArticleRepo.findByTitleLikeOrKeywordLike(title, keyword1, keyword2, pageable);
        List<NewsArticle> content = page.getContent();
        if (content != null) {
            for (NewsArticle newsArticle : content) {
                list.add(new NewsArticleDto(newsArticle, appUrlH5Utils, ""));
            }
        }
        return new PageResult<>(list, page.hasNext());
    }

}
