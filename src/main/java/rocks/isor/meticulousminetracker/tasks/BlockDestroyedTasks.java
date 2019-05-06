package rocks.isor.meticulousminetracker.tasks;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import rocks.isor.meticulousminetracker.database.Database;
import rocks.isor.meticulousminetracker.database.dao.ItemDropsDAO;
import rocks.isor.meticulousminetracker.database.dao.BlockDestroyedDAO;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.List;

@Singleton
public class BlockDestroyedTasks {

	@Inject
	public BlockDestroyedTasks() {}

	@Inject
	public BlockDestroyedDAO minedBlocksDAO;

	@Inject
	public ItemDropsDAO itemDropsDAO;

	@Inject
	public Plugin plugin;

	@Inject
	public BukkitScheduler bukkitScheduler;

	public Runnable registerNewBlockDestroyed(final Block block) {
		return () -> Database.wrapTransactional((connection) -> {
			final long blockId = minedBlocksDAO.addBlockDestroyed(connection, block);

			for (ItemStack drop : block.getDrops()) {
				itemDropsDAO.addItemDrop(connection, drop, blockId);
			}
		});
	}

	public Runnable registerNewBlockDestroyedCollection(final Collection<Block> blocks) {
		return () -> {
			for (Block block : blocks) {
				bukkitScheduler.runTaskAsynchronously(plugin, registerNewBlockDestroyed(block));
			}
		};
	}
}
