package rocks.isor.meticulousminetracker.listeners;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import rocks.isor.meticulousminetracker.database.dao.MinedBlocksDAO;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BlockMiningListener implements Listener {

	@Inject
	public BlockMiningListener() {}

	@Inject
	public MinedBlocksDAO minedBlocksDAO;

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		Block block = event.getBlock();

		minedBlocksDAO.addMinedBlock(block);
	}
}
