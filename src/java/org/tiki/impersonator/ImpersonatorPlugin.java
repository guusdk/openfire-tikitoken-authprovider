package org.tiki.impersonator;

import java.io.File;

import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.util.JiveGlobals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImpersonatorPlugin implements Plugin {
	private final static Logger Log = LoggerFactory.getLogger( ImpersonatorPlugin.class );

	@Override
	public void initializePlugin(PluginManager manager, File pluginDirectory) {
		String originalProvider = JiveGlobals.getProperty("provider.auth.className");
		JiveGlobals.setProperty("org.tiki.impersonator.originalProvider", "org.jivesoftware.openfire.ldap.LdapAuthProvider");
		JiveGlobals.setProperty("provider.auth.className", "org.tiki.impersonator.ImpersonatorAuthProvider");
	}

	@Override
	public void destroyPlugin() {
		String originalProvider = JiveGlobals.getProperty("org.tiki.impersonator.originalProvider");
		JiveGlobals.setProperty("provider.auth.className", originalProvider);
	}
	
}