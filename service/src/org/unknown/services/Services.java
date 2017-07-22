package org.unknown.services;

import org.apache.commons.configuration2.Configuration;
import org.unknown.services.db.DBService;

public class Services {

  private final DBService dbService;

  public Services(Configuration config) throws ServicesException {
    this.dbService = initDBService();
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

  public void shutdown() {
    dbService.shutdown();
  }

}
