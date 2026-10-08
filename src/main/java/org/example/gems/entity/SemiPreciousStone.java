package org.example.gems.entity;

import java.util.Objects;

public abstract class SemiPreciousStone extends Stone {
    public static final double MIN_HARDNESS = 1;
    public static final double MAX_HARDNESS = 10;

    private final double hardness;

    protected SemiPreciousStone(StoneType type, double caratWeight, double pricePerCarat, double transparency,
                                double hardness) {
        super(type, caratWeight, pricePerCarat, transparency);
        this.hardness = hardness;
    }

    public double getHardness() {
        return hardness;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        SemiPreciousStone that = (SemiPreciousStone) o;
        return Double.compare(hardness, that.hardness) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), hardness);
    }

    @Override
    protected String describeFeatures() {
        return ", hardness=" + hardness;
    }
}
