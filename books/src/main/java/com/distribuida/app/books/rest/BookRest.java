package com.distribuida.app.books.rest;

import com.distribuida.app.books.DTO.AuthorDto;
import com.distribuida.app.books.DTO.BookDto;
import com.distribuida.app.books.clients.AuthorRestClient;
import com.distribuida.app.books.db.Book;
import com.distribuida.app.books.repo.BookRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.stream.Collectors;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class BookRest {

    @Inject
    BookRepository rep;

    @Inject
    @RestClient
    AuthorRestClient clientAuthors;

    static BookDto fromBook(Book obj){
        BookDto dto = new BookDto();
        dto.setId(obj.getId());
        dto.setIsbn(obj.getIsbn());
        dto.setPrice(obj.getPrice());
        dto.setTitle(obj.getTitle());
        dto.setAuthor_id(obj.getAuthor_id());
        return dto;
    }
    @GET
    public List<BookDto> findAll() {

        return rep.streamAll()
                .map(BookRest::fromBook)
                .map(dto-> {
                    AuthorDto authorDto = clientAuthors.getById(dto.getAuthor_id());
                    String authorNombreCompleto = String.format("%s, %s",
                            authorDto.getFirst_name(),authorDto.getLast_name());
                    dto.setAuthor_name(authorNombreCompleto);

                    return dto;
                }
        ).collect(Collectors.toList());

        //return rep.findAll().list();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Integer id) {
        var book = rep.findByIdOptional(id);

        System.out.println("-****-: " +clientAuthors);
        if (book.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Book obj = book.get();
        AuthorDto authorDto = clientAuthors.getById(obj.getAuthor_id());

        /*BookDto dto = new BookDto();
        dto.setId(obj.getId());
        dto.setIsbn(obj.getIsbn());
        dto.getPrice(obj.getPrice());
        dto.getTitle(obj.getTitle());
        dto.getAuthor_id(obj.getAuthor_id());
        dto.getAuthor_name(authorDto.getFirst_name());*/

        BookDto dto = fromBook(obj);

        String authorNombreCompleto = String.format("%s, %s", authorDto.getFirst_name(),authorDto.getLast_name());
        dto.setAuthor_name(authorNombreCompleto);

        return Response.ok(book.get()).build();
    }

    @POST
    public Response create(Book p) {
        rep.persist(p);
        //p.getAuthor_id()!=null
        return Response.status(Response.Status.CREATED.getStatusCode(), "book created").build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id, Book bookObj) {
        Book book = rep.findById(id);

        book.setIsbn(bookObj.getIsbn());
        book.setPrice(bookObj.getPrice());
        book.setTitle(bookObj.getTitle());

        //rep.persistAndFlush(book);

        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
        rep.deleteById(id);

        return Response.ok( )
                .build();
    }


}
