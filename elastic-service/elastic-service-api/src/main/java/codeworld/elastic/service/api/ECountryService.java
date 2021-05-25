package codeworld.elastic.service.api;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import codeworld.health.service.model.CountryDto;

@Path("Ecountryservice")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ECountryService {

    @GET
    @Path("/findAll")
    List<CountryDto> findAll();

    @POST
    @Path("/create")
    Long create(CountryDto countryDto);

    @PUT
    @Path("/update")
    Long update(CountryDto countryDto);

    @POST
    @Path("/delete")
    void delete(@NotNull Long id);

}
