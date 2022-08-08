package com.tsb.sco.fl.rulesetprocessor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounterOfferDataModel {

    private Double currentAMR;
    private Long principalAmt;
    private Integer currentTerm;
    private Float rateOfInterest;
    @JsonIgnore
    private Float rateOfIntPerMonth;
    private Integer calculatedTerm;
    private Long calPrincipalAmt;

    public Float getRateOfIntPerMonth() {
        return getRateOfInterest()/ (12* 100);
    }
}
