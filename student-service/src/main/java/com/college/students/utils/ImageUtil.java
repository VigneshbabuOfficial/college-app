package com.college.students.utils;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtil {

	private static final String METHOD_LOG_STR = "ImageUtil.%s()";

	private static CustomLogger log = CustomLogger.getLogger("ImageUtil");

	private RequestId requestId;

	public static byte[] compressImage(byte[] data, String requestId) {

		Deflater deflater = new Deflater();
		deflater.setLevel(Deflater.BEST_COMPRESSION);
		deflater.setInput(data);
		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] tmp = new byte[4 * 1024];
		while (!deflater.finished()) {
			int size = deflater.deflate(tmp);
			outputStream.write(tmp, 0, size);
		}
		try {
			outputStream.close();
		} catch (Exception e) {
			log.error(String.format(METHOD_LOG_STR, "compressImage"), requestId, e);
		}
		return outputStream.toByteArray();
	}

	public static byte[] decompressImage(byte[] data, String requestId) {

		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] tmp = new byte[4 * 1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(tmp);
				outputStream.write(tmp, 0, count);
			}
			outputStream.close();
		} catch (Exception exception) {
			log.error(String.format(METHOD_LOG_STR, "decompressImage"), requestId, exception);
		}
		return outputStream.toByteArray();
	}
}
