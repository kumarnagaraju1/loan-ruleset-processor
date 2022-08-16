package com.tsb.sco.fl.rulesetprocessor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferDataModel {

    private Long principalAmt ;
    private Integer term ;
    private Float rateOfInterest;
    private Double amr;
}
