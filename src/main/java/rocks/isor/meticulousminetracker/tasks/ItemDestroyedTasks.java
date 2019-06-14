package rocks.isor.meticulousminetracker.tasks;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import rocks.isor.meticulousminetracker.database.dao.ItemDestroyedDAO;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

@Singleton
public class ItemDestroyedTasks {

	@Inject
	public ItemDestroyedTasks() {}

	@Inject
	public ItemDestroyedDAO itemDestroyedDAO;

	@Inject
	public Plugin plugin;

	@Inject
	public BukkitScheduler bukkitScheduler;

	public Runnable registerItemDestroyed(final ItemStack itemStack) {
		return () -> itemDestroyedDAO.addItemDestroyed(itemStack);
	}

	public Runnable registerItemDestroyed(final Collection<ItemStack> itemStacks) {
		return () -> {
			for (ItemStack itemStack : itemStacks) {
				bukkitScheduler.runTaskAsynchronously(plugin, registerItemDestroyed(itemStack));
			}
		};
	}
}
