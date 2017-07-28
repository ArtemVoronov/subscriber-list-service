package org.unknown;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * author: Artem Voronov
 */
public class InitListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    initWebContext();
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    WebContext.shutdown();
  }

  private void initWebContext() {
    try {
      WebContext.init();
    }
    catch(Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Can't init WebContext", e);
    }
  }
}
