package org.unknown.rest;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Author: Artem Voronov
 * Обработчик exception-ов, которые произошли при вызове REST-сервисов и не были корректно отловлены
 */
public class DefaultExceptionMapper implements ExceptionMapper<Exception> {

  @Override
  public Response toResponse(Exception e) {
    final int sc = e instanceof WebApplicationException ? ((WebApplicationException)e).getResponse().getStatus() : HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    StringWriter sw = new StringWriter();
    e.printStackTrace(new PrintWriter(sw, true));

    return Response.status(sc)
        .entity(sw.toString())
        .type(MediaType.TEXT_PLAIN_TYPE)
        .build();
  }
}
