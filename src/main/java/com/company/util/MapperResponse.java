
package com.company.util;

import javax.ws.rs.core.Response;

/**
 *
 * @author santiago.mamani
 */
public class MapperResponse {
       public Response toResponse(ObjectResponce objectResponce) {
         return Response.status(objectResponce.getStatus()).entity(objectResponce.getObjectModel()).build();
    }
}
