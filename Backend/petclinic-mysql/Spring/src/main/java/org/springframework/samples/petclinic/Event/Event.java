package org.springframework.samples.petclinic.Event;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;
/**
 * 
 * @author Opeyemi Abass
 *
 */

@Entity
@Table(name = "Event")
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Integer Id;
	@Column(name = "Name", length = 1000000 )
	@NotFound(action = NotFoundAction.IGNORE)
	private String Name;
	@Column(name = "Time",length = 1000000)
	@NotFound(action = NotFoundAction.IGNORE)
	private String importantInfo;
	@Column(name = "location",length = 1000000)
	@NotFound(action = NotFoundAction.IGNORE)
	private String location;
	@Column(name = "Food",length = 1000000)
	@NotFound(action = NotFoundAction.IGNORE)
	private String Food;
	 @Column(name = "organization",length = 1000000)
	 @NotFound(action = NotFoundAction.IGNORE)
	private String organization;


	 /**
	  * 
	  * @param name The organization name or event name
	  * @param importantInfo  The encoded words,ner,pos that has the date and time
	  * @param location The encoded sentence in the email that has the location of the event
	  * @param foodSen  The encoded sentence in the email that has the food
	  * @param organizationSen The encoded sentence in the email that has the organization name
	  */
	public Event(String name, String importantInfo, String location, String foodSen, String organizationSen) {
		super();
		
		this.Name = name;
		this.importantInfo = importantInfo;
		this.location = location;
		this.Food = foodSen;
		this.organization = organizationSen;
	}
	public Event() {

	}

	public String getorganName() {
		return Name;
	}

	public void organName(String name) {
		this.Name = name;
	}

	public String getimportantInfo() {
		return importantInfo;
	}

	public void importantInfo(String importantInfo) {
		this.importantInfo = importantInfo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String place) {
		this.location = place;
	}

	public String getFoodSen() {
		return Food;
	}

	public void setFoodSen(String food) {
		this.Food = food;
	}

	public String getOrganizationSen() {
		return organization;
	}

	public void setOrganizationSen(String organization) {
		this.organization = organization;
	}
	

	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		this.Id = id;
	}
	
	 @Override
	    public String toString() {
	        return new ToStringCreator(this)

	                .append("id", getId())
	                .append("Name", getorganName())
	                .append("importantInfo", getimportantInfo())
	                .append("Location", getLocation())
	                .append("FoodSen", getFoodSen())
	                .append("OrganizationSen",getOrganizationSen()).toString();
	    }
}


