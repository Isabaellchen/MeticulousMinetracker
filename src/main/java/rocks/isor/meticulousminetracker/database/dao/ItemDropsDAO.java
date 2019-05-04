package rocks.isor.meticulousminetracker.database.dao;

import org.bukkit.inventory.ItemStack;
import rocks.isor.meticulousminetracker.database.Database;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.SQLException;

@Singleton
public class ItemDropsDAO {

	private static final String QUERY_ADD_ITEM_DROP = "INSERT INTO item_drops VALUES (?,?,?);";

	@Inject
	public ItemDropsDAO() {}

	public void addItemDrop(Connection connection, ItemStack drop, long blockId) throws SQLException {
		Database.executeQuery(connection, QUERY_ADD_ITEM_DROP, blockId, drop.getType().ordinal(), drop.getAmount());
	}
}
