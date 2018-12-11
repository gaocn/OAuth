package govind.annotations;


import govind.validator.PasswdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswdValidator.class)
public @interface CheckPasswd {
	String message() default "设置密码不安全，建议设置复杂密码！";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
