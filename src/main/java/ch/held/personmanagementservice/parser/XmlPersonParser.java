package ch.held.personmanagementservice.parser;

import java.io.StringReader;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import ch.held.personmanagementservice.util.JaxbContextProvider;
import generated.Persons;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

/**
 * Service class for parsing XML data into {@link Persons} objects and validating XML against an XSD.
 */
@ApplicationScoped
public class XmlPersonParser {

	private static final Logger LOGGER = Logger.getLogger(XmlPersonParser.class.getName());

	@Inject
	private JaxbContextProvider jaxbContextProvider;

	public XmlPersonParser() {
		// public or package-private constructor
	}

	/**
	 * Parses XML string into a {@link Persons} object.
	 *
	 * @param personXml the XML data as String
	 * @return {@link Persons} object parsed from the XML data
	 * @throws JAXBException if an error occurs during the unmarshalling process
	 */
	public Persons parseXml(final String personXml) throws JAXBException {
		LOGGER.info("Parsing XML to Persons object.");
		JAXBContext jaxbContext = jaxbContextProvider.getJaxbContext(Persons.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Persons persons = (Persons) unmarshaller.unmarshal(new StringReader(personXml));

		// Validate the unmarshalled object
		validateJavaObject(persons);

		return persons;

	}

	/**
	 * Validates XML data against an XSD schema.
	 *
	 * @param xmlData the XML data as String to be validated
	 * @throws JAXBException if an error occurs during the unmarshalling process
	 * @throws SAXException  if an error occurs during XML schema loading or validation
	 */
	public void validateXml(final String xmlData) throws JAXBException, SAXException {
		LOGGER.info("Validating XML against XSD schema.");
		JAXBContext jaxbContext = JAXBContext.newInstance(Persons.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Schema schema = loadSchema();
		unmarshaller.setSchema(schema);
		unmarshaller.unmarshal(new StreamSource(new StringReader(xmlData))); // Perform validation
	}

	private Schema loadSchema() throws SAXException {
		try {
			SchemaFactory schemaFactory = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
			return schemaFactory.newSchema(XmlPersonParser.class.getResource("/xsd/fullstack-backend-challenge.xsd"));
		} catch (SAXException e) {
			LOGGER.log(Level.SEVERE, "Failed to load XSD schema", e);
			throw e;
		}
	}

	private void validateJavaObject(Persons persons) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Persons>> violations = validator.validate(persons);

		if (!violations.isEmpty()) {
			LOGGER.warning("XML validation errors: " + violations.toString());
			throw new ConstraintViolationException(violations);
		}
	}
}
