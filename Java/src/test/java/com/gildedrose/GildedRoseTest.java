package com.gildedrose;

import org.junit.jupiter.api.Test;

import static com.gildedrose.GildedRose.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void updateQuality_agedBrieNotExpired_qualityIncreasedByOne() {
        final GildedRose app = buildApp(new Item(AGED_BRIE, 2, 2));

        app.updateQuality();

        assertEquals(1, app.items[0].sellIn);
        assertEquals(3, app.items[0].quality);
    }

    @Test
    void updateQuality_agedBrieExpired_qualityIncreasedByTwo() {
        final GildedRose app = buildApp(new Item(AGED_BRIE, -1, 2));

        app.updateQuality();

        assertEquals(-2, app.items[0].sellIn);
        assertEquals(4, app.items[0].quality);
    }

    @Test
    void  updateQuality_backstagePassesMoreThan10DaysLeft_qualityIncreasedByOne() {
        final GildedRose app = buildApp(new Item(BACKSTAGE_PASSES, 15, 2));

        app.updateQuality();

        assertEquals(14, app.items[0].sellIn);
        assertEquals(3, app.items[0].quality);
    }

    @Test
    void  updateQuality_backstagePassesMoreThan5AndLessThanIncluding10DaysLeft_qualityIncreasedByTwo() {
        final GildedRose app = buildApp(new Item(BACKSTAGE_PASSES, 9, 2));

        app.updateQuality();

        assertEquals(8, app.items[0].sellIn);
        assertEquals(4, app.items[0].quality);
    }

    @Test
    void  updateQuality_backstagePassesLessThanIncluding5DaysLeft_qualityIncreasedByThree() {
        final GildedRose app = buildApp(new Item(BACKSTAGE_PASSES, 4, 2));

        app.updateQuality();

        assertEquals(3, app.items[0].sellIn);
        assertEquals(5, app.items[0].quality);
    }

    @Test
    void  updateQuality_backstagePassesExpired_quality0() {
        final GildedRose app = buildApp(new Item(BACKSTAGE_PASSES, -1, 2));

        app.updateQuality();

        assertEquals(-2, app.items[0].sellIn);
        assertEquals(0, app.items[0].quality);
    }

    @Test
    void updateQuality_sulfuras_shouldNotChange() {
        final GildedRose app = buildApp(new Item(SULFURAS, 10, 80));

        app.updateQuality();

        assertEquals(10, app.items[0].sellIn);
        assertEquals(80, app.items[0].quality);
    }

    @Test
    void updateQuality_conjuredNotExpired_qualityDecreasedBy2() {
        final GildedRose app = buildApp(new Item(CONJURED, 10, 10));

        app.updateQuality();

        assertEquals(9, app.items[0].sellIn);
        assertEquals(8, app.items[0].quality);
    }

    @Test
    void updateQuality_conjuredExpired_qualityDecreasedBy4() {
        final GildedRose app = buildApp(new Item(CONJURED, 0, 10));

        app.updateQuality();

        assertEquals(-1, app.items[0].sellIn);
        assertEquals(6, app.items[0].quality);
    }

    @Test
    void updateQuality_otherNotExpired_qualityDecreasedBy1() {
        final GildedRose app = buildApp(new Item("Some other item", 10, 10));

        app.updateQuality();

        assertEquals(9, app.items[0].sellIn);
        assertEquals(9, app.items[0].quality);
    }

    @Test
    void updateQuality_otherExpired_qualityDecreasedBy2() {
        final GildedRose app = buildApp(new Item("Yet another item", 0, 10));

        app.updateQuality();

        assertEquals(-1, app.items[0].sellIn);
        assertEquals(8, app.items[0].quality);
    }

    @Test
    void adjustQuality_upperBoundRespected() {
        final Item item = new Item("test", 2, QUALITY_UPPER_BOUND - 1);
        GildedRose.adjustQuality(item, 2);
        assertEquals(QUALITY_UPPER_BOUND, item.quality);
    }

    @Test
    void adjustQuality_lowerBoundRespected() {
        final Item item = new Item("test", 2, QUALITY_LOWER_BOUND + 1);
        GildedRose.adjustQuality(item, -2);
        assertEquals(QUALITY_LOWER_BOUND, item.quality);
    }

    private GildedRose buildApp(Item... items) {
        return new GildedRose(items);
    }
}
