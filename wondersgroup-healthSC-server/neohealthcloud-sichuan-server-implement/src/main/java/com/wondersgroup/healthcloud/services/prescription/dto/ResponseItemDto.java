package com.wondersgroup.healthcloud.services.prescription.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@XmlRootElement(name = "response")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseItemDto extends ResponseDto {

    @JsonIgnore
    private ItemDto list;

    private List<PrescriptionDetailDto> item;

    public List<PrescriptionDetailDto> getItem() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list.getItem();
    }
}
