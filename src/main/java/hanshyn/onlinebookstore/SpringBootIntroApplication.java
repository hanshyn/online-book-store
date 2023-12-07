package hanshyn.onlinebookstore;

import hanshyn.onlinebookstore.model.Book;
import hanshyn.onlinebookstore.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootIntroApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootIntroApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book book = new Book();
                book.setAuthor("Robert");
                book.setIsbn("9780132350884");
                book.setDescription("Clean Code is another classic for Java programmers");
                book.setTitle("Clear Code");
                book.setCoverImage("image");
                book.setPrice(BigDecimal.valueOf(25.33));

                bookService.save(book);

                System.out.println(bookService.getAll());
            }
        };
    }
}
