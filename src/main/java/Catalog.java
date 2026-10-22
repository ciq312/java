import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Catalog {
    private static final Logger logger = LogManager.getLogger(Catalog.class);

    private final Map<Book, List<IssueRecord>> historyByBook = new LinkedHashMap<>();
    private final List<CatalogListener> listeners = new ArrayList<>();
    private int lastRecordId;

    public void addListener(CatalogListener listener) {
        listeners.add(Objects.requireNonNull(listener, "listener"));
        logger.debug("Listener added: {}", listener);
    }

    public void addBook(Book book) {
        Objects.requireNonNull(book, "book");
        if (historyByBook.putIfAbsent(book, new ArrayList<>()) != null) {
            logger.warn("Book is already in catalog: {}", book);
            throw new IllegalArgumentException("Book is already in catalog: " + book);
        }
        logger.info("Book added: {}", book);
    }

    public List<Book> getBooks() {
        return List.copyOf(historyByBook.keySet());
    }

    public IssueRecord issueBook(Book book, Reader reader, LocalDate issueDate) {
        Objects.requireNonNull(reader, "reader");
        Objects.requireNonNull(issueDate, "issueDate");
        List<IssueRecord> history = historyOf(book);

        Optional<IssueRecord> lastRecord = history.isEmpty() ? Optional.empty() : Optional.of(history.getLast());
        if (lastRecord.isPresent() && !lastRecord.get().isReturned()) {
            logger.warn("Book {} is already issued to {}", book, lastRecord.get().getReader());
            throw new IllegalStateException("Book is already issued: " + book);
        }
        if (lastRecord.isPresent() && issueDate.isBefore(lastRecord.get().returnDate)) {
            logger.warn("Issue date {} is before last return date {} of book {}", issueDate, lastRecord.get().returnDate, book);
            throw new IllegalArgumentException("Issue date is before last return date of book: " + book);
        }

        IssueRecord record = new IssueRecord(book, reader, issueDate);
        history.add(record);
        logger.info("Book {} issued to {} on {} (record #{})", book, reader, issueDate, record.getId());
        listeners.forEach(listener -> listener.onBookIssued(record));
        return record;
    }

    public IssueRecord returnBook(Book book, LocalDate returnDate) {
        Objects.requireNonNull(returnDate, "returnDate");
        IssueRecord record = findActiveRecord(book).orElseThrow(() -> {
            logger.warn("Book {} is not issued, cannot return it", book);
            return new IllegalStateException("Book is not issued: " + book);
        });

        record.markReturned(returnDate);
        logger.info("Book {} returned by {} on {} (record #{})", book, record.getReader(), returnDate, record.getId());
        listeners.forEach(listener -> listener.onBookReturned(record));
        return record;
    }

    public boolean isAvailable(Book book) {
        return findActiveRecord(book).isEmpty();
    }

    public List<IssueRecord> getBookHistory(Book book) {
        List<IssueRecord> history = List.copyOf(historyOf(book));
        logger.debug("History of book {}: {} record(s)", book, history.size());
        return history;
    }

    public List<IssueRecord> getReaderHistory(Reader reader) {
        Objects.requireNonNull(reader, "reader");
        List<IssueRecord> history = historyByBook.values().stream()
                .flatMap(List::stream)
                .filter(record -> record.getReader().equals(reader))
                .sorted(Comparator.comparingInt(IssueRecord::getId))
                .toList();
        logger.debug("History of reader {}: {} record(s)", reader, history.size());
        return history;
    }

    private Optional<IssueRecord> findActiveRecord(Book book) {
        List<IssueRecord> history = historyOf(book);
        if (history.isEmpty() || history.getLast().isReturned()) {
            return Optional.empty();
        }
        return Optional.of(history.getLast());
    }

    private List<IssueRecord> historyOf(Book book) {
        Objects.requireNonNull(book, "book");
        List<IssueRecord> history = historyByBook.get(book);
        if (history == null) {
            logger.warn("Book not found in catalog: {}", book);
            throw new IllegalArgumentException("Book not found in catalog: " + book);
        }
        return history;
    }

    public class IssueRecord {
        private final int id;
        private final Book book;
        private final Reader reader;
        private final LocalDate issueDate;
        private LocalDate returnDate;

        private IssueRecord(Book book, Reader reader, LocalDate issueDate) {
            this.id = ++lastRecordId;
            this.book = book;
            this.reader = reader;
            this.issueDate = issueDate;
        }

        public int getId() {
            return id;
        }

        public Book getBook() {
            return book;
        }

        public Reader getReader() {
            return reader;
        }

        public LocalDate getIssueDate() {
            return issueDate;
        }

        public Optional<LocalDate> getReturnDate() {
            return Optional.ofNullable(returnDate);
        }

        public boolean isReturned() {
            return returnDate != null;
        }

        private void markReturned(LocalDate returnDate) {
            if (returnDate.isBefore(issueDate)) {
                logger.warn("Return date {} is before issue date {} (record #{})", returnDate, issueDate, id);
                throw new IllegalArgumentException("Return date is before issue date: " + returnDate);
            }
            this.returnDate = returnDate;
        }

        @Override
        public String toString() {
            return "#" + id + " " + book.title() + " (" + book.author() + ") -> " + reader.name()
                    + " [" + issueDate + " - " + (returnDate == null ? "not returned" : returnDate) + "]";
        }
    }
}
