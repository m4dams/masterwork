package com.gfa.library.books.controllers;

import com.gfa.library.books.models.dtos.BookAddDto;
import com.gfa.library.books.models.dtos.BookFullDto;
import com.gfa.library.books.models.dtos.BookSearchDto;
import com.gfa.library.books.models.dtos.BooksDto;
import com.gfa.library.books.services.BookService;
import com.gfa.library.users.models.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class BookController {
  private final BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @PostMapping("/books")
  public ResponseEntity<BookFullDto> addBook(@Valid @RequestBody BookAddDto newBook,
                                             Authentication auth) {
    User user = (User) auth.getPrincipal();
    BookFullDto response = bookService.addBook(user, newBook);
    return ResponseEntity.status(201).body(response);
  }

  @GetMapping("/books/{bookId}")
  public ResponseEntity<BookFullDto> viewBook(@Valid @PathVariable int bookId) {
    BookFullDto response = bookService.viewBook(bookId);
    return ResponseEntity.status(200).body(response);
  }

  @DeleteMapping("/books/{bookId}")
  public ResponseEntity<String> deleteBook(@Valid @PathVariable int bookId, Authentication auth) {
    User user = (User) auth.getPrincipal();
    bookService.deleteBookWithId(user, bookId);
    String response = "Book with id: " + bookId + " was deleted successfully";
    return ResponseEntity.status(200).body(response);
  }

  @GetMapping("/books/")
  public ResponseEntity<BooksDto> searchForBooks(BookSearchDto search) {
    BooksDto response = bookService.searchForBooksWithCriteria(search);
    return ResponseEntity.status(200).body(response);
  }
}
