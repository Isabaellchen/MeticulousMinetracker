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

	private static final String QUERY_ADD_BLOCK = "INSERT INTO block_placed(x,y,z,material,time) VALUES (?,?,?,?,?);";
	private static final String QUERY_GET_BLOCK = "SELECT * FROM block_placed WHERE x = ? AND y = ? AND z = ?;";
	private static final String QUERY_DELETE_BLOCK = "DELETE FROM block_placed WHERE x = ? AND y = ? AND z = ?;";

	@Inject
	public BlockPlacedDAO() {}

	public long addPlacedBlock(Block block) {
		return Database.executeInsertQuery(QUERY_ADD_BLOCK, block.getX(), block.getY(), block.getZ(), block.getType().ordinal(), new Date());
	}

	public boolean wasPlaced(Connection connection, Block block) throws SQLException {
		return Database.executeQuery(connection, QUERY_GET_BLOCK, block.getX(), block.getY(), block.getZ()).isEmpty();
	}

	public void deletePlacedBlock(Connection connection, Block block) throws SQLException {
		Database.executeQuery(connection, QUERY_DELETE_BLOCK, block.getX(), block.getY(), block.getZ());
	}
}
