package com.distribuida.app.books.DTO;
public class AuthorDto {

    private Integer id;

    private String firstname;

    private String lastname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String first_name) {
        this.firstname = first_name;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String last_name) {
        this.lastname = last_name;
    }
}
