package rocks.isor.meticulousminetracker.database.dao;

import org.bukkit.block.Block;
import rocks.isor.meticulousminetracker.database.Database;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

@Singleton
public class BlockPlacedDAO {

	private static final String QUERY_ADD_BLOCK =
			"INSERT INTO block_placed(x,y,z,material,time,world,is_destroyed) VALUES (?,?,?,?,?,?,?);";

	private static final String QUERY_GET_BLOCK =
			"SELECT * FROM block_placed WHERE x = ? AND y = ? AND z = ? AND world = ? AND is_destroyed = ?;";

	private static final String QUERY_DELETE_BLOCK =
			"UPDATE block_placed SET is_destroyed = ? WHERE x = ? AND y = ? AND z = ? AND world = ? AND is_destroyed = ?;";

	private static final String QUERY_DELETE_ITEM_BLOCK =
			"UPDATE block_placed SET is_destroyed = ? WHERE x = ? AND y = ? AND z = ? AND material = ? AND world = ? AND is_destroyed = ?;";

	private static final String QUERY_CLEAN_BLOCKS =
			"DELETE FROM block_placed WHERE is_destroyed = ?;";

	@Inject
	public BlockPlacedDAO() {}

	public long addPlacedBlock(Block block) {
		return Database.executeInsertQuery(QUERY_ADD_BLOCK,
				block.getX(), block.getY(), block.getZ(), block.getType().ordinal(), new Date(), block.getWorld().getUID(), false);
	}

	public boolean wasPlaced(Connection connection, Block block) throws SQLException {
		return Database.executeQuery(connection, QUERY_GET_BLOCK,
				block.getX(), block.getY(), block.getZ(), block.getWorld().getUID(), false).isEmpty();
	}

	public void deletePlacedBlock(Connection connection, Block block) throws SQLException {
		Database.executeQuery(connection, QUERY_DELETE_BLOCK,
				true, block.getX(), block.getY(), block.getZ(), block.getWorld().getUID(), false);
	}

	public void deletePlacedBlock(Block block, int materialOrdinal) {
		Database.executeQuery(QUERY_DELETE_ITEM_BLOCK,
						true, block.getX(), block.getY(), block.getZ(), materialOrdinal, block.getWorld().getUID(), false);
	}

	public void cleanPlacedBlocks() {
		Database.executeQuery(QUERY_CLEAN_BLOCKS, true);
	}
}
