package it.scopped.lifestealcore.providers.scheduler;

import it.scopped.lifestealcore.LifestealCore;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SchedulerProviderImpl implements SchedulerProvider {

    private final LifestealCore plugin;
    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    public SchedulerProviderImpl(LifestealCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public void timer(Runnable task, long initialDelay, long period, TimeUnit unit) {
        scheduledExecutor.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    @Override
    public void later(Runnable task, long delay, TimeUnit unit) {
        scheduledExecutor.schedule(task, delay, unit);
    }

    public void shutdown() {
        scheduledExecutor.shutdown();

        try {
            if (!scheduledExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                plugin.loggerProvider().warn("ScheduledExecutor did not terminate in time.");
            }
        } catch (InterruptedException e) {
            plugin.loggerProvider().error("Executor shutdown interrupted.", e);
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public ScheduledExecutorService scheduledExecutor() {
        return scheduledExecutor;
    }

}