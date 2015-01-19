package fr.treeptik.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadUtils {

	private static final Logger logger = Logger
			.getLogger(FileUploadUtils.class);

	public HttpServletResponse downloadFileHandler(String fullPath,
			HttpServletResponse response) {
		try {
			File downloadFile = new File(fullPath);
			FileInputStream inputStream = new FileInputStream(downloadFile);
			IOUtils.copy(inputStream, response.getOutputStream());

			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"",
					downloadFile.getName());
			response.setContentLength((int) downloadFile.length());
			response.setHeader(headerKey, headerValue);

			response.flushBuffer();
		} catch (IOException ex) {
			logger.info("Error writing file to output stream.", ex);
		}
		return response;

	}

	/**
	 * Upload single file using Spring Controller
	 */
	public String uploadFileHandler(MultipartFile file, String name) {

		// @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
		// public @ResponseBody
		// String uploadFileHandler(@RequestParam("name") String name,
		// @RequestParam("file") MultipartFile file) {

		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "doc");
				if (!dir.exists())
					dir.mkdirs();

				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location :"
						+ serverFile.getAbsolutePath());

				return "You successfully uploaded file : " + name;
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name
					+ " because the file was empty.";
		}
	}

}
