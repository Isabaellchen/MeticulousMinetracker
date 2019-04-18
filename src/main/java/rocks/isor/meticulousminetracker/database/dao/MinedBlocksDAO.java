package rocks.isor.meticulousminetracker.database.dao;

import org.bukkit.block.Block;
import rocks.isor.meticulousminetracker.database.Database;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MinedBlocksDAO {

	private static final String QUERY_ADD_BLOCK = "INSERT INTO mined_blocks VALUES (?,?,?,?);";

	@Inject
	public MinedBlocksDAO() {}

	public void addMinedBlock(Block block) {
		Database.executeQuery(QUERY_ADD_BLOCK, block.getX(), block.getY(), block.getZ(), block.getType().ordinal());
	}
}
