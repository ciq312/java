package org.example.gems.entity;

import java.util.Objects;

public class Ruby extends PreciousStone {
    private final boolean heated;

    public Ruby(double caratWeight, double pricePerCarat, double transparency, CutType cut, boolean heated) {
        super(StoneType.RUBY, caratWeight, pricePerCarat, transparency, cut);
        this.heated = heated;
    }

    public boolean isHeated() {
        return heated;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        Ruby ruby = (Ruby) o;
        return heated == ruby.heated;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), heated);
    }

    @Override
    protected String describeFeatures() {
        return super.describeFeatures() + ", heated=" + heated;
    }
}
