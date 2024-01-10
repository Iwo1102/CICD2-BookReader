package ie.atu.bookreader;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReaderRepo extends JpaRepository<BookReader, Long> {

    BookReader findByUsername(String username);
    BookReader findBookReaderById(Long id);
}
