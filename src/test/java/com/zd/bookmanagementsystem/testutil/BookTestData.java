package com.zd.bookmanagementsystem.testutil;

import com.zd.bookmanagementsystem.model.Book;

public class BookTestData {

    public static Book.BookBuilder book1Builder = Book.builder()
            .id(1L)
            .title("title1")
            .author("author1")
            .publicationYear("2001")
            .isbn("123-456");

    public static Book.BookBuilder book2Builder = Book.builder()
            .id(2L)
            .title("title2")
            .author("author2")
            .publicationYear("2002")
            .isbn("123-789");
}
