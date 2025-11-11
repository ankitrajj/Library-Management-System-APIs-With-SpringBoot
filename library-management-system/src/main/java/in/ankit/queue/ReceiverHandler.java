package in.ankit.queue;

import java.util.List;
import java.util.Optional;

import in.ankit.entity.BookEntity;
import in.ankit.entity.TaskEntity;
import in.ankit.entity.UserEntity;
import in.ankit.service.BookService;
import in.ankit.service.TaskService;
import in.ankit.service.UserService;

public class ReceiverHandler {

    private final BookService bookService;
    private final UserService userService;
    private final TaskService taskService;

    public ReceiverHandler(BookService bookService, UserService userService, TaskService taskService) {
        this.bookService = bookService;
        this.userService = userService;
        this.taskService = taskService;
    }

    public void loanBook(TaskEntity loanTask) {
        List<BookEntity> bookList = bookService.getBookEntityByIsbn(loanTask.getIsbn());
        Optional<BookEntity> availableBook =
                bookList.stream().filter(bookEntity -> bookEntity.getBorrower() == null).findFirst();
        Optional<UserEntity> user = userService.findUserEntityById(loanTask.getUserId());

        if (availableBook.isEmpty())
            loanTask.setMessage("Book ISBN " + loanTask.getIsbn() + " is currently not available");
        else if (user.isEmpty())
            loanTask.setMessage("User Id " + loanTask.getUserId() + " not found.");
        else {
            addBookToUser(availableBook.get(), user.get());
            updateEntities(loanTask, availableBook.get(), user.get());
        }
        loanTask.taskComplete();
        taskService.updateTask(loanTask);
    }

    private void updateEntities(TaskEntity loanTask, BookEntity book, UserEntity user) {
        loanTask.setSuccess(true);
        bookService.updateBook(book);
        userService.updateUserEntity(user);
    }

    private void addBookToUser(BookEntity book, UserEntity user) {
        user.addBook(book);
        book.setBorrower(user);
    }

}
