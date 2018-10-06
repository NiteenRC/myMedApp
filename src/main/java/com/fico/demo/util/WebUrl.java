package com.fico.demo.util;

public interface WebUrl {

	String USER = "user";
	String USER_FORGET_PASSWORD = "user/forgetPassword/";
	String USER_FORGET_PASSWORD_EMAILID = "user/forgetPassword/{emailId}";
	String USER_BY_EMAILID_PASSWORD = "user/byEmailAndPassword";

	String CATEGORY_BY_NAME = "/category/byName/";
	String CATEGORY = "/category";
	String CATEGORY_BY_PRODUCT_COUNT = "/category/productCount";

	String PRODUCT = "/product";
	String PRODUCTS = "/products";
	String PRODUCT_BY_NAME_CATEGORYID_PRODUCTNAME_SORTTYPE = "/product/byName/{categoryID}/{productName}/{sortType}";
	String PRODUCT_BY_CATEGORYID_SORTTYPE = "/product/category/{categoryID}/{sortType}";
	String PRODUCT_AND_CATEGORYID = "/product/{categoryID}";
	String PRODUCT_BY_PRODUCTID = "/product/{productID}";

	String CART = "/cart/";
	String CART_BY_USERID = "/cart/{userID}";
	String CART_BY_CARTID = "/cart/{cartID}";
	String CARTS = "/carts";

	String ORDER = "/order/";
	String ORDER_BY_ORDERNO = "/order/{orderNo}";
	String ORDER_BY_ORDERDATES = "/order/{fromDate}/{toDate}";
	String ORDER_BY_USERID = "/order/byUser/{userID}";
	String ORDERS = "/orders";

	String RATING = "/rating/";
	String RATING_BY_PRODUCTID_USERID = "/rating/{productID}/{userID}";

}
