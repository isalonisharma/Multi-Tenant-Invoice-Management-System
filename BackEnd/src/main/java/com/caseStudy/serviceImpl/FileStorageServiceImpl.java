package com.caseStudy.serviceImpl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.caseStudy.exception.FileNotFoundException;
import com.caseStudy.exception.FileStorageException;
import com.caseStudy.properties.FileStorageProperties;
import com.caseStudy.service.FileStorageService;

@Service("fileStorageService")
public class FileStorageServiceImpl implements FileStorageService {

	static final Logger logger = Logger.getLogger(FileStorageServiceImpl.class);

	private final Path fileStorageLocation;

	@Autowired
	public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
		logger.info("FileStorageServiceImpl--->>Constructor--->>Start");
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
		logger.info("FileStorageServiceImpl--->>Constructor--->>End");
	}

	public String storeFile(MultipartFile file) {
		logger.info("FileStorageServiceImpl--->>storeFile--->>Start");

		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains(".."))
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);

			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			logger.info("FileStorageServiceImpl--->>storeFile--->>End");

			return fileName;
		} catch (IOException ex) {

			logger.info("FileStorageServiceImpl--->>storeFile--->>Exception Occured" + ex.getMessage() + ex);

			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}

	}

	public Resource loadFileAsResource(String fileName) {
		logger.info("FileStorageServiceImpl--->>loadFileAsResource--->>Start");

		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			logger.info("FileStorageServiceImpl--->>loadFileAsResource--->>filePath--->> " + filePath);

			Resource resource = new UrlResource(filePath.toUri());
			logger.info("FileStorageServiceImpl--->>loadFileAsResource--->>resource--->> " + resource);

			if (resource.exists()) {

				logger.info("FileStorageServiceImpl--->>loadFileAsResource--->>End");

				return resource;
			} else {
				
				logger.info("FileStorageServiceImpl--->>storeFile--->>FileNotFoundException Occured");
				
				throw new FileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {

			logger.info(
					"FileStorageServiceImpl--->>storeFile--->>MalformedURLException Occured " + ex.getMessage() + ex);

			throw new FileNotFoundException("File not found " + fileName, ex);
		}
	}

	@Override
	public String storeFile(MultipartFile file, String fileName) {
		logger.info("FileStorageServiceImpl--->>storeFile--->>Start");

		// Normalize file name
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains(".."))
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			logger.info("FileStorageServiceImpl--->>storeFile--->>End");

			return fileName;
		} catch (IOException ex) {
			logger.info("FileStorageServiceImpl--->>storeFile--->>IOException Occured " + ex.getMessage() + ex);

			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}
}
