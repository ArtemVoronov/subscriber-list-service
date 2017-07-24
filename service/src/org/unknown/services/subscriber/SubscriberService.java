package org.unknown.services.subscriber;

import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.affinity.AffinityKey;
import org.apache.ignite.cache.query.SqlQuery;
import org.apache.ignite.configuration.CacheConfiguration;
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

  private static final String CELLS_CACHE = "CELLS";
  private static final String MSISDNS_CACHE = "MSISDNS";
  private static final String COLLOCATED_CELLS_MSISDNS_CACHE = "COLLOCATED_CELLS_MSISDNS";
  private static final String PROFILES_CACHE = "PROFILES";

  private final DBService db;

  private final IgniteCache<Integer, Cell> cells;
  private final IgniteCache<Integer, Msisdn> msisdns;
  private final IgniteCache<AffinityKey<Integer>, Msisdn> collocated;
  private final IgniteCache<Integer, Profile> profiles;

  public SubscriberService(DBService db) {
    this.db = db;

    //TODO
//    cells = db.getOrCreateCache(CELLS_CACHE);
//    msisdns = db.getOrCreateCache(MSISDNS_CACHE);
    profiles = db.getOrCreateCache(PROFILES_CACHE);

    CacheConfiguration<Integer, Cell> cacheConfig1 = new CacheConfiguration<>(CELLS_CACHE);
    cacheConfig1.setCacheMode(CacheMode.PARTITIONED);
    cacheConfig1.setIndexedTypes(Integer.class, Cell.class);
    cacheConfig1.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
    cells = db.getIgnite().getOrCreateCache(cacheConfig1);

    CacheConfiguration<Integer, Msisdn> cacheConfig2 = new CacheConfiguration<>(MSISDNS_CACHE);
    cacheConfig2.setCacheMode(CacheMode.PARTITIONED);
    cacheConfig2.setIndexedTypes(Integer.class, Msisdn.class);
    cacheConfig2.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
    msisdns = db.getIgnite().getOrCreateCache(cacheConfig2);

    CacheConfiguration<AffinityKey<Integer>, Msisdn> cacheConfig = new CacheConfiguration<>(COLLOCATED_CELLS_MSISDNS_CACHE);
    cacheConfig.setCacheMode(CacheMode.PARTITIONED);
    cacheConfig.setIndexedTypes(AffinityKey.class, Msisdn.class);
    cacheConfig.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
    collocated = db.getIgnite().getOrCreateCache(cacheConfig);

  }

  public void createMsisdn(Msisdn msisdn) {
    db.vtx(() -> {
      cells.put(msisdn.getCellId(), new Cell(msisdn.getCellId()));
      collocated.put(msisdn.key(), msisdn);
      msisdns.put(msisdn.getCtn(), msisdn);
    });
  }


  @SuppressWarnings("unchecked")
  public Set<Msisdn> getMsisdns(Cell cell) {
    return db.tx(() -> {

      String joinSql = "from Msisdn, \"CELLS\".Cell as c where Msisdn.cellId = c.id and c.id = ?";

      List<Cache.Entry<AffinityKey<Integer>, Msisdn>> list = collocated.query(new SqlQuery<AffinityKey<Integer>, Msisdn>(Msisdn.class, joinSql).
          setArgs(cell.getId())).getAll();

      return list.stream().map(Cache.Entry::getValue).collect(Collectors.toSet());
    });
  }

  //TODO: clean
//  @SuppressWarnings("unchecked")
//  public Set<Msisdn> getMsisdns2(Cell cell) {
//    return db.tx(() -> {
//
//      // SQL join on Person and Organization.
//      SqlQuery sql = new SqlQuery(Msisdn.class,"from Msisdn, \"CELLS\".Cell as zozo where Msisdn.cellId = zozo.id and zozo.id = ?");
//      sql.setDistributedJoins(true);
//      try (QueryCursor<Cache.Entry<Integer, Msisdn>> cursor = msisdns.query(sql.setArgs(cell.getId()))) {
//        for (Cache.Entry<Integer, Msisdn> e : cursor)
//          System.out.println(e.getValue().toString());
//      }
//
//
//      return null;
//    });
//  }

  public void createProfile(Integer msisdn, Profile profile) {
    db.vtx(() -> profiles.put(msisdn, profile));
  }

  public Profile getProfile(Integer ctn) {
    return db.tx(() -> profiles.get(ctn));
  }
}
