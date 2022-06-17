package de.clevermc.skyGrid.generator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class SkyGridGenerator extends ChunkGenerator {

	private int[] nRarity1 = { 1, 2, 3, 5, 12, 16, 17, 162 }, nRarity2 = { 13, 15, 24, 48, 82, 179 },
			nRarity3 = { 14, 21, 30, 35, 45, 49, 73, 86, 98, 103, 168, 170, 201, 202 },
			nRarity4 = { 9, 11, 46, 47, 52, 54, 56, 110, 129, 152 }, nRarity5 = { 22, 41, 42, 57, 116, 133 },
			neRarity1 = { 87, 87, 87, 87, 87, 87, 112, 113, 153 }, neRarity2 = { 11, 13, 52, 54, 88, 89, 155 },
			eRarity1 = { 49, 121, 121, 121, 121, 121, 121, 121 }, eRarity2 = { 52, 54 };

	@Override
	public byte[][] generateBlockSections(World world, Random random, int chunkX, int chunkZ,
			ChunkGenerator.BiomeGrid biomes) {
		byte[][] result = new byte[16][];
		for (int x = 0; x < 16; x += 4) {
			for (int y = 0; y < 256; y += 4) {
				for (int z = 0; z < 16; z += 4) {
					byte ID = this.getRandBlockID(world, random);
					setBlock(result, x, y, z, ID);
				}
			}
		}

		return result;
	}

	public byte getRandBlockID(World world, Random random) {
		byte ID;
		if (world.getEnvironment() == World.Environment.NORMAL) {
			int r = random.nextInt(10000);
			if(r<4) {
				ID=(byte) nRarity5[random.nextInt(nRarity5.length)];
			}else if(r<181) {
				ID=(byte) nRarity4[random.nextInt(nRarity4.length)];
			}else if(r<1801) {
				ID=(byte) nRarity3[random.nextInt(nRarity3.length)];
			}else if(r<4001) {
				ID=(byte) nRarity2[random.nextInt(nRarity2.length)];
			}else {
				ID=(byte) nRarity1[random.nextInt(nRarity1.length)];
			}
		} else if (world.getEnvironment() == World.Environment.NETHER) {
			int r = random.nextInt(100);
			if(r==0) {
				ID=(byte) neRarity2[random.nextInt(neRarity2.length)];
			}else {
			ID=(byte) neRarity1[random.nextInt(neRarity1.length)];
			}
		} else {
			int r = random.nextInt(100);
			if(r==0) {
				ID=(byte) eRarity2[random.nextInt(eRarity2.length)];
			}else {
			ID=(byte) eRarity1[random.nextInt(eRarity1.length)];
			}
		}
		return ID;
	}

	public List<BlockPopulator> getDefaultPopulators(final World world) {
		return Arrays.asList(new SkyGridPopulator());
	}
	
	private void setBlock(byte[][] result, int x, int y, int z, byte blkid) {
		if (result[y >> 4] == null) {
			result[y >> 4] = new byte[4096];
		}
		result[y >> 4][(y & 0xF) << 8 | z << 4 | x] = blkid;
	}

}
