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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
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
import com.fisglobal.taxreporting.validation.ValidationResults;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCaseCreateCorrection extends SubcutaneousTest {
    private List<TrTableBsb> bsbList_B_before_t1;
    private List<TrTableBsb> bsbList_B_after_t1;
    private List<TrTableBsb> bsbList_B_before_t2;
    private List<TrTableBsb> bsbList_B_before_t3;
    private List<TrTableBsb> bsbList_B_after_t3;
    private List<TrTableBsb> bsbList_B_before_t4;
    private List<TrTableBsb> bsbList_B_after_t4;
    private List<TrTableBsb> bsbList_B_before_t5;
    private List<TrTableBsb> bsbList_B_after_t5;

    @Autowired
    Journaling journaling;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    void setupClass() {
        String filename = "Testcases_UC03.xlsx";

        bsbList_B_before_t1 = this.getTestCaseData("TEST001.before", filename);
        bsbList_B_after_t1 = this.getTestCaseData("TEST001.after", filename);
        bsbList_B_before_t3 = this.getTestCaseData("TEST003.before", filename);
        bsbList_B_after_t3 = this.getTestCaseData("TEST003.after", filename);
        bsbList_B_before_t2 = this.getTestCaseData("TEST002.before", filename);
        bsbList_B_before_t4 = this.getTestCaseData("TEST004.before", filename);
        bsbList_B_after_t4 = this.getTestCaseData("TEST004.after", filename);
        bsbList_B_before_t5 = this.getTestCaseData("TEST005.before", filename);
        bsbList_B_after_t5 = this.getTestCaseData("TEST005.after", filename);

        timeProvider.stopTime();
    }

    @Test
    void test_Bm1_status_changed_from_H_to_B(@Autowired MockMvc mvc) throws Exception {
        saveBsbList(bsbList_B_before_t1);
        TrTableBsb trTableBsbRequest = cloneBsbDeep(bsbList_B_before_t1.get(0));
        TrTableBsb trTableBsbResponse = cloneBsbDeep(bsbList_B_after_t1.get(0));

        ResultActions correctionApiResponse = createBsbRequest(trTableBsbRequest, mvc);
        String responseContent = correctionApiResponse.andReturn().getResponse().getContentAsString();
        ValidationResults validationResults = objectMapper.readValue(responseContent, ValidationResults.class);

        assertEquals(trTableBsbResponse.getStatus(), validationResults.getTrTableBsb().getStatus());
        assertEquals(trTableBsbResponse.getMeldeStatus(), validationResults.getTrTableBsb().getMeldeStatus());
        assertEquals(trTableBsbResponse.getTrTableBm1Set().size(),
                validationResults.getTrTableBsb().getTrTableBm1Set().size());

        assertTrue(validationResults.getTrTableBsb().getKmId().contains(trTableBsbResponse.getPersonId().trim()));
        assertTrue(validationResults.getTrTableBsb().getNdTicket().contains(trTableBsbResponse.getPersonId().trim()));

        journaling.assertJournaling(trTableBsbRequest, "UC03", "TEST001");
    }

    @Test
    void test_Bm3_status_changed_from_H_to_B(@Autowired MockMvc mvc) throws Exception {
        saveBsbList(bsbList_B_before_t3);
        TrTableBsb trTableBsbRequest = cloneBsbDeep(bsbList_B_before_t3.get(1));
        TrTableBsb trTableBsbResponse = cloneBsbDeep(bsbList_B_after_t3.get(1));

        ResultActions correctionApiResponse = createBsbRequest(trTableBsbRequest, mvc);
        String responseContent = correctionApiResponse.andReturn().getResponse().getContentAsString();
        ValidationResults validationResults = objectMapper.readValue(responseContent, ValidationResults.class);

        assertEquals(trTableBsbResponse.getStatus(), validationResults.getTrTableBsb().getStatus());
        assertEquals(trTableBsbResponse.getMeldeStatus(), validationResults.getTrTableBsb().getMeldeStatus());
        assertEquals(trTableBsbResponse.getTrTableBm3Set().size(),
                validationResults.getTrTableBsb().getTrTableBm3Set().size());

        assertTrue(validationResults.getTrTableBsb().getKmId().contains(trTableBsbResponse.getPersonId().trim()));
        assertTrue(validationResults.getTrTableBsb().getNdTicket().contains(trTableBsbResponse.getPersonId().trim()));

        journaling.assertJournaling(trTableBsbRequest, "UC03", "TEST002");
    }

    @Test
    @Tags({ @Tag("smoke"), })
    void smoke_test_H_for_Bsb(@Autowired MockMvc mvc) throws Exception {
        saveBsbList(bsbList_B_before_t2);
        TrTableBsb trTableBsbRequest = cloneBsbDeep(bsbList_B_before_t2.get(0));
        ResultActions correctionApiResponse = createBsbRequest(trTableBsbRequest, mvc);
        correctionApiResponse.andExpect(status().isBadRequest());
        String responseContent = correctionApiResponse.andReturn().getResponse().getContentAsString();
        ValidationResults validationResults = objectMapper.readValue(responseContent, ValidationResults.class);
        assertEquals(validationResults.getValidationResults().get(0).getCode(), ValidationMessage.ERROR_CODE_104);
    }

    @Test
    void test_Bm1_subtables_creation(@Autowired MockMvc mvc) throws Exception {
        saveBsbList(bsbList_B_before_t4);
        TrTableBsb trTableBsbRequest = cloneBsbDeep(bsbList_B_before_t4.get(0));
        TrTableBsb trTableBsbResponse = cloneBsbDeep(bsbList_B_after_t4.get(0));

        ResultActions correctionApiResponse = createBsbRequest(trTableBsbRequest, mvc);
        String responseContent = correctionApiResponse.andReturn().getResponse().getContentAsString();
        ValidationResults validationResults = objectMapper.readValue(responseContent, ValidationResults.class);
        com.fisglobal.taxreporting.controller.dto.bm1.TrTableBm1 trTableBm1response = validationResults.getTrTableBsb()
                .getTrTableBm1Set().stream()
                .filter(bm1 -> bm1.getTrTableBm1PK().getKeySatzart().equals(trTableBsbResponse.getStatus())).findFirst()
                .get();

        TrTableBm1 trTableBm1After = trTableBsbResponse.getTrTableBm1Set().stream()
                .filter(bm1 -> bm1.getTrTableBm1PK().getKeySatzart().equals(trTableBsbResponse.getStatus())).findFirst()
                .get();

        assertEquals(trTableBm1After.getTrTableBm1AamSet().size(), trTableBm1response.getTrTableAamSet().size());
        assertEquals(trTableBm1After.getTrTableBm1AkeSet().size(), trTableBm1response.getTrTableAkeSet().size());
        assertEquals(trTableBm1After.getTrTableBm1EikSet().size(), trTableBm1response.getTrTableEikSet().size());
        assertEquals(trTableBm1After.getTrTableBm1PivSet().size(), trTableBm1response.getTrTablePivSet().size());

        journaling.assertJournaling(trTableBsbRequest, "UC03", "TEST004");
    }

    @Test
    void test_Bm3_subtables_creation(@Autowired MockMvc mvc) throws Exception {
        saveBsbList(bsbList_B_before_t5);
        TrTableBsb trTableBsbRequest = cloneBsbDeep(bsbList_B_before_t5.get(1));
        TrTableBsb trTableBsbResponse = cloneBsbDeep(bsbList_B_after_t5.get(1));

        ResultActions correctionApiResponse = createBsbRequest(trTableBsbRequest, mvc);
        String responseContent = correctionApiResponse.andReturn().getResponse().getContentAsString();
        ValidationResults validationResults = objectMapper.readValue(responseContent, ValidationResults.class);
        com.fisglobal.taxreporting.controller.dto.bm3.TrTableBm3 trTableBm3response = validationResults.getTrTableBsb()
                .getTrTableBm3Set().stream()
                .filter(bm3 -> bm3.getTrTableBm3PK().getKeySatzart().equals(trTableBsbResponse.getStatus())).findFirst()
                .get();

        TrTableBm3 trTableBm3After = trTableBsbResponse.getTrTableBm3Set().stream()
                .filter(bm3 -> bm3.getTrTableBm3PK().getKeySatzart().equals(trTableBsbResponse.getStatus())).findFirst()
                .get();

        assertEquals(trTableBm3After.getTrTableBm3AamSet().size(), trTableBm3response.getTrTableAamSet().size());
        assertEquals(trTableBm3After.getTrTableBm3AkeSet().size(), trTableBm3response.getTrTableAkeSet().size());
        assertEquals(trTableBm3After.getTrTableBm3EikSet().size(), trTableBm3response.getTrTableEikSet().size());
        assertEquals(trTableBm3After.getTrTableBm3PivSet().size(), trTableBm3response.getTrTablePivSet().size());

        journaling.assertJournaling(trTableBsbRequest, "UC03", "TEST005");
    }

    private ResultActions createBsbRequest(TrTableBsb currentBsb, MockMvc mvc) throws Exception {
        String mandSl = currentBsb.getTrTableBsbPK().getMandSl();
        String keyIdNr = currentBsb.getTrTableBsbPK().getKeyIdNr().trim();
        long keyMeldejahr = currentBsb.getTrTableBsbPK().getKeyMeldejahr();
        String keyMuster = currentBsb.getTrTableBsbPK().getKeyMuster().trim();
        long keyLaufnummer = currentBsb.getTrTableBsbPK().getKeyLaufnummer();
        long keySysDatum = currentBsb.getTrTableBsbPK().getKeySysDatum();
        long keyUhrzeit = currentBsb.getTrTableBsbPK().getKeyUhrzeit();

        MockHttpServletRequestBuilder request = post(
                "/jstb-correction/v1/correction?mandSl={0}&keyIdNr={1}&keyMeldejahr={2}&keyMuster={3}&keyLaufnummer={4}&keySysDatum={5}&keyUhrzeit={6}",
                mandSl, keyIdNr, keyMeldejahr, keyMuster, keyLaufnummer, keySysDatum, keyUhrzeit);
        request.characterEncoding("utf-8");

        return mvc.perform(request);
    }
}
