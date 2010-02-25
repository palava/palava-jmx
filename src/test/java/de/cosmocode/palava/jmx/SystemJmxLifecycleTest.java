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

import java.util.Properties;

import org.junit.Test;

import de.cosmocode.palava.core.CoreConfig;
import de.cosmocode.palava.core.Framework;
import de.cosmocode.palava.core.Palava;

public class SystemJmxLifecycleTest {
	private Properties defaultConfiguration() {
		Properties prop = new Properties();
		prop.put(CoreConfig.APPLICATION, SystemJmxTestApplication.class.getName());
		return prop;
	}

	private Framework startFramework(Properties configuration) {
		Framework framework = Palava.createFramework(configuration);
		framework.start();
		return framework;
	}

	private void stopFramework(Framework framework) {
		framework.stop();
	}

	/**
	 * tests the behaviour with the default test application
	 */
	@Test
	public void simpleConfiguration() {
		Properties conf = defaultConfiguration();
		Framework f = startFramework(conf);

		stopFramework(f);
	}
}
