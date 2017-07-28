package ilusr.core.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author Jeff Riggle
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FXThread {
	/**
	 * 
	 * @return If the method should be ran on the JavaFx Thread.
	 */
	public boolean runOnFXThread() default true;
}
