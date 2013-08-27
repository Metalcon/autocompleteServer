package de.metalcon.autocompleteServer.Create;

import javax.servlet.ServletContext;

import org.json.simple.JSONObject;

import de.metalcon.autocompleteServer.Helper.ProtocolConstants;

public class ProcessCreateResponse {

	private final JSONObject jsonResponse;
	private CreateRequestContainer container;

	public ProcessCreateResponse(ServletContext context) {
		this.jsonResponse = new JSONObject();

	}

	/**
	 * Adds a queryName-Error to the container object.Expects the String to be
	 * not NULL and correctly formatted.
	 * 
	 * @param querynameNotGiven
	 */
	@SuppressWarnings("unchecked")
	public void addQueryNameMissingError(String querynameNotGiven) {
		this.jsonResponse.put(ProtocolConstants.STATUS_NO_QUERY,
				querynameNotGiven);
	}

	@SuppressWarnings("unchecked")
	public void addQueryNameTooLongError(String querynameTooLong) {
		this.jsonResponse.put(ProtocolConstants.STATUS_QUERY_TOO_LONG,
				querynameTooLong);
	}

	/**
	 * Adds the suggestion String to the container object. Expects the String to
	 * be not NULL and correctly formatted.
	 * 
	 * @param suggestionString
	 */
	@SuppressWarnings("unchecked")
	public void addSuggestStringToContainer(String suggestionString) {
		this.jsonResponse.put(ProtocolConstants.QUERY_PARAMETER,
				suggestionString);
	}

	/**
	 * Adds a DefaultIndex-Warning to the container object.Expects the String to
	 * be not NULL and correctly formatted.
	 * 
	 * @param indexnameNotGiven
	 */
	@SuppressWarnings("unchecked")
	public void addDefaultIndexWarning(String indexnameNotGiven) {
		this.jsonResponse.put(ProtocolConstants.STATUS_DEFAULT_INDEX,
				indexnameNotGiven);
	}

	/**
	 * Adds a NoImage-Warning to the container object.Expects the String to be
	 * not NULL and correctly formatted.
	 * 
	 * @param noImage
	 */
	@SuppressWarnings("unchecked")
	public void addNoImageWarning(String noImage) {
		this.jsonResponse.put(ProtocolConstants.STATUS_NO_IMAGE, noImage);
	}

	/**
	 * Adds a requestMustBeMultipart-Error to the container object.Expects the
	 * String to be not NULL and correctly formatted.
	 * 
	 * @param noImage
	 */
	@SuppressWarnings("unchecked")
	public void addHttpRequestError(String requestMustBeMultipart) {
		this.jsonResponse.put(ProtocolConstants.STATUS_REQUEST_NOT_MULTIPART,
				requestMustBeMultipart);
	}

	@SuppressWarnings("unchecked")
	public void addWeightNotGivenError(String weightNotGiven) {
		this.jsonResponse.put(ProtocolConstants.STATUS_NO_WEIGHT,
				weightNotGiven);

	}

	@SuppressWarnings("unchecked")
	public void addWeightNotANumberError(String weightNotANumber) {
		this.jsonResponse.put(ProtocolConstants.STATUS_WEIGHT_NOT_A_NUMBER,
				weightNotANumber);
	}

	@SuppressWarnings("unchecked")
	public void addNoKeyWarning(String keyNotGiven) {
		this.jsonResponse.put(ProtocolConstants.STATUS_NO_KEY, keyNotGiven);
	}

	@SuppressWarnings("unchecked")
	public void addKeyTooLongWarning(String keyTooLong) {
		this.jsonResponse
				.put(ProtocolConstants.STATUS_KEY_TOO_LONG, keyTooLong);
	}

	/**
	 * Getter for the response as JSONObject.
	 * 
	 * @return JSONObject
	 */
	public JSONObject getResponse() {
		return this.jsonResponse;
	}

	public CreateRequestContainer getContainer() {
		return this.container;
	}

	public void addContainer(
			CreateRequestContainer suggestTreeCreateRequestContainer) {
		this.container = suggestTreeCreateRequestContainer;

	}

}
