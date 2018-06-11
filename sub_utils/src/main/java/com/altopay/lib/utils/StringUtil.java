package com.altopay.lib.utils;

import android.text.TextUtils;

/**
 * 字符串服务
 * @author wenzh
 *
 */
public final class StringUtil {
	
	/**
     * 空字符串常量 <br>
     * <br>
     * <i>佛曰：四大皆�?</i>
     */
    public static final String EMPTY = "";
    /**
     * "不可�?"字符串常�?
     */
    public static final String NOT_AVALIBLE = "N/A";


	/**
	 * 忽略尾数0
	 * @param price(分)
	 * @return
	 */
	public static String getPriceIgnoreZero(int price, String unit) {
		String priceString = String.format("%2.2f", price / 100.00);
		String newPriceStr = priceString;
		if (priceString.endsWith("00")) {
			newPriceStr = priceString.substring(0, priceString.length() - 3);
		} else if (priceString.endsWith("0")) {
			newPriceStr = priceString.substring(0, priceString.length() - 1);
		}
		return TextUtils.isEmpty(unit) ? newPriceStr : newPriceStr + " " + unit;
	}
	/**
	 * 忽略尾数0
	 * @param price
	 * @return
	 */
	public static String getPriceIgnoreZero(float price, String unit) {
		String priceString = String.format("%2.2f", price / 100.00);
		String newPriceStr = priceString;
		if (priceString.endsWith("00")) {
			newPriceStr = priceString.substring(0, priceString.length() - 3);
		} else if (priceString.endsWith("0")) {
			newPriceStr = priceString.substring(0, priceString.length() - 1);
		}
		return TextUtils.isEmpty(unit) ? newPriceStr : newPriceStr + " " + unit;
	}
	/**
	 * 忽略尾数0
	 * @param price
	 * @return
	 */
	public static String getPriceIgnoreZero(double price, String unit) {
		String priceString = String.format("%2.2f", price / 100.00);
		String newPriceStr = priceString;
		if (priceString.endsWith("00")) {
			newPriceStr = priceString.substring(0, priceString.length() - 3);
		} else if (priceString.endsWith("0")) {
			newPriceStr = priceString.substring(0, priceString.length() - 1);
		}
		return TextUtils.isEmpty(unit) ? newPriceStr : newPriceStr + " " + unit;
	}

}
