package com.tvg.filex.FileX;

import java.io.File;

public class Utils {
	public static final String UPLOAD_DIR = "Upload-Dir";

	public static void createUploadDir() {
		File uploadDir = new File(UPLOAD_DIR);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
	}

	public static String getUploadDir() {
		File uploadDir = new File(UPLOAD_DIR);
		return uploadDir.getAbsolutePath();
	}
}
