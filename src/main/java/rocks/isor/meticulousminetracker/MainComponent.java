package rocks.isor.meticulousminetracker;

import dagger.Component;
import rocks.isor.meticulousminetracker.database.DatabaseModule;
import rocks.isor.meticulousminetracker.listeners.ListenerModule;

import javax.inject.Singleton;

@Singleton
@Component(
		modules = {
				DatabaseModule.class,
				PluginModule.class,
				ListenerModule.class
		})
public interface MainComponent {
	void inject(Main main);
}
