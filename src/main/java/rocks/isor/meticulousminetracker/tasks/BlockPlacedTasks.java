package rocks.isor.meticulousminetracker.tasks;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import rocks.isor.meticulousminetracker.database.dao.BlockPlacedDAO;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

@Singleton
public class BlockPlacedTasks {

	@Inject
	public BlockPlacedTasks() {}

	@Inject
	public BlockPlacedDAO blockPlacedDAO;

	@Inject
	public Plugin plugin;

	@Inject
	public BukkitScheduler bukkitScheduler;

	public Runnable registerBlockPlaced(final Block block) {
		return () -> blockPlacedDAO.addPlacedBlock(block);
	}

	public Runnable registerBlockPlaced(final Collection<BlockState> blocks) {
		return () -> {
			for (BlockState bs: blocks) {
				bukkitScheduler.runTaskAsynchronously(plugin, registerBlockPlaced(bs.getBlock()));
			}
		};
	}
}
