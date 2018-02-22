package com.company.resources;

import com.company.logic.EmployeeLogic;
import com.company.model.Employee;
import com.company.util.Either;
import com.company.util.ErrorContainer;
import com.company.util.MapperResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author santiago.mamani
 */
@Path("/employee")
public class EmployeeResource {

    private EmployeeLogic employeeLogic = new EmployeeLogic();
    private MapperResponse mapper = new MapperResponse();

    @GET
    @Path("/{id}/historyVacations")
    @Produces("application/json")
    public Response getEmployeeHistoryVacation(@PathParam("id") Long idEmployee) {
        Either<ErrorContainer, Employee> employee = employeeLogic.getEmployeeHistoryVacation(idEmployee);
        if (employee.errorContainer()) {
            ErrorContainer errorContainer =  employee.getErrorContainer();
            Status status = Response.Status.valueOf(errorContainer.getStatus().name());
            return mapper.toResponse(status,errorContainer);
        }
        return mapper.toResponse(Response.Status.OK, employee.getSuccess());
    }
}
