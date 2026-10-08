package org.example.gems.entity;

import java.util.Objects;

public class Amethyst extends SemiPreciousStone {
    private final ColorIntensity intensity;

    public Amethyst(double caratWeight, double pricePerCarat, double transparency, double hardness,
                    ColorIntensity intensity) {
        super(StoneType.AMETHYST, caratWeight, pricePerCarat, transparency, hardness);
        this.intensity = Objects.requireNonNull(intensity, "intensity");
    }

    public ColorIntensity getIntensity() {
        return intensity;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        Amethyst amethyst = (Amethyst) o;
        return intensity == amethyst.intensity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), intensity);
    }

    @Override
    protected String describeFeatures() {
        return super.describeFeatures() + ", intensity=" + intensity;
    }
}
