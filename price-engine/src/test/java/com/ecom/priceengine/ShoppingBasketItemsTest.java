package com.ecom.priceengine;

import com.ecom.priceengine.bean.Product;
import com.ecom.priceengine.bean.ProductPrice;
import com.ecom.priceengine.controller.PriceEngineController;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/*
 *  Apple Total=48.0
 Orange Total=64.0
Total = 112.0
2022-10-23 18:12:48.009  INFO 3116 --- [           main] c.e.p.controller.PriceEngineController   : PriceData is empty
 */
@SpringBootTest(classes = PriceEngineApplication.class)
@AutoConfigureMockMvc
class ShoppingBasketItemsTest {
	public static final Logger logger = LoggerFactory.getLogger(PriceEngineController.class);

	@Test
	void getProductPriceTest() {
		String selectedItems = "Apple,Apple,Orange , Apple,Pineapple!!, Banana,Orange,Banana";
		List<Product> cart = createFruitCart(selectedItems);
		System.out.println("\n" + " ~~~~~~~~~~~~~~~~    Shopping Basket ~~~~~~~~~~~~~~~~~~~~~" + "\n");
		try {
			if (cart.size() > 0) {
				for (Product p : cart) {
					System.out.println(p.getName() + " = " + p.getQuantity());
				}
				BigDecimal cartTotal = cart.stream().map(product -> getPriceByProduct(product)).filter(Objects::nonNull)
						.reduce(BigDecimal.ZERO, BigDecimal::add);
				System.out.println("Total = " + cartTotal);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private BigDecimal getPriceByProduct(Product product) {
		BigDecimal appleTotal = BigDecimal.ZERO;
		BigDecimal orangeTotal = BigDecimal.ZERO;
		BigDecimal notAppleOrangeTotal = BigDecimal.ZERO;

		OkHttpClient client = new OkHttpClient();

		String url = HttpUrl.parse("http://localhost:2022/price").newBuilder()
				.addQueryParameter("title", product.getName()).build().toString();

		Request request = new Request.Builder().url(url).build();

		Response response = null;
		String priceData = null;
		try {
			response = client.newCall(request).execute();
			priceData = (response != null) ? response.body().string() : null;

			ObjectMapper objectMapper = new ObjectMapper();

			if (priceData.isEmpty() || null == priceData) {
				PriceEngineController.logger.info("PriceData is empty");
				return null;
			}
			ProductPrice productPrice = objectMapper.readValue(priceData, ProductPrice.class);

			/** Buy one, get one free on Apple **/
			if ((!product.getName().isEmpty() || !product.getName().isBlank())
					&& product.getName().equalsIgnoreCase("Apple")) {
				appleTotal = getCalculatePriceForBuyOneGetOne(productPrice.getPrice(), product.getQuantity());
				System.out.println(product.getName() + " Sub Total=" + appleTotal);
			}
			/** 3 for the price of 2 on Oranges **/
			if ((!product.getName().isEmpty() || !product.getName().isBlank())
					&& product.getName().equalsIgnoreCase("Orange")) {
				orangeTotal = getCalculatePriceForBuyTwoGetOne(productPrice.getPrice(), product.getQuantity());
				System.out.println(product.getName() + " Sub Total=" + orangeTotal);
			}
			/** No offer products **/
			if ((!product.getName().isEmpty() || !product.getName().isBlank())
					&& !product.getName().equalsIgnoreCase("Orange") && !product.getName().equalsIgnoreCase("Apple")) {
				notAppleOrangeTotal = getCalculatedPrice(productPrice.getPrice(), product.getQuantity());
				System.out.println(product.getName() + " Sub Total =" + notAppleOrangeTotal);
			}
			return appleTotal.add(orangeTotal).add(notAppleOrangeTotal);
		} catch (IOException e) {
			return null;
		}
	}

	private BigDecimal getCalculatePriceForBuyOneGetOne(double price, long quantity) {
		try {
			if (price > 0 && quantity > 0) {
				long eligOfferItem = quantity / 2;
				long notEligOfferItem = quantity % 2;
				BigDecimal eligOfferItemPrice = getCalculatedPrice(price, eligOfferItem);
				BigDecimal notEligOfferItemPrice = getCalculatedPrice(price, notEligOfferItem);
				return eligOfferItemPrice.add(notEligOfferItemPrice);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new BigDecimal(0);

	}

	private BigDecimal getCalculatePriceForBuyTwoGetOne(double price, long quantity) {
		try {
			if (price > 0 && quantity > 0) {
				long eligOfferItem = quantity / 3;
				eligOfferItem = eligOfferItem * 2;
				long notEligOfferItem = quantity % 3;
				BigDecimal eligOfferItemPrice = getCalculatedPrice(price, eligOfferItem);
				BigDecimal notEligOfferItemPrice = getCalculatedPrice(price, notEligOfferItem);
				return eligOfferItemPrice.add(notEligOfferItemPrice);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new BigDecimal(0);

	}

	private BigDecimal getCalculatedPrice(double price, long quantity) {
		try {
			if (price > 0 && quantity > 0) {
				return BigDecimal.valueOf(price).multiply(BigDecimal.valueOf(quantity));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new BigDecimal(0);
	}

	private List<Product> createFruitCart(String selectedProducts) {
		List<Product> products = new ArrayList<>();
		if (selectedProducts.length() > 0 && !selectedProducts.equals("") && !selectedProducts.equals(null)) {
			List<String> list = Arrays.asList(selectedProducts.split("\\s*,\\s*"));
			Set<String> uniqueWords = new HashSet<String>(list);
			for (String word : uniqueWords) {
				Product product = new Product(word, Collections.frequency(list, word));
				products.add(product);
			}
		}
		return products;
	}
}
