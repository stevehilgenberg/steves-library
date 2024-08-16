package com.galvanize.library;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenresController.class)
public class GenresControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    GenresService genresService;

    ObjectMapper objectMapper = new ObjectMapper();

    // Search for genres:
    //- GET /api/library/genres returns all genres when any genres exist
    @Test
    void getAllGenresReturnsWholeListNoParams() throws Exception {
        List<Genre> genresList = new ArrayList<>();
        genresList.add(new Genre("Horror"));
        genresList.add(new Genre("Mystery"));
        genresList.add(new Genre("Drama"));
        when(genresService.getGenresList())
                .thenReturn(new GenresList(genresList));
        mockMvc.perform(get("/api/library/genre"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genresList", hasSize(3)));
    }

    //- GET /api/library/genre returns 204 when No genres found
    @Test
    void getGenresReturnsNoContentWithNoParams204() throws Exception {
        when(genresService.getGenresList())
                .thenReturn(new GenresList());
        mockMvc.perform(get("/api/library/genre"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    //- GET /api/autos returns/Drama returns 200
    @Test
    void getGenresReturnsWithSearchParam() throws Exception {
        List<Genre> genresList = new ArrayList<>();
        genresList.add(new Genre("Horror"));
        genresList.add(new Genre("Mystery"));
        genresList.add(new Genre("Drama"));
        when(genresService.getGenresList())
                .thenReturn(new GenresList(genresList));
        mockMvc.perform(get("/api/library/genre/Drama"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //- GET /api/library/genre returns 204 when there is no matching Genre name so result is Genre name not found
    @Test
    void getGenresWithGenreNameBadRequestReturns204() throws Exception {
        when(genresService.getGenre(anyString()))
                .thenThrow(GenreNotFoundException.class);
        mockMvc.perform(get("/api/library/genre/Dull"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    //Add a Genre:
    //- POST /api/library/genre returns 200 when Genre Name added successfully
    @Test
    void addGenreNamePostValidReturnsGenre() throws Exception {
        Genre genre = new Genre("Science Fiction");
        when(genresService.addGenre(any(Genre.class)))
                .thenReturn(genre);
        mockMvc.perform(post("/api/library/genre").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genre)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Science Fiction"));
    }

    //- POST /api/library/genre returns 400 when a Bad request is sent (not proper format or data in sent schema)
    @Test
    void addGenreNamePostBadRequestReturn400() throws Exception {
        when(genresService.addGenre(any(Genre.class))).thenThrow(InvalidGenreException.class);
        String json = "{\"name\":\"foreign\"}";
        mockMvc.perform(post("/api/library/genre").contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    //Update Genre Name:
    //- PATCH /api/library/genre/{name} returns 200 when the Genre Name updated successfully
    @Test
    void updateGenreNamePatchWithObjectReturnsGenre() throws Exception {
        Genre genre = new Genre("Artstic");
        when(genresService.updateGenre(anyString())).thenReturn(genre);
        mockMvc.perform(patch("/api/library/genre/"+genre.getName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Artistic\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Artistic"));
    }

    //- PATCH /api/library/genre/{name} returns 204 when there is no matching Genre Name so result is Genre Name not found so it is not updated
    @Test
    void updateGenreNamePatchBadRequestReturns204() throws Exception {
        when(genresService.updateGenre(anyString())).thenThrow(GenreNotFoundException.class);
        mockMvc.perform(patch("/api/library/genre/NOTFOUND")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Artistic\"}"))
                .andExpect(status().isNoContent());
    }

    //- PATCH /api/library/genre/{name}  returns 400 when a Bad request is sent (not proper format or data in sent schema)
    @Test
    void updateAutoPatchBadRequestReturns400() throws Exception {
        when(genresService.updateGenre(anyString())).thenThrow(InvalidGenreException.class);
        mockMvc.perform(patch("/api/library/genre/BADREQUEST")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Autistic\"}"))
                .andExpect(status().isBadRequest());
    }

    //delete a Genre by its name:
    //- DELETE /api/library/genre/{name} returns 202 Genre delete request accepted and auto is deleted
    @Test
    void deleteGenreWithGenreNameExistsReturns202() throws Exception {
        mockMvc.perform(delete("/api/library/genre/Horror"))
                .andExpect(status().isAccepted());
        verify(genresService).deleteGenre(anyString());
    }

    //- DELETE /api/library/genre/{name} returns 204 when there is no matching GenreName so result is GenreName not found so it is not deleted
    @Test
    void deleteGenreWithGenreNameNotExistsReturnsNoContent204() throws Exception {
        doThrow(new GenreNotFoundException()).when(genresService).deleteGenre(anyString());
        mockMvc.perform(delete("/api/library/genre/Love"))
                .andExpect(status().isNoContent());
    }
}
