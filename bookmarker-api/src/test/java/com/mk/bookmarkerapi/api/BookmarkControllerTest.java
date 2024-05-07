package com.mk.bookmarkerapi.api;

import com.mk.bookmarkerapi.domain.Bookmark;
import com.mk.bookmarkerapi.domain.BookmarkRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:tc:postgresql:14-alpine:///demo"
})
class BookmarkControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    BookmarkRepository bookmarkRepository;

    private List<Bookmark> bookmarks;

    @BeforeEach
    void setUp() {
        bookmarkRepository.deleteAllInBatch();
        bookmarks = new ArrayList<>();

        bookmarks.add(new Bookmark(null, "Test 10", "https://localhsot:8080", Instant.now()));
        bookmarks.add(new Bookmark(null, "Test 11", "https://localhsot:8080", Instant.now()));
        bookmarks.add(new Bookmark(null, "Test 12", "https://localhsot:8080", Instant.now()));
        bookmarks.add(new Bookmark(null, "Test 13", "https://localhsot:8080", Instant.now()));
        bookmarks.add(new Bookmark(null, "Test 14", "https://localhsot:8080", Instant.now()));
        bookmarks.add(new Bookmark(null, "Test 15", "https://localhsot:8080", Instant.now()));
        bookmarks.add(new Bookmark(null, "Test 16", "https://localhsot:8080", Instant.now()));
        bookmarks.add(new Bookmark(null, "Test 17", "https://localhsot:8080", Instant.now()));
        bookmarks.add(new Bookmark(null, "Test 18", "https://localhsot:8080", Instant.now()));
        bookmarks.add(new Bookmark(null, "Test 19", "https://localhsot:8080", Instant.now()));
        bookmarks.add(new Bookmark(null, "Test 20", "https://localhsot:8080", Instant.now()));
        bookmarks.add(new Bookmark(null, "Test 21", "https://localhsot:8080", Instant.now()));

        bookmarkRepository.saveAll(bookmarks);
    }

    @ParameterizedTest
    @CsvSource({
            "1,12,2,1,false,true,true,false",
            "2,12,2,2,true,false,false,true",
    })
    void shouldGetBookmarks(int pageNo, int totalElements, int totalPages,
                            int currentPage, boolean isLast, boolean isFirst,
                            boolean hasNext, boolean hasPrevious) throws Exception {
        mvc.perform(get("/api/bookmarks?page=" + pageNo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", CoreMatchers.equalTo(totalElements)))
                .andExpect(jsonPath("$.totalPages", CoreMatchers.equalTo(totalPages)))
                .andExpect(jsonPath("$.currentPage", CoreMatchers.equalTo(currentPage)))
                .andExpect(jsonPath("$.isLast", CoreMatchers.equalTo(isLast)))
                .andExpect(jsonPath("$.isFirst", CoreMatchers.equalTo(isFirst)))
                .andExpect(jsonPath("$.hasNext", CoreMatchers.equalTo(hasNext)))
                .andExpect(jsonPath("$.hasPrevious", CoreMatchers.equalTo(hasPrevious)))
        ;
    }
}