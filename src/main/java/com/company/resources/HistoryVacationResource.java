package com.company.resources;

import com.company.logic.HistoryVacationLogic;
import com.company.model.HistoryVacation;
import com.company.util.Either;
import com.company.util.ErrorContainer;
import com.company.util.MapperResponse;
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
public class HistoryVacationResource {

    private HistoryVacationLogic historyVacationLogic = new HistoryVacationLogic();
    private MapperResponse mapper = new MapperResponse();

    @POST
    @Path("/{id}/sendVacation")
    @Produces("application/json")
    public Response sendVacation(
            @PathParam("id") long idEmployee,
            @QueryParam("startDate") String startDate,
            @QueryParam("endDate") String endDate,
            @QueryParam("reason") String reason) {
        Either<ErrorContainer, HistoryVacation> historyVacation = historyVacationLogic.sendVacation(idEmployee, startDate, endDate, reason);
        if (historyVacation.errorContainer()) {
            ErrorContainer errorContainer = historyVacation.getErrorContainer();
            Response.Status status = Response.Status.valueOf(errorContainer.getStatus().name());
            return mapper.toResponse(status, errorContainer);
        }
        return mapper.toResponse(Response.Status.CREATED, historyVacation.getSuccess());
    }
}
