package com.galvanize.library;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.util.List;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "genres")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Genre {
    //ID (auto-generated)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private Long id;
    //Name
    private String name;

    @OneToMany(mappedBy = "genre")
    private List<Book> books;

    public Genre() {    }

    public Genre(String name) {
        this.name = name;
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
}
