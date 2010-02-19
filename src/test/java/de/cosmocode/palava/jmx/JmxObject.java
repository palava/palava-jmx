/**
 * palava - a java-php-bridge
 * Copyright (C) 2007-2010  CosmoCode GmbH
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
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
