package com.hybridavenger69.mtdisks.items.storage.item;

public enum ItemStorageType {
    TIER_9(102400),
    TIER_10(204800),
    TIER_11(409600),
    TIER_12(819200),
    TIER_13(2048000);


    private final int capacity;
    private final String name;

    ItemStorageType(int capacity) {
        this.name = capacity + "k";
        this.capacity = capacity * 1000;
    }

    public String getName() {
        return this.name;
    }

    public int getCapacity() {
        return this.capacity;
    }
}
