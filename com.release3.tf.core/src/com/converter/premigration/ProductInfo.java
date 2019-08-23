package com.converter.premigration;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;

public class ProductInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String productName;
	private String productVersion;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductVersion() {
		return productVersion;
	}

	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}

	public static ProductInfo getProductInfo() {
		ProductInfo productInfo = new ProductInfo();
		String version = null;
		try {
			// this approach fails in "Rational Application Developer 6.0.1"
			IProduct product = Platform.getProduct();
			String name = product.getName();
			productInfo.setProductName(name);
			String aboutText = product.getProperty("aboutText"); //$NON-NLS-1$

			String pattern = "Version: (.*)"; //$NON-NLS-1$
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(aboutText);
			boolean found = m.find();

			if (found) {
				version = m.group(1);
				productInfo.setProductVersion(version);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productInfo;
	}
}
