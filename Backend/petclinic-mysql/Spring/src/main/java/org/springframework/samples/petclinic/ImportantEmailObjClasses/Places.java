package org.springframework.samples.petclinic.ImportantEmailObjClasses;

public class Places {
	
	private String bName;
	
	private String roomNumber;
	public Places(String bName, String roomNumber) {
		this.bName=bName;
	
		this.roomNumber=roomNumber;
	}
	public String getbName() {
		return bName;
	}
	public void setbName(String bName) {
		this.bName = bName;
	}
	
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
}
