package org.unknown.services.subscriber;

import org.apache.ignite.IgniteCache;
import org.unknown.model.Cell;
import org.unknown.model.Msisdn;
import org.unknown.model.Profile;
import org.unknown.services.db.DBService;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Artem Voronov
 */
public class SubscriberService {

  private final DBService db;

  private final IgniteCache<Cell, Set<Msisdn>> msisdns;//TODO: use collocated cache
  private final IgniteCache<Msisdn, Profile> profiles;

  public SubscriberService(DBService db) {
    this.db = db;

    msisdns = db.getOrCreateCache("CELLS_AND_MSISDNS");
    profiles = db.getOrCreateCache("PROFILES");
  }

  public void createMsisdn(Cell cell, Msisdn msisdn) {
    db.vtx(() -> {
      Set<Msisdn> cellMsisdns = msisdns.get(cell);
      if (cellMsisdns == null) {
        cellMsisdns = new HashSet<>();
        msisdns.put(cell, cellMsisdns);
      }

      cellMsisdns.add(msisdn);
    });
  }


  public Set<Msisdn> getMsisdns(Cell cell) {
    return db.tx(() -> {
      Set<Msisdn> result = msisdns.get(cell);

      if (result == null)
        return null;

      return result;
    });
  }

  public void createProfile(Msisdn msisdn, Profile profile) {
    db.vtx(() -> profiles.put(msisdn, profile));
  }

  public Profile getProfile(Msisdn msisdn) {
    return db.tx(() -> profiles.get(msisdn));
  }
}
