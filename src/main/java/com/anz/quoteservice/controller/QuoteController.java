/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anz.quoteservice.controller;

import com.anz.quoteservice.domain.XeDeals;
import com.anz.quoteservice.domain.XeRates;
import com.anz.quoteservice.repository.XeDealsRepository;
import com.anz.quoteservice.repository.XeRatesRepository;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 631713
 */
@RestController
public class QuoteController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(QuoteController.class);
    
    @Autowired
    private XeRatesRepository ratesRepository;
    
    @Autowired
    private XeDealsRepository dealsRepository;
    
    @RequestMapping(value="/xe/rates", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<XeRates> getXeRates(@RequestParam String src, 
            @RequestParam String to) {
        
        LOGGER.info("Received request to get a quote");
        
        XeRates rate = ratesRepository.findBySrcAndTo(src, to);
        
        if (null == rate) {
            rate = ratesRepository.findBySrcAndTo(to, src);
        }
        
        return new ResponseEntity<>(rate, HttpStatus.OK);
    }
    
    @RequestMapping(value="/xe/deals", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> getXeDeals() {
        
        LOGGER.info("Received request to get a quote");
        
        Iterable<XeDeals> deals = dealsRepository.findAll();
        
        Map<String, Iterable<XeDeals>> map = new ConcurrentHashMap<>();
        
        map.put("deals", deals);
        
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
    @RequestMapping(value="/xe/deals", method = RequestMethod.PUT)
    public ResponseEntity createDeal(@RequestBody XeDeals deal) {
        
        LOGGER.info("Received request to create a deal");
        deal.setId(0);
        dealsRepository.save(deal);
        
        return new ResponseEntity(HttpStatus.CREATED);
    }
    
}
