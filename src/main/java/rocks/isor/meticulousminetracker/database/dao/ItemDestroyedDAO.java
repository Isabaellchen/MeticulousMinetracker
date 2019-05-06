package rocks.isor.meticulousminetracker.database.dao;

import org.bukkit.inventory.ItemStack;
import rocks.isor.meticulousminetracker.database.Database;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ItemDestroyedDAO {

	private static final String QUERY_ADD_ITEM_DESTROYED = "INSERT INTO item_destroyed (material, amount) VALUES (?,?);";

	@Inject
	public ItemDestroyedDAO() {}

	public void addItemDestroyed(ItemStack drop) {
		Database.executeQuery(QUERY_ADD_ITEM_DESTROYED, drop.getType().ordinal(), drop.getAmount());
	}
}
