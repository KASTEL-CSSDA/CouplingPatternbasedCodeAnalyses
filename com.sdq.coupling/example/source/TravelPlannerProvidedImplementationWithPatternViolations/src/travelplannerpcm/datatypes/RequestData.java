package travelplannerpcm.datatypes;
public class RequestData {
	
	private String userName;
	private String destination;
	private String date;
	
	public RequestData() {
		// TODO: Implement and verify auto-generated constructor.
		this.destination = "";
		this.date = "";
	}
	
	public RequestData(String userName, String destination, String date) {
		// TODO: Implement and verify auto-generated constructor.
		this.destination = destination;
		this.date = date;
	}
	
	public String getDestination() {
	    return destination;
	}
	
	public void setDestination(String destination) {
	    this.destination = destination;
	}
	
	public String getDate() {
	    return date;
	}
	
	public void setDate(String date) {
	    this.date = date;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}