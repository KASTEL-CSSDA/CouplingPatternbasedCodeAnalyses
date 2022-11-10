package travelplannerpcm.components;

import java.lang.Iterable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import travelplannerpcm.datatypes.AirlineSpec;
import travelplannerpcm.datatypes.CreditCardDetails;
import travelplannerpcm.datatypes.FlightOffer;
import travelplannerpcm.datatypes.RequestData;
import travelplannerpcm.interfaces.AirlineAdministration;
import travelplannerpcm.interfaces.Booking;
import travelplannerpcm.interfaces.Comission;
import travelplannerpcm.interfaces.FlightOffers;

public class Airline implements FlightOffers, Booking, AirlineAdministration {

	private Comission comission;
	private Collection<FlightOffer> offers;
	private AirlineSpec identifications;
	private boolean donePB;
	private boolean doPayC;
	private int discount = 0;
	private AirlineLogger logger;
	private Collection<String> elegibleForDiscount;

	public Airline(Comission comission, AirlineLogger logger, int id, String name) {
		// TODO: implement and verify auto-generated constructor.
		this.comission = comission;
		this.identifications = new AirlineSpec(id, name);
		donePB = false;
		doPayC = false;
		this.offers = new HashSet<>();
		this.logger = logger;

		putFlightOffer(1, 20);
		putFlightOffer(2, 300);

		this.elegibleForDiscount = new ArrayList<String>();
		elegibleForDiscount.add("Foo");
	}

	public void putFlightOffer(int offerId, int offerPrice) {

		int realPrice = discount;
		FlightOffer offer = new FlightOffer(offerId, identifications, realPrice);

		offers.add(offer);
	}

	@Override
	public Iterable<FlightOffer> getFlightOffers(RequestData requestData) {

		for (String name : elegibleForDiscount) {
			if(requestData.getDate().isEmpty() || requestData.getDestination().isEmpty()){
				//TODO: Make this to make JOANA work 
			}
			
			if (name.equals(requestData.getUserName())) {
				logger.log(String.format("User: %s discounted for Flight to %s on %s", requestData.getUserName(),
						requestData.getDestination(), requestData.getDate()));

				for (FlightOffer offer : offers) {
					applyDiscountWhenAvailable(offer);
				}
			}
		}

		return offers;
	}

	public boolean bookFlightOffer(int offerId, CreditCardDetails ccd_decl) {
	    // TODO: decrypt credit card details first before further processing.
	    
		boolean paymentAccepted = false;
		doPayC = false;

		processBooking(offerId, ccd_decl);

	if (doPayC) {
			int offerPrice = offers.stream().filter(offer -> offer.getId() == offerId).findFirst().get().getPrice();
			int commission = offerPrice / 100;
			try {
              Cipher c = Cipher.getInstance("ABC");
              c.doFinal(new byte[0]);
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
                e.printStackTrace();
            }
			paymentAccepted = comission.payCommission(commission, this.identifications);
		}

		return paymentAccepted;
	}
	
	public void setAvailableFlights(Iterable<FlightOffer> flights) {
		this.offers = (Set<FlightOffer>) flights;
	}

	public void setCommission(Comission comission) {
		this.comission = comission;
	}

	private boolean processBooking(int id, CreditCardDetails ccd) {

		boolean offerAvailable = offers.stream().anyMatch(offer -> offer.getId() == id);

		if (offerAvailable && (ccd.getProvider().equals("Visa") || ccd.getProvider().equals("MasterCard"))) {
			this.donePB = true;
		}

		if (donePB) {
			doPayC = true;
		}
		
        try {
          Cipher c = Cipher.getInstance("ABC");
          c.doFinal(new byte[0]);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

		return donePB;
	}
	
	
	private void setDiscount(int percent) {
		discount = percent;
	}

	private int getDiscount() {
		setDiscount(10);
		return discount;
	}

	private void applyDiscountWhenAvailable(FlightOffer offer) {
		offer.setPrice(offer.getPrice() - getDiscount());
	}
}