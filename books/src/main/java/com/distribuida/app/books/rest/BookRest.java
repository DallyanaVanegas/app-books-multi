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
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.stream.Collectors;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
@Schema(name = "BookRest API")
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
        dto.setAuthorId(obj.getAuthorId());
        return dto;
    }
    @GET
    @Operation(summary = "Obtener todos los libros")
    @APIResponse(responseCode = "200", description = "Lista de libros",
            content = @Content(schema = @Schema(implementation = Book.class)))
    public List<BookDto> findAll() {

        return rep.streamAll()
                .map(BookRest::fromBook)
                .map(dto-> {
                    AuthorDto authorDto = clientAuthors.getById(dto.getAuthorId());
                    String authorNombreCompleto = String.format("%s, %s",
                            authorDto.getFirstName(),authorDto.getLastName());
                    dto.setAuthorName(authorNombreCompleto);

                    return dto;
                }
        ).collect(Collectors.toList());

        //return rep.findAll().list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener un libro por ID")
    @APIResponse(responseCode = "200", description = "Libro encontrado",
            content = @Content(schema = @Schema(implementation = Book.class)))
    @APIResponse(responseCode = "404", description = "Libro no encontrado")
    public Response getById(@PathParam("id") Integer id) {
        var book = rep.findByIdOptional(id);

        System.out.println("-****-: " +clientAuthors);
        if (book.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Book obj = book.get();
        AuthorDto authorDto = clientAuthors.getById(obj.getAuthorId());

        //Proxy Manual
//        var config = ConfigProvider.getConfig();
//        String.format("http://%s:%s",
//                config.getValue("app.authors.host", String.class),
//                config.getValue("app.authors.port", String.class)
//        );
//        RestClientBuilder.newBuilder()
//                .baseUri(URI.create("http://127.0.0.1:9090"))
//                .connectTimeout(400, TimeUnit.MILLISECONDS)
//                .build(AuthorRestClient.class);

        /*BookDto dto = new BookDto();
        dto.setId(obj.getId());
        dto.setIsbn(obj.getIsbn());
        dto.getPrice(obj.getPrice());
        dto.getTitle(obj.getTitle());
        dto.getAuthor_id(obj.getAuthor_id());
        dto.getAuthor_name(authorDto.getFirst_name());*/

        BookDto dto = fromBook(obj);

        String authorNombreCompleto = String.format("%s, %s", authorDto.getFirstName(),authorDto.getLastName());
        dto.setAuthorName(authorNombreCompleto);

        return Response.ok(book.get()).build();
    }

    @POST
    @Operation(summary = "Crear un nuevo libro")
    @APIResponse(responseCode = "201", description = "Libro creado")
    public Response create(Book p) {

        var author = clientAuthors.getById(p.getAuthorId());

        if(author!=null){
            rep.persist(p);return Response.status(Response.Status.CREATED.getStatusCode(), "book created").build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualizar un libro por ID")
    @APIResponse(responseCode = "200", description = "Libro actualizado")
    @APIResponse(responseCode = "404", description = "Libro no encontrado")
    public Response update(@PathParam("id") Integer id, Book bookObj) {

        var author = clientAuthors.getById(bookObj.getAuthorId());

        if(author!=null){
            Book book = rep.findById(id);

            book.setIsbn(bookObj.getIsbn());
            book.setPrice(bookObj.getPrice());
            book.setTitle(bookObj.getTitle());
            book.setAuthorId(bookObj.getAuthorId());
            return Response.ok().build();

        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        //rep.persistAndFlush(book);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar un libro por ID")
    @APIResponse(responseCode = "200", description = "Libro eliminado")
    public Response delete(@PathParam("id") Integer id) {
        rep.deleteById(id);

        return Response.ok( )
                .build();
    }


}
