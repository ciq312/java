import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Catalog catalog = new Catalog();

        // Anonymous class: interface has two methods (so no lambda) and the object keeps its own state
        catalog.addListener(new CatalogListener() {
            private int booksOnHand;

            @Override
            public void onBookIssued(Catalog.IssueRecord record) {
                booksOnHand++;
                System.out.println("Issued: " + record + ", books on hand: " + booksOnHand);
            }

            @Override
            public void onBookReturned(Catalog.IssueRecord record) {
                booksOnHand--;
                System.out.println("Returned: " + record + ", books on hand: " + booksOnHand);
            }
        });

        Book warAndPeace = new Book("War and Peace", "Leo Tolstoy");
        Book dune = new Book("Dune", "Frank Herbert");
        catalog.addBook(warAndPeace);
        catalog.addBook(dune);

        Reader alice = new Reader("Alice");
        Reader bob = new Reader("Bob");

        catalog.issueBook(warAndPeace, alice, LocalDate.of(2026, 9, 1));
        catalog.returnBook(warAndPeace, LocalDate.of(2026, 9, 15));
        catalog.issueBook(warAndPeace, bob, LocalDate.of(2026, 9, 20));
        catalog.issueBook(dune, alice, LocalDate.of(2026, 9, 21));

        System.out.println("History of book \"" + warAndPeace.title() + "\":");
        catalog.getBookHistory(warAndPeace).forEach(System.out::println);

        System.out.println("History of reader " + alice.name() + ":");
        catalog.getReaderHistory(alice).forEach(System.out::println);
    }
}
