package org.unknown.services;

import javax.inject.Qualifier;
import java.lang.annotation.*;

/**
 * Author: Artem Voronov
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier
public @interface Service {
}
