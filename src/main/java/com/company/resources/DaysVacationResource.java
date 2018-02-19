
package com.company.resources;
import com.company.logic.DaysVacationLogic;
import com.company.util.MapperResponse;
import com.company.util.ObjectResponce;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author santiago.mamani
 */
@Path("/daysVacation")
@Produces("application/json")
public class DaysVacationResource {

    private DaysVacationLogic daysVacationLogic = new DaysVacationLogic();
    private MapperResponse mapper = new MapperResponse();

    @POST
    public Response fillVacationDays() {
        ObjectResponce objectResponce = daysVacationLogic.fillVacationDays();
        Response response = mapper.toResponse(objectResponce);
        return response;
    }
}