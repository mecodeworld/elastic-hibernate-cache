package home.app.api.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import home.app.api.model.Film;

/**
 * @author haridas.kanure
 *
 */
@Path("filmService")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface FilmService {

    @GET
    @Path("/findAll")
    public List<Film> findAll();

}
