package de.cosmocode.palava.jmx;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;

/**
 * Binds {@link MBeanServer} to {@link ManagementFactory#getPlatformMBeanServer()}.
 *
 * @author Willi Schoenborn
 */
public final class PlatformMBeanServerModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.install(new DefaultMBeanServiceModule());
    }

    @Provides
    @Singleton
    MBeanServer getMBeanServer() {
        return ManagementFactory.getPlatformMBeanServer();
    }

}
