package com.distribuida.app.books.db;

import jakarta.persistence.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Schema(description = "ID del libro")
    private Integer id;

    @Column
    @Schema(description = "ISBN del libro")
    private String isbn;

    @Column
    @Schema(description = "titulo del libro")
    private String title;

    @Column
    @Schema(description = "precio del libro")
    private Double price;

    @Column(name="author_id")
    @Schema(description = "ID del autor del libro")
    private int authorId;

    public Book() {
    }

    @Schema(hidden = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Schema(description = "Obtiene el ISBN")
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    @Schema(description = "Obtiene el titulo")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @Schema(description = "Obtiene el precio")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    @Schema(description = "Obtiene el id del autor")
    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", authorId=" + authorId +
                '}';
    }

}
