package org.unknown.services.db;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;

/**
 * Author: Artem Voronov
 */
public class DBService {

  public static void main(String[] args) {
    //TODO
    Ignite ignite = Ignition.ignite();

    // Get an instance of named cache.
    final IgniteCache<Integer, String> cache = ignite.cache("cacheName");

    // Store keys in cache.
    for (int i = 0; i < 10; i++)
      cache.put(i, Integer.toString(i));

    // Retrieve values from cache.
    for (int i = 0; i < 10; i++)
      System.out.println("Got [key=" + i + ", val=" + cache.get(i) + ']');

    // Remove objects from cache.
    for (int i = 0; i < 10; i++)
      cache.remove(i);

    // Atomic put-if-absent.
    cache.putIfAbsent(1, "1");

    // Atomic replace.
    cache.replace(1, "1", "2");
  }
}
