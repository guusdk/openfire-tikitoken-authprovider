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
		String classURL = "org.tiki.impersonator.ImpersonatorAuthProvider";
		String currentProvider = JiveGlobals.getProperty("provider.auth.className");

		if (!currentProvider.equals(classURL)) {
			JiveGlobals.setProperty("org.tiki.impersonator.originalProvider", currentProvider);
			JiveGlobals.setProperty("provider.auth.className", classURL);
		}
	}

	@Override
	public void destroyPlugin() {
		String originalProvider = JiveGlobals.getProperty("org.tiki.impersonator.originalProvider");
		JiveGlobals.setProperty("provider.auth.className", originalProvider);
	}
	
}