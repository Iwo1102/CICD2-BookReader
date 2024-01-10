package ie.atu.bookreader;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "Book-service", url = "http://localhost:8081")
public interface BookServiceClient {

    @GetMapping("/getBook/{title}")
    Book getBook(@PathVariable String title);

    @DeleteMapping ("/deleteBook/{id}")
    String deleteBookFromRepo(@PathVariable Long id);

    @PostMapping("/saveBook")
    String addBookToRepo(@RequestBody Book book);

    @PutMapping("/updateBook/{title}")
    String editBookInRepo(@PathVariable String title, @RequestBody Book book);
}
