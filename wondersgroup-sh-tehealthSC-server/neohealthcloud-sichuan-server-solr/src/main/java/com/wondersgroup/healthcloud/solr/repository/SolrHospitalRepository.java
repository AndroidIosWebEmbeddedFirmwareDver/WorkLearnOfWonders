package com.wondersgroup.healthcloud.solr.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import com.wondersgroup.healthcloud.solr.document.SolrHospital;

public interface SolrHospitalRepository extends SolrCrudRepository<SolrHospital, Integer> {

    @Query(value = "hospitalName:?0", filters = {"delFlag:0", "status:1"})
    Page<SolrHospital> queryByHospitalName(String hospitalName, Pageable pageable);

    List<SolrHospital> findByHospitalNameAndDelFlag(String hospitalName, String delFlag);

    @Query(value = "text:?0^50 or ikText:?1^1", fields = {"id", "hospitalCode", "hospitalName"}, filters = {"delFlag:0", "status:1"})
    List<SolrHospital> findRecoment(String wd, String text);

    @Query(value = "text:?0^50 or ikText:?1^1", fields = {"id", "hospitalCode", "hospitalName"}, filters = {"delFlag:0", "status:1", "cityCode:?2"})
    List<SolrHospital> findRecoment(String wd, String text, String cityCode);

}
