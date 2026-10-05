package com.benzimmer123.sumo.util;

import java.nio.charset.StandardCharsets;

public class ByteUtil {

	public static String getConvertedFromBytes(byte[] convertedBytes) {
		return new String(convertedBytes, StandardCharsets.UTF_8);
	}

}
