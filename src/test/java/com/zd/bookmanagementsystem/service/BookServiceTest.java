package com.zd.bookmanagementsystem.service;

import com.zd.bookmanagementsystem.model.Book;
import com.zd.bookmanagementsystem.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.zd.bookmanagementsystem.testutil.BookTestData.book1Builder;
import static com.zd.bookmanagementsystem.testutil.BookTestData.book2Builder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
        assertEquals(book1, bookList.get(0));
        assertEquals(book2, bookList.get(1));
    }

    @Test
    public void should_return_null_when_get_book_by_id_given_non_exist_id() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Book actualBook = bookService.getBookById(1L);

        assertNull(actualBook);
    }

    @Test
    public void should_return_book_when_get_book_by_id_given_exist_id() {
        Book savedBook = book1Builder.build();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(savedBook));

        Book actualBook = bookService.getBookById(1L);

        assertEquals(savedBook, actualBook);
    }

    @Test
    public void should_save_book_success_when_create_book() {
        Book bookRequest = book1Builder.id(null).build();
        Book bookResponse = book1Builder.id(1L).build();
        when(bookRepository.save(bookRequest)).thenReturn(bookResponse);

        Book actualBook = bookService.createBook(bookRequest);

        verify(bookRepository).save(bookRequest);
        assertEquals(bookResponse, actualBook);
    }

    @Test
    public void should_return_null_when_update_book_given_non_exist_id() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        Book bookRequest = book2Builder.build();

        Book actualBook = bookService.updateBook(1L, bookRequest);

        verify(bookRepository, times(0)).save(bookRequest);
        assertNull(actualBook);
    }

    @Test
    public void should_update_book_success_when_update_book_given_exist_id() {
        Book savedBook = book1Builder.build();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(savedBook));
        Book bookRequest = Book.builder()
                .id(1L)
                .title("new-title")
                .author("new-author")
                .publicationYear("2024")
                .isbn("456-789")
                .build();

        when(bookRepository.save(bookRequest)).thenReturn(bookRequest);

        Book actualBook = bookService.updateBook(1L, bookRequest);

        verify(bookRepository).save(bookRequest);
        assertEquals(bookRequest, actualBook);
    }

    @Test
    public void should_do_nothing_when_delete_book_given_non_exist_id() {
        when(bookRepository.existsById(1L)).thenReturn(false);

        bookService.deleteBook(1L);

        verify(bookRepository, times(0)).deleteById(1L);
    }

    @Test
    public void should_delete_book_success_when_delete_book_given_exist_id() {
        when(bookRepository.existsById(1L)).thenReturn(true);

        bookService.deleteBook(1L);

        verify(bookRepository).deleteById(1L);
    }
}
