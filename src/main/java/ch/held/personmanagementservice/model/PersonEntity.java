package ch.held.personmanagementservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD) //hint: This line makes JAXB use fields directly ... no use for @XmlElement at getter methods
@XmlType(propOrder = { "id", "name", "gender", "age" })
public class PersonEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank(message = "Name cannot be blank")
	private String name;

	@Pattern(regexp = "[fm]", message = "Gender must be 'f' or 'm'")
	private String gender;

	@Min(value = 0, message = "Age cannot be negative")
	private Integer age;

	public PersonEntity() {
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
