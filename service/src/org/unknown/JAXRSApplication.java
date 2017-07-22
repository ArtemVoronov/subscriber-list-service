package org.unknown;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.unknown.rest.DefaultExceptionMapper;

/**
 * Конфигурация JAX-RS.
 */
public class JAXRSApplication extends ResourceConfig {

  public JAXRSApplication() {
    register(new JacksonFeature());
    register(DefaultExceptionMapper.class);
    packages("org.unknown");
  }

}
