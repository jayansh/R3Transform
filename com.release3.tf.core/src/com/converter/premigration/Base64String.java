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
package com.converter.premigration;

import	java.io.*;
import	java.text.*;

/**
 * convert format between bytes and string
 */
public class Base64String 
{
	// Base64Chars include 'A'~'Z','a'~'z','0'~'9','+','/'
	// useless chars '='
	// all chars are 6 bit alphabets
    private final char[] Base64Chars = 
    {
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
		'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
		'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
		'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
		'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
		'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
		'w', 'x', 'y', 'z', '0', '1', '2', '3',
		'4', '5', '6', '7', '8', '9', '+', '/',
        '='
    };

    // SessionKeyChars are safe to use in cookies, URLs and file names
    private final char[] SessionKeyChars =
    {
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
		'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
		'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
		'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
		'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
		'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
		'w', 'x', 'y', 'z', '0', '1', '2', '3',
		'4', '5', '6', '7', '8', '9', '_', '-',
        '.'
    };

    // the string should be converted to "UTF8"
    public String toBase64String(byte[] bytes) 
    {
        return toBase64String(bytes,Base64Chars);
    }

    public String toBase64SessionKeyString(byte[] bytes) 
    {
        return toBase64String(bytes,SessionKeyChars);
    }

    // convert bytes to string
    private String toBase64String(byte[] bytes,char[] chars) 
    {
    	StringBuffer sb = new StringBuffer();
    	int len = bytes.length,i=0,ival;
    	while (len >= 3) 
	    {
		    ival = ((int)bytes[i++] + 256) & 0xff;
		    ival <<= 8;
		    ival += ((int)bytes[i++] + 256) & 0xff;
		    ival <<= 8;
		    ival += ((int)bytes[i++] + 256) & 0xff;
		    len -= 3;
		    sb.append(chars[(ival >> 18) & 63]);
		    sb.append(chars[(ival >> 12) & 63]);
		    sb.append(chars[(ival >> 6) & 63]);
		    sb.append(chars[ival & 63]);
	    }
    	// remainder
    	switch (len) 
    	{
		    case 0:
		    	break;
		    case 1:
				ival = ((int)bytes[i++] + 256) & 0xff;
				ival <<= 16;
				sb.append(chars[(ival >> 18) & 63]);
				sb.append(chars[(ival >> 12) & 63]);
				sb.append(chars[64]);
				sb.append(chars[64]);
				break;
		    case 2:
				ival = ((int)bytes[i++] + 256) & 0xff;
				ival <<= 8;
				ival += ((int)bytes[i] + 256) & 0xff;
				ival <<= 8;
				sb.append(chars[(ival >> 18) & 63]);
				sb.append(chars[(ival >> 12) & 63]);
				sb.append(chars[(ival >> 6) & 63]);
				sb.append(chars[64]);
				break;
		}
		return new String(sb);
    }
    
    // convert string to bytes
    public byte[] fromBase64String(String s) 
    {
		try 
		{
		    StringCharacterIterator iter = new StringCharacterIterator(s);
		    ByteArrayOutputStream bytestr = new ByteArrayOutputStream();
		    DataOutputStream outstr = new DataOutputStream(bytestr);
		    int[] bgroup = new int[4];
		    char c;
		    int d,i,group;

		    decode: 
		    for(i=0,group=0,c = iter.first();c != CharacterIterator.DONE;c = iter.next())
		    {
		    	switch (c) 
		    	{
				    case 'A': d =  0; break; case 'B': d =  1; break;
				    case 'C': d =  2; break; case 'D': d =  3; break;
				    case 'E': d =  4; break; case 'F': d =  5; break;
				    case 'G': d =  6; break; case 'H': d =  7; break;
				    case 'I': d =  8; break; case 'J': d =  9; break;
				    case 'K': d = 10; break; case 'L': d = 11; break;
				    case 'M': d = 12; break; case 'N': d = 13; break;
				    case 'O': d = 14; break; case 'P': d = 15; break;
				    case 'Q': d = 16; break; case 'R': d = 17; break;
				    case 'S': d = 18; break; case 'T': d = 19; break;
				    case 'U': d = 20; break; case 'V': d = 21; break;
				    case 'W': d = 22; break; case 'X': d = 23; break;
				    case 'Y': d = 24; break; case 'Z': d = 25; break;
				    case 'a': d = 26; break; case 'b': d = 27; break;
				    case 'c': d = 28; break; case 'd': d = 29; break;
				    case 'e': d = 30; break; case 'f': d = 31; break;
				    case 'g': d = 32; break; case 'h': d = 33; break;
				    case 'i': d = 34; break; case 'j': d = 35; break;
				    case 'k': d = 36; break; case 'l': d = 37; break;
				    case 'm': d = 38; break; case 'n': d = 39; break;
				    case 'o': d = 40; break; case 'p': d = 41; break;
				    case 'q': d = 42; break; case 'r': d = 43; break;
				    case 's': d = 44; break; case 't': d = 45; break;
				    case 'u': d = 46; break; case 'v': d = 47; break;
				    case 'w': d = 48; break; case 'x': d = 49; break;
				    case 'y': d = 50; break; case 'z': d = 51; break;
				    case '0': d = 52; break; case '1': d = 53; break;
				    case '2': d = 54; break; case '3': d = 55; break;
				    case '4': d = 56; break; case '5': d = 57; break;
				    case '6': d = 58; break; case '7': d = 59; break;
				    case '8': d = 60; break; case '9': d = 61; break;
				    case '+': d = 62; break; case '/': d = 63; break;
				    case '_': d = 62; break; case '-': d = 63; break;
				    default:break decode;// Skip illegal characters.
				}
		    	bgroup[i++] = d;
		    	if (i >= 4) 
		    	{
				    i = 0;
				    group = ((bgroup[0] & 63) << 18) + ((bgroup[1] & 63) << 12) +
					    ((bgroup[2] & 63) << 6) + (bgroup[3] & 63);
				    outstr.writeByte(((group >> 16) & 255));
				    outstr.writeByte(((group >> 8) & 255));
				    outstr.writeByte(group & 255);
		    	}
		    }

		    // remainder
		    switch (i) 
		    {
		    	case 2:
				    group = ((bgroup[0] & 63) << 18) + ((bgroup[1] & 63) << 12);
				    outstr.writeByte(((group >> 16) & 255));
				    break;
		    	case 3:
				    group = ((bgroup[0] & 63) << 18) + ((bgroup[1] & 63) << 12)+
					    ((bgroup[2] & 63) << 6);
				    outstr.writeByte(((group >> 16) & 255));
				    outstr.writeByte(((group >> 8) & 255));
				    break;
				default:
				    break;
		    }
		    outstr.flush();
		    return bytestr.toByteArray();
		}
		catch (IOException e) 
		{
			return null;
		} 
    }
}