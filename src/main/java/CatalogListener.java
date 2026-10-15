public interface CatalogListener {
    void onBookIssued(Catalog.IssueRecord record);

    void onBookReturned(Catalog.IssueRecord record);
}
