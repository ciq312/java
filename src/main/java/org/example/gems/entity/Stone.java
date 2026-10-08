package org.example.gems.entity;

import java.util.Objects;

public abstract class Stone {
    private final StoneType type;
    private final double caratWeight;
    private final double pricePerCarat;
    private final double transparency;

    protected Stone(StoneType type, double caratWeight, double pricePerCarat, double transparency) {
        this.type = Objects.requireNonNull(type, "type");
        this.caratWeight = caratWeight;
        this.pricePerCarat = pricePerCarat;
        this.transparency = transparency;
    }

    public StoneType getType() {
        return type;
    }

    public double getCaratWeight() {
        return caratWeight;
    }

    public double getPricePerCarat() {
        return pricePerCarat;
    }

    public double getTransparency() {
        return transparency;
    }

    public double getValue() {
        return caratWeight * pricePerCarat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Stone stone = (Stone) o;
        return Double.compare(caratWeight, stone.caratWeight) == 0
                && Double.compare(pricePerCarat, stone.pricePerCarat) == 0
                && Double.compare(transparency, stone.transparency) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, caratWeight, pricePerCarat, transparency);
    }

    @Override
    public String toString() {
        return type + "{carat=" + caratWeight
                + ", pricePerCarat=" + pricePerCarat
                + ", transparency=" + transparency
                + describeFeatures() + "}";
    }

    protected abstract String describeFeatures();
}
