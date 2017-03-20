package org.tiki.tikitoken;

import java.io.File;

import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.util.JiveGlobals;

public class TikiTokenPlugin implements Plugin {

	@Override
	public void initializePlugin(PluginManager manager, File pluginDirectory) {
		String classURL = "org.tiki.tikitoken.TikiTokenAuthProvider";
		String currentProvider = JiveGlobals.getProperty("provider.auth.className");

		if (!currentProvider.equals(classURL)) {
			JiveGlobals.setProperty("org.tiki.tikitoken.originalProvider", currentProvider);
		}
		JiveGlobals.setProperty("provider.auth.className", classURL);
	}

	@Override
	public void destroyPlugin() {
		String originalProvider = JiveGlobals.getProperty("org.tiki.tikitoken.originalProvider");
		JiveGlobals.setProperty("provider.auth.className", originalProvider);
	}
	
}
