package org.springframework.samples.petclinic.ImportantEmailObjClasses;

import java.util.List;

public class EventOut {
	
	private List<Date> date;
	private List<Places> places;
	private String organization;
	private String foodSentence;
	private String organizationSentence;
	public EventOut(List<Date> date, List<Places> places, String organization, String foodSentence,
			String organizationSentence) {
		super();
		this.date = date;
		this.places = places;
		this.organization = organization;
		this.foodSentence = foodSentence;
		this.organizationSentence = organizationSentence;
	}
	public List<Date> getDate() {
		return date;
	}
	public void setDate(List<Date> date) {
		this.date = date;
	}
	public List<Places> getPlaces() {
		return places;
	}
	public void setPlaces(List<Places> places) {
		this.places = places;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getFoodSentence() {
		return foodSentence;
	}
	public void setFoodSentence(String foodSentence) {
		this.foodSentence = foodSentence;
	}
	public String getOrganizationSentence() {
		return organizationSentence;
	}
	public void setOrganizationSentence(String organizationSentence) {
		this.organizationSentence = organizationSentence;
	}
	
	

}
