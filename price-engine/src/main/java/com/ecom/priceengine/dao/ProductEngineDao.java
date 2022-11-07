package com.ecom.priceengine.dao;

import com.ecom.priceengine.bean.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductEngineDao extends JpaRepository<ProductPrice, String> {
}
