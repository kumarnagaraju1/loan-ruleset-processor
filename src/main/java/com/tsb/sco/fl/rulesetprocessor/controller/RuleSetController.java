package com.tsb.sco.fl.rulesetprocessor.controller;

import com.tsb.sco.fl.rulesetprocessor.model.CounterOfferDataModel;
import com.tsb.sco.fl.rulesetprocessor.model.OfferDataModel;
import com.tsb.sco.fl.rulesetprocessor.service.RuleSetProcessorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RuleSetController {

    @Autowired
    RuleSetProcessorService ruleSetProcessorService;

    @PostMapping(value="/getLoanQuote")
    public ResponseEntity<CounterOfferDataModel> getLoanQuote(@RequestBody CounterOfferDataModel offerDataModel){
        return ResponseEntity.ok(ruleSetProcessorService.getCounterOffer(offerDataModel));
    }

    @GetMapping(value = "/test")
    public ResponseEntity<String> sampleTest() {
        log.info("Indise sampleTest");
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("data");
    }
}
