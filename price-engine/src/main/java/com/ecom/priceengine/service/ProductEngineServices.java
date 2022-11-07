package com.ecom.priceengine.service;

import java.util.List;

import com.ecom.priceengine.bean.ProductPrice;

public interface ProductEngineServices {
	public ProductPrice getProductPrice (String productName) throws Exception; 
	public List<ProductPrice> getAllProducts () throws Exception; 
	
}
