package hanshyn.onlinebookstore.mapper;

import hanshyn.onlinebookstore.config.MapperConfig;
import hanshyn.onlinebookstore.dto.book.BookDto;
import hanshyn.onlinebookstore.dto.book.CreateBookRequestDto;
import hanshyn.onlinebookstore.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);
}
