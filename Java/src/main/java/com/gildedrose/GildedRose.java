package com.gildedrose;

class GildedRose {

    static final int QUALITY_LOWER_BOUND = 0;
    static final int QUALITY_UPPER_BOUND = 50;

    static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    static final String AGED_BRIE = "Aged Brie";
    static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    static final String CONJURED = "Conjured Mana Cake";

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            // We're not testing the item.name for null as that should be the responsibility of Item which we cannot
            // alter in this exercise.
            item.sellIn = SULFURAS.equals(item.name) ? item.sellIn : item.sellIn - 1;

            final boolean isExpired = item.sellIn < 0;

            // We can also define a normal quality adjustment of -1 and double it in case of expiry:
            //
            // - In case of Aged Brie, we multiply with -1 to invert the quality adjustment
            // - In case of Conjured, we multiply with 2 to double the quality adjustment
            //
            // This keeps Aged Brie and Conjured adjustments "pegged" (i.e. relative) to the normal value adjustment if
            // that is what is required in all circumstances
            //
            // e.g.
            // final int normalAdjustment = isExpired ? -2 : -1;

            switch (item.name) {
                case AGED_BRIE:         adjustQuality(item, isExpired ? 2 : 1); break; // or: adjustQuality(item, -1 * normalAdjustment);
                case BACKSTAGE_PASSES:  adjustBackstagePassesQuality(item, isExpired); break;
                case SULFURAS:          break; // no quality adjustment required here
                case CONJURED:          adjustQuality(item, isExpired ? -4 : -2); break; // or: adjustQuality(item, 2 * normalAdjustment);
                default:                adjustQuality(item, isExpired ? -2 : -1); break; // or: adjustQuality(item, normalAdjustment);
            }
        }
    }

    private static void adjustBackstagePassesQuality(Item item, boolean isExpired) {
        if (isExpired) {
            item.quality = 0;
        } else {
            adjustQuality(item, 1);

            if (item.sellIn < 10) {
                adjustQuality(item, 1);
            }

            if (item.sellIn < 5) {
                adjustQuality(item, 1);
            }
        }
    }

    static void adjustQuality(Item item, int adjustBy) {
        item.quality = Math.clamp(item.quality + adjustBy, QUALITY_LOWER_BOUND, QUALITY_UPPER_BOUND);
    }
}
