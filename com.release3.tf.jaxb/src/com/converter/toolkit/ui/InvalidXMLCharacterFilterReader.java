package com.converter.toolkit.ui;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.converter.toolkit.ui.InvalidXMLCharacterFilterReader;

public class InvalidXMLCharacterFilterReader extends FilterReader {

	private static final Log LOG = LogFactory
			.getLog(InvalidXMLCharacterFilterReader.class);

	public InvalidXMLCharacterFilterReader(Reader in) {
		super(in);
	}

	public int read() throws IOException {
		char[] buf = new char[1];
		int result = read(buf, 0, 1);
		if (result == -1)
			return -1;
		else
			return (int) buf[0];
	}

	public int read(char[] buf, int from, int len) throws IOException {
		int count = 0;
		while (count == 0) {
			count = in.read(buf, from, len);
			if (count == -1)
				return -1;

			int last = from;
			for (int i = from; i < from + count; i++) {
				LOG.debug("" + (char) buf[i]);
				if (!isBadXMLChar(buf[i])) {
					buf[last++] = buf[i];
				}
			}

			count = last - from;
		}
		return count;
	}

	//more characters can be added in future.
	private boolean isBadXMLChar(char c) {

		if (c == '$') {
			return true;
		}
		return false;

		
	}
}
