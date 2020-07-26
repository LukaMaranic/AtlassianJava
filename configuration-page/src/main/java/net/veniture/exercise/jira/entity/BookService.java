package net.veniture.exercise.jira.entity;

import com.atlassian.activeobjects.tx.Transactional;

@Transactional
public interface BookService {
    Book getBookById(Integer id);

    Book[] getAllBooks();

    Book[] findBooks(String searchParam);

    Book[] getBooks(Integer offset, Integer max);

    Book addBook(String title, String author);

    boolean removeBook(Book book);

}
