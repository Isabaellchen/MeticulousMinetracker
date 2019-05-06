package rocks.isor.meticulousminetracker.listeners;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoSet;
import org.bukkit.event.Listener;

@Module
public abstract class ListenerModule {

	@Binds
	@IntoSet
	public abstract Listener provideBlockMiningListener(BlockDestroyedListener blockMiningListener);

	@Binds
	@IntoSet
	public abstract Listener provideItemCraftingListener(ItemCraftingListener itemCraftingListener);

	@Binds
	@IntoSet
	public abstract Listener provideItemDestroyListener(ItemDestroyedListener itemDestroyListener);
}
