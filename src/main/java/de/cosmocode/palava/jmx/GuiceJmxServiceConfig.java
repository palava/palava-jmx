package de.cosmocode.palava.jmx;

/**
 * Static constant holder class for guice jmx config key names.
 *
 * @author Willi Schoenborn
 */
public final class GuiceJmxServiceConfig {

    public static final String PREFIX = JmxConfig.PREFIX + "jmx.";
    
    public static final String DOMAIN = PREFIX + "domain";

    private GuiceJmxServiceConfig() {
        
    }
    
}
