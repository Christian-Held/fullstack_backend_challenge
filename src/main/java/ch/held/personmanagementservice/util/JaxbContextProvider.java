package ch.held.personmanagementservice.util;

import jakarta.ejb.ConcurrencyManagement;
import jakarta.ejb.ConcurrencyManagementType;
import jakarta.ejb.Lock;
import jakarta.ejb.LockType;
import jakarta.ejb.Singleton;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

/**
 * A singleton class that provides JAXBContext instances for specified classes. This class ensures that JAXBContext is initialized only once for performance optimization.
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class JaxbContextProvider {

	private volatile JAXBContext jaxbContext = null;

	/**
	 * Provides a singleton instance of JAXBContext for the specified classes. This method ensures that JAXBContext is initialized only once to improve performance.
	 *
	 * @param classesToBeBound the classes to be bound to the JAXBContext.
	 * @return an instance of JAXBContext for the specified classes.
	 * @throws JAXBException if an error occurs during the initialization of JAXBContext.
	 */
	@Lock(LockType.READ)
	public JAXBContext getJaxbContext(Class... classesToBeBound) throws JAXBException {
		if (jaxbContext == null) {
			synchronized (this) {
				if (jaxbContext == null) { // Double-check locking pattern
					jaxbContext = JAXBContext.newInstance(classesToBeBound);
				}
			}
		}
		return jaxbContext;
	}
}
