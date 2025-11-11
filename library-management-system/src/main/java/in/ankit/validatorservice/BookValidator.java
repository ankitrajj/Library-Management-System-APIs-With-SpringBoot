package in.ankit.validatorservice;



import java.util.List;

import in.ankit.dto.Book;
import in.ankit.dto.Isbn;
import in.ankit.entity.BookEntity;
import in.ankit.entity.UserEntity;
import in.ankit.exceptions.IdNotFoundException;
import in.ankit.exceptions.InvalidInputException;
import in.ankit.exceptions.ValueNotFoundException;
import in.ankit.service.BookService;

public class BookValidator extends LibraryValidator {

    BookService bookService;

    public BookValidator(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void validId(Long id) {
        if (id == null || id < 1L)
            throw new InvalidInputException(id + " is an invalid id.", "/books/");
    }

    @Override
    public void idExists(Long id) {
        bookService.findById(id).orElseThrow(() -> new IdNotFoundException("book", id));
    }

    public void hasUser(BookEntity book, UserEntity user) {
        if (book.getBorrower() == null || !book.getBorrower().equals(user)) {
            String message = "User Id " + user.getId() + " does not have a book with Id " + book.getId();
            String path = "users/" + user.getId() + "/books/" + book.getId();
            throw new ValueNotFoundException(message, path);
        }
    }

    public void isbnExists(Isbn isbn) {
        List<Book> books = bookService.getBooksByIsbn(isbn.getIsbn());

        if(books.isEmpty()) {
            String message = "Book with ISBN " + isbn.getIsbn() + " does not exist.";
            String path = "books?isbn=" + isbn.getIsbn();
            throw new InvalidInputException(message, path);
        }
    }

}
