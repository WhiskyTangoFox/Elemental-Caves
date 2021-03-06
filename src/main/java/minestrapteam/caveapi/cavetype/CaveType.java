package minestrapteam.caveapi.cavetype;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import minestrapteam.caveapi.world.OreGenData;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class CaveType
{
	public final String			name;
	public Block				block;
	public Block				floorBlock;
	public Block				ceilingBlock;
	
	public int					blockMetadata;
	public int					floorMetadata;
	public int					ceilingMetadata;
	
	public int					spawnHeight			= 62;
	protected float				ceilingAddonWeight	= 0.1F;
	protected float				floorAddonWeight	= 0.2F;
	
	public BiomeGenBase			biome;
	
	protected CaveWallGenerator	wallGen;
	
	public List<OreGenData>		ores				= new ArrayList();
	
	public CaveType(String name, Block mainCaveBlock)
	{
		if (mainCaveBlock == null)
		{
			throw new IllegalArgumentException("Cannot set the main cave generator block to null");
		}
		
		this.name = name;
		this.block = mainCaveBlock;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * Sets a block for cave generation.
	 * 
	 * @param world
	 *            the world
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param z
	 *            z coordinate
	 * @param block
	 *            the block
	 */
	protected static void setBlock(World world, int x, int y, int z, Block block)
	{
		setBlock(world, x, y, z, block, 0);
	}
	
	/**
	 * Sets a block for cave generation.
	 * 
	 * @param world
	 *            the world
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param z
	 *            z coordinate
	 * @param block
	 *            the block
	 * @param metadata
	 *            the block metadata
	 */
	protected static void setBlock(World world, int x, int y, int z, Block block, int metadata)
	{
		world.setBlock(x, y, z, block, metadata, 0);
	}
	
	public CaveType setBlock(Block block)
	{
		this.block = block;
		return this;
	}
	
	public CaveType setBlock(Block block, int blockMetadata)
	{
		this.block = block;
		this.blockMetadata = blockMetadata;
		this.wallGen = null;
		return this;
	}
	
	public CaveType setFloorBlock(Block block)
	{
		this.floorBlock = block;
		return this;
	}
	
	public CaveType setFloorBlock(Block block, int blockMetadata)
	{
		this.floorBlock = block;
		this.floorMetadata = blockMetadata;
		return this;
	}
	
	public CaveType setCeilingBlock(Block block)
	{
		this.ceilingBlock = block;
		return this;
	}
	
	public CaveType setCeilingBlock(Block block, int blockMetadata)
	{
		this.ceilingBlock = block;
		this.ceilingMetadata = blockMetadata;
		return this;
	}
	
	public CaveType setBiome(BiomeGenBase biome)
	{
		this.biome = biome;
		return this;
	}
	
	/**
	 * Set the rate at which floor addons spawn. Must be between 0 and 1.
	 * 
	 * @param weight
	 * @return
	 */
	public CaveType setFloorAddonSpawnWeight(float weight)
	{
		this.floorAddonWeight = weight;
		return this;
	}
	
	/**
	 * Set the rate at which ceiling addons spawn. Must be between 0 and 1.
	 * 
	 * @param weight
	 * @return
	 */
	public CaveType setCeilingAddonSpawnWeight(float weight)
	{
		this.ceilingAddonWeight = weight;
		return this;
	}
	
	public boolean canGenerateAt(World world, int x, int z)
	{
		return this.canGenerateInBiome(world.getBiomeGenForCoords(x, z));
	}
	
	public boolean canGenerateInBiome(BiomeGenBase biome)
	{
		return this.biome == null || this.biome == biome;
	}
	
	public CaveType setSpawnHeight(int height)
	{
		this.spawnHeight = height;
		return this;
	}
	
	public void generate(World world, Random random, int x, int floor, int ceiling, int z)
	{
		int center = floor + (ceiling - floor) / 2;
		
		this.generateCeiling(world, random, x, ceiling, z);
		if (random.nextFloat() < this.ceilingAddonWeight)
		{
			this.generateCeilingAddons(world, random, x, ceiling - 1, z);
		}
		
		this.generate(world, random, x, center, z);
		
		this.generateFloor(world, random, x, floor, z);
		if (random.nextFloat() < this.floorAddonWeight)
		{
			this.generateFloorAddons(world, random, x, floor, z);
		}
	}
	
	public CaveWallGenerator getWallGen()
	{
		if (this.wallGen == null)
		{
			this.wallGen = new CaveWallGenerator(this.block, this.blockMetadata);
		}
		return this.wallGen;
	}
	
	public void generate(World world, Random random, int x, int y, int z)
	{
		this.getWallGen().generate(world, random, x, y, z);
	}
	
	public void generateCeiling(World world, Random random, int x, int y, int z)
	{
		if (this.ceilingBlock != null)
		{
			world.setBlock(x, y, z, this.ceilingBlock, this.ceilingMetadata, 2);
		}
		else
		{
			world.setBlock(x, y, z, this.block, this.blockMetadata, 2);
		}
	}
	
	public void generateFloor(World world, Random random, int x, int y, int z)
	{
		if (this.floorBlock != null)
		{
			world.setBlock(x, y, z, this.floorBlock, this.floorMetadata, 2);
		}
		else
		{
			world.setBlock(x, y, z, this.block, this.blockMetadata, 2);
		}
	}
	
	public void generateCeilingAddons(World world, Random random, int x, int y, int z)
	{
	}
	
	public void generateFloorAddons(World world, Random random, int x, int y, int z)
	{
	}
	
	public void addOre(Block ore, int vainsPerChunk, int orePerVain, int spawnHeight)
	{
		this.addOre(ore, 0, vainsPerChunk, orePerVain, spawnHeight);
	}
	
	public void addOre(Block ore, int metadata, int vainsPerChunk, int orePerVain, int spawnHeight)
	{
		this.ores.add(new OreGenData(this, ore, metadata, vainsPerChunk, orePerVain, spawnHeight));
	}
}
