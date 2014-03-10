package de.metalcon.autocompleteServer.Create;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.fileupload.FileItem;
import org.junit.Before;
import org.junit.Test;

import de.metalcon.autocompleteServer.Helper.ProtocolConstants;
import de.metalcon.autocompleteServer.Helper.SuggestTree;
import de.metalcon.utils.FormItemList;

/**
 * 
 * This TestClass makes sure that the ASTP Create part of the protocol is
 * implemented properly.
 * 
 * It tests whether the correct responses are given in any case of correct or
 * incorrect requests.
 * 
 * @author Christian Schowalter
 */
public class TestProcessCreateRequest {

	final private ServletConfig servletConfig = mock(ServletConfig.class);
	final private ServletContext servletContext = mock(ServletContext.class);

	@Before
	public void initializeTest() {

		HttpServlet servlet = mock(HttpServlet.class);
		when(this.servletConfig.getServletContext()).thenReturn(
				this.servletContext);
		SuggestTree generalIndex = new SuggestTree(7);
		when(
				this.servletContext
						.getAttribute(ProtocolConstants.INDEX_PARAMETER
								+ "testIndex")).thenReturn(generalIndex);

		when(
				this.servletContext
						.getAttribute(ProtocolConstants.INDEX_PARAMETER
								+ "defaultIndex")).thenReturn(generalIndex);

		try {
			servlet.init(this.servletConfig);
		} catch (ServletException e) {
			fail("could not initialize servlet");
			e.printStackTrace();
		}
	}

	// TODO: Check if all possible states are tested

	@Test
	public void testFormMissingSuggestString() {

		ProcessCreateResponse testResponse = this.processTestRequest(
				ProtocolTestConstants.VALID_SUGGESTION_KEY, null,
				ProtocolTestConstants.VALID_SUGGESTION_WEIGHT,
				ProtocolTestConstants.VALID_SUGGESTION_INDEX, null);

		if (testResponse.getResponse().containsKey("Error:queryNameNotGiven")) {
			assertEquals(CreateStatusCodes.QUERYNAME_NOT_GIVEN, testResponse
					.getResponse().get("Error:queryNameNotGiven"));
		} else {
			fail("noTerm Status-Message missing!");
		}

	}

	@Test
	public void testFormMissingKey() {

		ProcessCreateResponse testResponse = this.processTestRequest(null,
				ProtocolTestConstants.VALID_SUGGESTION_STRING,
				ProtocolTestConstants.VALID_SUGGESTION_WEIGHT,
				ProtocolTestConstants.VALID_SUGGESTION_INDEX, null);

		if (testResponse.getResponse().containsKey("Warning:KeyNotGiven")) {
			assertEquals(CreateStatusCodes.SUGGESTION_KEY_NOT_GIVEN,
					testResponse.getResponse().get("Warning:KeyNotGiven"));
		} else {
			fail("Error-Message missing!");
		}

	}

	@Test
	public void testFormMissingIndex() {

		ProcessCreateResponse testResponse = this.processTestRequest(
				ProtocolTestConstants.VALID_SUGGESTION_KEY,
				ProtocolTestConstants.VALID_SUGGESTION_STRING,
				ProtocolTestConstants.VALID_SUGGESTION_WEIGHT, null, null);

		if (testResponse.getResponse().containsKey("Warning:DefaultIndex")) {
			assertEquals(CreateStatusCodes.INDEXNAME_NOT_GIVEN, testResponse
					.getResponse().get("Warning:DefaultIndex"));
		} else {
			fail("Error-Message missing!");
		}
		assertEquals(ProtocolTestConstants.DEFAULT_INDEX, testResponse
				.getContainer().getComponents().getIndexName());

	}

	@Test
	public void testFormSuggestStringTooLong() {

		ProcessCreateResponse testResponse = this.processTestRequest(
				ProtocolTestConstants.VALID_SUGGESTION_KEY,
				ProtocolTestConstants.TOO_LONG_SUGGESTION_STRING,
				ProtocolTestConstants.VALID_SUGGESTION_WEIGHT,
				ProtocolTestConstants.VALID_SUGGESTION_INDEX, null);

		if (testResponse.getResponse().containsKey("Error:queryNameTooLong")) {
			assertEquals(CreateStatusCodes.SUGGESTION_STRING_TOO_LONG,
					testResponse.getResponse().get("Error:queryNameTooLong"));
		} else {
			fail("Error-Message missing!");
		}

	}

