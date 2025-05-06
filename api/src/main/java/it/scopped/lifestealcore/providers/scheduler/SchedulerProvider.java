package it.scopped.lifestealcore.providers.scheduler;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public interface SchedulerProvider {

    void timer(Runnable task, long initialDelay, long period, TimeUnit unit);

    void later(Runnable task, long delay, TimeUnit unit);

    void shutdown();

    ScheduledExecutorService scheduledExecutor();

}
