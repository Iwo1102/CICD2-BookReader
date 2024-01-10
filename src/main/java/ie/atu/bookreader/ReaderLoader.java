package ie.atu.bookreader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ReaderLoader implements CommandLineRunner {

    private final BookReaderRepo bookReaderRepo;

    @Autowired
    public ReaderLoader(BookReaderRepo bookReaderRepo) {
        this.bookReaderRepo = bookReaderRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        BookReader reader1 = new BookReader(1L, "User1", "user123", "UserReader@keeper.com", 18, null);

        bookReaderRepo.save(reader1);
    }
}
