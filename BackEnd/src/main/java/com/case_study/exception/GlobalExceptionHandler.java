package com.case_study.exception;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.case_study.model.ErrorModel;

/**
 * Class Name: GlobalExceptionHandler Use: To handle the exceptions globally at
 * controller level
 * 
 * ResponseEntityExceptionHandler is a convenient base class for to provide
 * centralized exception handling across all @RequestMapping methods
 * through @ExceptionHandler methods
 * 
 * @ControllerAdvice is an annotation, to handle the exceptions globally
 * 
 * @ExceptionHandler is an annotation used to handle the specific exceptions and
 *                   sending the custom responses to the user.
 * 
 * @author saloni.sharma
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Function Name: springHandleCannotModifyItems
	 * 
	 * @param exception
	 * @return errorModel
	 * @throws IOException
	 */
	@ExceptionHandler({ ClientNotFoundException.class, FileNotFoundException.class, FileStorageException.class,
			DocumentCreationException.class, IllegalTryToAccessException.class, InvoiceItemNotFoundException.class,
			InvoiceNotFoundException.class, ItemNotFoundException.class, OrganizationNotFoundException.class,
			RecurringInvoiceNotFoundException.class, RegularInvoiceNotFoundException.class, RoleNotFoundException.class,
			UserNotFoundException.class, UserOrganizationNotFoundException.class, UserRoleNotFoundException.class,
			InvoiceOrganizationNotFoundException.class, ItemOrganizationNotFoundException.class,
			ClientOrganizationNotFoundException.class })
	public ResponseEntity<ErrorModel> springHandleCannotModifyItems(Exception exception) throws IOException {

		ErrorModel errorModel = new ErrorModel(LocalDateTime.now(), exception.getMessage());

		if (exception instanceof ClientNotFoundException) {
			errorModel.setStatus(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(errorModel, HttpStatus.OK);
		}
		if (exception instanceof FileNotFoundException) {
			errorModel.setStatus(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(errorModel, HttpStatus.OK);
		}
		if (exception instanceof ClientNotFoundException) {
			errorModel.setStatus(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(errorModel, HttpStatus.OK);
		}
		if (exception instanceof DocumentCreationException) {
			errorModel.setStatus(HttpStatus.NO_CONTENT.value());
			return new ResponseEntity<>(errorModel, HttpStatus.OK);
		}
		if (exception instanceof InvoiceNotFoundException) {
			errorModel.setStatus(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(errorModel, HttpStatus.OK);
		}
		if (exception instanceof ItemNotFoundException) {
			errorModel.setStatus(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(errorModel, HttpStatus.OK);
		}
		if (exception instanceof OrganizationNotFoundException) {
			errorModel.setStatus(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(errorModel, HttpStatus.OK);
		}
		if (exception instanceof RecurringInvoiceNotFoundException) {
			errorModel.setStatus(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(errorModel, HttpStatus.OK);
		}
		if (exception instanceof RegularInvoiceNotFoundException) {
			errorModel.setStatus(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(errorModel, HttpStatus.OK);
		}
		if (exception instanceof RoleNotFoundException) {
			errorModel.setStatus(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(errorModel, HttpStatus.OK);
		}
		if (exception instanceof UserNotFoundException) {
			errorModel.setStatus(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(errorModel, HttpStatus.OK);
		}
		if (exception instanceof UserOrganizationNotFoundException) {
			errorModel.setStatus(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(errorModel, HttpStatus.OK);
		}
		if (exception instanceof InvoiceOrganizationNotFoundException) {
			errorModel.setStatus(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(errorModel, HttpStatus.OK);
		}
		if (exception instanceof ItemOrganizationNotFoundException) {
			errorModel.setStatus(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(errorModel, HttpStatus.OK);
		}
		if (exception instanceof ClientOrganizationNotFoundException) {
			errorModel.setStatus(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(errorModel, HttpStatus.OK);
		}

		if (exception instanceof UserRoleNotFoundException) {
			errorModel.setStatus(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(errorModel, HttpStatus.OK);
		}
		if (exception instanceof FileStorageException) {
			errorModel.setStatus(HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<>(errorModel, HttpStatus.OK);
		}
		if (exception instanceof IllegalTryToAccessException) {
			errorModel.setStatus(HttpStatus.UNAUTHORIZED.value());
			return new ResponseEntity<>(errorModel, HttpStatus.OK);
		}
		return new ResponseEntity<>(errorModel, HttpStatus.OK);
	}
}
