package org.example.gems.entity;

import java.util.Objects;

public class Emerald extends PreciousStone {
    private final String origin;

    public Emerald(double caratWeight, double pricePerCarat, double transparency, CutType cut, String origin) {
        super(StoneType.EMERALD, caratWeight, pricePerCarat, transparency, cut);
        this.origin = Objects.requireNonNull(origin, "origin");
    }

    public String getOrigin() {
        return origin;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        Emerald emerald = (Emerald) o;
        return origin.equals(emerald.origin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), origin);
    }

    @Override
    protected String describeFeatures() {
        return super.describeFeatures() + ", origin=" + origin;
    }
}
