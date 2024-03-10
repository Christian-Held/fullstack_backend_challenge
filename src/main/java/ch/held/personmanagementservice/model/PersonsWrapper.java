package ch.held.personmanagementservice.model;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "persons")
public class PersonsWrapper {

	private List<PersonEntity> persons;

	public PersonsWrapper() {
	} // JAXB needs a no-arg constructor

	public PersonsWrapper(List<PersonEntity> persons) {
		this.persons = persons;
	}

	@XmlElement(name = "person")
	public List<PersonEntity> getPersons() {
		return persons;
	}

	public void setPersons(List<PersonEntity> persons) {
		this.persons = persons;
	}
}

