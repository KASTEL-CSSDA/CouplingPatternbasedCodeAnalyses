package travelplannerpcm.datatypes;
public class FlightOffer {
	
	private int id;
	private AirlineSpec airline;
	private int price;

	private String from;
	private String to; 
	
	
	public FlightOffer(int id, AirlineSpec airline, int price) {
		this.id = id;
		this.airline = airline;
		this.price = price;
	}
	
	public int getId() {
	    return id;
	}
	
	public void setId(int id) {
	    this.id = id;
	}

	public AirlineSpec getAirline() {
		return airline;
	}

	public void setAirline(AirlineSpec airline) {
		this.airline = airline;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	
}