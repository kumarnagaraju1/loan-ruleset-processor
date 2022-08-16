package com.tsb.sco.fl.rulesetprocessor.service;

import com.tsb.sco.fl.rulesetprocessor.model.CounterOfferDataModel;


public class CalculateBestTerm {
    public Long RoundedMidpointValue;



    //step3 a=MaxLoanTerm b=MaxTermPossibleUsingMaxPercentMaxTermIncrease
    public int termPossible(RuleSetProcessorService rsp){
        int MaxLoanTerm=60;
        int MaxTermPossibleUsingMaxPercentMaxTermIncrease=CounterOfferDataModel.currentTerm+CounterOfferDataModel.currentTerm*(50/100);

        if(MaxLoanTerm<MaxTermPossibleUsingMaxPercentMaxTermIncrease){
            System.out.println("MaxLoanTerm");
        }
        else{
            System.out.println("MaxTermPossibleUsingMaxPercentMaxTermIncrease");
        }

        if (CounterOfferDataModel.currentTerm<=60){
            return MaxLoanTerm=60;
        }

        if(Roundedn<=MaxTermPossible){
            System.out.prinln("call loansimulationApi and generate quotation ");//go to step4
        }
        else if(Roundedn>MaxTermPossible){
            System.out.println("Generate quotation at Lowest possible Loan Amount");//go to step 6
        }
        return null;


    }

    public Float calculateCMR(){
        return (float) (CounterOfferDataModel.principalAmount / CounterOfferDataModel.currentTerm);
    }

    public Long calculateMidPoint(){
        long midPoint= (CounterOfferDataModel.principalAmount + CounterOfferDataModel.lowestLoanAmount) / 2;
        RoundedMidpointValue= (long) Math.round(midPoint);
        return RoundedMidpointValue;
    }


    //step7
    public String checkGoodQuoteOrBadQuote(){
        Double TapPercentIncrease;
        TapPercentIncrease=(COTap-CounterOfferDataModel.Tap)/(CounterOfferDataModel.Tap);

        if((CounterOfferDataModel.currentCMR < CounterOfferDataModel.currentAMR) && (TapPercentIncrease < CounterOfferDataModel.MaxTapIncrease)){
            System.out.println("Good Quotation");//we need to start iteration from here

        }
        if((CounterOfferDataModel.currentCMR < CounterOfferDataModel.currentAMR) && (TapPercentIncrease > CounterOfferDataModel.MaxTapIncrease)){
            System.out.println("Bad Quotation");
        }
        return null;
    }
    //step9
    public String doQuote() {

        if(RoundedMidpointValue == CounterOfferDataModel.lowestLoanAmount){
            System.out.println("we have reached the happy path");
            System.out.println("CMR:"+ CounterOfferDataModel.currentCMR);
            System.out.println("TAP:"+ CounterOfferDataModel.Tap);
       }
        else{
            return checkGoodQuoteOrBadQuote();
        }
        return checkGoodQuoteOrBadQuote();
    }

   /*public int binarySearch(int principalAmount, int lowestLoanAmount){
        if (principalAmount >= lowestLoanAmount) {
        int midPoint = lowestLoanAmount + (principalAmount - lowestLoanAmount) / 2;
         RoundedMidpointValue= (long) Math.round(midPoint);
        }
  }
*/

    public CounterOfferDataModel getBestTerm(CounterOfferDataModel counterOfferDataModel) {

        return counterOfferDataModel;
    }

/*int binarySearch(int a[], int l, int h, int x)
{
if (h >= l) {
int mid = l + (h - l) / 2;
// If the element is present at the middle itself
if (a[mid] == x)
return mid;
// If element is smaller than mid, then it can only be present in left subarray
if (a[mid] >x)
return binarySearch(arr, l, mid - 1, x);
// Else the element can only be present in right subarray
return binarySearch(arr, mid + 1, h, x);
}
// We reach here when element is not present in array
return -1;
}*/













    }



