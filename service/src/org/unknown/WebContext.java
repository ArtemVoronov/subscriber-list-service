package org.unknown;

import org.apache.commons.configuration2.Configuration;
import org.unknown.services.Services;
import org.unknown.services.db.DBService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

/**
 * author: Artem Voronov
 */
@Named
@ApplicationScoped
public class WebContext {

  private static Services services;

  static synchronized void init(Configuration config) throws Exception {
    if (WebContext.services == null) {
      WebContext.services = new Services(config);
    }
  }

  static synchronized void shutdown() {
    if (services != null)
      services.shutdown();
  }

  public static Services getServices() {
    return services;
  }

  @Produces
  public DBService getDBService() {
    return services.getDbService();
  }

}