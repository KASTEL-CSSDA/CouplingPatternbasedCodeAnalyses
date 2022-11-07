package travelplannerpcm.components;

import travelplannerpcm.datatypes.AirlineSpec;
import travelplannerpcm.datatypes.CreditCardDetails;
import travelplannerpcm.datatypes.FlightOffer;
import travelplannerpcm.datatypes.RequestData;
import travelplannerpcm.datatypes.UserCredentials;
import travelplannerpcm.interfaces.BookingConfirmation;
import travelplannerpcm.interfaces.BookingSelection;
import travelplannerpcm.interfaces.CreditCardConfiguration;
import travelplannerpcm.interfaces.Confirmation;
import travelplannerpcm.interfaces.DeclassificationConfirmation;
import travelplannerpcm.interfaces.Input;
import travelplannerpcm.interfaces.UserIdentityConfiguration;


/**
 * UserSimulation is optional for test purposes. We have to think about if we can omit the user interaction and "replace" them with PCM usage profiles
 * @author Frederik Reiche
 *
 */
public class UserInterface implements BookingConfirmation, Input, Confirmation, UserIdentityConfiguration{

	private BookingSelection bookingSelection;
	private DeclassificationConfirmation confirmation;
	private CreditCardConfiguration creditCardConfiguration;
	private boolean ok = false;
	private UserCredentials credentials;
	
	public UserInterface(BookingSelection bookingSelection, DeclassificationConfirmation confirmation, CreditCardConfiguration creditCardConfiguration) {
		this.bookingSelection = bookingSelection;
		this.confirmation = confirmation;
		this.creditCardConfiguration = creditCardConfiguration;
		setCredentials("Foo", "Bar");
		this.creditCardConfiguration.setCreditCard(new CreditCardDetails("Visa", 8));
	}

	public void setCredentials(String name, String passwort) {
		this.credentials = new UserCredentials(name, passwort);
	}


	@Override
	public void ok() {
		System.out.println("Happy");
		ok = true;
	}


	@Override
	public void confirmRelease(CreditCardDetails ccd, AirlineSpec airline) {
		confirmation.getDeclassifiedCCD(ccd, airline);
	}
	
	public void setBookingSelection(BookingSelection bookingSelection) {
		this.bookingSelection = bookingSelection;
	}




	@Override
	public void getInput() {
		RequestData request = new RequestData(credentials.getName(),"Frankfurt", "19.09.1999");
		bookingSelection.getFlightOffers(request);
	}


	
	@Override
	public void getSingleSelection(Iterable<FlightOffer> flightOffers) {

		FlightOffer best = null;
		//get one with best price, assume that every flight offer is valid. 
		for(FlightOffer fo : flightOffers) {
			if(best == null) {
				best = fo;
			}
			
			if(best.getPrice() > fo.getPrice()) {
				best = fo;
			}
		}
		
		
		if(flightOffers.iterator().hasNext()) {
			FlightOffer selected = flightOffers.iterator().next(); 
			bookingSelection.bookSelected(selected);
		}
		
		if(!ok) {
			System.out.println("Oh no, something went wrong");
		}
	}
}
