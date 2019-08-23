/*******************************************************************************
 * Copyright (c) 2009, 2013 ObanSoft Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jayansh Shinde
 *******************************************************************************/
package com.release3.transform.util;

public class SpecialCharSearcher {
	
	

	public static boolean findSpecialChar(String aText) {
		if (aText == null || aText.length() == 0) {
			return false;
		}
		for (int i = 0; i < aText.length(); i++) {
			char character = aText.charAt(i);
			switch (character) {
			case '<':
				return true;

			case '>':
				return true;
			case '&':
				if (aText.charAt(i + 2) == '1' && aText.charAt(i + 3) == '0') {
					return false;
				}
				return true;
			case '"':
				return true;
			case '\'':
				return true;
			case '�':
				return true;
			case '�':
				return true;
			case '�':
				return true;
			case '�':
				return true;
			case '�':
				return true;
			case '�':
				return true;
			case '�':
				return true;
			case '�':
				return true;
			case '�':
				return true;
			case '�':
				return true;
			case '�':
				return true;
			case '�':
				return true;
			case '�':
				return true;

			}

		}
		return false;
	}
	
	public static String replaceSpecialChar(String text) {
		if (text == null || text.length() == 0) {
			return text;
		}
		
		StringBuffer aText = new StringBuffer(text);
		for (int i = 0; i < aText.length(); i++) {
			char character = aText.charAt(i);
			String newChar = "";
			switch (character) {
			case '<':
				newChar = SpecialCharConstants.LESS_THAN;
				aText.replace(i, i+1, newChar);
				break;

			case '>':
				newChar = SpecialCharConstants.GREATER_THAN;
				aText.replace(i, i+1, newChar);
				break;
			case '&':
				if ((aText.length() >= i+3) && aText.charAt(i + 2) == '1' && aText.charAt(i + 3) == '0') {
					return aText.toString();
				}
				newChar = SpecialCharConstants.AND;
				aText.replace(i, i+1, newChar);
				break;
			case '"':
				newChar = SpecialCharConstants.DOUBL_QUOTE_REPLACER;
				aText.replace(i, i+1, newChar);
				break;
			case '\'':
				newChar = SpecialCharConstants.BACK_SLASH_REPLACER;
				aText.replace(i, i+1, newChar);
				break;
			case '�':
				newChar = SpecialCharConstants.SPANISH_A_REPLACER;
				aText.replace(i, i+1, newChar);
				break;
			case '�':
				newChar = SpecialCharConstants.SPANISH_E_REPLACER;
				aText.replace(i, i+1, newChar);
				break;
			case '�':
				newChar = SpecialCharConstants.SPANISH_I_REPLACER;
				aText.replace(i, i+1, newChar);
				break;
			case '�':
				newChar = SpecialCharConstants.SPANISH_O_REPLACER;
				aText.replace(i, i+1, newChar);
				break;
			case '�':
				newChar = SpecialCharConstants.SPANISH_U_REPLACER;
				aText.replace(i, i+1, newChar);
				break;
			case '�':
				newChar = SpecialCharConstants.SPANISH_CAP_A_REPLACER;
				aText.replace(i, i+1, newChar);
				break;
			case '�':
				newChar = SpecialCharConstants.SPANISH_CAP_E_REPLACER;
				aText.replace(i, i+1, newChar);
				break;
			case '�':
				newChar = SpecialCharConstants.SPANISH_CAP_I_REPLACER;
				aText.replace(i, i+1, newChar);
				break;
			case '�':
				newChar = SpecialCharConstants.SPANISH_CAP_O_REPLACER;
				aText.replace(i, i+1, newChar);
				break;
			case '�':
				newChar = SpecialCharConstants.SPANISH_CAP_U_REPLACER;
				aText.replace(i, i+1, newChar);
				break;
			case '�':
				newChar = SpecialCharConstants.SPANISH_N_REPLACER;
				aText.replace(i, i+1, newChar);
				break;
			case '�':
				newChar = SpecialCharConstants.SPANISH_CAP_N_REPLACER;
				aText.replace(i, i+1, newChar);
				break;
			case '�':
				newChar = SpecialCharConstants.SPANISH_U_REPLACER;
				aText.replace(i, i+1, newChar);
				break;
			
			  
//				aText.replace(i, i, newChar);
			
				
			}
			
		}
		return aText.toString();
	}
	
	public static void main(String args[]){
		String text = "my string with special chars & sp�n�sh chars";
//		text = "&";
		System.out.println(text);
		System.out.println(replaceSpecialChar(text));
	}
	
	
}
