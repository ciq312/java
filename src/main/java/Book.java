public record Book(String title, String author) {
    public Book {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Book title must not be blank");
        }
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Book author must not be blank");
        }
    }
}
