package com.distribuida.books.authors.db;

import jakarta.persistence.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Schema(description = "ID del autor")
    private Integer id;

    @Column(name = "first_name")
    @Schema(description = "Primer nombre del autor")
    private String firstName;

    @Column(name = "last_name")
    @Schema(description = "Apellido del autor")
    private String lastName;

    @Schema(hidden = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Schema(description = "Obtiene el primer nombre del autor")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Schema(description = "Obtiene el apellido del autor")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
