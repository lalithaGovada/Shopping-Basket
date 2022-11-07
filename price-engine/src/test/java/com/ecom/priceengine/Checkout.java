package com.ecom.priceengine;

import com.ecom.priceengine.bean.Product;
import com.ecom.priceengine.bean.ProductPrice;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/*Add 2  X Apple @ 12.0 each
Add 3  X Banana @ 51.0 each
Subtotal = 177.0
Tax = 8.85
Total = 185.85*/
@SpringBootTest(classes = PriceEngineApplication.class)
@AutoConfigureMockMvc
class Checkout {
	@Test
	void getProductPriceTest() {

		List<Product> cart = createCart();
		System.out.println("\n");
		BigDecimal cartSubTotal = cart
				.stream()
				.map(product -> getPriceByProduct(product))
				.filter(Objects::nonNull)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		System.out.println("Subtotal = "+cartSubTotal);
		BigDecimal finalPrice =  getCalculatedFinalPriceByTax(cartSubTotal);
		System.out.println("Total = "+finalPrice+"\n");
		
	}

	private BigDecimal getCalculatedFinalPriceByTax(BigDecimal cartSubTotal) {
		//if( cartSubTotal> 30)
		BigDecimal tax = (cartSubTotal.multiply(BigDecimal.valueOf(5))).divide(BigDecimal.valueOf(100));
		tax = tax.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		System.out.println("Tax = "+tax);
		return cartSubTotal.add(tax);
	}

	private BigDecimal getPriceByProduct(Product product)  {

		OkHttpClient client = new OkHttpClient();

		String url =  HttpUrl.parse("http://localhost:2022/price")
				.newBuilder()
				.addQueryParameter("title", product.getName())
				.build()
				.toString();

		Request request = new Request.Builder()
				.url(url)
				.build();

		Response response = null;
		String priceData = null;
		try {
			response = client.newCall(request).execute();
			priceData = response.body().string();
			//System.out.println(response.body());
			ObjectMapper objectMapper = new ObjectMapper(); //used Jackson object mapper to "convert JSON String to JavaObject"
			if (priceData.isEmpty() || null == priceData)
				return null;
			ProductPrice productPrice =  objectMapper.readValue(priceData, ProductPrice.class);
			System.out.println("Add " +product.getQuantity()+"  X "+product.getName()+" @ " +productPrice.getPrice()+" each");
			
			return getCalculatedPrice(productPrice.getPrice(), product.getQuantity());
		} catch (IOException e) {
			return null;
		}
	}

	private BigDecimal getCalculatedPrice(double price, long quantity) {
		return BigDecimal.valueOf(price).multiply(BigDecimal.valueOf(quantity));
	}

	private List<Product> createCart() {
		List<Product> products = new ArrayList<>();
		Product product = new Product("Apple", 2);
		Product product2 = new Product("Banana", 3);
	
		products.add(product);
		products.add(product2);
		return products;
	}
}
