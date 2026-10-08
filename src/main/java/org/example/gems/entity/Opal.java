package org.example.gems.entity;

import java.util.Objects;

public class Opal extends SemiPreciousStone {
    private final boolean playOfColor;

    public Opal(double caratWeight, double pricePerCarat, double transparency, double hardness, boolean playOfColor) {
        super(StoneType.OPAL, caratWeight, pricePerCarat, transparency, hardness);
        this.playOfColor = playOfColor;
    }

    public boolean hasPlayOfColor() {
        return playOfColor;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        Opal opal = (Opal) o;
        return playOfColor == opal.playOfColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), playOfColor);
    }

    @Override
    protected String describeFeatures() {
        return super.describeFeatures() + ", playOfColor=" + playOfColor;
    }
}
