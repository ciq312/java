public record Reader(String name) {
    public Reader {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Reader name must not be blank");
        }
    }
}
