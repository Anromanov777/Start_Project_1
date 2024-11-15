package ru.model;

import jakarta.validation.constraints.NotEmpty;

public class Book {

    int id;
    Integer id_person;
    @NotEmpty
    String name;
    @NotEmpty
    String author;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getId_person() {
        return id_person;
    }

    public void setId_person(Integer id_person) {
        this.id_person = id_person;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", id_person=" + id_person +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
