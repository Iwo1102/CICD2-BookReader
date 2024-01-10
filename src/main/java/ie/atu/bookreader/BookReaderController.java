package ie.atu.bookreader;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class BookReaderController {

    private final BookReaderService bookReaderService;
    private final BookServiceClient bookServiceClient;

    @Autowired
    public BookReaderController(BookReaderService bookReaderService, BookServiceClient bookServiceClient) {
        this.bookReaderService = bookReaderService;
        this.bookServiceClient = bookServiceClient;
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, String password) {
        if(bookReaderService.getLogged() == 1) {
            return ResponseEntity.badRequest().body("Already Logged in");
        }
        if (username == null || password == null) {
            return ResponseEntity.badRequest().body("Missing username or password");
        }
        BookReader user = bookReaderService.getBookKeeperByUsername(username);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        if (password.equals(user.getPassword())) {
            bookReaderService.setLogged(1);
            bookReaderService.setUserID(user.getId());
            return ResponseEntity.ok("Logged in Successfully as " + username);
        } else {
            return ResponseEntity.badRequest().body("Incorrect password");
        }

    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        if(bookReaderService.getLogged() == 0) {
            return ResponseEntity.badRequest().body("Already logged out");
        }
        bookReaderService.setLogged(0);
        return ResponseEntity.ok("Logged out Successfully");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody BookReader bookReader) {
        if(bookReaderService.getLogged() == 1) {
            return ResponseEntity.badRequest().body("Already logged in");
        }
        List<BookReader> userList;
        userList = bookReaderService.getAllReaders();

        for (BookReader currentUserList : userList) {
            if (currentUserList.getUsername().equals(bookReader.getUsername())) {
                return ResponseEntity.badRequest().body("UserName Already Exists");
            }
            if (currentUserList.getEmail().equals(bookReader.getEmail())) {
                return ResponseEntity.badRequest().body("Email Already Used");
            }
        }
        bookReaderService.saveReader(bookReader);
        bookReaderService.setLogged(1);
        bookReaderService.setUserID(bookReader.getId());
        return ResponseEntity.ok("Signed up successfully");

    }

   /* @GetMapping("/getBook/{title}")
    Book getBook(@PathVariable String title) {
        Book book = new Book();
        if (bookReaderService.getLogged() != 1) {
            book.setBookTitle("Not logged in");
        } else {
            book = bookServiceClient.getBook(title);
        }
        return book;
    }

    @PostMapping("/addBook")
    ResponseEntity<?> saveBook(@Valid @RequestBody Book book) {
        if (bookReaderService.getLogged() != 1) {
            return ResponseEntity.badRequest().body("Not logged in");
        }
        bookServiceClient.addBookToRepo(book);
        return ResponseEntity.ok("Book Added");
    }


    @DeleteMapping ("/removeBook/{id}")
    ResponseEntity<?> removeBook(@PathVariable Long id) {
        if (bookReaderService.getLogged() != 1) {
            return ResponseEntity.badRequest().body("Not logged in");
        }
        bookServiceClient.deleteBookFromRepo(id);
        return ResponseEntity.ok("Book " + id + " Removed");
    }

    @PutMapping("/editBook/{title}")
    ResponseEntity<?> editBook(@PathVariable String title, @RequestBody Book book) {
        if (bookReaderService.getLogged() != 1) {
            return ResponseEntity.badRequest().body("Not logged in");
        }
        bookServiceClient.editBookInRepo(title, book);
        return ResponseEntity.ok("Book Edited");
    }*/

    @PutMapping("/orderBook/{title}")
    public ResponseEntity<?> orderBook(@PathVariable String title) {
        if (bookReaderService.getLogged() != 1) {
            return ResponseEntity.badRequest().body("Not logged in");
        }
        BookReader currentReader = bookReaderService.getBookReader(bookReaderService.getUserID());

        if (currentReader.getAge() < bookServiceClient.getBook(title).getBookAgeRange()) {
            return ResponseEntity.badRequest().body("Not old enough to order book");
        }
        Book updatedBook = bookReaderService.addOrder(bookServiceClient.getBook(title));
        bookServiceClient.editBookInRepo(updatedBook.getBookTitle(), updatedBook);
        return ResponseEntity.ok("Order added");
    }

}
