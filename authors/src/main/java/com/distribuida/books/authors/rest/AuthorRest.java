package com.distribuida.books.authors.rest;

import com.distribuida.books.authors.db.Author;
import com.distribuida.books.authors.repo.AuthorRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;


import java.util.List;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
@Schema(name = "AuthorRest API")
public class AuthorRest {

    @Inject
    AuthorRepository rep;

    @GET
    @Operation(summary = "Obtener todos los autores")
    @APIResponse(responseCode = "200", description = "Lista de autores",
            content = @Content(schema = @Schema(implementation = Author.class)))
    public List<Author> findAll() {
        return rep.findAll().list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener un autor por ID")
    @APIResponse(responseCode = "200", description = "Autor encontrado",
            content = @Content(schema = @Schema(implementation = Author.class)))
    @APIResponse(responseCode = "404", description = "Autor no encontrado")
    public Response getById(@PathParam("id") Integer id) {
        var author = rep.findByIdOptional(id);
        if (author.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(author.get()).build();
    }

    @POST
    @Operation(summary = "Crear un nuevo autor")
    @APIResponse(responseCode = "201", description = "Autor creado")
    public Response create(Author author) {
        rep.persist(author);

        return Response.status(Response.Status.CREATED.getStatusCode(), "Autor creado").build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualizar un autor por ID")
    @APIResponse(responseCode = "200", description = "Autor actualizado")
    @APIResponse(responseCode = "404", description = "Autor no encontrado")
    public Response update(@PathParam("id") Integer id, Author authorObj) {
        Author author = rep.findById(id);
        author.setFirstName(authorObj.getFirstName());
        author.setLastName(authorObj.getLastName());

        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar un autor por ID")
    @APIResponse(responseCode = "200", description = "Autor eliminado")
    public Response delete(@PathParam("id") Integer id) {
        rep.deleteById(id);

        return Response.ok().build();
    }
}


