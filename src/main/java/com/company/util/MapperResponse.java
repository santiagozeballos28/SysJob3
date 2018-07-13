package com.company.util;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author santiago.mamani
 */
public class MapperResponse {
    public Response toResponse(Status status, Object object) {
        return Response.status(status).entity(object).build();
    }
}
