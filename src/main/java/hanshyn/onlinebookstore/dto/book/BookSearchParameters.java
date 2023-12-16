package hanshyn.onlinebookstore.dto.book;

public record BookSearchParameters(
        String[] titles, String[] authors, String[] isbns, String[] descriptions) {
}

