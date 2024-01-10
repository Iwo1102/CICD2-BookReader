package ie.atu.bookreader;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title Cannot be blank")
    private String bookTitle;

    @NotBlank(message = "Author Cannot be blank")
    private String bookAuthor;

    @NotNull(message = "year cannot be blank")
    private int bookYear;

    @Min(value = 0, message = "Amount cannot be less than 0")
    private int bookAmount;

    @NotNull(message = "Age Range must be be entered")
    private int bookAgeRange;
}
