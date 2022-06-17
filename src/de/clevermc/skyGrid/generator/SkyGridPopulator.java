package de.clevermc.skyGrid.generator;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("deprecation")
public class SkyGridPopulator extends BlockPopulator {
	private static Random r = new Random();

	private int[] spawnEgg = { 61, 56, 62, 50, 58, 54, 51, 52, 57, 55, 90, 91, 93, 92, 96, 95, 120, 59, 98, 66 };

	public void populate(World world, Random random, Chunk chunk) {
		int wH = world.getMaxHeight();
		for (int x = 0; x < 16; x += 4) {
			for (int y = 0; y < wH; y += 4) {
				for (int z = 0; z < 16; z += 4) {
					Block blk = chunk.getBlock(x, y, z);
					switch (blk.getType()) {
					case CHEST:
						Chest chest = (Chest) blk.getState();
						populateChest(world, random, chest);
						break;
					case GRASS:
						blk.getRelative(BlockFace.UP).setType(getGrassPop(), false);
						if (blk.getRelative(BlockFace.UP).getType() == Material.RED_ROSE) {
							blk.getRelative(BlockFace.UP).setData((byte) r.nextInt(9));
						}
						break;
					case DIRT: {
						int data = r.nextInt(3);
						blk.setData((byte) data);
						if (data == 0) {
							blk.getRelative(BlockFace.UP).setType(getDirtPop());
							if (blk.getRelative(BlockFace.UP).getType() == Material.SAPLING) {
								blk.getRelative(BlockFace.UP).setData((byte) r.nextInt(6));
							}
						}
						break;
					}
					case WOOL:
						blk.setData((byte) r.nextInt(16));
						break;
					case PRISMARINE:
						blk.setData((byte) r.nextInt(3));
						break;
					case RED_SANDSTONE:
						blk.setData((byte) r.nextInt(3));
						break;
					case STONE:
						blk.setData((byte) r.nextInt(7));
						break;
					case LOG:
						blk.setData((byte) r.nextInt(4));
						break;
					case LOG_2:
						blk.setData((byte) r.nextInt(2));
						break;
					case WOOD:
						blk.setData((byte) r.nextInt(6));
						break;
					case SAND: {
						int data = r.nextInt(2);
						blk.setData((byte) data);
						if (data == 0 && r.nextInt(10) < 1) {
							blk.getRelative(BlockFace.UP).setTypeId(Material.CACTUS.getId(), false);
						}
						break;
					}
					case SOUL_SAND:
						blk.getRelative(BlockFace.UP).setType(getSoulPop());
						break;
					case MOB_SPAWNER:
						if (world.getEnvironment() == World.Environment.NETHER
								|| world.getEnvironment() == World.Environment.THE_END) {
							if (world.getEnvironment() == World.Environment.NETHER) {
								CreatureSpawner spawner = (CreatureSpawner) blk.getState();
								spawner.setSpawnedType(this.getNetherEntity());
							} else {
								CreatureSpawner spawner = (CreatureSpawner) blk.getState();
								spawner.setSpawnedType(EntityType.ENDERMAN);
							}
						} else {
							CreatureSpawner spawner = (CreatureSpawner) blk.getState();
							spawner.setSpawnedType(this.getNormEntity());
						}
						break;
					default:
						break;
					}
				}
			}
		}
	}

	public static EntityType getEntityType() {
		EntityType[] mobHosNorm = { EntityType.ZOMBIE, EntityType.SKELETON, EntityType.SPIDER, EntityType.PIG_ZOMBIE,
				EntityType.SLIME };
		EntityType[] mobHosRare = { EntityType.BLAZE, EntityType.GHAST, EntityType.MAGMA_CUBE, EntityType.CREEPER,
				EntityType.ENDERMAN };
		EntityType[] mobNorm = { EntityType.PIG, EntityType.SHEEP, EntityType.CHICKEN };
		EntityType[] mobRare = { EntityType.COW, EntityType.MUSHROOM_COW };
		Random random = new Random();
		int c = random.nextInt(100);
		EntityType entRet;
		if (c < 2) {
			entRet = mobHosRare[random.nextInt(mobHosRare.length)];
		} else if (c < 5) {
			entRet = mobRare[random.nextInt(mobRare.length)];
		} else if (c < 14) {
			entRet = mobHosNorm[random.nextInt(mobHosNorm.length)];
		} else {
			entRet = mobNorm[random.nextInt(mobNorm.length)];
		}
		return entRet;
	}

