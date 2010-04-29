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

import javax.management.MBeanServer;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.google.inject.tools.jmx.Manager;

import de.cosmocode.palava.core.CoreConfig;
import de.cosmocode.palava.core.lifecycle.Initializable;
import de.cosmocode.palava.core.lifecycle.LifecycleException;

/**
 * Binds the injector to a configured {@link MBeanServer}.
 *
 * @author Willi Schoenborn
 */
final class GuiceJmxService implements Initializable {

    private final MBeanServer server;
    
    private final Injector injector;
    
    private String domain;
    
    @Inject
    public GuiceJmxService(MBeanServer server, Injector injector) {
        this.server = Preconditions.checkNotNull(server, "MBeanServer");
        this.injector = Preconditions.checkNotNull(injector, "Injector");
    }
    
    @Inject(optional = true)
    void setApplication(@Named(CoreConfig.APPLICATION) String application) {
        // only inject if no domain was set already
        this.domain = domain == null ? Preconditions.checkNotNull(application, "Application") : domain;
    }
    
    @Inject(optional = true)
    void setDomain(@Named(GuiceJmxServiceConfig.DOMAIN) String domain) {
        this.domain = Preconditions.checkNotNull(domain, "Domain");
    }
    
    @Override
    public void initialize() throws LifecycleException {
        Manager.manage(server, domain, injector);
    }

}
