package com.galvanize.library;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorList getAuthorList() {
        return new AuthorList(authorRepository.findAll());
    }

    public AuthorList getAuthor(String name, int birthYear) {
        List<Author> authorList = authorRepository.findByNameAndBirthYear(name, birthYear);
        if(!authorList.isEmpty()) {
            return new AuthorList(authorList);
        }
        return null;
    }

    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    public void deleteAuthor(String name) {
        Optional<Author> optAuthor = authorRepository.findByName(name);
        if(optAuthor.isPresent()) {
            authorRepository.delete(optAuthor.get());
        } else {
            throw new AuthorNotFoundException();
        }
    }

    public Author updateAuthor(String name, String replace) {
        Optional<Author> optAuthor = authorRepository.findByName(name);
        if(optAuthor.isPresent()) {
            optAuthor.get().setName(replace);
            return authorRepository.save(optAuthor.get());
        }
        return null;
    }
}
