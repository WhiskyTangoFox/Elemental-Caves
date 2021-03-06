package minestrapteam.elementalcaves.cavetype;

import java.util.Random;

import minestrapteam.caveapi.cavetype.CaveType;
import minestrapteam.elementalcaves.lib.ECBlocks;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
 
public class CaveTypeDesert extends CaveType
{
	public CaveTypeDesert(String name)
	{
		super(name, ECBlocks.pressurizedSandstone);
		
		this.addOre(ECBlocks.sand, 1, 15, 6, this.spawnHeight);		
	}

	@Override
	public void generateFloor(World world, Random random, int x, int y, int z)
	{
		world.setBlock(x, y, z, ECBlocks.sand, 0, 3);
	}
	
	@Override
	public boolean canGenerateInBiome(BiomeGenBase biome)
	{
		return biome == BiomeGenBase.desert || biome == BiomeGenBase.desertHills;
	}
}
