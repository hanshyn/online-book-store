package hanshyn.onlinebookstore.dto;

public record BookSearchParameters(
        String[] titles, String[] authors, String[] isbns, String[] descriptions) {
}