	public EntityType getNormEntity() {
		EntityType[] mobHosNorm = { EntityType.ZOMBIE, EntityType.SKELETON, EntityType.SPIDER, EntityType.SLIME };
		EntityType[] mobHosRare = { EntityType.CREEPER, EntityType.ENDERMAN };
		EntityType[] mobNorm = { EntityType.PIG, EntityType.SHEEP, EntityType.CHICKEN };
		EntityType[] mobRare = { EntityType.COW, EntityType.MUSHROOM_COW };
		Random random = new Random();
		int c = random.nextInt(100);
		EntityType ent;
		if (c < 2) {
			ent = mobHosRare[random.nextInt(mobHosRare.length)];
		} else if (c < 5) {
			ent = mobRare[random.nextInt(mobRare.length)];
		} else if (c < 14) {
			ent = mobHosNorm[random.nextInt(mobHosNorm.length)];
		} else {
			ent = mobNorm[random.nextInt(mobNorm.length)];
		}
		return ent;
	}

	public EntityType getNetherEntity() {
		EntityType[] mobHosNorm = { EntityType.PIG_ZOMBIE, EntityType.SKELETON };
		EntityType[] mobHosRare = { EntityType.BLAZE, EntityType.GHAST, EntityType.MAGMA_CUBE };
		Random random = new Random();
		int c = random.nextInt(100);
		EntityType ent;
		if (c < 2) {
			ent = mobHosRare[random.nextInt(mobHosRare.length)];
		} else {
			ent = mobHosNorm[random.nextInt(mobHosNorm.length)];
		}
		return ent;
	}

	public static Material getGrassPop() {
		Random rand = new Random();
		int p = rand.nextInt(100);
		if (p < 5) {
			Material popMat = Material.RED_MUSHROOM;
			return popMat;
		}
		if (p < 10) {
			Material popMat = Material.BROWN_MUSHROOM;
			return popMat;
		}
		if (p < 18) {
			Material popMat = Material.RED_ROSE;
			return popMat;
		}
		if (p < 20) {
			Material popMat = Material.YELLOW_FLOWER;
			return popMat;
		}
		if (p < 25) {
			Material popMat = Material.SUGAR_CANE_BLOCK;
			return popMat;
		}
		return Material.AIR;
	}

	public static Material getSoulPop() {
		Random random = new Random();
		int a = random.nextInt(10);
		if (a < 2) {
			return Material.NETHER_WARTS;
		}
		return Material.AIR;
	}

	public static Material getDirtPop() {

		int p = r.nextInt(10);
		if (p < 1) {
			return Material.SAPLING;
		}
		return Material.AIR;
	}

