package com.caseStudy.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * Class: FileStorageService
 * 
 * @author saloni.sharma
 */
public interface FileStorageService {
	/**
	 * Function Name: storeFile
	 * 
	 * @param file
	 * 
	 * @return String
	 */
	String storeFile(MultipartFile file);

	/**
	 * Function Name: storeFile
	 * 
	 * @param file
	 * @param fileName
	 * 
	 * @return String
	 */
	String storeFile(MultipartFile file, String fileName);

	/**
	 * Function Name: loadFileAsResource
	 * 
	 * @param fileName
	 * 
	 * @return Resource
	 */
	Resource loadFileAsResource(String fileName);
}
