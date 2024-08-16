package com.galvanize.library;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/library/author")
public class AuthorController {

    AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping()
    public ResponseEntity<AuthorList> getAuthors() {
        AuthorList authorList;
        authorList = authorService.getAuthorList();
        return authorList.isEmpty() ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(authorList);
    }

    @GetMapping("/{name}")
    public ResponseEntity<AuthorList> getAuthor(@PathVariable String name,
                                                @RequestParam int birthYear) {
        try {
            authorService.getAuthor(name, birthYear);
        } catch (AuthorNotFoundException e){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(authorService.getAuthor(name, birthYear));
    }

    @PostMapping()
    public Author addAuthor(@RequestBody Author author) {
        return authorService.addAuthor(author);
    }

    @PatchMapping()
    public Author updateAuthor(@RequestParam String badName,
                               @RequestParam String replace) {
        Author author  = authorService.updateAuthor(badName, replace);
        author.setName(author.getName());
        return author;
    }

    @DeleteMapping("/{name}")
    public ResponseEntity deleteName(@PathVariable String name) {
        try {
            authorService.deleteAuthor(name);
        } catch (AuthorNotFoundException e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.accepted().build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void AuthorNotFoundExceptionHandler(AuthorNotFoundException e) {
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void invalidAuthorExceptionHandler(InvalidAuthorException e) {
    }
}
