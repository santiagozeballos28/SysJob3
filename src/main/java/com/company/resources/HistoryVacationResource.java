package com.company.resources;

import com.company.logic.HistoryVacationLogic;
import com.company.util.MapperResponse;
import com.company.util.ObjectResponce;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author santiago.mamani
 */
@Path("/historyVacation")
@Produces("application/json")
@Consumes("application/json")
public class HistoryVacationResource {

    private HistoryVacationLogic historyVacationLogic = new HistoryVacationLogic();
    private MapperResponse mapper = new MapperResponse();

    @POST
    @Path("/{id}/sendVacation")
    public Response sendVacation(
            @PathParam("id") long idEmployee,
            @QueryParam("startDate") String startDate,
            @QueryParam("endDate") String endDate,
            @QueryParam("reason") String reason) {
        ObjectResponce objectResponce = historyVacationLogic.sendVacation(idEmployee,startDate, endDate, reason);
        Response response = mapper.toResponse(objectResponce);
        return response;
    }
}
