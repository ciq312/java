package org.example.gems.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Necklace {
    private final String name;
    private final List<Stone> stones = new ArrayList<>();

    public Necklace(String name) {
        this.name = Objects.requireNonNull(name, "name");
    }

    public String getName() {
        return name;
    }

    public List<Stone> getStones() {
        return List.copyOf(stones);
    }

    public void setStones(List<Stone> newStones) {
        List<Stone> copy = List.copyOf(newStones);
        stones.clear();
        stones.addAll(copy);
    }

    public void addStone(Stone stone) {
        stones.add(Objects.requireNonNull(stone, "stone"));
    }

    public int size() {
        return stones.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Necklace necklace = (Necklace) o;
        return name.equals(necklace.name) && stones.equals(necklace.stones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, stones);
    }

    @Override
    public String toString() {
        return "Necklace{name='" + name + "', stones=" + stones + "}";
    }
}
