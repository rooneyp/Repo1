package com.rooney.myspringboot.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
 
@ControllerAdvice
//https://www.petrikainulainen.net/programming/spring-framework/spring-from-the-trenches-adding-validation-to-a-rest-api/
public class RestErrorHandler {
 
    private MessageSource messageSource;
 
    @Autowired
    public RestErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
 
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorDTO processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
 
        return processFieldErrors(fieldErrors);
    }
 
    private ValidationErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
        ValidationErrorDTO dto = new ValidationErrorDTO();
 
        for (FieldError fieldError: fieldErrors) {
            String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
            dto.addFieldError(fieldError.getField(), localizedErrorMessage);
        }
 
        return dto;
    }
 
    private String resolveLocalizedErrorMessage(FieldError fieldError) {
        Locale currentLocale =  LocaleContextHolder.getLocale();
        String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);
 
        //If the message was not found, return the most accurate field error code instead.
        //You can remove this check if you prefer to get the default error message.
        if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
            String[] fieldErrorCodes = fieldError.getCodes();
            localizedErrorMessage = fieldErrorCodes[0];
        }
 
        return localizedErrorMessage;
    }
    
    public class FieldErrorDTO {
    	 
        private String field;
     
        private String message;
     
        public FieldErrorDTO(String field, String message) {
            this.field = field;
            this.message = message;
        }

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
     
    }
    
    public class ValidationErrorDTO {
    	 
        private List<FieldErrorDTO> fieldErrors = new ArrayList<>();
     
        public ValidationErrorDTO() {
     
        }
     
        public void addFieldError(String path, String message) {
            FieldErrorDTO error = new FieldErrorDTO(path, message);
            fieldErrors.add(error);
        }

		public List<FieldErrorDTO> getFieldErrors() {
			return fieldErrors;
		}

		public void setFieldErrors(List<FieldErrorDTO> fieldErrors) {
			this.fieldErrors = fieldErrors;
		}
     
        
    }
}