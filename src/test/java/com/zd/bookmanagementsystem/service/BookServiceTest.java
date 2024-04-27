package com.zd.bookmanagementsystem.service;

import com.zd.bookmanagementsystem.model.Book;
import com.zd.bookmanagementsystem.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.zd.bookmanagementsystem.testutil.BookTestData.book1Builder;
import static com.zd.bookmanagementsystem.testutil.BookTestData.book2Builder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void should_return_all_books_when_get_all_books() {
        Book book1 = book1Builder.build();
        Book book2 = book2Builder.build();
        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        List<Book> bookList = bookService.getAllBooks();

        assertEquals(2, bookList.size());
        assertEquals(book1Builder.build(), bookList.get(0));
        assertEquals(book2Builder.build(), bookList.get(1));
    }
}
