package com.distribuida.app.books.clients;

import com.distribuida.app.books.DTO.AuthorDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;
@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient(baseUri = "http://localhost:9090")
public interface AuthorRestClient {
//los mismos metodos de author rest
    @GET
    public List<AuthorDto> findAll();
    @GET
    @Path("/{id}")
    public AuthorDto getById(@PathParam("id") Integer id);
}
