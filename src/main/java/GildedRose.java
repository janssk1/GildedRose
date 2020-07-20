import java.util.LinkedList;
import java.util.List;


public class GildedRose {

    interface QualityFunction {
        /**
         * @return updated quality, taking into account remaining days and current quality
         */
        int getUpdatedQuality(int remainingDays, int currentQuality);
    }

    private static final QualityFunction BRIE = (remainingDays, quality) -> {
        quality++;
        if (remainingDays < 0) {
            quality++;//todo: this behavior is not documented !
        }
        return quality;
    };

    private static final QualityFunction BACKSTAGE = (remainingDays, quality) -> {
        if (remainingDays < 0) {
            return 0;
        } else if (remainingDays < 5) {
            return quality + 3;
        } else if (remainingDays < 10) {
            return quality + 2;
        } else {
            return quality + 1;
        }
    };

    private final List<Item> items = new LinkedList<>();

    public void addItem(Item item) {
        items.add(item);
    }

    private static int getDefaultQualityDegrade(int remaining) {
        return remaining < 0 ? 2 : 1;
    }

    private QualityFunction getQualityUpdater(String name) {
        if (name.equals("Aged Brie")) {
            return BRIE;
        } else if (name.equals("Backstage passes to a TAFKAL80ETC concert")) {
            return BACKSTAGE;
        } else if (name.contains("Conjured")) {
            return (remaining, quality) -> quality - 2 * getDefaultQualityDegrade(remaining);
        } else {
            return (remaining, quality) -> quality - getDefaultQualityDegrade(remaining);
        }
    }

    public void updateQuality() {
        for (Item item : items) {
            if (!"Sulfuras, Hand of Ragnaros".equals(item.getName())) {
                item.sellIn -= 1;
                int newQuality = getQualityUpdater(item.name).getUpdatedQuality(item.sellIn, item.quality);
                item.quality = Math.min(newQuality, 50);
                item.quality = Math.max(0, item.quality);
            }
        }
    }

}