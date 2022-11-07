package com.ecom.priceengine.service;

import com.ecom.priceengine.bean.ProductPrice;
import com.ecom.priceengine.dao.ProductEngineDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class ProductEngineServiceImpl implements ProductEngineServices{

    @Autowired
    ProductEngineDao productEngineDao;

    public ProductPrice getProductPrice(String productName) throws Exception {
    	try {
        List<ProductPrice> productPrices = (List<ProductPrice>) productEngineDao.findAll();
        ProductPrice priceOfProduct = productPrices.stream()
                .filter(Objects::nonNull)
                .filter(productPrice -> productPrice.getTitle().equalsIgnoreCase(productName))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
        return priceOfProduct;
    	}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
    public List<ProductPrice> getAllProducts() throws Exception {
    	try {
        List<ProductPrice> productPrices = (List<ProductPrice>) productEngineDao.findAll();
        return productPrices;
    	}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
   
}
