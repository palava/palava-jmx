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

import com.google.inject.Inject;
import de.cosmocode.palava.core.lifecycle.Disposable;
import de.cosmocode.palava.core.lifecycle.Initializable;
import de.cosmocode.palava.core.lifecycle.LifecycleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.*;
import java.util.Hashtable;

/**
 * @author Tobias Sarnowski
 */
public abstract class MBeanRegistered implements Initializable, Disposable {
    private final Logger LOG;

    private MBeanServer mBeanServer;
    private ObjectName objectName;

    private static ObjectName newObjectName(Class cls) {
        return newObjectName(cls, "type", cls.getSimpleName());
    }

    private static ObjectName newObjectName(Class cls, String key, String value) {
        Hashtable<String,String> keys = new Hashtable<String,String>();
        keys.put(key, value);
        return newObjectName(cls, keys);
    }

    private static ObjectName newObjectName(Class cls, Hashtable<String,String> keys) {
        keys.put("type", cls.getSimpleName());
        return newObjectName(cls.getPackage().getName(), keys);
    }

    private static ObjectName newObjectName(String domain, String key, String value) {
        Hashtable<String,String> keys = new Hashtable<String,String>();
        keys.put(key, value);
        return newObjectName(domain, keys);
    }

    private static ObjectName newObjectName(String domain, Hashtable<String,String> keys) {
        try {
            return new ObjectName(domain, keys);
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



    public MBeanRegistered(Class cls) {
        this(cls, newObjectName(cls));
    }

    public MBeanRegistered(Class cls, String objectName) {
        this(cls, newObjectName(objectName));
    }

    public MBeanRegistered(Class cls, String key, String value) {
        this(cls, newObjectName(cls, key, value));
    }

    public MBeanRegistered(Class cls, String objectName, String key, String value) {
        this(cls, newObjectName(objectName, key, value));
    }

    public MBeanRegistered(Class cls, Hashtable<String,String> keys) {
        this(cls, newObjectName(cls, keys));
    }

    public MBeanRegistered(Class cls, String objectName, Hashtable<String, String> keys) {
        this(cls, newObjectName(objectName, keys));
    }

    /**
     * Registers the instance under the given name.
     * @param cls
     * @param objectName
     */
    public MBeanRegistered(Class cls, ObjectName objectName) {
        LOG = LoggerFactory.getLogger(cls);
        this.objectName = objectName;
    }

    @Inject(optional = true)
    public void setMBeanServer(MBeanServer mBeanServer) {
        this.mBeanServer = mBeanServer;
    }

    @Override
    public void initialize() throws LifecycleException {
        if (mBeanServer != null) {
            LOG.info("Registering MBean {} as {}", this, objectName);
            try {
                mBeanServer.registerMBean(this, objectName);
            } catch (JMException e) {
                throw new LifecycleException(e);
            }
        } else {
            LOG.trace("No MBeanServer bound; don't activating MBean {}", this);
        }
    }

    @Override
    public void dispose() throws LifecycleException {
        if (mBeanServer != null) {
            LOG.trace("Unregistering MBean {} ({})", this, objectName);
            try {
                mBeanServer.unregisterMBean(objectName);
            } catch (JMException e) {
                throw new LifecycleException(e);
            }
        }
    }
}