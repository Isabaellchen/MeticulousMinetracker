package rocks.isor.meticulousminetracker.tasks;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import rocks.isor.meticulousminetracker.database.Database;
import rocks.isor.meticulousminetracker.database.dao.ItemDropsDAO;
import rocks.isor.meticulousminetracker.database.dao.MinedBlocksDAO;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

@Singleton
public class BlockMiningTasks {

	@Inject
	public BlockMiningTasks() {}

	@Inject
	public MinedBlocksDAO minedBlocksDAO;

	@Inject
	public ItemDropsDAO itemDropsDAO;

	public Runnable registerNewBlockBroken(final Block block, final Collection<ItemStack> drops) {
		return () -> Database.wrapTransactional((connection) -> {
			final long blockId = minedBlocksDAO.addMinedBlock(connection, block);

			for (ItemStack drop : drops) {
				itemDropsDAO.addItemDrop(connection, drop, blockId);
			}
		});
	}
}
