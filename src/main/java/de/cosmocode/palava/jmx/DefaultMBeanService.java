/**
 * Copyright 2010 CosmoCode GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.cosmocode.palava.jmx;

import java.util.Hashtable;
import java.util.Map;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.internal.Maps;

/**
 * Default implementation of the {@link MBeanService} interface which delegates
 * to the actual {@link MBeanServer}.
 *
 * @author Willi Schoenborn
 * @author Tobias Sarnowski
 */
final class DefaultMBeanService extends ForwardingMBeanServer implements MBeanService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultMBeanService.class);

    private final MBeanServer server;
    
    @Inject
    public DefaultMBeanService(MBeanServer server) {
        this.server = Preconditions.checkNotNull(server, "Server");
    }
    
    @Override
    protected MBeanServer delegate() {
        return server;
    }

    @Override
    public void register(Object bean) {
        Preconditions.checkNotNull(bean, "Bean");
        register(bean, "type", bean.getClass().getSimpleName());
    }

    @Override
    public void register(Object bean, String key, String value) {
        Preconditions.checkNotNull(bean, "Bean");
        Preconditions.checkNotNull(key, "Key");
        final Map<String, String> table = Maps.newHashMap();
        table.put("type", bean.getClass().getSimpleName());
        table.put(key, value);
        register(bean, table);
    }

    @Override
    public void register(Object bean, Map<String, String> table) {
        Preconditions.checkNotNull(bean, "Bean");
        Preconditions.checkNotNull(table, "Table");
        try {
            final String domain = bean.getClass().getPackage().getName();
            table.put("type", bean.getClass().getSimpleName());
            final ObjectName name = new ObjectName(domain, new Hashtable<String, String>(table));
            LOG.info("Registering MBean {} as {}", bean, name);
            server.registerMBean(bean, name);
        } catch (JMException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void unregister(Object bean) {
        Preconditions.checkNotNull(bean, "Bean");
        unregister(bean, "type", bean.getClass().getSimpleName());
    }

    @Override
    public void unregister(Object bean, String key, String value) {
        Preconditions.checkNotNull(bean, "Bean");
        Preconditions.checkNotNull(key, "Key");
        final Map<String, String> table = Maps.newHashMap();
        table.put("type", bean.getClass().getSimpleName());
        table.put(key, value);
        unregister(bean, table);
    }

    @Override
    public void unregister(Object bean, Map<String, String> table) {
        Preconditions.checkNotNull(bean, "Bean");
        Preconditions.checkNotNull(table, "Table");
        try {
            final String domain = bean.getClass().getPackage().getName();
            table.put("type", bean.getClass().getSimpleName());
            final ObjectName name = new ObjectName(domain, new Hashtable<String, String>(table));
            LOG.trace("Unregistering MBean {} ({})", bean, name);
            server.unregisterMBean(name);
        } catch (JMException e) {
            throw new IllegalStateException(e);
        }
    }

}
