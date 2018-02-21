package com.company.resources;

import com.company.logic.DaysVacationLogic;
import com.company.util.Either;
import com.company.util.ErrorContainer;
import com.company.util.MapperResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author santiago.mamani
 */
@Path("/daysVacation")
public class DaysVacationResource {

    private DaysVacationLogic daysVacationLogic = new DaysVacationLogic();
    private MapperResponse mapper = new MapperResponse();

    @POST
    @Produces("application/json")
    public Response fillVacationDays() {
        Either<ErrorContainer, Boolean> resul = daysVacationLogic.fillVacationDays();
        if (resul.errorContainer()) {
            ErrorContainer errorContainer = resul.getErrorContainer();
            Response.Status status = Response.Status.valueOf(errorContainer.getStatus().name());
            return mapper.toResponse(status, errorContainer);
        }
        return mapper.toResponse(Response.Status.CREATED, null);
    }
}
