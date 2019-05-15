package rocks.isor.meticulousminetracker.tasks;

import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import rocks.isor.meticulousminetracker.database.dao.BlockPlacedDAO;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BlockPlacedTasks {

	@Inject
	public BlockPlacedTasks() {}

	@Inject
	public BlockPlacedDAO blockPlacedDAO;

	@Inject
	public Plugin plugin;

	public Runnable registerBlockPlaced(final Block block) {
		return () -> blockPlacedDAO.addPlacedBlock(block);
	}
}