	@Test
	public void testFormWeightNotGiven() {

		ProcessCreateResponse testResponse = this.processTestRequest(
				ProtocolTestConstants.VALID_SUGGESTION_KEY,
				ProtocolTestConstants.VALID_SUGGESTION_STRING, null,
				ProtocolTestConstants.VALID_SUGGESTION_INDEX, null);

		if (testResponse.getResponse().containsKey("Error:WeightNotGiven")) {
			assertEquals(CreateStatusCodes.WEIGHT_NOT_GIVEN, testResponse
					.getResponse().get("Error:WeightNotGiven"));
		} else {
			fail("Error-Message missing!");
		}

	}

	@Test
	public void testFormWeightNotANumber() {

		ProcessCreateResponse testResponse = this.processTestRequest(
				ProtocolTestConstants.VALID_SUGGESTION_KEY,
				ProtocolTestConstants.VALID_SUGGESTION_STRING,
				ProtocolTestConstants.NOT_A_NUMBER_WEIGHT,
				ProtocolTestConstants.VALID_SUGGESTION_INDEX, null);

		if (testResponse.getResponse().containsKey("Error:WeightNotANumber")) {
			assertEquals(CreateStatusCodes.WEIGHT_NOT_A_NUMBER, testResponse
					.getResponse().get("Error:WeightNotANumber"));
		} else {
			fail("Error-Message missing!");
		}

	}

	@Test
	public void testFullFormWithImage() throws Exception {

		File image = new File("testImage_valid.jpg");

		if (image.length() > 0) {

			ImageFileItem imageFileItem = new ImageFileItem(image);

			ProcessCreateResponse testResponse = this
					.processTestRequest(
							ProtocolTestConstants.VALID_SUGGESTION_KEY,
							ProtocolTestConstants.VALID_SUGGESTION_STRING,
							ProtocolTestConstants.VALID_SUGGESTION_WEIGHT,
							ProtocolTestConstants.VALID_SUGGESTION_INDEX,
							imageFileItem);

			assertEquals(ProtocolTestConstants.VALID_SUGGESTION_KEY,
					testResponse.getContainer().getComponents().getKey());
			assertEquals(ProtocolTestConstants.VALID_SUGGESTION_STRING,
					testResponse.getContainer().getComponents()
							.getSuggestString());
			assertEquals(ProtocolTestConstants.VALID_SUGGESTION_WEIGHT,
					testResponse.getContainer().getComponents().getWeight()
							.toString());
			assertEquals(ProtocolTestConstants.VALID_SUGGESTION_INDEX,
					testResponse.getContainer().getComponents().getIndexName());

			if (testResponse.getResponse().containsKey(
					CreateStatusCodes.STATUS_OK)) {
				assertEquals(CreateStatusCodes.STATUS_OK, testResponse
						.getResponse().get("Status:OK"));
			} else {
				fail("Error-Message missing!");
			}

		} else {
			fail("no image to test with provided!");
		}
	}

	@Test
	public void testFullFormWithImageTooWide() throws Exception {

		File image = new File("testImage_too_wide.jpg");

		if (image.length() > 0) {

			ImageFileItem imageFileItem = new ImageFileItem(image);

			ProcessCreateResponse testResponse = this
					.processTestRequest(
							ProtocolTestConstants.VALID_SUGGESTION_KEY,
							ProtocolTestConstants.VALID_SUGGESTION_STRING,
							ProtocolTestConstants.VALID_SUGGESTION_WEIGHT,
							ProtocolTestConstants.VALID_SUGGESTION_INDEX,
							imageFileItem);

			if (testResponse.getResponse().containsKey(
					CreateStatusCodes.IMAGE_GEOMETRY_TOO_BIG)) {
				assertEquals(
						CreateStatusCodes.IMAGE_GEOMETRY_TOO_BIG,
						testResponse.getResponse().get(
								CreateStatusCodes.IMAGE_GEOMETRY_TOO_BIG));
			} else {
				fail("Error-Message missing!");
			}

		} else {
			fail("no image to test with provided!");
		}
	}

