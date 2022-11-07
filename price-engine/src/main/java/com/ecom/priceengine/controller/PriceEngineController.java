package com.ecom.priceengine.controller;

import com.ecom.priceengine.bean.ProductPrice;
import com.ecom.priceengine.exception.InternalServerError;
import com.ecom.priceengine.service.ProductEngineServiceImpl;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
public class PriceEngineController {
	public static final Logger logger = LoggerFactory.getLogger(PriceEngineController.class);
	
   @Autowired
    ProductEngineServiceImpl productEngineServiceImpl;

    @GetMapping("/price")
    public ResponseEntity<ProductPrice> getProductPrice(@RequestParam(name="title") String name){
    	try {
        return new ResponseEntity<>(productEngineServiceImpl.getProductPrice(name), HttpStatus.OK);
    	}catch (Exception e) {
    		PriceEngineController.logger.info("Get product Price");
    		throw new InternalServerError(e.getMessage());
    	}
    }
    
    @GetMapping("/all")
    public List<ProductPrice> getAllProduct()  {
    	try {
        return productEngineServiceImpl.getAllProducts();
    	}catch (Exception e) {
    		PriceEngineController.logger.info("Get product Price");
    		throw new InternalServerError(e.getMessage());
    	}
    }
   
}
