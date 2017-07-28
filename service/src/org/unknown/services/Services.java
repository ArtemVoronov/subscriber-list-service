package org.unknown.services;

import org.unknown.services.db.DBService;
import org.unknown.services.subscriber.SubscriberService;

public class Services {

  private final DBService dbService;
  private final SubscriberService subscriberService;

  public Services() throws ServicesException {
    this.dbService = initDBService();
    this.subscriberService = new SubscriberService(dbService);
  }

  private DBService initDBService() throws ServicesException {
    try {
      return new DBService();
    } catch(Exception e) {
      throw new ServicesException("Error during DBService initialization.", e);
    }
  }

  public DBService getDbService() {
    return dbService;
  }

  public SubscriberService getSubscriberService() {
    return subscriberService;
  }

  public void shutdown() {
    dbService.shutdown();
  }

}
