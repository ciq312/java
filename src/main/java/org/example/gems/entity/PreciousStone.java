package org.example.gems.entity;

import java.util.Objects;

public abstract class PreciousStone extends Stone {
    private final CutType cut;

    protected PreciousStone(StoneType type, double caratWeight, double pricePerCarat, double transparency,
                            CutType cut) {
        super(type, caratWeight, pricePerCarat, transparency);
        this.cut = Objects.requireNonNull(cut, "cut");
    }

    public CutType getCut() {
        return cut;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        PreciousStone that = (PreciousStone) o;
        return cut == that.cut;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cut);
    }

    @Override
    protected String describeFeatures() {
        return ", cut=" + cut;
    }
}
