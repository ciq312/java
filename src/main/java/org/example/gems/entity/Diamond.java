package org.example.gems.entity;

import java.util.Objects;

public class Diamond extends PreciousStone {
    private final DiamondColor color;

    public Diamond(double caratWeight, double pricePerCarat, double transparency, CutType cut, DiamondColor color) {
        super(StoneType.DIAMOND, caratWeight, pricePerCarat, transparency, cut);
        this.color = Objects.requireNonNull(color, "color");
    }

    public DiamondColor getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        Diamond diamond = (Diamond) o;
        return color == diamond.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), color);
    }

    @Override
    protected String describeFeatures() {
        return super.describeFeatures() + ", color=" + color;
    }
}
