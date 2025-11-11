package in.ankit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.ankit.dto.Book;
import in.ankit.dto.Isbn;
import in.ankit.dto.Task;
import in.ankit.entity.BookEntity;
import in.ankit.entity.TaskEntity;
import in.ankit.entity.UserEntity;
import in.ankit.exceptions.IdNotFoundException;
import in.ankit.queue.QueueHandler;
import in.ankit.service.BookService;
import in.ankit.service.TaskService;
import in.ankit.service.UserService;
import in.ankit.validatorservice.BookValidator;
import in.ankit.validatorservice.UserValidator;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserBookController {

    private final UserService userService;
    private final UserValidator userValidator;
    private final BookValidator bookValidator;
    private final BookService bookService;
    private final TaskService taskService;
    private final QueueHandler queueHandler;

    public UserBookController(UserService userService, UserValidator userValidator, BookValidator bookValidator,
                              BookService bookService, TaskService taskService, QueueHandler queueHandler) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.bookValidator = bookValidator;
        this.bookService = bookService;
        this.taskService = taskService;
        this.queueHandler = queueHandler;
    }

    @GetMapping("{id}/books")
    public ResponseEntity<List<Book>> getAUsersBooks(@PathVariable Long id) {
        List<Book> books = userService.getUserBooks(id);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @DeleteMapping("{userId}/books/{bookId}")
    public ResponseEntity<Void> removeBookUserConnection(@PathVariable Long userId, @PathVariable Long bookId) {
        UserEntity user = userService.findUserEntityById(userId).orElseThrow(() -> new IdNotFoundException("user", userId));
        BookEntity book = bookService.findById(bookId).orElseThrow(() -> new IdNotFoundException("book", bookId));
        bookValidator.hasUser(book, user);
        book.removeBorrower();
        bookService.updateBook(book);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("{id}/books")
    public ResponseEntity<Task> addBookToUser(@PathVariable Long id, @RequestBody Isbn isbn) {
        userValidator.validId(id);
        userValidator.idExists(id);
        bookValidator.isbnExists(isbn);

        TaskEntity loanTask = new TaskEntity(isbn.getIsbn(), id);
        TaskEntity savedTask = taskService.createTask(loanTask);
        Task task = queueHandler.sendToQueue(savedTask);

        return new ResponseEntity<>(task, HttpStatus.ACCEPTED);
    }

}
