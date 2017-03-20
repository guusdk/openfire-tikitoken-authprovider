package org.tiki.tikitoken;

import org.jivesoftware.openfire.auth.AuthProvider;
import org.jivesoftware.openfire.auth.ConnectionException;
import org.jivesoftware.openfire.auth.InternalUnauthenticatedException;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.jivesoftware.openfire.user.UserNotFoundException;
import org.jivesoftware.util.JiveGlobals;


public class TikiTokenAuthProvider implements AuthProvider {
	private AuthProvider originalProvider; 

	public TikiTokenAuthProvider() {
		String originalProviderName = JiveGlobals.getProperty("org.tiki.tikitoken.originalProvider");
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
		TikiTokenQuery tokenQuery;
		
		if(password.startsWith("tt:") && password.length() > 3) { 
			tokenQuery = new TikiTokenQuery(username, password.substring(3));

			if (tokenQuery.isValid()){
				return;
			}
		}
		this.originalProvider.authenticate(username, password);
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
		return this.originalProvider.getIterations(username);
	}

	@Override
	public String getServerKey(String username) throws UnsupportedOperationException, UserNotFoundException {
		return this.originalProvider.getServerKey(username);
	}

	@Override
	public String getStoredKey(String username) throws UnsupportedOperationException, UserNotFoundException {
		return this.originalProvider.getStoredKey(username);
	}

}
