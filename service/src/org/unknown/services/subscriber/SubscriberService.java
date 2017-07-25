package org.unknown.services.subscriber;

import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.affinity.AffinityKey;
import org.apache.ignite.cache.query.SqlQuery;
import org.unknown.model.Cell;
import org.unknown.model.Msisdn;
import org.unknown.model.Profile;
import org.unknown.services.db.DBService;

import javax.cache.Cache;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Author: Artem Voronov
 */
public class SubscriberService {

  private final DBService db;

  private final IgniteCache<Integer, Cell> cells;
  private final IgniteCache<Integer, Msisdn> msisdns;
  private final IgniteCache<AffinityKey<Integer>, Msisdn> collocated;
  private final IgniteCache<Integer, Profile> profiles;

  public SubscriberService(DBService db) {
    this.db = db;

    cells = db.getOrCreateCache("CELLS", Integer.class, Cell.class);
    msisdns = db.getOrCreateCache("MSISDNS", Integer.class, Msisdn.class);
    collocated = db.getOrCreateCache("COLLOCATED_CELLS_AND_MSISDNS", AffinityKey.class, Msisdn.class);
    profiles = db.getOrCreateCache("PROFILES");
  }

  public void createMsisdn(Msisdn msisdn) {
    db.vtx(() -> {
      cells.put(msisdn.getCellId(), new Cell(msisdn.getCellId()));
      collocated.put(msisdn.key(), msisdn);
      msisdns.put(msisdn.getCtn(), msisdn);
    });
  }

  public Set<Msisdn> getMsisdns(Cell cell) {
    return db.tx(() -> {
      String joinSql = "from Msisdn, \"CELLS\".Cell as c where Msisdn.cellId = c.id and c.id = ?";
      List<Cache.Entry<AffinityKey<Integer>, Msisdn>> list = collocated.query(new SqlQuery<AffinityKey<Integer>, Msisdn>(Msisdn.class, joinSql).setArgs(cell.getId())).getAll();
      return list.stream().map(Cache.Entry::getValue).collect(Collectors.toSet());
    });
  }

  public void createProfile(Integer msisdn, Profile profile) {
    db.vtx(() -> profiles.put(msisdn, profile));
  }

  public Profile getProfile(Integer ctn) {
    return db.tx(() -> profiles.get(ctn));
  }
}
