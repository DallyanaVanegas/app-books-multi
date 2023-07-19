package com.distribuida.books.authors.rest;

import com.distribuida.books.authors.db.Author;
import com.distribuida.books.authors.repo.AuthorRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class AuthorRest {

    @Inject
    AuthorRepository rep;

    //books GET
    @GET
    public List<Author> findAll() {
        return rep.findAll().list();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Integer id) {
        var book = rep.findByIdOptional(id);
        if (book.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(book.get()).build();
    }

    @POST
    public Response create(Author p) {
        rep.persist(p);

        return Response.status(Response.Status.CREATED.getStatusCode(), "author created").build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id, Author authorObj) {
        Author author = rep.findById(id);
        author.setFirst_name(authorObj.getFirst_name());
        author.setLast_name(authorObj.getLast_name());

        //rep.persistAndFlush(author);

        return Response.ok().build();
    }

    //books/{id} DELETE
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
        rep.deleteById(id);

        return Response.ok( )
                .build();
    }


}
