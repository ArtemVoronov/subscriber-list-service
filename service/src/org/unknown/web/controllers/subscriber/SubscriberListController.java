package org.unknown.web.controllers.subscriber;

import groovy.transform.CompileStatic;
import groovy.util.logging.Log4j;
import org.unknown.model.Cell;
import org.unknown.services.Service;
import org.unknown.services.db.DBService;
import org.unknown.web.controllers.subscriber.model.SubscriberProfile;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * Author: Artem Voronov
 */
@Named
@Path("/subscribers")
@CompileStatic
@RequestScoped
@Log4j("logger")
public class SubscriberListController {

  @Inject @Service
  private DBService db;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/profile")
  public SubscriberProfile profile(@QueryParam("cellId") String cellId, @Context SecurityContext ctx) {
    try{
      //TODO
      return  new SubscriberProfile("Bob", "bob@example.com", "2017-02-24 12:45:00");
    } catch (Exception ignored) {
      throw new WebApplicationException(Response.status(Response.Status.BAD_GATEWAY).type(MediaType.TEXT_PLAIN_TYPE).entity("UNKNOWN").build());
    }
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/cell")
  public Cell getCell(@QueryParam("cellId") Integer cellId, @Context SecurityContext ctx) {
    try {
      //TODO
      return db.getCell(cellId);
    } catch (Exception ignored) {
      throw new WebApplicationException(Response.status(Response.Status.BAD_GATEWAY).type(MediaType.TEXT_PLAIN_TYPE).entity("UNKNOWN").build());
    }
  }

  @POST
  @Path("/cell")
  public void addCell(@FormParam("cellId") Integer cellId, @Context SecurityContext ctx) {
    try {
      //TODO
      db.addCell(new Cell(cellId));
    } catch (Exception ignored) {
      throw new WebApplicationException(Response.status(Response.Status.BAD_GATEWAY).type(MediaType.TEXT_PLAIN_TYPE).entity("UNKNOWN").build());
    }
  }
}
