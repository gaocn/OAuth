package govind.validator;

import com.sun.javafx.collections.ArrayListenerHelper;
import govind.annotations.CheckPasswd;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.ArrayUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class PasswdValidator implements ConstraintValidator<CheckPasswd,String> {
	//可以应用Service Bean已完成验证工作

	@Override
	public void initialize(CheckPasswd constraintAnnotation) {
		System.out.println("PasswdValidator  初始化");
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null ||  value.trim().isEmpty()) {
			return false;
		}
		String specialChars = "!@#$%^&*()";
		Boolean containsDigit = false;
		Boolean containsAlphabet = false;
		Boolean  containSpecialChars  = false;
		for (Character c : value.toCharArray())  {
			containsDigit |=  Character.isDigit(c);
			containsAlphabet |= Character.isLetter(c);
			containSpecialChars |=  specialChars.contains(c.toString());
		}
		return  containsDigit && containsAlphabet && containSpecialChars;
	}
}