	@Test
	public void testFullFormWithImageTooHigh() throws Exception {

		File image = new File("testImage_too_high.jpg");

		if (image.length() > 0) {

			ImageFileItem imageFileItem = new ImageFileItem(image);

			ProcessCreateResponse testResponse = this
					.processTestRequest(
							ProtocolTestConstants.VALID_SUGGESTION_KEY,
							ProtocolTestConstants.VALID_SUGGESTION_STRING,
							ProtocolTestConstants.VALID_SUGGESTION_WEIGHT,
							ProtocolTestConstants.VALID_SUGGESTION_INDEX,
							imageFileItem);

			if (testResponse.getResponse().containsKey(
					CreateStatusCodes.IMAGE_GEOMETRY_TOO_BIG)) {
				assertEquals(
						CreateStatusCodes.IMAGE_GEOMETRY_TOO_BIG,
						testResponse.getResponse().get(
								CreateStatusCodes.IMAGE_GEOMETRY_TOO_BIG));
			} else {
				fail("Error-Message missing!");
			}

		} else {
			fail("no image to test with provided!");
		}
	}

	@Test
	public void testFullFormWithImageWrongType() throws Exception {

		// TODO: review specifications, which file types are allowed!
		File image = new File("testImage_wrong_type.png");

		if (image.length() > 0) {

			ImageFileItem imageFileItem = new ImageFileItem(image);

			ProcessCreateResponse testResponse = this
					.processTestRequest(
							ProtocolTestConstants.VALID_SUGGESTION_KEY,
							ProtocolTestConstants.VALID_SUGGESTION_STRING,
							ProtocolTestConstants.VALID_SUGGESTION_WEIGHT,
							ProtocolTestConstants.VALID_SUGGESTION_INDEX,
							imageFileItem);

			if (testResponse.getResponse().containsKey(
					CreateStatusCodes.IMAGE_WRONG_TYPE)) {
				assertEquals(CreateStatusCodes.IMAGE_WRONG_TYPE, testResponse
						.getResponse().get(CreateStatusCodes.IMAGE_WRONG_TYPE));
			} else {
				fail("Error-Message missing!");
			}

		} else {
			fail("no image to test with provided!");
		}
	}

	@Test
	public void testFullFormWithoutImage() {

		ProcessCreateResponse testResponse = this.processTestRequest(
				ProtocolTestConstants.VALID_SUGGESTION_KEY,
				ProtocolTestConstants.VALID_SUGGESTION_STRING,
				ProtocolTestConstants.VALID_SUGGESTION_WEIGHT,
				ProtocolTestConstants.VALID_SUGGESTION_INDEX, null);

		assertEquals(ProtocolTestConstants.VALID_SUGGESTION_KEY, testResponse
				.getContainer().getComponents().getKey());
		assertEquals(ProtocolTestConstants.VALID_SUGGESTION_STRING,
				testResponse.getContainer().getComponents().getSuggestString());
		assertEquals(ProtocolTestConstants.VALID_SUGGESTION_WEIGHT,
				testResponse.getContainer().getComponents().getWeight()
						.toString());
		assertEquals(ProtocolTestConstants.VALID_SUGGESTION_INDEX, testResponse
				.getContainer().getComponents().getIndexName());
		assertEquals("{" + "\"term\"" + ":" + "\""
				+ ProtocolTestConstants.VALID_SUGGESTION_STRING + "\"" + ","
				+ "\"Warning:noImage\"" + ":" + "\"No image inserted\"" + "}",
				testResponse.getResponse().toString());
	}

	private ProcessCreateResponse processTestRequest(String key, String term,
			String weight, String index, FileItem image) {

		ProcessCreateResponse response = new ProcessCreateResponse(
				this.servletConfig.getServletContext());
		FormItemList testItems = new FormItemList();
		if (key != null) {
			testItems.addField(ProtocolConstants.SUGGESTION_KEY, key);
		}
		if (term != null) {
			testItems.addField(ProtocolConstants.SUGGESTION_STRING, term);
		}
		if (weight != null) {
			testItems.addField(ProtocolConstants.SUGGESTION_WEIGHT, weight);
		}
		if (index != null) {
			testItems.addField(ProtocolConstants.INDEX_PARAMETER, index);
		}
		if (image != null) {
			testItems.addFile(ProtocolConstants.IMAGE, image);
		}
		return ProcessCreateRequest.checkRequestParameter(testItems, response,
				this.servletConfig.getServletContext());
	}
}
