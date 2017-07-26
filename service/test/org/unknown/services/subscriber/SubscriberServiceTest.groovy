package org.unknown.services.subscriber

import org.unknown.model.Cell
import org.unknown.model.Msisdn
import org.unknown.model.Profile
import org.unknown.services.db.DBTestBase

/**
 * Author: Artem Voronov
 */
class SubscriberServiceTest extends DBTestBase {
  protected SubscriberService subscriberService

  @Override
  protected void setUp() {
    super.setUp()

    subscriberService = new SubscriberService(db)
  }

  void testSaveAndLoadProfile() {
    Profile profile = createCorrectProfile()
    Integer ctn = 71112223344

    subscriberService.createProfile(ctn, profile)

    Profile loaded = subscriberService.getProfile(ctn)

    assertNotNull(loaded)
    assertProfileEquals(profile, loaded)
  }

  void testSaveAndLoadMsisdn() {
    Msisdn first = createCorrectMsisdn()
    Msisdn second = createCorrectMsisdn(ctn: 777)
    Cell cell = createCorrectCell(id: first.cellId)

    subscriberService.createMsisdn(first)
    subscriberService.createMsisdn(second)

    List<Msisdn> listOfMsisdns = subscriberService.getMsisdns(cell)

    assertNotNull(listOfMsisdns)
    assertEquals(2, listOfMsisdns.size())

    Msisdn loaded1 = listOfMsisdns.find{it.ctn == first.ctn}
    Msisdn loaded2 = listOfMsisdns.find{it.ctn == second.ctn}

    assertNotNull(loaded1)
    assertNotNull(loaded2)
    assertMsisdnEquals(first, loaded1)
    assertMsisdnEquals(second, loaded2)
  }

  void testDuplicateProfile() {
    Profile profile = createCorrectProfile()
    Profile another = createCorrectProfile(name: "Tom")
    Integer ctn = 71112223344

    subscriberService.createProfile(ctn, profile)
    subscriberService.createProfile(ctn, another)

    Profile loaded = subscriberService.getProfile(ctn)

    assertNotNull(loaded)
    assertProfileEquals(another, loaded)
  }

  void testDuplicateMsisdn() {
    Msisdn msisdn = createCorrectMsisdn()
    Msisdn duplicate = createCorrectMsisdn()
    Cell cell = createCorrectCell(id: msisdn.cellId)

    subscriberService.createMsisdn(msisdn)
    subscriberService.createMsisdn(duplicate)

    Set<Msisdn> loaded = subscriberService.getMsisdns(cell)

    assertNotNull(loaded)
    assertEquals(1, loaded.size())
    assertMsisdnEquals(duplicate, loaded.iterator().next())
  }

  void testMultipleCellsOfMsisdn() {
    Msisdn msisdn = createCorrectMsisdn()
    Msisdn another = createCorrectMsisdn(cellId: 2)
    Cell cell1 = createCorrectCell(id: msisdn.cellId)
    Cell cell2 = createCorrectCell(id: another.cellId)

    subscriberService.createMsisdn(msisdn)
    subscriberService.createMsisdn(another)

    Set<Msisdn> loaded1 = subscriberService.getMsisdns(cell1)
    Set<Msisdn> loaded2 = subscriberService.getMsisdns(cell2)

    assertNotNull(loaded1)
    assertNotNull(loaded2)
    assertEquals(1, loaded1.size())
    assertEquals(1, loaded2.size())
    assertMsisdnEquals(msisdn, loaded1.iterator().next())
    assertMsisdnEquals(another, loaded2.iterator().next())
  }

  static Profile createCorrectProfile(Map overrides = [:]) {
    def defaultFields = [
      name              : 'Bob',
      email             : 'bob@example.com',
      activateDate      : '2017-02-24 12:45:00'
    ]
    return new Profile(defaultFields + overrides)
  }

  static Cell createCorrectCell(Map overrides = [:]) {
    def defaultFields = [
      id                : 1
    ]
    return new Cell(defaultFields + overrides)
  }

  static Msisdn createCorrectMsisdn(Map overrides = [:]) {
    def defaultFields = [
      ctn               : 1112223344,
      cellId            : 1
    ]
    return new Msisdn(defaultFields + overrides)
  }

  static void assertProfileEquals(Profile expected, Profile actual) {
    assertEquals(expected.name, actual.name)
    assertEquals(expected.email, actual.email)
    assertEquals(expected.activateDate, actual.activateDate)
  }

  static void assertMsisdnEquals(Msisdn expected, Msisdn actual) {
    assertEquals(expected.ctn, actual.ctn)
    assertEquals(expected.cellId, actual.cellId)
  }
}
