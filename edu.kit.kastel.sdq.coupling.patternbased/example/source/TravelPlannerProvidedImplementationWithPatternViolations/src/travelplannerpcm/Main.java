package travelplannerpcm;

import edu.kit.joana.ui.annotations.EntryPoint;
import travelplannerpcm.components.Airline;
import travelplannerpcm.components.AirlineLogger;
import travelplannerpcm.components.CreditCardCenter;
import travelplannerpcm.components.TravelAgency;
import travelplannerpcm.components.TravelPlanner;
import travelplannerpcm.components.UserInterface;


public class Main {
    
    @EntryPoint(tag="coupling")
	public static void main(String[] args) {
			
		Airline airline = new Airline(null, new AirlineLogger() ,0, "FlyingDonkey");
		TravelAgency travelAgency = new TravelAgency(airline);
		airline.setCommission(travelAgency);
	
		
		CreditCardCenter creditCardCenter = new CreditCardCenter(null);
		UserInterface simulation = new UserInterface(null, creditCardCenter, creditCardCenter);
		
		creditCardCenter.setConfirmation(simulation);
		
		TravelPlanner planner = new TravelPlanner(travelAgency, airline, creditCardCenter, simulation, simulation);
		creditCardCenter.setDeclassificationReceiver(planner);
		simulation.setBookingSelection(planner);
		
		planner.startScenario();
	}

}
