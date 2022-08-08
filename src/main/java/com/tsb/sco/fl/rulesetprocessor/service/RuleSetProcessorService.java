package com.tsb.sco.fl.rulesetprocessor.service;

import com.tsb.sco.fl.rulesetprocessor.model.CounterOfferDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.tsb.sco.fl.rulesetprocessor.constant.CounterOfferConstant.*;

@Component
public class RuleSetProcessorService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RestTemplate restTemplate;



    public static void main(String[] args) {
        RuleSetProcessorService ruleSetProcessorService = new RuleSetProcessorService();
        CounterOfferDataModel counterOfferDataModel = new CounterOfferDataModel();
        ruleSetProcessorService.getCounterOffer(counterOfferDataModel);

    }

    public CounterOfferDataModel getCounterOffer(CounterOfferDataModel counterOfferDataModel) {
        LOGGER.info("*************** START of RULESET Processor **************************");
        ResponseEntity<String> response = null;
        int termObtain = getProvidingTerm(counterOfferDataModel);
        //long obtainOfferAmt = getProvidingPrincipalAmount(currentTerm, rateOfInterest, currentAMR);
        //LOGGER.info("Principal Amount we can offer: "+obtainOfferAmt);
        LOGGER.info("Terms obtain from calculation: {}", termObtain);

        if(checkValidTerm(counterOfferDataModel)) {
            LOGGER.info("---****------- Loan will be offered by term increase : {} ----****----",termObtain);
        } else if(checkValidPrincipalAmount(counterOfferDataModel)){
            LOGGER.info("---****------- checkValidPrincipalAmount ----****----");
        } else {
            LOGGER.info("---****------- checkValidPrincipalAmount ----****----");
        }
        LOGGER.info("******************* END of RULESET Processor ************************");
        return counterOfferDataModel;
    }

    private boolean checkValidPrincipalAmount(CounterOfferDataModel cod) {
        long obtainOfferAmt;
        if(!checkTermLessThanMaxTerm(cod.getCalculatedTerm())){
            int termOffer = getMaxAllowableTerm(cod);
            obtainOfferAmt = getProvidingPrincipalAmount(cod.getCalculatedTerm(), cod.getRateOfIntPerMonth(), cod.getCurrentAMR());

        } else  {
            obtainOfferAmt = getProvidingPrincipalAmount(cod.getCalculatedTerm(), cod.getRateOfIntPerMonth(), cod.getCurrentAMR());
        }
        cod.setCalPrincipalAmt(obtainOfferAmt);
        if(checkTIPValid(cod.getCalculatedTerm(), cod.getCurrentAMR(), cod.getCalPrincipalAmt())
                && checkMaxLoanAmountReduction(cod)
            && checkLoanAmtOfferNotMiniAmt(cod.getCalPrincipalAmt())){
            LOGGER.info("---****------- Loan will be offered by term increase {} & Amount reduced {} ---****------- ",cod.getCalculatedTerm(), obtainOfferAmt);
            return true;
            }
        return false;
        }

    private boolean checkLoanAmtOfferNotMiniAmt(long obtainOfferAmt) {
        boolean status = obtainOfferAmt > MIN_LOAN_AMOUNT? true: false;
        LOGGER.info("Loan Amount offer less then mini loan amount Status: {}",status);
        return status;
    }


    private boolean checkMaxLoanAmountReduction(CounterOfferDataModel cod) {
        boolean reductionStatus = MAX_LOAN_AMOUNT_REDUCTION > (cod.getPrincipalAmt() - cod.getCalPrincipalAmt())? true: false;
        LOGGER.info("Max Loan Amount Reduction Status: {}",reductionStatus);
        return reductionStatus;
    }

    public double getAMR(int term, long principal, float rateOfInterest ) {
        double amr = 0.00;

        double roi = rateOfInterest / (12* 100);
        //A = P (r (1+r)^n) / ( (1+r)^n -1 )
        amr = principal * (roi * Math.pow((1 + roi), term)) / (Math.pow((1 + roi), term) -1);
        return amr;
    }

    public int getProvidingTerm(CounterOfferDataModel cod) {
        double term =  ( Math.log10(cod.getCurrentAMR()/(cod.getCurrentAMR() - (cod.getPrincipalAmt() *cod.getRateOfIntPerMonth()) ))/ Math.log10(1+cod.getRateOfIntPerMonth()));
        if( term % NEAREST_ROUND_OFF_TERM >  0 )
            term = term - (term % NEAREST_ROUND_OFF_TERM) + NEAREST_ROUND_OFF_TERM;
        cod.setCalculatedTerm((int)Math.ceil(term));
        return cod.getCalculatedTerm();
    }

    public long getProvidingPrincipalAmount(int term, float roi, double amr) {
        double principalAmount =  (amr * (Math.pow(1+roi, term) -1)) / (roi * Math.pow(1+roi, term));
        principalAmount = principalAmount - (principalAmount % NEAREST_ROUND_OFF_AMOUNT);
        return (long)Math.ceil(principalAmount);
    }

    public boolean checkValidTerm(CounterOfferDataModel cod) {

        if(checkTermLessThanMaxTerm(cod.getCalculatedTerm()) &&
                checkTermLessThanMaxAllowableTerm(cod)
                && checkTIPValid(cod.getCalculatedTerm(), cod.getCurrentAMR(), cod.getPrincipalAmt() ))
            return true;

        return false;
    }

    public boolean checkTermLessThanMaxTerm(int termObtain) {
        boolean status = termObtain < MAX_TERM? true: false;
        LOGGER.info("Term offered Less Than Max Term: {}",status);
        return status;
    }

    public boolean checkTermLessThanMaxAllowableTerm(CounterOfferDataModel cod) {
        int extendedCurrentTeam = getMaxAllowableTerm(cod);
        boolean status = cod.getCalculatedTerm() < extendedCurrentTeam && extendedCurrentTeam < MAX_TERM? true: false;
        LOGGER.info("Term Less Than Max Allowable Term Status: {}",status);
        return status;
    }

    public int getMaxAllowableTerm(CounterOfferDataModel cod) {
        int currentTerm = cod.getCurrentTerm();
        int extendedCurrentTerm = currentTerm + currentTerm/2;
        if(extendedCurrentTerm % NEAREST_ROUND_OFF_TERM > 0)
            extendedCurrentTerm = extendedCurrentTerm - (extendedCurrentTerm % NEAREST_ROUND_OFF_TERM);
        LOGGER.info("extendedCurrentTerm : {}", extendedCurrentTerm);
        cod.setCalculatedTerm(extendedCurrentTerm > MAX_TERM? MAX_TERM : extendedCurrentTerm);
        return cod.getCalculatedTerm() ;
    }

    public boolean checkTIPValid(int termObtain, double amr, long principalAmount) {
        double interstPaid = ((termObtain * amr) - principalAmount);
        boolean status = ((termObtain * amr) - principalAmount) < MAX_TIP ? true: false;
        LOGGER.info("InterestPaid : {}, TIP Status: {}",interstPaid ,status);
        return status;

    }

}
