package org.unknown.web.controllers.subscriber;

import groovy.transform.CompileStatic;
import groovy.util.logging.Log4j;
import org.unknown.model.Cell;
import org.unknown.model.Msisdn;
import org.unknown.model.Profile;
import org.unknown.services.Service;
import org.unknown.services.subscriber.SubscriberService;
import org.unknown.web.controllers.subscriber.model.CellPhone;
import org.unknown.web.controllers.subscriber.model.SubscriberList;
import org.unknown.web.controllers.subscriber.model.SubscriberProfile;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Author: Artem Voronov
 */
@Named
@Path("/")
@CompileStatic
@RequestScoped
@Log4j("logger")
public class SubscriberListController {

  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private static Pattern emailPattern = Pattern.compile("^[A-Za-z0-9]+[\\.\\-_A-Za-z0-9!#$&'*+/=?^_`{|}~:]*@[A-Za-z0-9]+[\\.\\-_A-Za-z0-9!#$&'*+/=?^_`{|}~:]*\\.[A-Za-z]{2,}$", Pattern.CASE_INSENSITIVE);

  @Inject @Service
  private SubscriberService subscriberService;

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/profile")
  public Response createProfile(SubscriberProfile subscriberProfile, @Context SecurityContext ctx) {
    try {
      checkSubscriberProfile(subscriberProfile);

      Msisdn msisdn = new Msisdn(subscriberProfile.getCtn());
      Profile profile = new Profile(subscriberProfile.getName(), subscriberProfile.getEmail(), subscriberProfile.getActivateDate());
      subscriberService.createProfile(msisdn, profile);
      return Response.ok().build();
    } catch (IllegalArgumentException ex) {
      return Response.ok("{error:\"" + ex.getMessage() + "\"}",  MediaType.APPLICATION_JSON_TYPE).build();//TODO: ckech error json response
    } catch (Exception ex) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/profile")
  public Profile createProfile(@QueryParam("ctn") Integer ctn, @Context SecurityContext ctx) {
    Msisdn msisdn = new Msisdn(ctn);
    return subscriberService.getProfile(msisdn);
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/msisdn")
  public Response createMsisdn(CellPhone cellPhone, @Context SecurityContext ctx) {
    try {
      checkCellPhone(cellPhone);
      Cell cell = new Cell(cellPhone.getCellId());
      Msisdn msisdn = new Msisdn(cellPhone.getCtn());
      subscriberService.createMsisdn(cell, msisdn);
      return Response.ok().build();
    } catch (IllegalArgumentException ex) {
      return Response.ok("{error:\"" + ex.getMessage() + "\"}",  MediaType.APPLICATION_JSON_TYPE).build();//TODO: ckech error json response
    } catch (Exception ex) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/subscribers")
  public SubscriberList getSubscribers(@QueryParam("cellId") Integer cellId, @Context SecurityContext ctx) {
    try {
      if (cellId == null || cellId < 0)
        throw new IllegalArgumentException("Wrong 'cellId' value");

      Set<Msisdn> msisdnSet = subscriberService.getMsisdns(new Cell(cellId));
      List<SubscriberProfile> profiles = new ArrayList<>(msisdnSet.size());
      for (Msisdn msisdn : msisdnSet) {
        Profile profile = subscriberService.getProfile(msisdn);
        profiles.add(new SubscriberProfile(msisdn.getCtn(), profile.getName(), profile.getEmail(), profile.getActivateDate()));
      }

      return new SubscriberList(profiles);
    } catch (Exception ex) {
      throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN_TYPE).entity("UNKNOWN").build());
    }
  }

  private static void checkSubscriberProfile(SubscriberProfile profile) {
    if (profile.getCtn() < 0)
      throw new IllegalArgumentException("Wrong 'ctn' value");

    String name = profile.getName();
    if (name == null || name.isEmpty())
      throw new IllegalArgumentException("Wrong 'name' value");

    String email = profile.getEmail();
    if (email == null || email.isEmpty())
      throw new IllegalArgumentException("Wrong 'email' value");

    if (!emailPattern.matcher(email).find())
      throw new IllegalArgumentException("Wrong format of 'email' value");

    String activateDate = profile.getActivateDate();
    if (activateDate == null || activateDate.isEmpty())
      throw new IllegalArgumentException("Wrong 'activateDate' value");

    try {
      LocalDateTime.parse(activateDate, formatter);
    } catch (DateTimeParseException ex) {
      throw new IllegalArgumentException("Wrong format of 'activateDate' value, use 'yyyy-MM-dd HH:mm:ss'");
    }
  }

  private static void checkCellPhone(CellPhone cellPhone) {
    if (cellPhone.getCellId() < 0)
      throw new IllegalArgumentException("Wrong 'cellId' value");

    if (cellPhone.getCtn() < 0)
      throw new IllegalArgumentException("Wrong 'ctn' value");
  }
}