	public void populateChest(World world, Random random, Chest chest) {
		World.Environment env = world.getEnvironment();
		int[] itemMythicID = { 0 };
		int[] itemMythicAmount = { 0 };
		int mythChance = 0;
		int rareChance;
		int[] itemRareID;
		int[] itemRareAmount;
		int[] itemID;
		int[] itemAmount;
		int maxI;
		if (env == World.Environment.NETHER) {
			rareChance = 2;
			itemRareID = new int[] { 13, 372, 399, 369, 377, 383, 371, 283, 378, 314, 289, 370 };
			itemRareAmount = new int[] { 4, 4, 1, 1, 2, 1, 9, 1, 1, 1, 5, 1 };
			itemID = new int[] { 87, 89, 113, 114, 153, 112, 405, 327, 406, 367, 348 };
			itemAmount = new int[] { 12, 4, 6, 6, 4, 4, 16, 1, 4, 4, 16 };
			int a = random.nextInt(10);
			int preMax1 = random.nextInt(2);
			int preMax2 = random.nextInt(5);
			if (a < 2) {
				maxI = 1 + preMax2;
			} else {
				maxI = 1 + preMax1;
			}
		} else if (env == World.Environment.THE_END) {
			rareChance = 2;
			itemRareID = new int[] { 383, 381, 368 };
			itemRareAmount = new int[] { 1, 1, 1 };
			itemID = new int[] { 49, 121 };
			itemAmount = new int[] { 4, 16 };
			int a = random.nextInt(10);
			int preMax1 = random.nextInt(2);
			int preMax2 = random.nextInt(5);
			if (a < 2) {
				maxI = 1 + preMax2;
			} else {
				maxI = 1 + preMax1;
			}
		} else {
			mythChance = 2;
			rareChance = 6;
			itemMythicID = new int[] { 383, 19 };
			itemMythicAmount = new int[] { 1, 1 };
			itemRareID = new int[] { 41, 42, 46, 47, 49, 152, 256, 257, 258, 267, 276, 277, 278, 279, 293, 292, 264,
					306, 307, 308, 309, 310, 311, 312, 313, 356, 368, 388 };
			itemRareAmount = new int[] { 1, 2, 2, 2, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 2,
					1, 3 };
			itemID = new int[] { 3, 2, 4, 17, 45, 81, 162, 260, 262, 263, 265, 266, 287, 295, 322, 331, 350, 352, 361,
					362, 364, 366, 392, 396 };
			itemAmount = new int[] { 32, 4, 32, 12, 6, 6, 12, 2, 16, 12, 6, 4, 12, 5, 2, 16, 10, 8, 5, 5, 10, 10, 5,
					3 };
			int a = random.nextInt(10);
			int preMax1 = random.nextInt(4);
			int preMax2 = random.nextInt(10);
			if (a < 2) {
				maxI = 1 + preMax2;
			} else {
				maxI = 1 + preMax1;
			}
		}
		for (int i = 0; i < maxI; ++i) {
			Inventory inv = chest.getInventory();
			int quality = random.nextInt(100);
			if (quality < rareChance) {
				if (quality < mythChance && env == World.Environment.NORMAL) {
					int aPos = random.nextInt(itemMythicID.length);
					int ID = itemMythicID[aPos];
					int maxAmount = itemMythicAmount[aPos];
					int amount;
					if (maxAmount == 1) {
						amount = 1;
					} else {
						amount = random.nextInt(maxAmount) + 1;
					}
					ItemStack itm = new ItemStack(ID, amount, (short) 0);
					if (itm.getType() == Material.MONSTER_EGG) {
						Random rdm = new Random();
						itm = new ItemStack(Material.MONSTER_EGG, 1,
								(short) this.spawnEgg[rdm.nextInt(this.spawnEgg.length)]);
						inv.addItem(new ItemStack[] { itm });
					} else {
						inv.addItem(new ItemStack[] { itm });
					}
				} else {
					int aPos = random.nextInt(itemRareID.length);
					int ID = itemRareID[aPos];
					int maxAmount = itemRareAmount[aPos];
					int amount;
					if (maxAmount == 1) {
						amount = 1;
					} else {
						amount = random.nextInt(maxAmount) + 1;
					}
					ItemStack itm = new ItemStack(ID, amount, (short) 0);
					if (itm.getType() == Material.MONSTER_EGG || itm.getType() == Material.LOG
							|| itm.getType() == Material.LOG_2) {
						if (itm.getType() == Material.MONSTER_EGG) {
							itm = new ItemStack(Material.MONSTER_EGG, amount,
									(short) this.spawnEgg[random.nextInt(this.spawnEgg.length)]);
							inv.addItem(new ItemStack[] { itm });
						} else if (itm.getType() == Material.LOG) {
							itm = new ItemStack(Material.LOG, amount, (short) random.nextInt(4));
							inv.addItem(new ItemStack[] { itm });
						} else if (itm.getType() == Material.LOG_2) {
							itm = new ItemStack(Material.LOG_2, amount, (short) random.nextInt(2));
							inv.addItem(new ItemStack[] { itm });
						}
					} else {
						inv.addItem(new ItemStack[] { itm });
					}
				}
			} else {
				int aPos = random.nextInt(itemID.length);
				int ID = itemID[aPos];
				int maxAmount = itemAmount[aPos];
				int amount;
				if (maxAmount == 1) {
					amount = 1;
				} else {
					amount = random.nextInt(maxAmount) + 1;
				}
				ItemStack itm = new ItemStack(ID, amount, (short) 0);
				if (itm.getType() == Material.MONSTER_EGG || itm.getType() == Material.LOG) {
					if (itm.getType() == Material.MONSTER_EGG) {
						itm = new ItemStack(Material.MONSTER_EGG, amount,
								(short) this.spawnEgg[random.nextInt(this.spawnEgg.length)]);
						inv.addItem(new ItemStack[] { itm });
					} else if (itm.getType() == Material.LOG) {
						itm = new ItemStack(Material.LOG, amount, (short) random.nextInt(4));
						inv.addItem(new ItemStack[] { itm });
					} else if (itm.getType() == Material.LOG_2) {
						itm = new ItemStack(Material.LOG_2, amount, (short) random.nextInt(2));
						inv.addItem(new ItemStack[] { itm });
					}
				} else {
					inv.addItem(new ItemStack[] { itm });
				}
			}
		}
	}

}
