package com.tsb.sco.fl.rulesetprocessor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounterOfferDataModel {

   // public static Long AMR;
    public static Long currentCMR ;
    public static Double TAPPercentIncrease;
    public static Double MaxTapIncrease;
    public static Long MaxTermPossible;
    public static Double Tap;
    public static long principalAmount;
    public static long lowestLoanAmount;
    public static Double currentAMR;
    public static Integer currentTerm;
    public static Float rateOfInterest;
    public static Long RoundedMidpointValue;

    @JsonIgnore
    private Float rateOfIntPerMonth;
    private Integer calculatedTerm;
    private Long calPrincipalAmt;

    public Float getRateOfIntPerMonth() {
        return getRateOfInterest()/ (12* 100);
    }

    private Float getRateOfInterest() {
        return null;
    }
}
