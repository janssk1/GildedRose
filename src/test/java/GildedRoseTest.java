import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertThat;


@RunWith(Parameterized.class)
public class GildedRoseTest {

	private final GildedRose unit = new GildedRose();
	private final Item item;
	private final int nextSellIn;
	private final int nextQuality;

	public GildedRoseTest(String item, int sellIn, int quality, int nextSellIn, int nextQuality) {
		this.nextSellIn = nextSellIn;
		this.nextQuality = nextQuality;

		this.item = new Item(item, sellIn, quality);
		unit.addItem(this.item);
	}

	@Parameterized.Parameters(name = "{0} {1}/{2}->{3}/{4}")
	public static Object[][] params() {
		return new Object[][] {
				new Object[] {"+5 Dexterity Vest", 10, 20, 9, 19},
				new Object[] {"+5 Dexterity Vest", 10, 0, 9, 0},
				new Object[] {"+5 Dexterity Vest", 0, 0, -1, 0},
				new Object[] {"+5 Dexterity Vest", -1, 10, -2, 8},

				new Object[] {"Aged Brie", 10, 10, 9, 11},
				new Object[] {"Aged Brie", 10, 50, 9, 50},
				new Object[] {"Aged Brie", 0, 1, -1, 3},//this seems a bug to me... Quality should be 2

				new Object[] {"Sulfuras, Hand of Ragnaros", 0, 80, 0, 80},

				new Object[] {"Backstage passes to a TAFKAL80ETC concert", 11, 10, 10, 11},
				new Object[] {"Backstage passes to a TAFKAL80ETC concert", 10, 10, 9, 12},
				new Object[] {"Backstage passes to a TAFKAL80ETC concert", 4, 10, 3, 13},
				new Object[] {"Backstage passes to a TAFKAL80ETC concert", 1, 10, 0, 13},
				new Object[] {"Backstage passes to a TAFKAL80ETC concert", 0, 50, -1, 0},

				//
				new Object[] {"Conjured Mana Cake", 10, 10, 9, 8},
				new Object[] {"Conjured Mana Cake", 0, 10, -1, 6},
				new Object[] {"Conjured Mana Cake", 0, 2, -1, 0},
		};
	}


	@Test
	public void testTheTruth() {
		unit.updateQuality();
		assertThat("unexpected sellIn", item.getSellIn(), CoreMatchers.equalTo(nextSellIn));
		assertThat("unexpected quality", item.getQuality(), CoreMatchers.equalTo(nextQuality));
	}
}
