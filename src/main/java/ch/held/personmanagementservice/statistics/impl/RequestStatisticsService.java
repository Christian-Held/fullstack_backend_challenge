package ch.held.personmanagementservice.statistics.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.held.personmanagementservice.model.StatisticsEntity;
import ch.held.personmanagementservice.statistics.api.StatisticsStrategy;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Service for recording and generating statistics about add requests.
 */
@ApplicationScoped
public class RequestStatisticsService implements StatisticsStrategy {

	private static final Logger LOGGER = Logger.getLogger(RequestStatisticsService.class.getName());

	private final AtomicInteger totalAddRequests = new AtomicInteger();
	private final AtomicInteger totalInvalidAddRequests = new AtomicInteger();
	private final AtomicInteger totalValidRequests = new AtomicInteger();
	private final AtomicInteger totalAddedPersons = new AtomicInteger();
	private final ConcurrentHashMap<String, List<ErrorDetail>> errorDetailsMap = new ConcurrentHashMap<>();

	/**
	 * Records the outcome of an add request.
	 *
	 * @param isValid Indicates if the add request was valid.
	 * @param e       The exception associated with an invalid request, if any.
	 */
	public void recordAddRequest(boolean isValid, int numberOfPersonsAdded, Exception e) {
		totalAddRequests.incrementAndGet();
		if (isValid) {
			totalValidRequests.incrementAndGet();
			totalAddedPersons.addAndGet(numberOfPersonsAdded);
			LOGGER.info("Valid add request recorded.");
		} else {
			totalInvalidAddRequests.incrementAndGet();
			try {
				String key = e.getClass().getSimpleName();
				String errorMessage = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
				String stackTrace = Arrays.toString(e.getStackTrace());

				LOGGER.info(() -> "Recording invalid add request: " + key + " - " + errorMessage);
				addOrUpdateErrorDetail(key, errorMessage, stackTrace);
			} catch (Exception ex) {
				LOGGER.log(Level.SEVERE, "Internal Server Error during extraction of error details", ex);
			}
		}
	}

	/**
	 * Records all valid Requests.
	 */
	public void recordValidRequest() {
		totalValidRequests.incrementAndGet();
		LOGGER.info("Valid add request recorded.");
	}

	/**
	 * Generates a {@link StatisticsEntity} containing statistics about add requests.
	 *
	 * @return StatisticsEntity with the current statistics.
	 */
	public StatisticsEntity generateStatistics() {
		StatisticsEntity statistics = new StatisticsEntity();
		statistics.setTotalAddRequests(totalAddRequests.get());
		statistics.setTotalInvalidAddRequests(totalInvalidAddRequests.get());
		statistics.setTotalValidRequests(totalValidRequests.get());
		statistics.setTotalAddedPersons(totalAddedPersons.get());

		// Populate the detailedErrors list with ErrorDetail objects from errorDetailsMap
		errorDetailsMap.forEach((errorType, errorDetailsList) -> {
			// Optionally filter or transform errorDetailsList if needed
			errorDetailsList.forEach(statistics::addDetailedError);
		});

		LOGGER.info("Generating detailed error statistics.");
		return statistics;
	}

	/**
	 * Adds a new error detail or updates an existing one based on the error key, message, and stack trace.
	 *
	 * @param key          The error key, usually the exception class name.
	 * @param errorMessage The error message.
	 * @param stackTrace   The stack trace as a string.
	 */
	private void addOrUpdateErrorDetail(String key, String errorMessage, String stackTrace) {
		errorDetailsMap.computeIfAbsent(key, k -> Collections.synchronizedList(new ArrayList<>()));
		List<ErrorDetail> errorDetails = errorDetailsMap.get(key);

		synchronized (errorDetails) {
			boolean found = false;
			for (ErrorDetail detail : errorDetails) {
				if (detail.getErrorMessage().equals(errorMessage) && detail.getStackTrace().equals(stackTrace)) {
					detail.incrementCount();
					found = true;
					LOGGER.info(() -> "Existing error detail incremented: " + key);
					break;
				}
			}

			if (!found) {
				errorDetails.add(new ErrorDetail(errorMessage, stackTrace));
				LOGGER.info(() -> "New error detail added: " + key);
			}
		}
	}
}
