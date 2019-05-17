package org.springframework.samples.petclinic.ImportantEmailObjClasses;

public class Date {

	private String day;
	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDateOfMonth() {
		return dateOfMonth;
	}

	public void setDateOfMonth(String dateOfMonth) {
		this.dateOfMonth = dateOfMonth;
	}

	public String getTimeOfDay() {
		return timeOfDay;
	}

	public void setTimeOfDay(String timeOfDay) {
		this.timeOfDay = timeOfDay;
	}

	private String month;
	private String dateOfMonth;
	private String timeOfDay;
	
	 public Date(String day, String month, String dateOfMonth, String timeOfDay){
		this.day=day;
		this.month=month;
		this.dateOfMonth=dateOfMonth;
		this.timeOfDay=timeOfDay;
	}
}
