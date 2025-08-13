package functional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import helpers.Journaling;
import helpers.SubcutaneousTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fisglobal.taxreporting.entity.model.taxreporting.bm1.TrTableBm1;
import com.fisglobal.taxreporting.entity.model.taxreporting.bm3.TrTableBm3;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;
import com.fisglobal.taxreporting.exception.ValidationMessage;
import com.fisglobal.taxreporting.exception.ValidationResultsException;
import com.fisglobal.taxreporting.service.taxreportinghistory.TaxReportingCreateHistoricizedService;
import com.fisglobal.taxreporting.validation.ValidationResults;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCaseHistoricizedCreation extends SubcutaneousTest {
    private List<TrTableBsb> bsbList_B_after_t1;
    private List<TrTableBsb> bsbList_B_after_t2;
    private List<TrTableBsb> bsbList_B_after_t3;
    private List<TrTableBsb> bsbList_B_after_t4;
    private List<TrTableBsb> bsbList_B_after_t5;

    @Autowired
    Journaling journaling;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    TaxReportingCreateHistoricizedService taxReportingCreateHistoricizedService;

    @BeforeAll
    void setupClass() {
        String filename = "Testcases_UC12.xlsx";

        bsbList_B_after_t1 = this.getTestCaseData("TEST001.after", filename);
        bsbList_B_after_t2 = this.getTestCaseData("TEST002.after", filename);
        bsbList_B_after_t3 = this.getTestCaseData("TEST003.after", filename);
        bsbList_B_after_t4 = this.getTestCaseData("TEST004.after", filename);
        bsbList_B_after_t5 = this.getTestCaseData("TEST005.after", filename);
        timeProvider.stopTime();

    }

    @Test
    void test_create_hisztoricized_BSB_JSTB_I(@Autowired MockMvc mvc) throws Exception {
        TrTableBsb trTableBsbFileDetail = cloneBsbDeep(bsbList_B_after_t1.get(0));

        timeProvider.setDateTime("20250410", "150900");

        ResultActions createApiResponse = createBsbRequest(trTableBsbFileDetail, mvc);

        String json = createApiResponse.andReturn().getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        ValidationResults validationResults = objectMapper.readValue(json, ValidationResults.class);
        TrTableBsb trTableBsbResponse = objectMapper.convertValue(validationResults.getTrTableBsb(), TrTableBsb.class);

        assertEquals(trTableBsbFileDetail.getTrTableBsbPK().getKeyIdNr().trim(),
                trTableBsbResponse.getTrTableBsbPK().getKeyIdNr().trim());
        assertEquals(trTableBsbFileDetail.getTrTableBsbPK().getKeyMeldejahr(),
                trTableBsbResponse.getTrTableBsbPK().getKeyMeldejahr());
        assertEquals(trTableBsbFileDetail.getTrTableBsbPK().getKeyMuster().trim(),
                trTableBsbResponse.getTrTableBsbPK().getKeyMuster().trim());
        assertEquals(trTableBsbFileDetail.getTrTableBsbPK().getKeyLaufnummer(),
                trTableBsbResponse.getTrTableBsbPK().getKeyLaufnummer());

        assertEquals(trTableBsbFileDetail.getStatus(), trTableBsbResponse.getStatus());
        assertEquals(trTableBsbFileDetail.getMeldeStatus(), trTableBsbResponse.getMeldeStatus());
        assertEquals(trTableBsbFileDetail.getDokuArt(), trTableBsbResponse.getDokuArt());
        assertEquals(trTableBsbFileDetail.getMeldejahr(), trTableBsbResponse.getMeldejahr());
        assertEquals(trTableBsbFileDetail.getMeldungJjjjmmtt(), trTableBsbResponse.getMeldungJjjjmmtt());
        assertEquals(trTableBsbFileDetail.getMeldungUhrHms(), trTableBsbResponse.getMeldungUhrHms());
        assertEquals(trTableBsbFileDetail.getAenderDatum(), trTableBsbResponse.getAenderDatum());
        assertEquals(trTableBsbFileDetail.getAenderErfasser(), trTableBsbResponse.getAenderErfasser());
        assertEquals(trTableBsbFileDetail.getAenderZeit(), trTableBsbResponse.getAenderZeit());

        assertEquals(trTableBsbFileDetail.getAnlageDatum(), trTableBsbResponse.getAnlageDatum());
        assertEquals(trTableBsbFileDetail.getAnlageErfasser(), trTableBsbResponse.getAnlageErfasser());
        assertEquals(trTableBsbFileDetail.getAnlageZeit(), trTableBsbResponse.getAnlageZeit());

        assertEquals(trTableBsbFileDetail.getAnlass(), trTableBsbResponse.getAnlass());

        assertEquals(trTableBsbFileDetail.getUpdateCheck(), trTableBsbResponse.getUpdateCheck());

        // journalingHistoryCreationTest(trTableBsbResponse, "TEST001");
        // journaling.assertJournaling(trTableBsbResponse, "UC12", "TEST001");

    }

    @Test
    void test_create_hisztoricized_BM1(@Autowired MockMvc mvc) throws Exception {
        TrTableBsb trTableBsbFileDetail = cloneBsbDeep(bsbList_B_after_t2.get(0));

        timeProvider.setDateTime("20250410", "154800");

        ResultActions createApiResponse = createBsbRequest(trTableBsbFileDetail, mvc);

        String json = createApiResponse.andReturn().getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        ValidationResults validationResults = objectMapper.readValue(json, ValidationResults.class);
        TrTableBsb trTableBsbResponse = objectMapper.convertValue(validationResults.getTrTableBsb(), TrTableBsb.class);

        TrTableBm1 trTableBm1Response = trTableBsbResponse.getTrTableBm1Set().first();
        TrTableBm1 trTableBm1FileDetail = trTableBsbFileDetail.getTrTableBm1Set().first();

        assertEquals(trTableBm1FileDetail.getTrTableBm1PK().getKeyIdNr().trim(),
                trTableBm1Response.getTrTableBm1PK().getKeyIdNr().trim());
        assertEquals(trTableBm1FileDetail.getTrTableBm1PK().getKeyMeldejahr(),
                trTableBm1Response.getTrTableBm1PK().getKeyMeldejahr());
        assertEquals(trTableBm1FileDetail.getTrTableBm1PK().getKeyMuster().trim(),
                trTableBm1Response.getTrTableBm1PK().getKeyMuster().trim());
        assertEquals(trTableBm1FileDetail.getTrTableBm1PK().getKeyLaufnummer(),
                trTableBm1Response.getTrTableBm1PK().getKeyLaufnummer());
        assertEquals(trTableBm1FileDetail.getTrTableBm1PK().getKeySatzart(),
                trTableBm1Response.getTrTableBm1PK().getKeySatzart());

        assertEquals(trTableBm1FileDetail.getKapitalertrag(), trTableBm1Response.getKapitalertrag());
        assertEquals(trTableBm1FileDetail.getAamVeraeussert(), trTableBm1Response.getAamVeraeussert());
        // journalingHistoryCreationTest(trTableBsbResponse, "TEST002");
        // journaling.assertJournaling(trTableBsbResponse, "UC12", "TEST002");
    }

    @Test
    void test_create_hisztoricized_BSB_JSTB_III(@Autowired MockMvc mvc) throws Exception {
        TrTableBsb trTableBsbFileDetail = cloneBsbDeep(bsbList_B_after_t3.get(0));

        timeProvider.setDateTime("20250411", "160234");

        ResultActions createApiResponse = createBsbRequest(trTableBsbFileDetail, mvc);

        String json = createApiResponse.andReturn().getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        ValidationResults validationResults = objectMapper.readValue(json, ValidationResults.class);
        TrTableBsb trTableBsbResponse = objectMapper.convertValue(validationResults.getTrTableBsb(), TrTableBsb.class);

        assertEquals(trTableBsbFileDetail.getTrTableBsbPK().getKeyIdNr().trim(),
                trTableBsbResponse.getTrTableBsbPK().getKeyIdNr().trim());
        assertEquals(trTableBsbFileDetail.getTrTableBsbPK().getKeyMeldejahr(),
                trTableBsbResponse.getTrTableBsbPK().getKeyMeldejahr());
        assertEquals(trTableBsbFileDetail.getTrTableBsbPK().getKeyMuster().trim(),
                trTableBsbResponse.getTrTableBsbPK().getKeyMuster().trim());
        assertEquals(trTableBsbFileDetail.getTrTableBsbPK().getKeyLaufnummer(),
                trTableBsbResponse.getTrTableBsbPK().getKeyLaufnummer());

        assertEquals(trTableBsbFileDetail.getStatus(), trTableBsbResponse.getStatus());
        assertEquals(trTableBsbFileDetail.getMeldeStatus(), trTableBsbResponse.getMeldeStatus());
        assertEquals(trTableBsbFileDetail.getDokuArt(), trTableBsbResponse.getDokuArt());
        assertEquals(trTableBsbFileDetail.getMeldejahr(), trTableBsbResponse.getMeldejahr());
        assertEquals(trTableBsbFileDetail.getMeldungJjjjmmtt(), trTableBsbResponse.getMeldungJjjjmmtt());
        assertEquals(trTableBsbFileDetail.getMeldungUhrHms(), trTableBsbResponse.getMeldungUhrHms());
        assertEquals(trTableBsbFileDetail.getAenderDatum(), trTableBsbResponse.getAenderDatum());
        assertEquals(trTableBsbFileDetail.getAenderErfasser(), trTableBsbResponse.getAenderErfasser());
        assertEquals(trTableBsbFileDetail.getAenderZeit(), trTableBsbResponse.getAenderZeit());

        assertEquals(trTableBsbFileDetail.getAnlageDatum(), trTableBsbResponse.getAnlageDatum());
        assertEquals(trTableBsbFileDetail.getAnlageErfasser(), trTableBsbResponse.getAnlageErfasser());
        assertEquals(trTableBsbFileDetail.getAnlageZeit(), trTableBsbResponse.getAnlageZeit());

        assertEquals(trTableBsbFileDetail.getAnlass(), trTableBsbResponse.getAnlass());

        assertEquals(trTableBsbFileDetail.getUpdateCheck(), trTableBsbResponse.getUpdateCheck());

        // journalingHistoryCreationTest(trTableBsbResponse, "TEST003");
        // journaling.assertJournaling(trTableBsbResponse, "UC12", "TEST003");
    }

    @Test
    void test_create_hisztoricized_BM3(@Autowired MockMvc mvc) throws Exception {
        TrTableBsb trTableBsbFileDetail = cloneBsbDeep(bsbList_B_after_t4.get(0));

        timeProvider.setDateTime("20250410", "155500");

        ResultActions createApiResponse = createBsbRequest(trTableBsbFileDetail, mvc);

        String json = createApiResponse.andReturn().getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        ValidationResults validationResults = objectMapper.readValue(json, ValidationResults.class);
        TrTableBsb trTableBsbResponse = objectMapper.convertValue(validationResults.getTrTableBsb(), TrTableBsb.class);

        TrTableBm3 trTableBm3Response = trTableBsbResponse.getTrTableBm3Set().first();
        TrTableBm3 trTableBm3FileDetail = trTableBsbFileDetail.getTrTableBm3Set().first();

        assertEquals(trTableBm3FileDetail.getTrTableBm3PK().getKeyIdNr().trim(),
                trTableBm3Response.getTrTableBm3PK().getKeyIdNr().trim());
        assertEquals(trTableBm3FileDetail.getTrTableBm3PK().getKeyMeldejahr(),
                trTableBm3Response.getTrTableBm3PK().getKeyMeldejahr());
        assertEquals(trTableBm3FileDetail.getTrTableBm3PK().getKeyMuster().trim(),
                trTableBm3Response.getTrTableBm3PK().getKeyMuster().trim());
        assertEquals(trTableBm3FileDetail.getTrTableBm3PK().getKeyLaufnummer(),
                trTableBm3Response.getTrTableBm3PK().getKeyLaufnummer());
        assertEquals(trTableBm3FileDetail.getTrTableBm3PK().getKeySatzart(),
                trTableBm3Response.getTrTableBm3PK().getKeySatzart());

        assertEquals(trTableBm3FileDetail.getAbstandSteuerabz(), trTableBm3Response.getAbstandSteuerabz());

        // journalingHistoryCreationTest(trTableBsbResponse, "TEST004");

        // journaling.assertJournaling(trTableBsbResponse, "UC12", "TEST004");

    }

    @Test
    void test_request_validation(@Autowired MockMvc mvc) throws Exception {
        TrTableBsb trTableBsbFileDetail = cloneBsbDeep(bsbList_B_after_t5.get(0));
        ResultActions createApiResponse = createBsbRequest(trTableBsbFileDetail, mvc);

        createApiResponse.andExpect(status().isBadRequest());
        String responseContent = createApiResponse.andReturn().getResponse().getContentAsString();

        assertTrue(responseContent.contains(ValidationMessage.ERROR_CODE_127));
        assertTrue(responseContent.contains(ValidationMessage.ERROR_CODE_128));
        assertTrue(responseContent.contains(ValidationMessage.ERROR_CODE_129));
    }

    private ResultActions createBsbRequest(TrTableBsb currentBsb, MockMvc mvc) throws Exception {
        String keyIdNr = currentBsb.getTrTableBsbPK().getKeyIdNr().trim();
        long keyMeldejahr = currentBsb.getTrTableBsbPK().getKeyMeldejahr();
        String keyMuster = currentBsb.getTrTableBsbPK().getKeyMuster().trim();

        MockHttpServletRequestBuilder request = post(
                "/jstb-correction/v1/history?keyIdNr={1}&keyMeldejahr={2}&keyMuster={3}", keyIdNr, keyMeldejahr,
                keyMuster);
        request.characterEncoding("utf-8");

        return mvc.perform(request);
    }

    private void journalingHistoryCreationTest(TrTableBsb trTableBsbResponse, String testCase)
            throws ValidationResultsException, Exception {
        // TrTableJournaling trTableJournaling = taxReportingJournalingService
        // .journalingProcessExecutionHistory(trTableBsbResponse, CommonConstants.HISTORY_ACTION);
        // journaling.journalingTestProcess(trTableJournaling, "UC12", testCase);
    }
}
