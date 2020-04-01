package com.teste.votacao.handler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.teste.votacao.error.ErrorDetails;
import com.teste.votacao.error.ResourceNotFoundException;
import com.teste.votacao.error.ValidationErrorDetails;

//Classe responsavel para reescrever os metodos de erros
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
		ErrorDetails details = ErrorDetails.Builder.newBuilder().timestamp(new Date().getTime())
				.status(HttpStatus.NOT_FOUND.value()).title("Resourse not found")
				.details(resourceNotFoundException.getMessage())
				.developerMessage(resourceNotFoundException.getClass().getName()).build();
		return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
		String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
		ValidationErrorDetails details = ValidationErrorDetails.Builder.newBuilder().timestamp(new Date().getTime())
				.status(HttpStatus.BAD_REQUEST.value()).title("Field validation Error")
				.details("Field validation Error").developerMessage(exception.getClass().getName()).field(fields)
				.fieldMessage(fieldMessages).build();
		return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception exception, @Nullable Object body,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorDetails details = ErrorDetails.Builder.newBuilder().timestamp(new Date().getTime()).status(status.value())
				.title("Internal Exception").details(exception.getMessage())
				.developerMessage(exception.getClass().getName()).build();

		return new ResponseEntity<>(details, headers, status);
	}
}