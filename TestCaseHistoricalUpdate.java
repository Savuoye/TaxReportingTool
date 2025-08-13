package functional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import helpers.Journaling;
import helpers.SubcutaneousTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fisglobal.taxreporting.entity.model.taxreporting.bm1.TrTableBm1;
import com.fisglobal.taxreporting.entity.model.taxreporting.bm3.TrTableBm3;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;
import com.fisglobal.taxreporting.exception.ValidationMessage;
import com.fisglobal.taxreporting.validation.ValidationResult;
import com.fisglobal.taxreporting.validation.ValidationResults;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCaseHistoricalUpdate extends SubcutaneousTest {
    private List<TrTableBsb> bsbList_before_t1;
    private List<TrTableBsb> bsbList_before_t2;

    private List<TrTableBsb> bsbList_before_t3;
    private List<TrTableBsb> bsbList_patch_t3;
    private List<TrTableBsb> bsbList_after_t3;

    private List<TrTableBsb> bsbList_before_t4;
    private List<TrTableBsb> bsbList_patch_t4;
    private List<TrTableBsb> bsbList_after_t4;

    private List<TrTableBsb> bsbList_before_t5;
    private List<TrTableBsb> bsbList_patch_t5;
    private List<TrTableBsb> bsbList_after_t5;

    private List<TrTableBsb> bsbList_before_t6;
    private List<TrTableBsb> bsbList_patch_t6;
    private List<TrTableBsb> bsbList_after_t6;

    private List<TrTableBsb> bsbList_before_t7;
    private List<TrTableBsb> bsbList_patch_t7;
    private List<TrTableBsb> bsbList_after_t7;

    private List<TrTableBsb> bsbList_before_t8;
    private List<TrTableBsb> bsbList_patch_t8;
    private List<TrTableBsb> bsbList_after_t8;

    private List<TrTableBsb> bsbList_before_t9;
    private List<TrTableBsb> bsbList_patch_t9;
    private List<TrTableBsb> bsbList_after_t9;

    private List<TrTableBsb> bsbList_before_t10;
    private List<TrTableBsb> bsbList_patch_t10;
    private List<TrTableBsb> bsbList_after_t10;

    private List<TrTableBsb> bsbList_before_t11;
    private List<TrTableBsb> bsbList_patch_t11;
    private List<TrTableBsb> bsbList_after_t11;

    private List<TrTableBsb> bsbList_before_t12;
    private List<TrTableBsb> bsbList_patch_t12;
    private List<TrTableBsb> bsbList_after_t12;

    @Autowired
    Journaling journaling;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    void setupClass() {
        String filename = "Testcases_UC13.xlsx";
        bsbList_before_t1 = this.getTestCaseData("TEST001.before", filename);
        bsbList_before_t2 = this.getTestCaseData("TEST002.before", filename);

        bsbList_before_t3 = this.getTestCaseData("TEST003.before", filename);
        bsbList_patch_t3 = this.getTestCaseData("TEST003.patch", filename);
        bsbList_after_t3 = this.getTestCaseData("TEST003.after", filename);

        bsbList_before_t4 = this.getTestCaseData("TEST004.before", filename);
        bsbList_patch_t4 = this.getTestCaseData("TEST004.patch", filename);
        bsbList_after_t4 = this.getTestCaseData("TEST004.after", filename);

        bsbList_before_t5 = this.getTestCaseData("TEST005.before", filename);
        bsbList_patch_t5 = this.getTestCaseData("TEST005.patch", filename);
        bsbList_after_t5 = this.getTestCaseData("TEST005.after", filename);

        bsbList_before_t6 = this.getTestCaseData("TEST006.before", filename);
        bsbList_patch_t6 = this.getTestCaseData("TEST006.patch", filename);
        bsbList_after_t6 = this.getTestCaseData("TEST006.after", filename);

        bsbList_before_t7 = this.getTestCaseData("TEST007.before", filename);
        bsbList_patch_t7 = this.getTestCaseData("TEST007.patch", filename);
        bsbList_after_t7 = this.getTestCaseData("TEST007.after", filename);

        bsbList_before_t8 = this.getTestCaseData("TEST008.before", filename);
        bsbList_patch_t8 = this.getTestCaseData("TEST008.patch", filename);
        bsbList_after_t8 = this.getTestCaseData("TEST008.after", filename);

        bsbList_before_t9 = this.getTestCaseData("TEST009.before", filename);
        bsbList_patch_t9 = this.getTestCaseData("TEST009.patch", filename);
        bsbList_after_t9 = this.getTestCaseData("TEST009.after", filename);

        bsbList_before_t10 = this.getTestCaseData("TEST010.before", filename);
        bsbList_patch_t10 = this.getTestCaseData("TEST010.patch", filename);
        bsbList_after_t10 = this.getTestCaseData("TEST010.after", filename);

        bsbList_before_t11 = this.getTestCaseData("TEST011.before", filename);
        bsbList_patch_t11 = this.getTestCaseData("TEST011.patch", filename);
        bsbList_after_t11 = this.getTestCaseData("TEST011.after", filename);

        bsbList_before_t12 = this.getTestCaseData("TEST012.before", filename);
        bsbList_patch_t12 = this.getTestCaseData("TEST012.patch", filename);
        bsbList_after_t12 = this.getTestCaseData("TEST012.after", filename);

        timeProvider.stopTime();
    }

    @Test
    void test_update_hisztoricized_no_change(@Autowired MockMvc mvc) throws Exception {
        saveBsbList(bsbList_before_t1);
        TrTableBsb baseBsb = cloneBsbDeep(bsbList_before_t1.get(0));

        String response_baseBsb = historicalUpdateRequest(baseBsb, mvc);
        ValidationResults validationResults = objectMapper.readValue(response_baseBsb, ValidationResults.class);
        ValidationResult validationResult = validationResults.getValidationResults().get(0);
        assertEquals(BigDecimal.ZERO.toString(), validationResult.getAffectedField());

    }

    @Test
    void test_update_hisztoricized_year_validation(@Autowired MockMvc mvc) throws Exception {
        saveBsbList(bsbList_before_t2);
        TrTableBsb baseBsb = cloneBsbDeep(bsbList_before_t2.get(0));

        String response_baseBsb = historicalUpdateRequest(baseBsb, mvc);
        ValidationResults validationResults = objectMapper.readValue(response_baseBsb, ValidationResults.class);
        ValidationResult validationResult = validationResults.getValidationResults().get(0);
        assertEquals(ValidationMessage.ERROR_CODE_131, validationResult.getCode());

    }

    @Test
    void test_update_hisztoricized_ReadOnly_BSB_BM1(@Autowired MockMvc mvc) throws Exception {
        saveBsbList(bsbList_before_t3);

        TrTableBsb baseBsbRequest = cloneBsbDeep(bsbList_patch_t3.get(0));
        TrTableBsb baseBsbAfter = cloneBsbDeep(bsbList_after_t3.get(0));

        String response_baseBsb = historicalUpdateRequest(baseBsbRequest, mvc);
        ValidationResults validationResults = objectMapper.readValue(response_baseBsb, ValidationResults.class);
        ValidationResult validationResult = validationResults.getValidationResults().get(0);
        com.fisglobal.taxreporting.controller.dto.bsb.TrTableBsb trTableBsbResponse = validationResults.getTrTableBsb();

        TrTableBm1 trTableBm1After = baseBsbAfter.getTrTableBm1Set().first();
        com.fisglobal.taxreporting.controller.dto.bm1.TrTableBm1 trTableBm1Response = trTableBsbResponse
                .getTrTableBm1Set().first();

        assertEquals(BigDecimal.ZERO.toString(), validationResult.getAffectedField());
        assertEquals(baseBsbAfter.getMeldeStatus(), trTableBsbResponse.getMeldeStatus());
        assertEquals(baseBsbAfter.getKmId().trim(), trTableBsbResponse.getKmId().trim());

        assertEquals(trTableBm1After.getAamVeraeussert(), trTableBm1Response.getAamVeraeussert());
        assertEquals(trTableBm1After.getAkeVeraeussert(), trTableBm1Response.getAkeVeraeussert());
        assertEquals(trTableBm1After.getEikErstattet(), trTableBm1Response.getEikErstattet());
        assertEquals(trTableBm1After.getPivVerwahrt(), trTableBm1Response.getPivVerwahrt());

        assertEquals(trTableBm1After.getAnzahlAam(), trTableBm1Response.getAnzahlAam());
        assertEquals(trTableBm1After.getAnzahlAke(), trTableBm1Response.getAnzahlAke());
        assertEquals(trTableBm1After.getAnzahlEik(), trTableBm1Response.getAnzahlEik());
        assertEquals(trTableBm1After.getAnzahlPiv(), trTableBm1Response.getAnzahlPiv());

    }

    @Test
    void test_update_hisztoricized_ReadOnly_BSB_BM3(@Autowired MockMvc mvc) throws Exception {
        saveBsbList(bsbList_before_t4);

        TrTableBsb baseBsbRequest = cloneBsbDeep(bsbList_patch_t4.get(0));
        TrTableBsb baseBsbAfter = cloneBsbDeep(bsbList_after_t4.get(0));

        String response_baseBsb = historicalUpdateRequest(baseBsbRequest, mvc);
        ValidationResults validationResults = objectMapper.readValue(response_baseBsb, ValidationResults.class);
        ValidationResult validationResult = validationResults.getValidationResults().get(0);
        com.fisglobal.taxreporting.controller.dto.bsb.TrTableBsb trTableBsbResponse = validationResults.getTrTableBsb();

        TrTableBm3 trTableBm3After = baseBsbAfter.getTrTableBm3Set().first();
        com.fisglobal.taxreporting.controller.dto.bm3.TrTableBm3 trTableBm3Response = trTableBsbResponse
                .getTrTableBm3Set().first();

        assertEquals(BigDecimal.ZERO.toString(), validationResult.getAffectedField());
        assertEquals(baseBsbAfter.getMeldeStatus(), trTableBsbResponse.getMeldeStatus());
        assertEquals(baseBsbAfter.getKmId().trim(), trTableBsbResponse.getKmId().trim());
        assertEquals(baseBsbAfter.getRefKmId(), trTableBsbResponse.getRefKmId());

        assertEquals(trTableBm3After.getStornierung(), trTableBm3Response.getStornierung());
        assertEquals(trTableBm3After.getZusZeitraumBis(), trTableBm3Response.getZusZeitraumBis());
        assertEquals(trTableBm3After.getZusZeitraumVon(), trTableBm3Response.getZusZeitraumVon());

    }

    @Test
    void test_update_hisztoricized_BSB_Update(@Autowired MockMvc mvc) throws Exception {
        saveBsbList(bsbList_before_t5);

        TrTableBsb baseBsbRequest = cloneBsbDeep(bsbList_patch_t5.get(0));
        TrTableBsb baseBsbAfter = cloneBsbDeep(bsbList_after_t5.get(0));

        String response_baseBsb = historicalUpdateRequest(baseBsbRequest, mvc);
        ValidationResults validationResults = objectMapper.readValue(response_baseBsb, ValidationResults.class);
        com.fisglobal.taxreporting.controller.dto.bsb.TrTableBsb trTableBsbResponse = validationResults.getTrTableBsb();

        assertEquals(baseBsbAfter.getMeldejahr(), trTableBsbResponse.getMeldejahr());
        assertEquals(baseBsbAfter.getArt().trim(), trTableBsbResponse.getArt().trim());
        assertEquals(baseBsbAfter.getOrdnungsbegriff().trim(), trTableBsbResponse.getOrdnungsbegriff().trim());
        assertEquals(baseBsbAfter.getPersonRolle().trim(), trTableBsbResponse.getPersonRolle().trim());
        assertEquals(baseBsbAfter.getPersonId().trim(), trTableBsbResponse.getPersonId().trim());
        assertEquals(baseBsbAfter.getPartnerId().trim(), trTableBsbResponse.getPartnerId().trim());

        assertEquals(baseBsbAfter.getNp1Idnr().trim(), trTableBsbResponse.getNp1Idnr().trim());
        assertEquals(baseBsbAfter.getNp2Idnr().trim(), trTableBsbResponse.getNp2Idnr().trim());
        assertEquals(baseBsbAfter.getNwpTin().trim(), trTableBsbResponse.getNwpTin().trim());

    }

    @Test
    void test_update_hisztoricized_BM3_Update(@Autowired MockMvc mvc) throws Exception {
        saveBsbList(bsbList_before_t6);

        TrTableBsb baseBsbRequest = cloneBsbDeep(bsbList_patch_t6.get(0));
        TrTableBsb baseBsbAfter = cloneBsbDeep(bsbList_after_t6.get(0));

        String response_baseBsb = historicalUpdateRequest(baseBsbRequest, mvc);
        ValidationResults validationResults = objectMapper.readValue(response_baseBsb, ValidationResults.class);
        com.fisglobal.taxreporting.controller.dto.bsb.TrTableBsb trTableBsbResponse = validationResults.getTrTableBsb();

        TrTableBm3 trTableBm3After = baseBsbAfter.getTrTableBm3Set().first();
        com.fisglobal.taxreporting.controller.dto.bm3.TrTableBm3 trTableBm3Response = trTableBsbResponse
                .getTrTableBm3Set().first();

        assertEquals(trTableBm3After.getAbstandSteuerabz(), trTableBm3Response.getAbstandSteuerabz());
        assertEquals(trTableBm3After.getZahlungszeitrVon().toString(), trTableBm3Response.getZahlungszeitrVon());
        assertEquals(trTableBm3After.getZahlungszeitrBis().toString(), trTableBm3Response.getZahlungszeitrBis());
        assertEquals(trTableBm3After.getZahlungstag().toString(), trTableBm3Response.getZahlungstag());
        assertEquals(trTableBm3After.getErtragP431().toString(), trTableBm3Response.getErtragP431());
        assertEquals(trTableBm3After.getErtragTevP431().toString(), trTableBm3Response.getErtragTevP431());
        assertEquals(trTableBm3After.getErtragP19Reit1().toString(), trTableBm3Response.getErtragP19Reit1());
        assertEquals(trTableBm3After.getErtBeschrStpfl1().toString(), trTableBm3Response.getErtBeschrStpfl1());
        assertEquals(trTableBm3After.getErtragP432().toString(), trTableBm3Response.getErtragP432());
        assertEquals(trTableBm3After.getErtBeschrStpfl2().toString(), trTableBm3Response.getErtBeschrStpfl2());
        assertEquals(trTableBm3After.getErtragP433().toString(), trTableBm3Response.getErtragP433());
    }

    @Test
    void test_update_hisztoricized_BM1_Update(@Autowired MockMvc mvc) throws Exception {
        saveBsbList(bsbList_before_t7);

        TrTableBsb baseBsbRequest = cloneBsbDeep(bsbList_patch_t7.get(0));
        TrTableBsb baseBsbAfter = cloneBsbDeep(bsbList_after_t7.get(0));

        String response_baseBsb = historicalUpdateRequest(baseBsbRequest, mvc);
        ValidationResults validationResults = objectMapper.readValue(response_baseBsb, ValidationResults.class);
        com.fisglobal.taxreporting.controller.dto.bsb.TrTableBsb trTableBsbResponse = validationResults.getTrTableBsb();

        TrTableBm1 trTableBm1After = baseBsbAfter.getTrTableBm1Set().first();
        com.fisglobal.taxreporting.controller.dto.bm1.TrTableBm1 trTableBm1Response = trTableBsbResponse
                .getTrTableBm1Set().first();

        assertEquals(trTableBm1After.getKapitalertrag().toString(), trTableBm1Response.getKapitalertrag());

        assertEquals(trTableBm1After.getAktienErtrGew().toString(), trTableBm1Response.getAktienErtrGew());
        assertEquals(trTableBm1After.getStillhalterTermin().toString(), trTableBm1Response.getStillhalterTermin());
        assertEquals(trTableBm1After.getGewAltanteile().toString(), trTableBm1Response.getGewAltanteile());
        assertEquals(trTableBm1After.getErsatzBmg().toString(), trTableBm1Response.getErsatzBmg());

        assertEquals(trTableBm1After.getVerlustAkt().toString(), trTableBm1Response.getVerlustAkt());
        assertEquals(trTableBm1After.getVerlustOhneAkt().toString(), trTableBm1Response.getVerlustOhneAkt());
        assertEquals(trTableBm1After.getVerlTermingesch().toString(), trTableBm1Response.getVerlTermingesch());
        assertEquals(trTableBm1After.getVerlWertloUneinb().toString(), trTableBm1Response.getVerlWertloUneinb());

        assertEquals(trTableBm1After.getVerbrSparerpausch().toString(), trTableBm1Response.getVerbrSparerpausch());
        assertEquals(trTableBm1After.getKapstAbzug().toString(), trTableBm1Response.getKapstAbzug());
        assertEquals(trTableBm1After.getSolzAbzug().toString(), trTableBm1Response.getSolzAbzug());
        assertEquals(trTableBm1After.getKistAbzug().toString(), trTableBm1Response.getKistAbzug());
        assertEquals(trTableBm1After.getKistKonf().toString(), trTableBm1Response.getKistKonf());

    }

    @Test
    void test_update_hisztoricized_BM1_create_subtables(@Autowired MockMvc mvc) throws Exception {
        saveBsbList(bsbList_before_t8);

        TrTableBsb baseBsbRequest = cloneBsbDeep(bsbList_patch_t8.get(0));
        TrTableBsb baseBsbAfter = cloneBsbDeep(bsbList_after_t8.get(0));

        String response_baseBsb = historicalUpdateRequest(baseBsbRequest, mvc);
        ValidationResults validationResults = objectMapper.readValue(response_baseBsb, ValidationResults.class);
        com.fisglobal.taxreporting.controller.dto.bsb.TrTableBsb trTableBsbResponse = validationResults.getTrTableBsb();

        TrTableBm1 trTableBm1After = baseBsbAfter.getTrTableBm1Set().first();
        com.fisglobal.taxreporting.controller.dto.bm1.TrTableBm1 trTableBm1Response = trTableBsbResponse
                .getTrTableBm1Set().first();

        assertEquals(trTableBm1After.getAamVeraeussert(), trTableBm1Response.getAamVeraeussert());
        assertEquals(trTableBm1After.getAkeVeraeussert(), trTableBm1Response.getAkeVeraeussert());
        assertEquals(trTableBm1After.getEikErstattet(), trTableBm1Response.getEikErstattet());
        assertEquals(trTableBm1After.getPivVerwahrt(), trTableBm1Response.getPivVerwahrt());

        assertEquals(trTableBm1After.getAnzahlAam(), trTableBm1Response.getAnzahlAam());
        assertEquals(trTableBm1After.getAnzahlAke(), trTableBm1Response.getAnzahlAke());
        assertEquals(trTableBm1After.getAnzahlEik(), trTableBm1Response.getAnzahlEik());
        assertEquals(trTableBm1After.getAnzahlPiv(), trTableBm1Response.getAnzahlPiv());

        assertEquals(trTableBm1After.getTrTableAamSet().size(), trTableBm1Response.getTrTableAamSet().size());
        assertEquals(trTableBm1After.getTrTableAkeSet().size(), trTableBm1Response.getTrTableAkeSet().size());
        assertEquals(trTableBm1After.getTrTableEikSet().size(), trTableBm1Response.getTrTableEikSet().size());
        assertEquals(trTableBm1After.getTrTablePivSet().size(), trTableBm1Response.getTrTablePivSet().size());

    }

    @Test
    void test_update_hisztoricized_BM1_remove_subtables(@Autowired MockMvc mvc) throws Exception {
        saveBsbList(bsbList_before_t9);

        TrTableBsb baseBsbRequest = cloneBsbDeep(bsbList_patch_t9.get(0));
        TrTableBsb baseBsbAfter = cloneBsbDeep(bsbList_after_t9.get(0));

        String response_baseBsb = historicalUpdateRequest(baseBsbRequest, mvc);
        ValidationResults validationResults = objectMapper.readValue(response_baseBsb, ValidationResults.class);
        com.fisglobal.taxreporting.controller.dto.bsb.TrTableBsb trTableBsbResponse = validationResults.getTrTableBsb();

        TrTableBm1 trTableBm1After = baseBsbAfter.getTrTableBm1Set().first();
        com.fisglobal.taxreporting.controller.dto.bm1.TrTableBm1 trTableBm1Response = trTableBsbResponse
                .getTrTableBm1Set().first();

        assertEquals(trTableBm1After.getAamVeraeussert(), trTableBm1Response.getAamVeraeussert());
        assertEquals(trTableBm1After.getAkeVeraeussert(), trTableBm1Response.getAkeVeraeussert());
        assertEquals(trTableBm1After.getEikErstattet(), trTableBm1Response.getEikErstattet());
        assertEquals(trTableBm1After.getPivVerwahrt(), trTableBm1Response.getPivVerwahrt());

        assertEquals(trTableBm1After.getAnzahlAam(), trTableBm1Response.getAnzahlAam());
        assertEquals(trTableBm1After.getAnzahlAke(), trTableBm1Response.getAnzahlAke());
        assertEquals(trTableBm1After.getAnzahlEik(), trTableBm1Response.getAnzahlEik());
        assertEquals(trTableBm1After.getAnzahlPiv(), trTableBm1Response.getAnzahlPiv());

        assertEquals(trTableBm1After.getTrTableAamSet().size(), trTableBm1Response.getTrTableAamSet().size());
        assertEquals(trTableBm1After.getTrTableAkeSet().size(), trTableBm1Response.getTrTableAkeSet().size());
        assertEquals(trTableBm1After.getTrTableEikSet().size(), trTableBm1Response.getTrTableEikSet().size());
        assertEquals(trTableBm1After.getTrTablePivSet().size(), trTableBm1Response.getTrTablePivSet().size());

    }

    @Test
    void test_update_hisztoricized_BM3_create_subtables(@Autowired MockMvc mvc) throws Exception {
        saveBsbList(bsbList_before_t10);

        TrTableBsb baseBsbRequest = cloneBsbDeep(bsbList_patch_t10.get(0));
        TrTableBsb baseBsbAfter = cloneBsbDeep(bsbList_after_t10.get(0));

        String response_baseBsb = historicalUpdateRequest(baseBsbRequest, mvc);
        ValidationResults validationResults = objectMapper.readValue(response_baseBsb, ValidationResults.class);
        com.fisglobal.taxreporting.controller.dto.bsb.TrTableBsb trTableBsbResponse = validationResults.getTrTableBsb();

        TrTableBm3 trTableBm3After = baseBsbAfter.getTrTableBm3Set().first();
        com.fisglobal.taxreporting.controller.dto.bm3.TrTableBm3 trTableBm3Response = trTableBsbResponse
                .getTrTableBm3Set().first();

        assertEquals(trTableBm3After.getAkbVeraeussert(), trTableBm3Response.getAkbVeraeussert());
        assertEquals(trTableBm3After.getAkeVeraeussert(), trTableBm3Response.getAkeVeraeussert());
        assertEquals(trTableBm3After.getEikErstattet(), trTableBm3Response.getEikErstattet());
        assertEquals(trTableBm3After.getPivVerwahrt(), trTableBm3Response.getPivVerwahrt());

        assertEquals(trTableBm3After.getAnzahlAkb(), trTableBm3Response.getAnzahlAkb());
        assertEquals(trTableBm3After.getAnzahlAke(), trTableBm3Response.getAnzahlAke());
        assertEquals(trTableBm3After.getAnzahlEik(), trTableBm3Response.getAnzahlEik());
        assertEquals(trTableBm3After.getAnzahlPiv(), trTableBm3Response.getAnzahlPiv());

        assertEquals(trTableBm3After.getTrTableAkbSet().size(), trTableBm3Response.getTrTableAkbSet().size());
        assertEquals(trTableBm3After.getTrTableAkeSet().size(), trTableBm3Response.getTrTableAkeSet().size());
        assertEquals(trTableBm3After.getTrTableEikSet().size(), trTableBm3Response.getTrTableEikSet().size());
        assertEquals(trTableBm3After.getTrTablePivSet().size(), trTableBm3Response.getTrTablePivSet().size());

    }

    @Test
    void test_update_hisztoricized_BM3_delete_subtables(@Autowired MockMvc mvc) throws Exception {
        saveBsbList(bsbList_before_t11);

        TrTableBsb baseBsbRequest = cloneBsbDeep(bsbList_patch_t11.get(0));
        TrTableBsb baseBsbAfter = cloneBsbDeep(bsbList_after_t11.get(0));

        String response_baseBsb = historicalUpdateRequest(baseBsbRequest, mvc);
        ValidationResults validationResults = objectMapper.readValue(response_baseBsb, ValidationResults.class);
        com.fisglobal.taxreporting.controller.dto.bsb.TrTableBsb trTableBsbResponse = validationResults.getTrTableBsb();

        TrTableBm3 trTableBm3After = baseBsbAfter.getTrTableBm3Set().first();
        com.fisglobal.taxreporting.controller.dto.bm3.TrTableBm3 trTableBm3Response = trTableBsbResponse
                .getTrTableBm3Set().first();

        assertEquals(trTableBm3After.getAkbVeraeussert(), trTableBm3Response.getAkbVeraeussert());
        assertEquals(trTableBm3After.getAkeVeraeussert(), trTableBm3Response.getAkeVeraeussert());
        assertEquals(trTableBm3After.getEikErstattet(), trTableBm3Response.getEikErstattet());
        assertEquals(trTableBm3After.getPivVerwahrt(), trTableBm3Response.getPivVerwahrt());

        assertEquals(trTableBm3After.getAnzahlAkb(), trTableBm3Response.getAnzahlAkb());
        assertEquals(trTableBm3After.getAnzahlAke(), trTableBm3Response.getAnzahlAke());
        assertEquals(trTableBm3After.getAnzahlEik(), trTableBm3Response.getAnzahlEik());
        assertEquals(trTableBm3After.getAnzahlPiv(), trTableBm3Response.getAnzahlPiv());

        assertEquals(trTableBm3After.getTrTableAkbSet().size(), trTableBm3Response.getTrTableAkbSet().size());
        assertEquals(trTableBm3After.getTrTableAkeSet().size(), trTableBm3Response.getTrTableAkeSet().size());
        assertEquals(trTableBm3After.getTrTableEikSet().size(), trTableBm3Response.getTrTableEikSet().size());
        assertEquals(trTableBm3After.getTrTablePivSet().size(), trTableBm3Response.getTrTablePivSet().size());

    }

    @Test
    void test_update_hisztoricized_BM3_update_subtables(@Autowired MockMvc mvc) throws Exception {
        saveBsbList(bsbList_before_t12);

        TrTableBsb baseBsbRequest = cloneBsbDeep(bsbList_patch_t12.get(0));
        TrTableBsb baseBsbAfter = cloneBsbDeep(bsbList_after_t12.get(0));

        String response_baseBsb = historicalUpdateRequest(baseBsbRequest, mvc);
        ValidationResults validationResults = objectMapper.readValue(response_baseBsb, ValidationResults.class);
        com.fisglobal.taxreporting.controller.dto.bsb.TrTableBsb trTableBsbResponse = validationResults.getTrTableBsb();

        TrTableBm3 trTableBm3After = baseBsbAfter.getTrTableBm3Set().first();
        com.fisglobal.taxreporting.controller.dto.bm3.TrTableBm3 trTableBm3Response = trTableBsbResponse
                .getTrTableBm3Set().first();

        assertTrue(trTableBm3After.getTrTableBm3AkbSet().stream().anyMatch(e -> trTableBm3Response.getTrTableBm3AkbSet()
                .stream().anyMatch(j -> j.getAkbAntBezeichn().trim().equals(e.getAkbAntBezeichn().trim()))));

        assertTrue(trTableBm3After.getTrTableBm3AkeSet().stream().anyMatch(e -> trTableBm3Response.getTrTableBm3AkeSet()
                .stream().anyMatch(j -> j.getAkeAntBezeichn().trim().equals(e.getAkeAntBezeichn().trim()))));

        assertTrue(trTableBm3After.getTrTableBm3EikSet().stream().anyMatch(e -> trTableBm3Response.getTrTableBm3EikSet()
                .stream().anyMatch(j -> j.getEikAntBezeichn().trim().equals(e.getEikAntBezeichn().trim()))));

        assertTrue(trTableBm3After.getTrTableBm3PivSet().stream().anyMatch(e -> trTableBm3Response.getTrTableBm3PivSet()
                .stream().anyMatch(j -> j.getPivAntBezeichng().trim().equals(e.getPivAntBezeichng().trim()))));

    }

    protected String historicalUpdateRequest(TrTableBsb bsb, MockMvc mvc) throws Exception {
        MockHttpServletRequestBuilder request = put("/jstb-correction/v1/history");
        request.accept(MediaType.APPLICATION_JSON_VALUE);
        request.contentType(MediaType.APPLICATION_JSON_VALUE);
        request.characterEncoding("utf-8");
        request.content(new ObjectMapper().writeValueAsString(bsb));
        MvcResult result = mvc.perform(request).andReturn();
        this.storeChanges();
        return result.getResponse().getContentAsString();
    }
}
