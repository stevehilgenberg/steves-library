package com.galvanize.library;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library/genre")
public class GenresController {

    GenresService genresService;

    public GenresController(GenresService genresService) {
        this.genresService = genresService;
    }

    @GetMapping()
    //public ResponseEntity<GenresList> getGenres(@RequestParam(required = false) String name) {
    public ResponseEntity<GenresList> getGenresList() {
        GenresList genresList;
        //if (name == null) {
        genresList = genresService.getGenresList();
        //} else {
        //    genresList = genresService.getGenresList(name);
        //}
        return genresList.isEmpty() ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(genresList);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Genre> getGenre(@PathVariable String name) {
        try {
            genresService.getGenre(name);
        } catch (GenreNotFoundException e){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(genresService.getGenre(name));
    }

    @PostMapping()
    public Genre addGenre(@RequestBody Genre genre) {
        return genresService.addGenre(genre);
    }

    @PatchMapping("/{badName}")
    public Genre updateGenre(@PathVariable String badName,
                             @RequestBody UpdateGenreNameRequest update) {
        Genre genre  = genresService.updateGenre(badName);
        genre.setName(update.getName());
        return genre;
    }

    @DeleteMapping("/{name}")
    public ResponseEntity deleteName(@PathVariable String name) {
        try {
            genresService.deleteGenre(name);
        } catch (GenreNotFoundException e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.accepted().build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void GenreNotFoundExceptionHandler(GenreNotFoundException e) {
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void invalidGenreExceptionHandler(InvalidGenreException e) {
    }
}
