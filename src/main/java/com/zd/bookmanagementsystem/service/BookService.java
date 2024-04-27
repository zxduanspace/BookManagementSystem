package com.zd.bookmanagementsystem.service;

import com.zd.bookmanagementsystem.model.Book;
import com.zd.bookmanagementsystem.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book createBook(Book bookRequest) {
        return bookRepository.save(bookRequest);
    }

    public Book updateBook(Long id, Book bookRequest) {
        Book existBook = bookRepository.findById(id).orElse(null);
        if (existBook != null) {
            existBook.setTitle(bookRequest.getTitle());
            existBook.setAuthor(bookRequest.getAuthor());
            existBook.setPublicationYear(bookRequest.getPublicationYear());
            existBook.setIsbn(bookRequest.getIsbn());
            bookRepository.save(existBook);
        }
        return existBook;
    }

    public void deleteBook(Long id) {
        boolean existsById = bookRepository.existsById(id);
        if (existsById) {
            bookRepository.deleteById(id);
        }
    }
}
