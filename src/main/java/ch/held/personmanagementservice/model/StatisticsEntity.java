package ch.held.personmanagementservice.model;

import java.util.ArrayList;
import java.util.List;

import ch.held.personmanagementservice.statistics.impl.ErrorDetail;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Represents statistical data for person management operations.
 */
@XmlRootElement(name = "statistics")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "totalAddRequests", "totalValidRequests", "totalAddedPersons", "totalInvalidAddRequests", "detailedErrors" })
public class StatisticsEntity {

	@XmlElement(name = "totalAddRequests")
	private int totalAddRequests;

	@XmlElement(name = "totalInvalidAddRequests")
	private int totalInvalidAddRequests;

	@XmlElementWrapper(name = "detailedErrors")
	@XmlElement(name = "error")
	private List<ErrorDetail> detailedErrors = new ArrayList<>();

	@XmlElement(name = "totalValidRequests")
	private int totalValidRequests;

	@XmlElement(name = "totalAddedPersons")
	private int totalAddedPersons;

	public StatisticsEntity() {
		// No-arg constructor for instantiation and potential JPA or serialization/deserialization use cases.
	}

	// Add a single ErrorDetail to the list
	public void addDetailedError(ErrorDetail errorDetail) {
		this.detailedErrors.add(errorDetail);
	}

	public int getTotalAddRequests() {
		return totalAddRequests;
	}

	public void setTotalAddRequests(int totalAddRequests) {
		this.totalAddRequests = totalAddRequests;
	}

	public int getTotalInvalidAddRequests() {
		return totalInvalidAddRequests;
	}

	public void setTotalInvalidAddRequests(int totalInvalidAddRequests) {
		this.totalInvalidAddRequests = totalInvalidAddRequests;
	}

	public List<ErrorDetail> getDetailedErrors() {
		return detailedErrors;
	}

	public void setDetailedErrors(List<ErrorDetail> detailedErrors) {
		this.detailedErrors = detailedErrors;
	}

	public int getTotalValidRequests() {
		return totalValidRequests;
	}

	public void setTotalValidRequests(int totalValidRequests) {
		this.totalValidRequests = totalValidRequests;
	}

	public int getTotalAddedPersons() {
		return totalAddedPersons;
	}

	public void setTotalAddedPersons(int totalAddedPersons) {
		this.totalAddedPersons = totalAddedPersons;
	}
}
