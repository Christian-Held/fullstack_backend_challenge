package ch.held.personmanagementservice.service;

import java.util.List;
import java.util.logging.Logger;

import ch.held.personmanagementservice.model.PersonEntity;
import generated.Person;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * Service class responsible for managing person entities. Provides functionality to add, update, delete, and retrieve person records.
 */
@ApplicationScoped
public class PersonService {

	private static final Logger LOGGER = Logger.getLogger(PersonService.class.getName());

	@PersistenceContext(unitName = "default")
	private EntityManager em;

	/**
	 * Retrieves all person entities from the database.
	 *
	 * @return a list of all person entities.
	 */
	public List<PersonEntity> getAllPersons() {
		LOGGER.info("Retrieving all persons");
		return em.createQuery("SELECT p FROM PersonEntity p", PersonEntity.class).getResultList();
	}

	/**
	 * Adds a new person entity to the database.
	 *
	 * @param person the person object to be added.
	 */
	@Transactional
	public void addPerson(Person person) {
		LOGGER.info("Adding a new person");
		PersonEntity personEntity = this.convertToEntity(person);
		em.persist(personEntity);
	}

	/**
	 * Updates an existing person entity in the database.
	 *
	 * @param id            the ID of the person entity to update.
	 * @param updatedPerson the updated person entity.
	 * @return true if the person was updated successfully, false otherwise.
	 */
	@Transactional
	public boolean updatePerson(Long id, PersonEntity updatedPerson) {
		LOGGER.info("Updating person with ID: " + id);
		PersonEntity person = em.find(PersonEntity.class, id);
		if (person == null) {
			LOGGER.warning("Person not found with ID: " + id);
			return false;
		}
		person.setName(updatedPerson.getName());
		person.setGender(updatedPerson.getGender());
		person.setAge(updatedPerson.getAge());
		em.merge(person);
		return true;
	}

	/**
	 * Deletes a person entity from the database.
	 *
	 * @param id the ID of the person entity to delete.
	 * @return true if the person was deleted successfully, false otherwise.
	 */
	@Transactional
	public boolean deletePerson(Long id) {
		LOGGER.info("Deleting person with ID: " + id);
		PersonEntity person = em.find(PersonEntity.class, id);
		if (person == null) {
			LOGGER.warning("Person not found with ID: " + id);
			return false;
		}
		em.remove(person);
		return true;
	}

	/**
	 * Converts a generated Person object to a PersonEntity.
	 *
	 * @param person the generated Person object.
	 * @return a PersonEntity object.
	 */
	public PersonEntity convertToEntity(Person person) {
		LOGGER.info("Converting Person to PersonEntity");
		PersonEntity entity = new PersonEntity();
		entity.setName(person.getName());
		entity.setGender(person.getGender());
		entity.setAge(person.getAge().intValue()); // Assuming conversion is always safe
		return entity;
	}
}
