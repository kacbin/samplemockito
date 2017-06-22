package com.cubic.testutil;

import com.shashi.entity.ProductEntity;
import com.shashi.vo.ProductVO;

public class ProductTestData {
public static ProductVO createProductVO(){
	ProductVO results = new ProductVO();
	results.setProductName("VASELINE OPT TREATMENT");
	results.setDescription("SKIN ONITMENT");
	return results;
}
public static ProductEntity createProductEntity(){
	ProductEntity results = new ProductEntity();
	results.setProductName("VASELINE OPT TREATMENT");
	results.setDescription("SKIN ONITMENT");
	results.setId(new Long(2000));
	return results;
}
}
