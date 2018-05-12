package com.wondersgroup.healthcloud.solr.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {

    List<T> content;

    boolean more;

    public PageResult(Page<T> page) {
        this.more = page.hasNext();
        this.content = page.getContent();
    }

}
