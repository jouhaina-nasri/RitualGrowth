package com.yoterra.hippo.helpers;

public class HippoCommandLine {

	private static final String DOCKER = "--docker";
	boolean docker;
	
	public HippoCommandLine(String[] args) {
		// TODO CommandLineParser doesn't work due to spring boot options
		for( String arg : args ) if( DOCKER.equals(arg) ) docker = true;
	}
	
	public boolean isDockerEnvironment() {
		return docker;
	}
}
