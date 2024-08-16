package com.galvanize.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookController.class)
public class BookControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookService bookService;


}
