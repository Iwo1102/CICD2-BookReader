package ie.atu.bookreader;

import lombok.Data;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class BookReaderService {
    private final BookReaderRepo bookReaderRepo;

    private int logged = 0;
    private Long userID;

    public BookReaderService(BookReaderRepo bookReaderRepo) {
        this.bookReaderRepo = bookReaderRepo;
    }

    public BookReader getBookKeeperByUsername(String username) {

        return bookReaderRepo.findByUsername(username);
    }

    public List<BookReader> getAllReaders() {
        return (List<BookReader>) bookReaderRepo.findAll();
    }

    public BookReader getBookReader(Long id) {
        BookReader bookReader = bookReaderRepo.findBookReaderById(id);
        return bookReader;
    }

    public Book addOrder(Book book) {
        Book orderedBook = book;
        BookReader user = bookReaderRepo.findBookReaderById(userID);

        orderedBook.setBookAmount(book.getBookAmount() - 1);

        return orderedBook;
    }
}
