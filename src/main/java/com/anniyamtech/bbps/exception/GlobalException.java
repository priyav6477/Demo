package com.anniyamtech.bbps.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.anniyamtech.bbps.config.BaseDTO;
import com.anniyamtech.bbps.config.Library;

import lombok.extern.log4j.Log4j2;
@Log4j2
@ControllerAdvice
public class GlobalException {
	@ExceptionHandler
	public BaseDTO handleException(AccessDeniedException e) throws Exception {
		log.error(":: exception ::", e);
		return Library.getFailure();
	
	}

	@ExceptionHandler
	public BaseDTO handleException(InvalidDataException e) throws Exception {
		log.error(":: exception ::", e);
		return Library.getFailure();
	}


	@ExceptionHandler
	public BaseDTO handleException(NoSuchElementException e) throws Exception {
		log.error(":: exception ::", e);
		return Library.getFailure();
	}

	@ExceptionHandler
	public BaseDTO handleException(InvalidIdException e) throws Exception {
		log.error(":: exception ::", e);
		return Library.getFailure();
	}

	@ExceptionHandler
	public BaseDTO handleException(SQLIntegrityConstraintViolationException e) throws Exception {
		log.error(":: exception ::", e);
		return Library.getFailure();
	}

	@ExceptionHandler
	public BaseDTO handleException(ConstraintViolationException e) throws Exception {
		log.error(":: exception ::", e);
		return Library.getFailure();
	}

	@ExceptionHandler
	public BaseDTO handleException(Exception e) throws Exception {
		log.error(":: exception ::", e);
		return Library.getFailure();
	}

	@ExceptionHandler
	public BaseDTO handleException(RollbackException e) throws Exception {
		log.error(":: exception ::", e);
		return Library.getFailure();
		
	}
	
	@ExceptionHandler
	public BaseDTO handleException(ServerNotAccessableExcption e) throws Exception {
		log.error(":: exception ::", e);
		return Library.getFailure();
	}
	
	
	@ExceptionHandler
	public BaseDTO handleException(NullPointerException e) throws Exception {
		log.error(":: exception ::", e);
		return Library.getFailure();
	}
	
	

}
