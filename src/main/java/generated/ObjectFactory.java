//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v3.0.0 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.03.10 um 05:57:49 PM CET 
//

package generated;

import javax.xml.namespace.QName;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;

/**
 * This object contains factory methods for each Java content interface and Java element interface generated in the generated package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation for XML content. The Java representation of XML content can consist of schema derived interfaces and classes representing the binding of schema
 * type definitions, element declarations and model groups.  Factory methods for each of these are provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName _Gender_QNAME = new QName("", "gender");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link Persons }
	 */
	public Persons createPersons() {
		return new Persons();
	}

	/**
	 * Create an instance of {@link Person }
	 */
	public Person createPerson() {
		return new Person();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 *
	 * @param value Java instance representing xml element's value.
	 * @return the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 */
	@XmlElementDecl(namespace = "", name = "gender")
	public JAXBElement<String> createGender(String value) {
		return new JAXBElement<String>(_Gender_QNAME, String.class, null, value);
	}

}
