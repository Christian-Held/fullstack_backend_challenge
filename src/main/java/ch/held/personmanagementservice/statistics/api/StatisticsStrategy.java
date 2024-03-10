package ch.held.personmanagementservice.statistics.api;

import ch.held.personmanagementservice.model.StatisticsEntity;

/**
 * Strategy Pattern
 */
public interface StatisticsStrategy {
	StatisticsEntity generateStatistics();
}
