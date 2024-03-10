package ch.held.personmanagementservice.statistics.impl;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "errorDetail")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "countForXml", "errorMessage", "stackTrace" })
public class ErrorDetail {
	// JAXB does not directly support AtomicInteger, so this field is transient
	// and not directly serialized. Instead, use a regular int for XML representation.
	private transient final AtomicInteger count = new AtomicInteger(1);
	@XmlElement(name = "errorMessage")
	private String errorMessage;
	@XmlElement(name = "stackTrace")
	private String stackTrace;

	public ErrorDetail() {
		// JAXB requires a no-arg constructor
	}

	public ErrorDetail(String errorMessage, String stackTrace) {
		this.errorMessage = errorMessage;
		this.stackTrace = stackTrace;
	}

	@XmlElement(name = "countForXml")
	private int getCountForXml() {
		return count.get();
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public int getCount() {
		return count.get();
	}

	public void incrementCount() {
		count.incrementAndGet();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ErrorDetail that = (ErrorDetail) o;
		return Objects.equals(errorMessage, that.errorMessage) && Objects.equals(stackTrace, that.stackTrace);
	}

	@Override
	public int hashCode() {
		return Objects.hash(errorMessage, stackTrace);
	}
}

