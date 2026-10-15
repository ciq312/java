import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CatalogTest {
    private Catalog catalog;
    private Book dune;
    private Reader alice;
    private Reader bob;

    @BeforeEach
    public void setUp() {
        catalog = new Catalog();
        dune = new Book("Dune", "Frank Herbert");
        alice = new Reader("Alice");
        bob = new Reader("Bob");
        catalog.addBook(dune);
    }

    @Test
    public void addBook_ShouldRejectDuplicate() {
        assertThrows(IllegalArgumentException.class, () -> catalog.addBook(new Book("Dune", "Frank Herbert")));
        assertEquals(List.of(dune), catalog.getBooks());
    }

    @Test
    public void issueBook_ShouldCreateActiveRecord() {
        var record = catalog.issueBook(dune, alice, LocalDate.of(2026, 9, 1));

        assertSame(dune, record.getBook());
        assertSame(alice, record.getReader());
        assertEquals(LocalDate.of(2026, 9, 1), record.getIssueDate());
        assertFalse(record.isReturned());
        assertEquals(Optional.empty(), record.getReturnDate());
        assertFalse(catalog.isAvailable(dune));
    }

    @Test
    public void issueBook_ShouldRejectAlreadyIssuedBook() {
        catalog.issueBook(dune, alice, LocalDate.of(2026, 9, 1));

        assertThrows(IllegalStateException.class, () -> catalog.issueBook(dune, bob, LocalDate.of(2026, 9, 2)));
        assertEquals(1, catalog.getBookHistory(dune).size());
    }

    @Test
    public void issueBook_ShouldRejectUnknownBook() {
        var unknown = new Book("Solaris", "Stanislaw Lem");

        assertThrows(IllegalArgumentException.class, () -> catalog.issueBook(unknown, alice, LocalDate.of(2026, 9, 1)));
    }

    @Test
    public void issueBook_ShouldRejectDateBeforePreviousReturn() {
        catalog.issueBook(dune, alice, LocalDate.of(2026, 9, 1));
        catalog.returnBook(dune, LocalDate.of(2026, 9, 10));

        assertThrows(IllegalArgumentException.class, () -> catalog.issueBook(dune, bob, LocalDate.of(2026, 9, 5)));
        assertTrue(catalog.isAvailable(dune));
    }

    @Test
    public void returnBook_ShouldCloseActiveRecord() {
        var issued = catalog.issueBook(dune, alice, LocalDate.of(2026, 9, 1));

        var returned = catalog.returnBook(dune, LocalDate.of(2026, 9, 10));

        assertSame(issued, returned);
        assertTrue(returned.isReturned());
        assertEquals(Optional.of(LocalDate.of(2026, 9, 10)), returned.getReturnDate());
        assertTrue(catalog.isAvailable(dune));
    }

    @Test
    public void returnBook_ShouldRejectNotIssuedBook() {
        assertThrows(IllegalStateException.class, () -> catalog.returnBook(dune, LocalDate.of(2026, 9, 1)));
    }

    @Test
    public void returnBook_ShouldRejectDateBeforeIssue() {
        var record = catalog.issueBook(dune, alice, LocalDate.of(2026, 9, 10));

        assertThrows(IllegalArgumentException.class, () -> catalog.returnBook(dune, LocalDate.of(2026, 9, 1)));
        assertFalse(record.isReturned());
    }

    @Test
    public void getBookHistory_ShouldKeepAllIssuesInOrder() {
        catalog.issueBook(dune, alice, LocalDate.of(2026, 9, 1));
        catalog.returnBook(dune, LocalDate.of(2026, 9, 10));
        catalog.issueBook(dune, bob, LocalDate.of(2026, 9, 10));

        var history = catalog.getBookHistory(dune);

        assertEquals(2, history.size());
        assertEquals(alice, history.get(0).getReader());
        assertTrue(history.get(0).isReturned());
        assertEquals(bob, history.get(1).getReader());
        assertFalse(history.get(1).isReturned());
    }

    @Test
    public void getBookHistory_ShouldBeReadOnly() {
        var history = catalog.getBookHistory(dune);

        assertThrows(UnsupportedOperationException.class, history::clear);
    }

    @Test
    public void getReaderHistory_ShouldReturnRecordsOfReaderAcrossBooks() {
        var solaris = new Book("Solaris", "Stanislaw Lem");
        catalog.addBook(solaris);
        catalog.issueBook(dune, alice, LocalDate.of(2026, 9, 1));
        catalog.issueBook(solaris, bob, LocalDate.of(2026, 9, 2));
        catalog.returnBook(dune, LocalDate.of(2026, 9, 5));
        catalog.issueBook(dune, bob, LocalDate.of(2026, 9, 6));
        catalog.returnBook(solaris, LocalDate.of(2026, 9, 7));
        catalog.issueBook(solaris, alice, LocalDate.of(2026, 9, 8));

        var history = catalog.getReaderHistory(alice);

        assertEquals(2, history.size());
        assertEquals(dune, history.get(0).getBook());
        assertEquals(solaris, history.get(1).getBook());
    }

    @Test
    public void issueRecord_ShouldBeNumberedPerCatalog() {
        var otherCatalog = new Catalog();
        otherCatalog.addBook(dune);

        var first = catalog.issueBook(dune, alice, LocalDate.of(2026, 9, 1));
        catalog.returnBook(dune, LocalDate.of(2026, 9, 2));
        var second = catalog.issueBook(dune, bob, LocalDate.of(2026, 9, 3));
        var otherFirst = otherCatalog.issueBook(dune, bob, LocalDate.of(2026, 9, 1));

        assertEquals(1, first.getId());
        assertEquals(2, second.getId());
        assertEquals(1, otherFirst.getId());
    }

    @Test
    public void addListener_ShouldNotifyAboutSuccessfulIssueAndReturn() {
        List<String> events = new ArrayList<>();
        catalog.addListener(new CatalogListener() {
            @Override
            public void onBookIssued(Catalog.IssueRecord record) {
                events.add("issued #" + record.getId() + " to " + record.getReader().name());
            }

            @Override
            public void onBookReturned(Catalog.IssueRecord record) {
                events.add("returned #" + record.getId());
            }
        });

        catalog.issueBook(dune, alice, LocalDate.of(2026, 9, 1));
        assertThrows(IllegalStateException.class, () -> catalog.issueBook(dune, bob, LocalDate.of(2026, 9, 2)));
        catalog.returnBook(dune, LocalDate.of(2026, 9, 10));

        assertEquals(List.of("issued #1 to Alice", "returned #1"), events);
    }

    @Test
    public void book_ShouldRejectBlankTitle() {
        assertThrows(IllegalArgumentException.class, () -> new Book(" ", "Frank Herbert"));
    }

    @Test
    public void reader_ShouldRejectBlankName() {
        assertThrows(IllegalArgumentException.class, () -> new Reader(""));
    }
}
