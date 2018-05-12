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
public class ResponseListDto extends ResponseDto {

    @JsonIgnore
    private ItemListDto list;

    private List<PrescriptionDto> prescription;

    public List<PrescriptionDto> getPrescription() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list.getItem();
    }

}
