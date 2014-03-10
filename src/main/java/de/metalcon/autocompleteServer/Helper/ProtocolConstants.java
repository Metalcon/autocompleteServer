package de.metalcon.autocompleteServer.Helper;

/**
 * Constants that are used in the Auto Suggest Transfer Protocol (ASTP)
 * 
 * @author Rene Pickhardt
 * @author Christian Schowalter
 */
public class ProtocolConstants {
	public static final String STATUS_MESSAGE = "status";
	public static final String QUERY_PARAMETER = "term";
	public static final String INDEX_PARAMETER = "indexName";
	public static final String NUM_ITEMS = "numItems";

	/**
	 * parameters for the response JSON of the retrieval request
	 */
	public static final String RESP_JSON_FIELD_SUGGESTION_LIST = "suggestionList";
	public static final String RESP_JSON_FIELD_SUGGESTION = "suggestion";
	public static final String RESP_JSON_FIELD_SUGGESTION_KEY = "key";
	public static final String RESP_JSON_FIELD_SUGGESTION_IMAGE = "image";

	/**
	 * form parameter names for the create request of the ASTP
	 */
	public static final String SUGGESTION_WEIGHT = "weight";
	public static final String SUGGESTION_KEY = "key";
	public static final String SUGGESTION_STRING = "term";
	public static final int MAX_SUGGESTION_LENGTH = 80;
	public static final int MAX_KEY_LENGTH = 64;
	public static final String IMAGE = "image";
	public static final int IMAGE_WIDTH = 64;
	public static final int IMAGE_HEIGHT = 64;
	public static final long MAX_IMAGE_FILE_LENGTH = 3000000;

	/**
	 * the default Number of Items which can be retrieved from the Suggest
	 * Server
	 */
	public static final Integer MAX_NUMBER_OF_SUGGESTIONS = 7;
	public static final String DEFAULT_INDEX_NAME = "generalIndex";
	public static final String IMAGE_SERVER_CONTEXT_KEY = "image-index-hashMap";
}
