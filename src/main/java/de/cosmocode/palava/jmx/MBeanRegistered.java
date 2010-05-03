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
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import de.cosmocode.palava.core.lifecycle.Disposable;
import de.cosmocode.palava.core.lifecycle.Initializable;
import de.cosmocode.palava.core.lifecycle.LifecycleException;

/**
 * Abstract base class which handles jmx de/registration.
 *
 * @deprecated use {@link MBeanService} instead which provides a less invasive way
 *             to provide optional jmx support
 * 
 * @since 1.2
 * @author Tobias Sarnowski
 * @author Willi Schoenborn
 */
@Deprecated
public abstract class MBeanRegistered implements Initializable, Disposable {
    
    private final Logger log;

    private MBeanServer mBeanServer;
    private ObjectName objectName;

    public MBeanRegistered(Class<?> cls) {
        this(cls, newObjectName(cls));
    }

    public MBeanRegistered(Class<?> cls, String objectName) {
        this(cls, newObjectName(objectName));
    }

    public MBeanRegistered(Class<?> cls, String key, String value) {
        this(cls, newObjectName(cls, key, value));
    }

    public MBeanRegistered(Class<?> cls, String objectName, String key, String value) {
        this(cls, newObjectName(objectName, key, value));
    }

    public MBeanRegistered(Class<?> cls, Hashtable<String, String> keys) {
        this(cls, newObjectName(cls, keys));
    }

    public MBeanRegistered(Class<?> cls, String objectName, Hashtable<String, String> keys) {
        this(cls, newObjectName(objectName, keys));
    }

    public MBeanRegistered(Class<?> cls, ObjectName objectName) {
        log = LoggerFactory.getLogger(cls);
        this.objectName = objectName;
    }

    private static ObjectName newObjectName(Class<?> cls) {
        return newObjectName(cls, "type", cls.getSimpleName());
    }

    private static ObjectName newObjectName(Class<?> cls, String key, String value) {
        final Map<String, String> keys = new Hashtable<String, String>();
        keys.put(key, value);
        return newObjectName(cls, keys);
    }

    private static ObjectName newObjectName(Class<?> cls, Map<String, String> keys) {
        keys.put("type", cls.getSimpleName());
        return newObjectName(cls.getPackage().getName(), keys);
    }

    private static ObjectName newObjectName(String domain, String key, String value) {
        final Map<String, String> keys = new Hashtable<String, String>();
        keys.put(key, value);
        return newObjectName(domain, keys);
    }

    private static ObjectName newObjectName(String domain, Map<String, String> keys) {
        try {
            return new ObjectName(domain, new Hashtable<String, String>(keys));
        } catch (MalformedObjectNameException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static ObjectName newObjectName(String objectName) {
        try {
            return new ObjectName(objectName);
        } catch (MalformedObjectNameException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Inject(optional = true)
    public void setMBeanServer(MBeanServer mBeanServer) {
        this.mBeanServer = mBeanServer;
    }

    @Override
    public void initialize() throws LifecycleException {
        if (mBeanServer != null) {
            log.info("Registering MBean {} as {}", this, objectName);
            try {
                mBeanServer.registerMBean(this, objectName);
            } catch (JMException e) {
                throw new LifecycleException(e);
            }
        } else {
            log.trace("No MBeanServer bound; don't activating MBean {}", this);
        }
    }

    @Override
    public void dispose() throws LifecycleException {
        if (mBeanServer != null) {
            log.trace("Unregistering MBean {} ({})", this, objectName);
            try {
                mBeanServer.unregisterMBean(objectName);
            } catch (JMException e) {
                throw new LifecycleException(e);
            }
        }
    }
}
