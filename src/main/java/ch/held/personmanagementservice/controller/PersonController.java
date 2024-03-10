package ch.held.personmanagementservice.controller;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.held.personmanagementservice.model.PersonEntity;
import ch.held.personmanagementservice.model.PersonsWrapper;
import ch.held.personmanagementservice.model.StatisticsEntity;
import ch.held.personmanagementservice.parser.XmlPersonParser;
import ch.held.personmanagementservice.service.PersonService;
import ch.held.personmanagementservice.statistics.impl.RequestStatisticsService;
import generated.Person;
import generated.Persons;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.xml.bind.JAXBException;

/**
 * REST controller for managing persons and the statistic.
 */
@Path("/persons")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class PersonController {

	private static final Logger LOGGER = Logger.getLogger(PersonController.class.getName());

	@Inject
	private PersonService personService;

	@Inject
	private RequestStatisticsService statisticsService;

	@Inject
	private XmlPersonParser xmlPersonParser;

	/**
	 * Retrieves all persons.
	 *
	 * @return A response containing all persons.
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response getAllPersons() {
		LOGGER.info("Retrieving all persons.");
		List<PersonEntity> personList = personService.getAllPersons();
		PersonsWrapper wrapper = new PersonsWrapper(personList);
		statisticsService.recordValidRequest();
		return Response.ok(wrapper).build();
	}

	@POST
	@Path("/add")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public Response addPerson(String personXml) {
		LOGGER.log(Level.INFO, "Attempting to add person with provided XML: {0}", personXml);
		try {
			xmlPersonParser.validateXml(personXml);
			Persons personsToAdd = xmlPersonParser.parseXml(personXml);

			int personsAddedCount = 0;
			for (Person person : personsToAdd.getPerson()) {
				personService.addPerson(person);
				personsAddedCount++;
			}

			statisticsService.recordAddRequest(true, personsAddedCount, null);
			return Response.status(Response.Status.CREATED).entity(personsAddedCount + " person/s created").build();
		} catch (JAXBException e) {
			statisticsService.recordAddRequest(false, 0, e);
			return createErrorResponse(e, Response.Status.BAD_REQUEST, "JAXB Parsing Exception");
		} catch (ConstraintViolationException e) {
			statisticsService.recordAddRequest(false, 0, e);
			return createErrorResponse(e, Response.Status.BAD_REQUEST, "Constraint Violation Exception");
		} catch (Exception e) {
			statisticsService.recordAddRequest(false, 0, e);
			return createErrorResponse(e, Response.Status.INTERNAL_SERVER_ERROR, "Internal Server Error");
		}
	}

	/**
	 * Updates an existing person with XML data.
	 *
	 * @param id        The ID of the person to update.
	 * @param personXml The person data in XML format.
	 * @return A response indicating the result of the update operation.
	 */
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public Response updatePerson(@PathParam("id") Long id, String personXml) {
		LOGGER.log(Level.INFO, "Attempting to update person with ID {0}.", id);

		try {

			xmlPersonParser.validateXml(personXml);
			Persons personsToUpdate = xmlPersonParser.parseXml(personXml);

			boolean updateResult = false;

			int personsUpdatedCount = 0;
			for (Person person : personsToUpdate.getPerson()) {
				PersonEntity personEntity = personService.convertToEntity(person);
				updateResult = personService.updatePerson(id, personEntity);
				personsUpdatedCount++;
			}

			statisticsService.recordValidRequest();
			if (updateResult) {
				return Response.status(Response.Status.OK).entity(personsUpdatedCount + " person/s updated").build();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity("Person not found for update").build();
			}
		} catch (JAXBException e) {
			return createErrorResponse(e, Response.Status.BAD_REQUEST, "JAXB Parsing Exception");
		} catch (ConstraintViolationException e) {
			return createErrorResponse(e, Response.Status.BAD_REQUEST, "Constraint Violation Exception");
		} catch (Exception e) {
			return createErrorResponse(e, Response.Status.INTERNAL_SERVER_ERROR, "Internal Server Error");
		}
	}

	/**
	 * Deletes a person by ID.
	 *
	 * @param id The ID of the person to delete.
	 * @return A response indicating the result of the delete operation.
	 */
	@DELETE
	@Path("/{id}")
	public Response deletePerson(@PathParam("id") Long id) {
		LOGGER.log(Level.INFO, "Attempting to delete person with ID {0}.", id);
		boolean deleteResult = personService.deletePerson(id);

		statisticsService.recordValidRequest();
		if (deleteResult) {
			return Response.status(Response.Status.OK).entity("Person deleted").build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("Person not found").build();
		}
	}

	/**
	 * Generates statistic about valid and invalid add requests in XML format with error details and stacktrace
	 *
	 * @return response in XML format wir all add request details and error details for the add requests
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/statistics")
	public Response getStatistics() {
		StatisticsEntity statistics = statisticsService.generateStatistics();
		statisticsService.recordValidRequest();
		return Response.ok(statistics).build();
	}

	private Response createErrorResponse(Exception e, Response.Status status, String logMessage) {
		try {
			// Log the exception
			LOGGER.log(Level.WARNING, logMessage + ": {0}", e.getCause());

			// Prepare the error response based on exception type
			Map<String, Object> errorResponse;
			if (e instanceof ConstraintViolationException) {
				// Handling ConstraintViolationException separately to include specific violation messages
				var errors = ((ConstraintViolationException) e).getConstraintViolations().stream().map(violation -> violation.getPropertyPath() + ": " + violation.getMessage()).toList();
				errorResponse = Map.of("errors", errors, "totalErrors", errors.size());
			} else {
				// Generic error handling for JAXBException and other Exceptions
				errorResponse = Map.of("error", status.getReasonPhrase(), "details", e.getCause());
			}

			// Create and return the response
			return Response.status(status).entity(errorResponse).build();
		} catch (Exception ex) {
			ex.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}
