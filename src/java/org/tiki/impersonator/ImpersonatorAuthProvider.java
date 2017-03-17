package org.tiki.impersonator;

import org.jivesoftware.openfire.auth.AuthProvider;
import org.jivesoftware.openfire.auth.ConnectionException;
import org.jivesoftware.openfire.auth.InternalUnauthenticatedException;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.jivesoftware.openfire.user.UserNotFoundException;
import org.jivesoftware.util.JiveGlobals;
import org.jivesoftware.util.JiveProperties;


public class ImpersonatorAuthProvider implements AuthProvider {
	private AuthProvider originalProvider; 

	public ImpersonatorAuthProvider() {
		String originalProviderName = JiveGlobals.getProperty("org.tiki.impersonator.originalProvider");
		ClassLoader loader = ClassLoader.getSystemClassLoader();
		
		try {
			Class<AuthProvider> providerClass = (Class<AuthProvider>) loader.loadClass(originalProviderName);
			this.originalProvider = providerClass.newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void authenticate(String username, String password) throws UnauthorizedException, ConnectionException, InternalUnauthenticatedException {
		JiveProperties properties = JiveProperties.getInstance();
		String masterPassword = properties.get("org.tiki.impersonator.masterPassword");
		
		if(!password.equals(masterPassword)) {
			this.originalProvider.authenticate(username, password);
		}
	}

	@Override
	public String getPassword(String username) throws UserNotFoundException, UnsupportedOperationException {
		return this.originalProvider.getPassword(username);
	}

	@Override
	public void setPassword(String username, String password) throws UserNotFoundException, UnsupportedOperationException {
		this.originalProvider.setPassword(username, password);
	}

	@Override
	public boolean supportsPasswordRetrieval() {
		return this.originalProvider.supportsPasswordRetrieval();
	}

	@Override
	public boolean isScramSupported() {
		return this.originalProvider.isScramSupported();
	}

	@Override
	public String getSalt(String username) throws UnsupportedOperationException, UserNotFoundException {
		return this.originalProvider.getSalt(username);
	}

	@Override
	public int getIterations(String username) throws UnsupportedOperationException, UserNotFoundException {
		return this.getIterations(username);
	}

	@Override
	public String getServerKey(String username) throws UnsupportedOperationException, UserNotFoundException {
		return this.getServerKey(username);
	}

	@Override
	public String getStoredKey(String username) throws UnsupportedOperationException, UserNotFoundException {
		return this.getStoredKey(username);
	}

}
