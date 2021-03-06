package minestrapteam.elementalcaves.world.gen;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import minestrapteam.elementalcaves.lib.ECBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderEnd;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.ChunkProviderHell;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class ECWorldGenerator implements IWorldGenerator
{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		chunkX <<= 4;
		chunkZ <<= 4;
		if (chunkGenerator instanceof ChunkProviderGenerate)
		{
			ECWorldGenerator.generateSurface(world, random, chunkX, chunkZ, chunkGenerator);
		}
		else if (chunkGenerator instanceof ChunkProviderHell)
		{
			ECWorldGenerator.generateNether(world, random, chunkX, chunkZ);
		}
		else if (chunkGenerator instanceof ChunkProviderEnd)
		{
			ECWorldGenerator.generateEnd(world, random, chunkX, chunkZ);
		}
	}
	
	public static void generateSurface(World world, Random rand, int chunkX, int chunkZ, IChunkProvider chunk)
	{
	}
	
	public static void generateNether(World world, Random rand, int chunkX, int chunkZ)
	{
		WorldGenMinable glisteningOreGen = new WorldGenMinable(ECBlocks.oreGlistening, 5, Blocks.netherrack);
		
		int x1;
		int y1;
		int z1;
		
		for (int i = 0; i < 10; i++)
		{
			x1 = chunkX + rand.nextInt(16);
			y1 = rand.nextInt(40);
			z1 = chunkZ + rand.nextInt(16);
			
			glisteningOreGen.generate(world, rand, x1, y1, z1);
		}
	}
	
	public static void generateEnd(World world, Random rand, int chunkX, int chunkZ)
	{
	}
}
