package org.unknown.services;

import org.apache.commons.configuration2.Configuration;
import org.unknown.services.db.DBService;

public class Services {

  private final DBService dbService;

  public Services(Configuration config) throws ServicesException {
    this.dbService = initDBService(config);
  }

  private DBService initDBService(Configuration config) throws ServicesException {
    try {
      int nodesCount =  config.getInt("nodes.count");
      return new DBService(nodesCount);
    } catch(Exception e) {
      throw new ServicesException("Error during DBService initialization.", e);
    }
  }
  public void shutdown() {
    dbService.shutdown();
  }

}
