package com.bookStore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Book {
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private int id;
    // private String name;
    // private String author;
    // private String price;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Book name is required")
    @Size(min = 3, max = 50, message = "Book name must be between 3 and 50 characters")
    private String name;

    @NotBlank(message = "Author name is required")
    @Size(min = 3, max = 50, message = "Author name must be between 3 and 500 characters")
    private String author;

    @NotBlank(message = "Price is required")
    @Pattern(regexp = "^\\d{1,6}$", message = "Price must be digits only (1 to 6 digits)")
    private String price;


    public Book(int id, String name, String author, String price) {
        super();
        this.id = id;
        this.name = name;
        this.author = author;
        this.price = price;
    }
    public Book(){
        super();
    }

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
