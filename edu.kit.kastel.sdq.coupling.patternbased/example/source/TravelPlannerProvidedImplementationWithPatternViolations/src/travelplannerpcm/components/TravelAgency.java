package travelplannerpcm.components;

import java.lang.Iterable;
import java.util.HashMap;
import java.util.Map;


import travelplannerpcm.datatypes.AirlineSpec;
import travelplannerpcm.datatypes.FlightOffer;
import travelplannerpcm.datatypes.RequestData;
import travelplannerpcm.interfaces.Comission;
import travelplannerpcm.interfaces.FlightOffers;

public class TravelAgency implements FlightOffers, Comission {

	private FlightOffers flightOffers;
	private Map<AirlineSpec, Integer> comissions;
	
	public TravelAgency(FlightOffers flightOffers) {
		// TODO: implement and verify auto-generated constructor.
		this.flightOffers = flightOffers;
		this.comissions = new HashMap<>();

	}
	
	@Override
	public Iterable<FlightOffer> getFlightOffers(RequestData requestData) {
		return flightOffers.getFlightOffers(requestData);
	}

	@Override
	public boolean payCommission(int value, AirlineSpec airline) {
		Integer newComission = comissions.get(airline);

		if (newComission == null) {
			newComission = value;
		} else {
			newComission += value;
		}

		comissions.put(airline, newComission);

		return true;
	}
}