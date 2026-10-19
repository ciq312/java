// A seal guarantees that nobody accesses the cargo on the way
public interface Sealable {
    void seal();

    void breakSeal();

    boolean isSealed();
}
