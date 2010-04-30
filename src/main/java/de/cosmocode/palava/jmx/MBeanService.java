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

import java.util.Map;

import javax.annotation.Nonnull;
import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * A service on top of {@link MBeanServer} which adds several
 * register and unregister methods which provide a more easy
 * api.
 *
 * @author Willi Schoenborn
 */
public interface MBeanService extends MBeanServer {

    /**
     * Registeres the given bean using its package name
     * as domain and its simple class name as a the type property.
     * 
     * <p>
     *   Using this method is equivalent to:
     *   {@code service.register(bean, "type", bean.getClass().getSimpleName()}
     * </p>
     * 
     * @param bean the bean being registered
     * @throws NullPointerException if bean is null
     * @throws IllegalStateException if an underlying {@link JMException} occured
     */
    void register(@Nonnull Object bean);
    
    /**
     * Registeres the given bean using its package name
     * as domain and the specified key and value as a property.
     * 
     * @param bean the bean being registered
     * @param key property key
     * @param value property value
     * @throws NullPointerException if bean or key is null
     * @throws IllegalStateException if an underlying {@link JMException} occured
     */
    void register(@Nonnull Object bean, @Nonnull String key, String value);
    
    /**
     * Registered the given bean using its package name
     * as domain and the specified table.
     * 
     * @param bean the bean being registered
     * @param table the table being passed to {@link ObjectName#ObjectName(String, java.util.Hashtable)}
     * @throws NullPointerException if bean or table is null
     * @throws IllegalStateException if an underlying {@link JMException} occured
     */
    void register(@Nonnull Object bean, @Nonnull Map<String, String> table);
    
    /**
     * Unregisters the given bean using its package name
     * as domain and its simple class name as a the type property.
     * 
     * <p>
     *   Using this method is equivalent to:
     *   {@code service.unregister(bean, "type", bean.getClass().getSimpleName()}
     * </p>
     * 
     * @param bean the bean being unregistered
     * @throws NullPointerException if bean is null
     * @throws IllegalStateException if an underlying {@link JMException} occured
     */
    void unregister(@Nonnull Object bean);

    /**
     * Unregisteres the given bean using its package name
     * as domain and the specified key and value as a property.
     * 
     * @param bean the bean being unregistered
     * @param key property key
     * @param value property value
     * @throws NullPointerException if bean or key is null
     * @throws IllegalStateException if an underlying {@link JMException} occured
     */
    void unregister(@Nonnull Object bean, @Nonnull String key, String value);

    /**
     * Unregistered the given bean using its package name
     * as domain and the specified table.
     * 
     * @param bean the bean being unregistered
     * @param table the table being passed to {@link ObjectName#ObjectName(String, java.util.Hashtable)}
     * @throws NullPointerException if bean or table is null
     * @throws IllegalStateException if an underlying {@link JMException} occured
     */
    void unregister(@Nonnull Object bean, @Nonnull Map<String, String> table);

}
