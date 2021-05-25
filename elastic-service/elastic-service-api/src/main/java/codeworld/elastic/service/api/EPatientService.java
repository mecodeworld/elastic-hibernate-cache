package codeworld.elastic.service.api;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import codeworld.health.service.model.PatientDto;

@Path("epatientservice")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface EPatientService {

    @GET
    @Path("/findAll")
    List<PatientDto> findAll();

    @GET
    @Path("/findById/{id : \\d+}")
    Optional<PatientDto> findById(@PathParam("id") @NotNull Long id);

    @POST
    @Path("/delete")
    void delete(@NotNull Long id);

}
