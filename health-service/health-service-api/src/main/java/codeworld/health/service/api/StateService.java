package codeworld.health.service.api;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import codeworld.health.service.model.StateDto;

@Path("stateservice")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface StateService {

    @GET
    @Path("/findAll")
    List<StateDto> findAll();

    @GET
    @Path("/findById/{id : \\d+}")
    StateDto findById(@PathParam("id") @NotNull Long id);

    @POST
    @Path("/create")
    Long create(StateDto dto);

    @PUT
    @Path("/update")
    Long update(StateDto dto);

    @POST
    @Path("/delete")
    void delete(@NotNull Long id);

}
