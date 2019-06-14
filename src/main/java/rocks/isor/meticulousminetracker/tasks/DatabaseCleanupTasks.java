package rocks.isor.meticulousminetracker.tasks;

import rocks.isor.meticulousminetracker.database.dao.BlockDestroyedDAO;
import rocks.isor.meticulousminetracker.database.dao.BlockPlacedDAO;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DatabaseCleanupTasks {

	@Inject
	public DatabaseCleanupTasks() {}

	@Inject
	public BlockDestroyedDAO blockDestroyedDAO;

	@Inject
	public BlockPlacedDAO blockPlacedDAO;

	public Runnable cleanUpTables() {
		return () -> {
			blockDestroyedDAO.cleanDestroyedBlocks();
			blockPlacedDAO.cleanPlacedBlocks();
		};
	}
}
