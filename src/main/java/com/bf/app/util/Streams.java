package com.bf.app.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Streams {
	
	public static void copy(InputStream from, OutputStream to) throws IOException {
		byte[] buffer = new byte[512];
		int len;
		while ((len = from.read(buffer)) != -1) {
			to.write(buffer, 0, len);
		}
	}

}
