package travelplannerpcm.components;

import java.util.ArrayList;
import java.util.Collection;

import travelplannerpcm.interfaces.AirlineLogging;

public class AirlineLogger implements AirlineLogging{

	Collection<String> logs;
	
	public AirlineLogger() {
		this.logs = new ArrayList<String>();
	}
	
	@Override
	public void log(String entry) {
		//simulate logging and printing functionality
		this.logs.add(entry);
		System.out.println(entry);
	}
}
