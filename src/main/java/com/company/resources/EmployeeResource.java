package com.company.resources;

import com.company.logic.EmployeeLogic;
import com.company.util.MapperResponse;
import com.company.util.ObjectResponce;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author santiago.mamani
 */
@Path("/employee")
@Produces("application/json")
@Consumes("application/json")
public class EmployeeResource {

    private EmployeeLogic employeeLogic = new EmployeeLogic();
    private MapperResponse mapper = new MapperResponse();

    @GET
    @Path("/{id}/historyVacations")
    public Response getEmployeeHistoryVacation(@PathParam("id") Long idEmployee) {
        ObjectResponce objectResponce = employeeLogic.getEmployeeHistoryVacation(idEmployee);
        Response response = mapper.toResponse(objectResponce);
        return response;
    }
}
