//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v3.0.0 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.03.10 um 05:57:49 PM CET 
//

package generated;

import java.math.BigInteger;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * <p>Java-Klasse für anonymous complex type.
 *
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element ref="{}gender"/&gt;
 *         &lt;element name="age" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "name", "gender", "age" })
@XmlRootElement(name = "person")
public class Person {

	@XmlElement(required = true)
	protected String name;
	@XmlElement(required = true)
	protected String gender;
	@XmlElement(required = true)
	protected BigInteger age;

	/**
	 * Ruft den Wert der name-Eigenschaft ab.
	 *
	 * @return possible object is {@link String }
	 */
	public String getName() {
		return name;
	}

	/**
	 * Legt den Wert der name-Eigenschaft fest.
	 *
	 * @param value allowed object is {@link String }
	 */
	public void setName(String value) {
		this.name = value;
	}

	/**
	 * Ruft den Wert der gender-Eigenschaft ab.
	 *
	 * @return possible object is {@link String }
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Legt den Wert der gender-Eigenschaft fest.
	 *
	 * @param value allowed object is {@link String }
	 */
	public void setGender(String value) {
		this.gender = value;
	}

	/**
	 * Ruft den Wert der age-Eigenschaft ab.
	 *
	 * @return possible object is {@link BigInteger }
	 */
	public BigInteger getAge() {
		return age;
	}

	/**
	 * Legt den Wert der age-Eigenschaft fest.
	 *
	 * @param value allowed object is {@link BigInteger }
	 */
	public void setAge(BigInteger value) {
		this.age = value;
	}

}
