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

import java.io.ObjectInputStream;
import java.util.Set;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.InvalidAttributeValueException;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.NotCompliantMBeanException;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.OperationsException;
import javax.management.QueryExp;
import javax.management.ReflectionException;
import javax.management.loading.ClassLoaderRepository;

import com.google.common.collect.ForwardingObject;

/**
 * Abstract decorator for {@link MBeanServer}s.
 *
 * @author Willi Schoenborn
 */
abstract class ForwardingMBeanServer extends ForwardingObject implements MBeanServer {

    @Override
    protected abstract MBeanServer delegate();
    
    @Override
    public void addNotificationListener(ObjectName name, NotificationListener listener, NotificationFilter filter,
            Object handback) throws InstanceNotFoundException {
        delegate().addNotificationListener(name, listener, filter, handback);
    }

    @Override
    public void addNotificationListener(ObjectName name, ObjectName listener, NotificationFilter filter, 
        Object handback) throws InstanceNotFoundException {
        delegate().addNotificationListener(name, listener, filter, handback);
    }

    @Override
    public ObjectInstance createMBean(String className, ObjectName name, Object[] params, String[] signature)
        throws ReflectionException, InstanceAlreadyExistsException, MBeanException,
            NotCompliantMBeanException {
        return delegate().createMBean(className, name, params, signature);
    }

    @Override
    public ObjectInstance createMBean(String className, ObjectName name, ObjectName loaderName, Object[] params,
            String[] signature) throws ReflectionException, InstanceAlreadyExistsException, 
            MBeanException, NotCompliantMBeanException, InstanceNotFoundException {
        return delegate().createMBean(className, name, loaderName, params, signature);
    }

    @Override
    public ObjectInstance createMBean(String className, ObjectName name, ObjectName loaderName)
        throws ReflectionException, InstanceAlreadyExistsException, MBeanException,
            NotCompliantMBeanException, InstanceNotFoundException {
        return delegate().createMBean(className, name, loaderName);
    }

    @Override
    public ObjectInstance createMBean(String className, ObjectName name) throws ReflectionException,
            InstanceAlreadyExistsException, MBeanException, NotCompliantMBeanException {
        return delegate().createMBean(className, name);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ObjectInputStream deserialize(ObjectName name, byte[] data) throws OperationsException {
        return delegate().deserialize(name, data);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ObjectInputStream deserialize(String className, byte[] data) 
        throws OperationsException, ReflectionException {
        return delegate().deserialize(className, data);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ObjectInputStream deserialize(String className, ObjectName loaderName, byte[] data)
        throws OperationsException, ReflectionException {
        return delegate().deserialize(className, loaderName, data);
    }

    @Override
    public Object getAttribute(ObjectName name, String attribute) throws MBeanException, AttributeNotFoundException,
            InstanceNotFoundException, ReflectionException {
        return delegate().getAttribute(name, attribute);
    }

    @Override
    public AttributeList getAttributes(ObjectName name, String[] attributes) throws InstanceNotFoundException,
            ReflectionException {
        return delegate().getAttributes(name, attributes);
    }

    @Override
    public ClassLoader getClassLoader(ObjectName loaderName) throws InstanceNotFoundException {
        return delegate().getClassLoader(loaderName);
    }

    @Override
    public ClassLoader getClassLoaderFor(ObjectName mbeanName) throws InstanceNotFoundException {
        return delegate().getClassLoaderFor(mbeanName);
    }

    @Override
    public ClassLoaderRepository getClassLoaderRepository() {
        return delegate().getClassLoaderRepository();
    }

    @Override
    public String getDefaultDomain() {
        return delegate().getDefaultDomain();
    }

    @Override
    public String[] getDomains() {
        return delegate().getDomains();
    }

    @Override
    public Integer getMBeanCount() {
        return delegate().getMBeanCount();
    }

    @Override
    public MBeanInfo getMBeanInfo(ObjectName name) throws InstanceNotFoundException, IntrospectionException,
            ReflectionException {
        return delegate().getMBeanInfo(name);
    }

    @Override
    public ObjectInstance getObjectInstance(ObjectName name) throws InstanceNotFoundException {
        return delegate().getObjectInstance(name);
    }

    @Override
    public Object instantiate(String className, Object[] params, String[] signature) throws ReflectionException,
            MBeanException {
        return delegate().instantiate(className, params, signature);
    }

    @Override
    public Object instantiate(String className, ObjectName loaderName, Object[] params, String[] signature)
        throws ReflectionException, MBeanException, InstanceNotFoundException {
        return delegate().instantiate(className, loaderName, params, signature);
    }

    @Override
    public Object instantiate(String className, ObjectName loaderName) throws ReflectionException, MBeanException,
            InstanceNotFoundException {
        return delegate().instantiate(className, loaderName);
    }

    @Override
    public Object instantiate(String className) throws ReflectionException, MBeanException {
        return delegate().instantiate(className);
    }

    @Override
    public Object invoke(ObjectName name, String operationName, Object[] params, String[] signature)
        throws InstanceNotFoundException, MBeanException, ReflectionException {
        return delegate().invoke(name, operationName, params, signature);
    }

    @Override
    public boolean isInstanceOf(ObjectName name, String className) throws InstanceNotFoundException {
        return delegate().isInstanceOf(name, className);
    }

    @Override
    public boolean isRegistered(ObjectName name) {
        return delegate().isRegistered(name);
    }

    @Override
    public Set<ObjectInstance> queryMBeans(ObjectName name, QueryExp query) {
        return delegate().queryMBeans(name, query);
    }

    @Override
    public Set<ObjectName> queryNames(ObjectName name, QueryExp query) {
        return delegate().queryNames(name, query);
    }

    @Override
    public ObjectInstance registerMBean(Object object, ObjectName name) throws InstanceAlreadyExistsException,
            MBeanRegistrationException, NotCompliantMBeanException {
        return delegate().registerMBean(object, name);
    }

    @Override
    public void removeNotificationListener(ObjectName name, NotificationListener listener, NotificationFilter filter,
            Object handback) throws InstanceNotFoundException, ListenerNotFoundException {
        delegate().removeNotificationListener(name, listener, filter, handback);
    }

    @Override
    public void removeNotificationListener(ObjectName name, NotificationListener listener)
        throws InstanceNotFoundException, ListenerNotFoundException {
        delegate().removeNotificationListener(name, listener);
    }

    @Override
    public void removeNotificationListener(ObjectName name, ObjectName listener, NotificationFilter filter,
            Object handback) throws InstanceNotFoundException, ListenerNotFoundException {
        delegate().removeNotificationListener(name, listener, filter, handback);
    }

    @Override
    public void removeNotificationListener(ObjectName name, ObjectName listener) throws InstanceNotFoundException,
            ListenerNotFoundException {
        delegate().removeNotificationListener(name, listener);
    }

    @Override
    public void setAttribute(ObjectName name, Attribute attribute) throws InstanceNotFoundException,
            AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
        delegate().setAttribute(name, attribute);
    }

    @Override
    public AttributeList setAttributes(ObjectName name, AttributeList attributes) throws InstanceNotFoundException,
            ReflectionException {
        return delegate().setAttributes(name, attributes);
    }

    @Override
    public void unregisterMBean(ObjectName name) throws InstanceNotFoundException, MBeanRegistrationException {
        delegate().unregisterMBean(name);
    }

}
