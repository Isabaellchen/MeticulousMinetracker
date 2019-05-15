package rocks.isor.meticulousminetracker.tasks;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import rocks.isor.meticulousminetracker.database.Database;
import rocks.isor.meticulousminetracker.database.dao.BlockDestroyedDAO;
import rocks.isor.meticulousminetracker.database.dao.BlockItemsDroppedDAO;
import rocks.isor.meticulousminetracker.database.dao.BlockPlacedDAO;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

@Singleton
public class BlockDestroyedTasks {

	@Inject
	public BlockDestroyedTasks() {
	}

	@Inject
	public BlockDestroyedDAO minedBlocksDAO;

	@Inject
	public BlockItemsDroppedDAO blockItemsDroppedDAO;

	@Inject
	public BlockPlacedDAO blockPlacedDAO;

	@Inject
	public Plugin plugin;

	@Inject
	public BukkitScheduler bukkitScheduler;

	public Runnable registerBlockDestroyed(final Block block) {
		return () -> Database.wrapTransactional((connection) -> {
			if (blockPlacedDAO.wasPlaced(connection, block)) {
				blockPlacedDAO.deletePlacedBlock(connection, block);
			} else {
				final long blockId = minedBlocksDAO.addBlockDestroyed(connection, block);

				for (ItemStack drop : block.getDrops()) {
					blockItemsDroppedDAO.addItemDrop(connection, drop, blockId);
				}
			}
		});
	}

	public Runnable registerBlockDestroyedCollection(final Collection<Block> blocks) {
		return () -> {
			for (Block block : blocks) {
				bukkitScheduler.runTaskAsynchronously(plugin, registerBlockDestroyed(block));
			}
		};
	}
}
