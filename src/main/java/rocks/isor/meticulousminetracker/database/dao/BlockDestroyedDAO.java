package rocks.isor.meticulousminetracker.database.dao;

import org.bukkit.block.Block;
import rocks.isor.meticulousminetracker.database.Database;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

@Singleton
public class BlockDestroyedDAO {

	private static final String QUERY_ADD_BLOCK =
			"INSERT INTO block_destroyed(x,y,z,material,time,world,is_restored) VALUES (?,?,?,?,?,?,?);";

	private static final String QUERY_CLEAN_BLOCKS =
			"DELETE FROM block_destroyed WHERE is_restored = ?;";

	@Inject
	public BlockDestroyedDAO() {}

	public long addBlockDestroyed(Connection connection, Block block) throws SQLException {
		return Database.executeInsertQuery(connection, QUERY_ADD_BLOCK,
				block.getX(), block.getY(), block.getZ(), block.getType().ordinal(), new Date(), block.getWorld().getUID(), false);
	}

	public void cleanDestroyedBlocks() {
		Database.executeQuery(QUERY_CLEAN_BLOCKS, true);
	}
}
