package rocks.isor.meticulousminetracker.database.dao;

import org.bukkit.inventory.ItemStack;
import rocks.isor.meticulousminetracker.database.Database;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.SQLException;

@Singleton
public class BlockItemsDroppedDAO {

	private static final String QUERY_ADD_ITEM_DROP = "INSERT INTO block_items_dropped VALUES (?,?,?);";

	@Inject
	public BlockItemsDroppedDAO() {}

	public void addItemDrop(Connection connection, ItemStack drop, long blockId) throws SQLException {
		Database.executeQuery(connection, QUERY_ADD_ITEM_DROP, blockId, drop.getType().ordinal(), drop.getAmount());
	}
}
