package com.galvanize.library;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.util.List;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "authors")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Author {
    //ID (auto-generated)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long id;
    //Name
    private String name;
    //Birth Year
    private int birthYear;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books;

    public Author() {
    }

    public Author(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
}
