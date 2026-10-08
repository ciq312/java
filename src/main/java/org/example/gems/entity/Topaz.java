package org.example.gems.entity;

import java.util.Objects;

public class Topaz extends SemiPreciousStone {
    private final TopazColor color;

    public Topaz(double caratWeight, double pricePerCarat, double transparency, double hardness, TopazColor color) {
        super(StoneType.TOPAZ, caratWeight, pricePerCarat, transparency, hardness);
        this.color = Objects.requireNonNull(color, "color");
    }

    public TopazColor getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        Topaz topaz = (Topaz) o;
        return color == topaz.color;
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
