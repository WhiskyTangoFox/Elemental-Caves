package minestrapteam.elementalcaves.creativetab;

import minestrapteam.elementalcaves.lib.ECItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ECCreativeTabItems extends CreativeTabs
{
	public ECCreativeTabItems(String label)
	{
		super(label);
	}
	
	@Override
	public Item getTabIconItem()
	{
		return ECItems.gems;
	}
}
