package com.rooney.myspringboot.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.rooney.myspringboot.Foo;

///http://docs.spring.io/spring/docs/current/spring-framework-reference/html/validation.html
public class MyValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Foo.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//fields are accessed by name as Errors has ref to object
		ValidationUtils.rejectIfEmpty(errors, "name", "name.empty"); //TODO use enumerated code
		
		Foo foo = (Foo) target;
		
		
		if(! HistoricPatternType.contains(foo.getHistoricPattern())) {
			errors.rejectValue("historicPattern", "historicPattern.invalid");
		}
		
		
		//pattern for nested objects
//		try {
//            errors.pushNestedPath("address");
//            ValidationUtils.invokeValidator(this.addressValidator, customer.getAddress(), errors);
//        } finally {
//            errors.popNestedPath();
//        }
		
		
		//how to build errors manually
		//Errors errors = new BeanPropertyBindingResult(entry, entry.getClass().getName());
	}

	//TODO extract to file
	public enum HistoricPatternType{ 
		YYYY, YYYYMM, YYYYMMDD, YYYYQ;
		
		public static boolean contains(String nameToTest) {
			List<String> values = new ArrayList<>();
			
			for (HistoricPatternType historicPatternEnum : HistoricPatternType.values()) {
				values.add(historicPatternEnum.name());
			}
			return values.contains(nameToTest.toUpperCase());
		}
	}
}
