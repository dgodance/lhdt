package lhdt.anals.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 필드에 사용되는 어노테이션
 * @author gravity@daumsoft.com
 * @since 2020. 8. 10.
 *
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface AnalsField {

	/**
	 * 업무키 여부
	 * @return
	 */
	boolean bizKey() default true;
	
	/**
	 * 업무키 순서. 동적으로 쿼리 생성할 때 사용. 순서 중요. order순서가 jpa method name만들때 사용됨
	 * @return
	 */
	int order() default 0;
}
