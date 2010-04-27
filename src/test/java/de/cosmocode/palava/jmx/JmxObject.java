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

import javax.management.InstanceNotFoundException;
import javax.management.JMException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.google.inject.Inject;

import de.cosmocode.palava.core.lifecycle.Disposable;
import de.cosmocode.palava.core.lifecycle.LifecycleException;

/**
 * Dummy service.
 *
 * @author Willi Schoenborn
 */
public class JmxObject implements Disposable, JmxObjectMBean {
    
    private ObjectName mBeanName;
    private MBeanServer mBeanServer;

    @Inject
    public JmxObject(MBeanServer mBeanServer) throws JMException {
        mBeanName = new ObjectName("de.cosmocode.palava.jmx:type=JmxTest");
        this.mBeanServer = mBeanServer;
        mBeanServer.registerMBean(this, mBeanName);
    }

    public String getWorld() {
        return "Hello World!";
    }

    @Override
    public void dispose() throws LifecycleException {
        try {
            mBeanServer.unregisterMBean(mBeanName);
        } catch (InstanceNotFoundException e) {
            throw new LifecycleException(e);
        } catch (MBeanRegistrationException e) {
            throw new LifecycleException(e);
        }
    }
    
}
