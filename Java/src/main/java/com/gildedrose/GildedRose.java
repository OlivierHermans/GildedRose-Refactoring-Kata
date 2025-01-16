package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            item.sellIn = "Sulfuras, Hand of Ragnaros".equals(item.name) ? item.sellIn : item.sellIn - 1;

            final boolean isExpired = item.sellIn < 0;

            // We can also define a normal quality adjustment of -1 and double it in case of expiry:
            //
            // - In case of Aged Brie, we then multiply with -1 to invert the quality adjustment
            // - In case of Conjured, we then multiply with 2 to double the quality adjustment
            //
            // This keeps Aged Brie and Conjured adjustments "pegged" (i.e. relative) to the normal value adjustment if
            // that is what is required in all circumstances
            //
            // e.g.
            // final int normalAdjustment = isExpired ? -2 : -1;

            switch (item.name) {
//                case "Aged Brie": adjustQuality(item, isExpired ? 2 : 1); break; // or: adjustQuality(item, -1 * normalAdjustment);
                case "Aged Brie": adjustQuality(item, isExpired ? 2 : 1); break; // or: adjustQuality(item, -1 * normalAdjustment);
                case "Backstage passes to a TAFKAL80ETC concert":
                    adjustQuality(item, 1);

                    if (item.sellIn < 10) {
                        adjustQuality(item, 1);
                    }

                    if (item.sellIn < 5) {
                        adjustQuality(item, 1);
                    }

                    if (isExpired) {
                        item.quality = 0;
                    }
                    break;
                case "Sulfuras, Hand of Ragnaros": break;
                case "Conjured Mana Cake": adjustQuality(item, isExpired ? -4 : -2); break; // or: adjustQuality(item, 2 * normalAdjustment);
                default: adjustQuality(item, isExpired ? -2 : -1); break; // or: adjustQuality(item, normalAdjustment);
            }
        }
    }

    static void adjustQuality(Item item, int adjustBy) {
        item.quality = Math.clamp(item.quality + adjustBy, 0, 50);
    }
}
