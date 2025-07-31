package com.fisglobal.taxreporting.controller.taxreport;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fisglobal.taxreporting.controller.dto.TaxReportingDetail;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;
import com.fisglobal.taxreporting.enums.ValidationClassEnum;
import com.fisglobal.taxreporting.exception.BusinessValidation;
import com.fisglobal.taxreporting.exception.RecordNotFoundException;
import com.fisglobal.taxreporting.exception.ValidationResultsException;
import com.fisglobal.taxreporting.service.taxreportingapproval.TaxReportingApprovalService;
import com.fisglobal.taxreporting.service.taxreportingcancellation.impl.TaxReportingCancellationServiceImpl;
import com.fisglobal.taxreporting.service.taxreportingcorrection.TaxReportingCorrectionService;
import com.fisglobal.taxreporting.service.taxreportingcreatecertificate.TaxReportingCreateCorrectionService;
import com.fisglobal.taxreporting.service.taxreportingcreatecertificate.impl.TaxReportingAdjustmentCertificateService;
import com.fisglobal.taxreporting.service.taxreportingdeletion.TaxReportingDeletionService;
import com.fisglobal.taxreporting.service.taxreportingdetail.TaxReportingDetailService;
import com.fisglobal.taxreporting.service.taxreportinghistory.TaxReportingCreateHistoricizedService;
import com.fisglobal.taxreporting.service.taxreportinghistory.TaxReportingHistoricizedDeletionService;
import com.fisglobal.taxreporting.service.taxreportinghistory.TaxReportingHistoricizedUpdateService;
import com.fisglobal.taxreporting.service.taxreportingresponse.TaxReportingResponseService;
import com.fisglobal.taxreporting.service.taxreportingreview.TaxReportingReviewService;
import com.fisglobal.taxreporting.service.taxreportingsearch.TaxReportingSearchService;
import com.fisglobal.taxreporting.service.taxreportingsearch.model.TaxReports;
import com.fisglobal.taxreporting.util.CommonConstants;
import com.fisglobal.taxreporting.validation.ResponseMessage;
import com.fisglobal.taxreporting.validation.ValidationResults;

import de.kordoba.framework.common.log.KORLogger;


/**
 * Taxreporting controller handle all the API request, validate it, and then
 * return the response
 */
@Validated
@RestController
@EnableWebMvc
@Tag(name = "taxreporting", description = "the taxreporting API")
@RequestMapping(value = "jstb-correction/v1")
public class TaxReportingController {
    private static final KORLogger LOG = KORLogger.getLogger(TaxReportingController.class);

    @Autowired
    private TaxReportingSearchService taxReportingSearchService;

    @Autowired
    private TaxReportingDetailService taxReportingServiceDetail;

    @Autowired
    private TaxReportingDeletionService taxReportingDeletionService;

    @Autowired
    private TaxReportingCorrectionService taxReportingCorrectionService;

    @Autowired
    private TaxReportingApprovalService taxReportingApprovalService;

    @Autowired
    private TaxReportingCancellationServiceImpl taxReportingCancellationService;

    @Autowired
    private TaxReportingReviewService taxReportingReviewService;

    @Autowired
    private TaxReportingAdjustmentCertificateService taxReportingCertificateService;

    @Autowired
    private TaxReportingResponseService taxReportingResponseService;

    @Autowired
    private TaxReportingCreateCorrectionService taxReportingCreateCorrectionService;

    @Autowired
    private TaxReportingCreateHistoricizedService taxReportingCreateHistoricizedService;

    @Autowired
    private TaxReportingHistoricizedUpdateService taxReportingHistoricizedUpdateService;

    @Autowired
    private TaxReportingHistoricizedDeletionService taxReportingHistoricizedDeletionService;

    /**
     * Tax report search api will accept search parameters, validate the input
     * values and construct the response. Response contains list of taxReports
     * object
     *
     * @param keyIdNr
     *                                        Tax report Key id number
     * @param keyMeldejahr
     *                                        Tax report filed year
     * @param meldeStatus
     *                                        Current workflow status of the taxreport
     * @param antwortStatus
     *                                        Result status after taxreport update
     * @param pageNumber
     *                                        Filter the result based on page number
     * @param pageLimit
     *                                        Filter the number of result in a page
     * @param totalCountWithoutPagingFlag
     *                                        Total number of Taxreport available
     * @param response
     *                                        Http response object
     *
     * @return Tax report response object
     *
     * @throws RecordNotFoundException
     *                                        throws no records available
     * @throws ValidationResultsException
     *                                        throws validation error details
     */
    @PreAuthorize("hasAuthority('SEARCH_TAXREPORTING')")
    @Operation(
            tags = { "taxreportingsearch" },
            description = "Search tax reporting details",
            method = "searchTaxReports")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(schema = @Schema(implementation = TaxReports.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))) })
    @GetMapping(path = "search", produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody ResponseEntity<TaxReports> searchTaxReports(
            @Size(min = 1, max = 13) @Parameter(
                    description = "EN: <br> keyIdNr" + "DE: <br> keyIdNr") @Valid @RequestParam(
                            value = "keyIdNr",
                            required = false) String keyIdNr,

            @Parameter(description = "EN: <br> keyMeldejahr" + "DE: <br> keyMeldejahr") @Valid @RequestParam(
                    value = "keyMeldejahr",
                    required = false) Long keyMeldejahr,

            @Size(min = 1, max = 2) @Pattern(regexp = "[\\d\\s]*", message = "Must be a number or space") @Parameter(
                    description = "EN: <br> meldeStatus" + "DE: <br> meldeStatus") @Valid @RequestParam(
                            value = "meldeStatus",
                            required = false) String meldeStatus,

            @Size(max = 2) @Pattern(regexp = "[\\d\\s]*", message = "Must be a number") @Parameter(
                    description = "EN: <br> antwortStatus" + "DE: <br> antwortStatus") @Valid @RequestParam(
                            value = "antwortStatus",
                            required = false) String antwortStatus,

            @Valid @Parameter(description = "EN: <br> pageNumber" + "DE: <br> pageNumber") @RequestParam(
                    value = "pageNumber",
                    required = true) int pageNumber,

            @Valid @Parameter(description = "EN: <br> pageLimit" + "DE: <br> pageLimit") @RequestParam(
                    value = "pageLimit",
                    required = true) int pageLimit,

            @Valid @Parameter(
                    description = "EN: <br> TotalCountWithoutPagingFlag "
                            + "DE: <br> TotalCountWithoutPagingFlag") @RequestParam(
                                    value = "totalCountWithoutPagingFlag",
                                    defaultValue = "false") boolean totalCountWithoutPagingFlag,

            HttpServletResponse response) throws RecordNotFoundException, ValidationResultsException {
        keyIdNr = keyIdNr != null ? keyIdNr.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;
        meldeStatus = meldeStatus != null ? meldeStatus.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE)
                : null;
        antwortStatus = antwortStatus != null
                ? antwortStatus.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE)
                : null;

        LOG.trace("[TaxReportingController] [searchTaxReports] Enter into searchTaxReports method");

        taxReportingSearchService.mandatoryValidationForNonEmptyParameter(keyIdNr, keyMeldejahr, meldeStatus,
                antwortStatus);

        if (meldeStatus != null) {
            taxReportingSearchService.mandatoryFieldValidationCheck(meldeStatus, keyIdNr);
        }
        if (antwortStatus != null) {
            taxReportingSearchService.mandatoryValidationForAntwortStatus(antwortStatus, keyIdNr);
        }
        if (keyMeldejahr != null) {
            taxReportingSearchService.mandatoryValidationForkeyMeldejahr(keyIdNr, meldeStatus, antwortStatus);
        }

        TaxReports taxReports = taxReportingSearchService.searchByTaxReportParameters(keyIdNr, keyMeldejahr,
                meldeStatus, antwortStatus, pageNumber, pageLimit, totalCountWithoutPagingFlag);

        LOG.trace("[TaxReportingController] [searchTaxReports] Exit searchTaxReports method");

        return new ResponseEntity<>(taxReports, HttpStatus.OK);
    }

    /**
     * Details api will accept taxreport unique parameters, validate the input
     * values and return full details of taxreport record .
     *
     * @param mandSl
     *                          Section of tax report
     * @param keyIdNr
     *                          Tax report Key id number
     * @param keyMeldejahr
     *                          Tax report filed year
     * @param keyMuster
     *                          Taxreport type whether BM1 / BM3
     * @param keyLaufnummer
     *                          Sequence number for Key id number
     * @param keySysDatum
     *                          Taxreport created date
     * @param keyUhrzeit
     *                          Tax report created time
     * @param response
     *                          Used to set the uuid in header
     *
     * @return Tax report detail response object
     *
     * @throws RecordNotFoundException
     *                                        throws no records available in table
     * @throws ValidationResultsException
     *                                        throws validation error details
     */
    @PreAuthorize("hasAuthority('DETAILS_TAXREPORTING')")
    @Operation(tags = { "taxreporting" }, description = "Get all tax reporting details", method = "getDetailTaxReports")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(schema = @Schema(implementation = TrTableBsb.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))) })
    @GetMapping(path = "details", produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody ResponseEntity<TaxReportingDetail> getAllDetailTaxReports(
            @Size(min = 1, max = 3) @Parameter(
                    description = "EN: <br> mandSl" + "DE: <br> mandSl") @Valid @RequestParam(
                            value = "mandSl",
                            required = true) String mandSl,
            @Size(min = 1, max = 13) @Parameter(
                    description = "EN: <br> keyIdNr" + "DE: <br> keyIdNr") @Valid @RequestParam(
                            value = "keyIdNr",
                            required = true) String keyIdNr,
            @Parameter(description = "EN: <br> keyMeldejahr" + "DE: <br> keyMeldejahr") @Valid @RequestParam(
                    value = "keyMeldejahr",
                    required = true) long keyMeldejahr,
            @Parameter(description = "EN: <br> keyMuster" + "DE: <br> keyMuster") @Valid @RequestParam(
                    value = "keyMuster",
                    required = true) String keyMuster,
            @Parameter(description = "EN: <br> keyLaufnummer" + "DE: <br> keyLaufnummer") @Valid @RequestParam(
                    value = "keyLaufnummer",
                    required = true) long keyLaufnummer,
            @Parameter(description = "EN: <br> keySysDatum" + "DE: <br> keySysDatum") @Valid @RequestParam(
                    value = "keySysDatum",
                    required = true) long keySysDatum,
            @Parameter(description = "EN: <br> keyUhrzeit" + "DE: <br> keyUhrzeit") @Valid @RequestParam(
                    value = "keyUhrzeit",
                    required = true) long keyUhrzeit,
            HttpServletResponse response) throws RecordNotFoundException, ValidationResultsException {
        mandSl = mandSl != null ? mandSl.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;
        keyIdNr = keyIdNr != null ? keyIdNr.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;
        keyMuster = keyMuster != null ? keyMuster.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;

        LOG.trace("[TaxReportingController] [getAllDetailTaxReports] Enter in to getAllDetailTaxReports method");

        response.setHeader("uuid", String.valueOf(UUID.randomUUID()));

        TrTableBsb trTableBsb = taxReportingServiceDetail.getAllDetailTaxReports(mandSl, keyIdNr, keyMeldejahr,
                keyMuster, keyLaufnummer, keySysDatum, keyUhrzeit);

        TaxReportingDetail taxReportingDetail = taxReportingResponseService.taxreportingDetailResponse(trTableBsb);

        LOG.trace("[TaxReportingController] [getAllDetailTaxReports] Exit getAllDetailTaxReports method");

        return new ResponseEntity<TaxReportingDetail>(taxReportingDetail, HttpStatus.OK);
    }

    /**
     * This api will update the request and persist in the table
     *
     * @param data
     *                 Tax report data object
     * 
     * @return Validation result response object
     * 
     * @throws ValidationResultsException
     *                                        throws validation exception details
     * @throws IOException
     *                                        throws input object is not available
     * @throws ClassNotFoundException
     *                                        throws input class is not available
     * @throws RecordNotFoundException
     *                                        throws no records available in table
     */
    @PreAuthorize("hasAuthority('UPDATE_TAXREPORTING')")
    @Operation(description = "Update tax reporting data", tags = { "taxreportingcorrection" })
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))) })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ValidationResults> updateTaxReportingData(
            @RequestBody com.fisglobal.taxreporting.controller.dto.bsb.TrTableBsb data)
            throws ValidationResultsException, IOException, ClassNotFoundException, RecordNotFoundException {
        LOG.trace("[TaxReportingController] [updateTaxReportingData] Enter into update taxreporting method");

        BusinessValidation.inputRequestFieldValidation(data);

        TrTableBsb requestData = new ObjectMapper().convertValue(data, TrTableBsb.class);
        BusinessValidation.duplicateDataValidation(data, requestData);

        TrTableBsb validatedData = new TrTableBsb();
        BeanUtils.copyProperties(requestData, validatedData);

        LOG.trace("[TaxReportingController] [updateTaxReportingData] Enter into update taxreporting method for BSB: {}",
                validatedData.taxCertificateInfo());

        ValidationResults validationResults = taxReportingCorrectionService.updateTaxReportingData(validatedData,
                CommonConstants.UPDATE_ACTION);

        LOG.trace("[TaxReportingController] [updateTaxReportingData] Exit update taxreporting method for BSB: {}",
                validatedData.taxCertificateInfo());

        return new ResponseEntity<>(validationResults, HttpStatus.OK);
    }

    /**
     * Delete api will remove the data from sub tables and update the status in BSB
     * table
     *
     * @param mandSl
     *                          Section of tax report
     * @param keyIdNr
     *                          Tax report Key id number
     * @param keyMeldejahr
     *                          Tax report filed year
     * @param keyMuster
     *                          Taxreport type whether BM1 / BM3
     * @param keyLaufnummer
     *                          Sequence number for Key id number
     * @param keySysDatum
     *                          Taxreport created date
     * @param keyUhrzeit
     *                          Tax report created time
     * @param response
     *                          Used to set the uuid in header
     * 
     * @return Validation result response object
     * 
     * @throws RecordNotFoundException
     *                                        throws no records available in table
     * @throws ValidationResultsException
     *                                        throws validation error details
     * @throws IOException
     *                                        throws input object is not available
     * @throws ClassNotFoundException
     *                                        throws input class is not available
     */
    @PreAuthorize("hasAuthority('DELETE_TAXREPORTING')")
    @Operation(description = "Delete tax reporting data", tags = { "taxreportingdeletion" })
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))) })
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ValidationResults> deleteTaxReportingData(
            @Size(min = 1, max = 3) @Parameter(
                    description = "EN: <br> mandSl" + "DE: <br> mandSl") @Valid @RequestParam(
                            value = "mandSl",
                            required = true) String mandSl,
            @Size(min = 1, max = 13) @Parameter(
                    description = "EN: <br> keyIdNr" + "DE: <br> keyIdNr") @Valid @RequestParam(
                            value = "keyIdNr",
                            required = true) String keyIdNr,
            @Parameter(description = "EN: <br> keyMeldejahr" + "DE: <br> keyMeldejahr") @Valid @RequestParam(
                    value = "keyMeldejahr",
                    required = true) long keyMeldejahr,
            @Parameter(description = "EN: <br> keyMuster" + "DE: <br> keyMuster") @Valid @RequestParam(
                    value = "keyMuster",
                    required = true) String keyMuster,
            @Parameter(description = "EN: <br> keyLaufnummer" + "DE: <br> keyLaufnummer") @Valid @RequestParam(
                    value = "keyLaufnummer",
                    required = true) long keyLaufnummer,
            @Parameter(description = "EN: <br> keySysDatum" + "DE: <br> keySysDatum") @Valid @RequestParam(
                    value = "keySysDatum",
                    required = true) long keySysDatum,
            @Parameter(description = "EN: <br> keyUhrzeit" + "DE: <br> keyUhrzeit") @Valid @RequestParam(
                    value = "keyUhrzeit",
                    required = true) long keyUhrzeit,
            HttpServletResponse response)
            throws RecordNotFoundException, ValidationResultsException, IOException, ClassNotFoundException {
        mandSl = mandSl != null ? mandSl.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;
        keyIdNr = keyIdNr != null ? keyIdNr.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;
        keyMuster = keyMuster != null ? keyMuster.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;

        LOG.trace("[TaxReportingController] [deleteTaxReportingData] Enter into delete tax report method");

        mandSl = mandSl != null ? mandSl.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;
        keyIdNr = keyIdNr != null ? keyIdNr.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;
        keyMuster = keyMuster != null ? keyMuster.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;

        ValidationResults validationResults = taxReportingDeletionService.deleteTaxReportingData(mandSl, keyIdNr,
                keyMeldejahr, keyMuster, keyLaufnummer, keySysDatum, keyUhrzeit);

        LOG.trace("[TaxReportingController] [deleteTaxReportingData] Exit delete tax report method");

        return new ResponseEntity<>(validationResults, HttpStatus.OK);
    }

    /**
     * Review api will validate the changes and initiate the workflow process
     *
     * @param mandSl
     *                          Section of tax report
     * @param keyIdNr
     *                          Tax report Key id number
     * @param keyMeldejahr
     *                          Tax report filed year
     * @param keyMuster
     *                          Tax report type whether BM1 / BM3
     * @param keyLaufnummer
     *                          Sequence number for Key id number
     * @param keySysDatum
     *                          Tax report created date
     * @param keyUhrzeit
     *                          Tax report created time
     * 
     * @return Validation result response object
     * 
     * @throws ValidationResultsException
     *                                        throws validation error details
     */
    @PreAuthorize("hasAuthority('REVIEW_TAXREPORTING')")
    @Operation(description = "Mark data for review", tags = { "taxreportingreview" })
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))) })
    @PatchMapping(path = "review", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ValidationResults> markDataForReview(
            @Size(min = 1, max = 3) @Parameter(
                    description = "EN: <br> mandSl" + "DE: <br> mandSl") @Valid @RequestParam(
                            value = "mandSl",
                            required = true) String mandSl,
            @Size(min = 1, max = 13) @Parameter(
                    description = "EN: <br> keyIdNr" + "DE: <br> keyIdNr") @Valid @RequestParam(
                            value = "keyIdNr",
                            required = true) String keyIdNr,
            @Parameter(description = "EN: <br> keyMeldejahr" + "DE: <br> keyMeldejahr") @Valid @RequestParam(
                    value = "keyMeldejahr",
                    required = true) long keyMeldejahr,
            @Parameter(description = "EN: <br> keyMuster" + "DE: <br> keyMuster") @Valid @RequestParam(
                    value = "keyMuster",
                    required = true) String keyMuster,
            @Parameter(description = "EN: <br> keyLaufnummer" + "DE: <br> keyLaufnummer") @Valid @RequestParam(
                    value = "keyLaufnummer",
                    required = true) long keyLaufnummer,
            @Parameter(description = "EN: <br> keySysDatum" + "DE: <br> keySysDatum") @Valid @RequestParam(
                    value = "keySysDatum",
                    required = true) long keySysDatum,
            @Parameter(description = "EN: <br> keyUhrzeit" + "DE: <br> keyUhrzeit") @Valid @RequestParam(
                    value = "keyUhrzeit",
                    required = true) long keyUhrzeit)
            throws ValidationResultsException {
        mandSl = mandSl != null ? mandSl.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;
        keyIdNr = keyIdNr != null ? keyIdNr.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;
        keyMuster = keyMuster != null ? keyMuster.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;

        LOG.trace("[TaxReportingController] [markDataForReview] Enter in to review tax report method");

        ValidationResults validationResults = taxReportingReviewService.markForReview(mandSl, keyIdNr, keyMeldejahr,
                keyMuster, keyLaufnummer, keySysDatum, keyUhrzeit);

        LOG.trace("[TaxReportingController] [markDataForReview] Exit review tax report method");

        return new ResponseEntity<>(validationResults, HttpStatus.OK);

    }

    /**
     * Cancel api will cancel taxreport record in the workflow and the status to S
     *
     * @param mandSl
     *                          Section of tax report
     * @param keyIdNr
     *                          Tax report Key id number
     * @param keyMeldejahr
     *                          Tax report filed year
     * @param keyMuster
     *                          Tax report type whether BM1 / BM3
     * @param keyLaufnummer
     *                          Sequence number for Key id number
     * @param keySysDatum
     *                          Tax report created date
     * @param keyUhrzeit
     *                          Tax report created time
     * 
     * @return Validation result response object
     * 
     * @throws ValidationResultsException
     *                                        throws validation error details
     */
    @PreAuthorize("hasAuthority('CANCEL_TAXREPORTING')")
    @Operation(description = "Create certificate cancellation", tags = { "taxreportingcancellation" })
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))) })
    @PatchMapping(path = "cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ValidationResults> createCertificateCancellation(
            @Size(min = 1, max = 3) @Parameter(
                    description = "EN: <br> mandSl" + "DE: <br> mandSl") @Valid @RequestParam(
                            value = "mandSl",
                            required = true) String mandSl,
            @Size(min = 1, max = 13) @Parameter(
                    description = "EN: <br> keyIdNr" + "DE: <br> keyIdNr") @Valid @RequestParam(
                            value = "keyIdNr",
                            required = true) String keyIdNr,
            @Parameter(description = "EN: <br> keyMeldejahr" + "DE: <br> keyMeldejahr") @Valid @RequestParam(
                    value = "keyMeldejahr",
                    required = true) long keyMeldejahr,
            @Parameter(description = "EN: <br> keyMuster" + "DE: <br> keyMuster") @Valid @RequestParam(
                    value = "keyMuster",
                    required = true) String keyMuster,
            @Parameter(description = "EN: <br> keyLaufnummer" + "DE: <br> keyLaufnummer") @Valid @RequestParam(
                    value = "keyLaufnummer",
                    required = true) long keyLaufnummer,
            @Parameter(description = "EN: <br> keySysDatum" + "DE: <br> keySysDatum") @Valid @RequestParam(
                    value = "keySysDatum",
                    required = true) long keySysDatum,
            @Parameter(description = "EN: <br> keyUhrzeit" + "DE: <br> keyUhrzeit") @Valid @RequestParam(
                    value = "keyUhrzeit",
                    required = true) long keyUhrzeit)
            throws ValidationResultsException {
        mandSl = mandSl != null ? mandSl.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;
        keyIdNr = keyIdNr != null ? keyIdNr.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;
        keyMuster = keyMuster != null ? keyMuster.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;

        LOG.trace("[TaxReportingController] [createCertificateCancellation] Enter into cancel tax report method");

        ValidationResults validationResults = taxReportingCancellationService.createCertificateCancellation(mandSl,
                keyIdNr, keyMeldejahr, keyMuster, keyLaufnummer, keySysDatum, keyUhrzeit);

        LOG.trace("[TaxReportingController] [createCertificateCancellation] Exit cancel tax report method");

        return new ResponseEntity<>(validationResults, HttpStatus.OK);
    }

    /**
     * Approve api will accept the changes in workflow and persist in DB
     *
     * @param mandSl
     *                          Section of tax report
     * @param keyIdNr
     *                          Tax report Key id number
     * @param keyMeldejahr
     *                          Tax report filed year
     * @param keyMuster
     *                          Tax report type whether BM1 / BM3
     * @param keyLaufnummer
     *                          Sequence number for Key id number
     * @param keySysDatum
     *                          Tax report created date
     * @param keyUhrzeit
     *                          Tax report created time
     * @param action
     *                          To approve or reject the workflow record
     * @param response
     *                          Used to set the uuid in header
     * 
     * @return Validation result response object
     * 
     * @throws RecordNotFoundException
     *                                        throws no records available in table
     * @throws ValidationResultsException
     *                                        throws validation exception details
     */
    @PreAuthorize("hasAuthority('APPROVE_TAXREPORTING')")
    @Operation(
            tags = { "taxreporting" },
            description = "Approve the corrected tax certificate",
            method = "approveTaxReports")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))) })
    @PatchMapping(path = "approve", produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody ResponseEntity<ValidationResults> approveTaxReports(
            @Size(min = 1, max = 3) @Parameter(
                    description = "EN: <br> mandSl" + "DE: <br> mandSl") @Valid @RequestParam(
                            value = "mandSl",
                            required = true) String mandSl,
            @Size(min = 1, max = 13) @Parameter(
                    description = "EN: <br> keyIdNr" + "DE: <br> keyIdNr") @Valid @RequestParam(
                            value = "keyIdNr",
                            required = true) String keyIdNr,
            @Parameter(description = "EN: <br> keyMeldejahr" + "DE: <br> keyMeldejahr") @Valid @RequestParam(
                    value = "keyMeldejahr",
                    required = true) long keyMeldejahr,
            @Parameter(description = "EN: <br> keyMuster" + "DE: <br> keyMuster") @Valid @RequestParam(
                    value = "keyMuster",
                    required = true) String keyMuster,
            @Parameter(description = "EN: <br> keyLaufnummer" + "DE: <br> keyLaufnummer") @Valid @RequestParam(
                    value = "keyLaufnummer",
                    required = true) long keyLaufnummer,
            @Parameter(description = "EN: <br> keySysDatum" + "DE: <br> keySysDatum") @Valid @RequestParam(
                    value = "keySysDatum",
                    required = true) long keySysDatum,
            @Parameter(description = "EN: <br> keyUhrzeit" + "DE: <br> keyUhrzeit") @Valid @RequestParam(
                    value = "keyUhrzeit",
                    required = true) long keyUhrzeit,
            @Parameter(description = "EN: <br> action" + "DE: <br> action") @Valid @RequestParam(
                    value = "action",
                    required = true) String action,
            HttpServletResponse response) throws RecordNotFoundException, ValidationResultsException {
        mandSl = mandSl != null ? mandSl.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;
        keyIdNr = keyIdNr != null ? keyIdNr.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;
        keyMuster = keyMuster != null ? keyMuster.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;
        action = action != null ? action.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;

        LOG.trace("[TaxReportingController] [approveTaxReports] Enter into approve tax report method");

        ValidationResults validationResults = taxReportingApprovalService.approveRecord(mandSl, keyIdNr, keyMeldejahr,
                keyMuster, keyLaufnummer, keySysDatum, keyUhrzeit, action);

        LOG.trace("[TaxReportingController] [approveTaxReports] Exit approve tax report method");

        return new ResponseEntity<>(validationResults, HttpStatus.OK);
    }

    /**
     * Adjustment api will create a record using existing data details
     *
     * @param mandSl
     *                          Section of tax report
     * @param keyIdNr
     *                          Tax report Key id number
     * @param keyMeldejahr
     *                          Tax report filed year
     * @param keyMuster
     *                          Tax report type whether BM1 / BM3
     * @param keyLaufnummer
     *                          Sequence number for Key id number
     * @param keySysDatum
     *                          Tax report created date
     * @param keyUhrzeit
     *                          Tax report created time
     * 
     * @return Validation result response object
     * 
     * @throws ValidationResultsException
     *                                        throws validation exception details
     * @throws JsonProcessingException
     *                                        throws Json parsing exception
     * @throws IOException
     *                                        throws input object is not available
     * @throws ClassNotFoundException
     *                                        throws input class is not available
     */
    @PreAuthorize("hasAuthority('ADJUST_TAXREPORTING')")
    @Operation(description = "Adjust tax reporting data", tags = { "taxreportcerticateCreation" })
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))) })
    @PostMapping(path = "adjustment", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ValidationResults> createTaxReportingCertificate(@RequestParam String mandSl,
            @RequestParam String keyIdNr, @RequestParam long keyMeldejahr, @RequestParam String keyMuster,
            @RequestParam long keyLaufnummer, @RequestParam long keySysDatum, @RequestParam long keyUhrzeit)
            throws ValidationResultsException, JsonProcessingException, IOException, ClassNotFoundException {
        mandSl = mandSl != null ? mandSl.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;
        keyIdNr = keyIdNr != null ? keyIdNr.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;
        keyMuster = keyMuster != null ? keyMuster.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;

        LOG.trace("[TaxReportingController] [createTaxReportingCertificate] Enter into create tax report method");

        ValidationResults validationResults = taxReportingCertificateService.createCertificate(mandSl, keyIdNr,
                keyMeldejahr, keyMuster, keyLaufnummer, keySysDatum, keyUhrzeit);

        LOG.trace("[TaxReportingController] [createTaxReportingCertificate] Exit create tax report method");

        return new ResponseEntity<>(validationResults, HttpStatus.OK);
    }

    /**
     * Create correction api will create a record using existing data details
     *
     * @param mandSl
     *                          Section of tax report
     * @param keyIdNr
     *                          Tax report Key id number
     * @param keyMeldejahr
     *                          Tax report filed year
     * @param keyMuster
     *                          Tax report type whether BM1 / BM3
     * @param keyLaufnummer
     *                          Sequence number for Key id number
     * @param keySysDatum
     *                          Tax report created date
     * @param keyUhrzeit
     *                          Tax report created time
     * 
     * @return Validation result response object
     * 
     * @throws ValidationResultsException
     *                                        throws validation exception details
     * @throws JsonProcessingException
     *                                        throws Json parsing exception
     * @throws IOException
     *                                        throws input object is not available
     * @throws ClassNotFoundException
     *                                        throws input class is not available
     */
    @PreAuthorize("hasAuthority('CREATE_TAXREPORTING')")
    @Operation(
            description = "Create correction tax reporting data",
            tags = { "taxreportCreateCorrection" },
            method = "createCorrectionTaxReports")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))) })
    @PostMapping(path = "correction", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ValidationResults> createCorrectionTaxReports(
            @Size(min = 1, max = 3, message = "Maximum allowed size will be 3") @Parameter(
                    description = "EN: <br> mandSl" + "DE: <br> mandSl") @Valid @RequestParam(
                            value = "mandSl",
                            required = true) String mandSl,
            @Size(min = 1, max = 13, message = "Maximum allowed size will be 13") @Parameter(
                    description = "EN: <br> keyIdNr" + "DE: <br> keyIdNr") @Valid @RequestParam(
                            value = "keyIdNr",
                            required = true) String keyIdNr,
            @Digits(integer = 4, fraction = 0, message = "Maximum allowed size will be 4") @Parameter(
                    description = "EN: <br> keyMeldejahr" + "DE: <br> keyMeldejahr") @Valid @RequestParam(
                            value = "keyMeldejahr",
                            required = true) long keyMeldejahr,
            @Size(min = 6, max = 8, message = "Maximum allowed size will be 8") @Parameter(
                    description = "EN: <br> keyMuster" + "DE: <br> keyMuster") @Valid @RequestParam(
                            value = "keyMuster",
                            required = true) String keyMuster,
            @Digits(integer = 15, fraction = 0, message = "Maximum allowed size will be 15") @Parameter(
                    description = "EN: <br> keyLaufnummer" + "DE: <br> keyLaufnummer") @Valid @RequestParam(
                            value = "keyLaufnummer",
                            required = true) long keyLaufnummer,
            @Digits(integer = 8, fraction = 0, message = "Maximum allowed size will be 8") @Parameter(
                    description = "EN: <br> keySysDatum" + "DE: <br> keySysDatum") @Valid @RequestParam(
                            value = "keySysDatum",
                            required = true) long keySysDatum,
            @Digits(integer = 6, fraction = 0, message = "Maximum allowed size will be 6") @Parameter(
                    description = "EN: <br> keyUhrzeit" + "DE: <br> keyUhrzeit") @Valid @RequestParam(
                            value = "keyUhrzeit",
                            required = true) long keyUhrzeit)
            throws ValidationResultsException, JsonProcessingException, IOException, ClassNotFoundException {
        mandSl = mandSl != null ? mandSl.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;
        keyIdNr = keyIdNr != null ? keyIdNr.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;
        keyMuster = keyMuster != null ? keyMuster.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;

        LOG.trace("[TaxReportingController] [createCorrectionTaxReports] Enter in to create tax report method");

        ValidationResults validationResults = taxReportingCreateCorrectionService.createCorrectionCertificate(mandSl,
                keyIdNr, keyMeldejahr, keyMuster, keyLaufnummer, keySysDatum, keyUhrzeit);

        LOG.trace("[TaxReportingController] [createCorrectionTaxReports] Exit create tax report method");

        return new ResponseEntity<>(validationResults, HttpStatus.OK);
    }

    /**
     * This api will create historicized record in BSB and BM1/BM3 tables using below details
     *
     * @param keyIdNr
     *                         Tax report Key id number
     * @param keyMeldejahr
     *                         Tax report filed year
     * @param keyMuster
     *                         Tax report type whether BM1 / BM3
     * 
     * @return Validation result response object
     * 
     * @throws ValidationResultsException
     *                                        throws validation exception details
     * @throws JsonProcessingException
     *                                        throws Json parsing exception
     * @throws IOException
     *                                        throws input object is not available
     * @throws ClassNotFoundException
     *                                        throws input class is not available
     */
    @PreAuthorize("hasAuthority('HISTORICAL_TAXREPORTING')")
    @Operation(description = "Create historicized tax reporting data", tags = { "taxreportHistoricizedCreation" })
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))) })
    @PostMapping(path = "history", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ValidationResults> createHistoricizedTaxReportingCertificate(
            @Size(min = 10, max = 13) @Parameter(
                    description = "EN: <br> keyIdNr" + "DE: <br> keyIdNr") @Valid @RequestParam(
                            value = "keyIdNr",
                            required = true) String keyIdNr,
            @Parameter(description = "EN: <br> keyMeldejahr" + "DE: <br> keyMeldejahr") @Valid @RequestParam(
                    value = "keyMeldejahr",
                    required = true) long keyMeldejahr,
            @Parameter(description = "EN: <br> keyMuster" + "DE: <br> keyMeldejahr") @Valid @RequestParam(
                    value = "keyMuster",
                    required = true) String keyMuster)
            throws ValidationResultsException, JsonProcessingException, IOException, ClassNotFoundException {
        keyIdNr = keyIdNr != null ? keyIdNr.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;
        keyMuster = keyMuster != null ? keyMuster.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;

        LOG.trace(
                "[TaxReportingController] [createHistoricizedTaxReportingCertificate] Enter into create historicized tax report method");

        TrTableBsb bsbAfter = taxReportingCreateHistoricizedService.createHistoricizedCertificate(keyIdNr, keyMeldejahr,
                keyMuster);

        ValidationResults validationResults = new ValidationResults();
        validationResults.addValidationError(ValidationClassEnum.INFO, ResponseMessage.MSG_CODE_111,
                ResponseMessage.MSG_CODE_111_DESC, null);
        taxReportingResponseService.taxReportingCommonResponse(validationResults, bsbAfter);
        LOG.trace(
                "[TaxReportingController] [createHistoricizedTaxReportingCertificate] Exit create historicized tax report method");

        return new ResponseEntity<>(validationResults, HttpStatus.OK);
    }

    /**
     * Historicized update api will update the record in H status and persist in the table
     *
     * @param data
     *                 Tax report data object
     * 
     * @return Validation result response object
     * 
     * @throws ValidationResultsException
     *                                        throws validation exception details
     * @throws IOException
     *                                        throws input object is not available
     * @throws ClassNotFoundException
     *                                        throws input class is not available
     * @throws RecordNotFoundException
     *                                        throws no records available in table
     */
    @PreAuthorize("hasAuthority('HISTORICAL_TAXREPORTING')")
    @Operation(description = "Update historical tax reporting data", tags = { "taxreportingHistoryUpdate" })
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))) })
    @PutMapping(
            path = "history",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ValidationResults> historicalUpdateTaxReportingData(
            @RequestBody com.fisglobal.taxreporting.controller.dto.bsb.TrTableBsb data)
            throws ValidationResultsException, IOException, ClassNotFoundException, RecordNotFoundException {
        LOG.trace("[TaxReportingController] [historicalUpdateTaxReportingData] Enter into update taxreporting method");

        BusinessValidation.inputRequestFieldValidation(data);

        TrTableBsb requestData = new ObjectMapper().convertValue(data, TrTableBsb.class);
        BusinessValidation.duplicateDataValidation(data, requestData);

        TrTableBsb validatedData = new TrTableBsb();
        BeanUtils.copyProperties(requestData, validatedData);

        LOG.trace(
                "[TaxReportingController] [historicalUpdateTaxReportingData] Enter into update taxreporting method for BSB: {}",
                validatedData.taxCertificateInfo());

        ValidationResults validationResults = taxReportingHistoricizedUpdateService
                .historicizedUpdateCertificate(validatedData);

        LOG.trace(
                "[TaxReportingController] [historicalUpdateTaxReportingData] Exit update taxreporting method for BSB: {}",
                validatedData.taxCertificateInfo());

        return new ResponseEntity<>(validationResults, HttpStatus.OK);
    }

    /**
     * This api will delete historicized record in BSB and BM1/BM3 tables and
     * sub-tables
     *
     * @param mandSl
     *                          Section of tax report
     * @param keyIdNr
     *                          Tax report Key id number
     * @param keyMeldejahr
     *                          Tax report filed year
     * @param keyMuster
     *                          Taxreport type whether BM1 / BM3
     * @param keyLaufnummer
     *                          Sequence number for Key id number
     * @param keySysDatum
     *                          Taxreport created date
     * @param keyUhrzeit
     *                          Tax report created time
     * @param response
     *                          Used to set the uuid in header
     * 
     * @return Validation result response object
     * 
     * @throws RecordNotFoundException
     *                                        throws no records available in table
     * @throws ValidationResultsException
     *                                        throws validation error details
     * @throws IOException
     *                                        throws input object is not available
     * @throws ClassNotFoundException
     *                                        throws input class is not available
     */
    @PreAuthorize("hasAuthority('HISTORICAL_TAXREPORTING')")
    @Operation(description = "Delete historicized tax reporting data", tags = { "taxreportHistoricizedDeletion" })
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ValidationResults.class))) })
    @DeleteMapping(path = "history", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ValidationResults> deleteHistoricizedTaxReportingCertificate(
            @Size(min = 1, max = 3, message = "Maximum allowed size will be 3") @Parameter(
                    description = "EN: <br> mandSl" + "DE: <br> mandSl") @Valid @RequestParam(
                            value = "mandSl",
                            required = true) String mandSl,
            @Size(min = 1, max = 13, message = "Maximum allowed size will be 13") @Parameter(
                    description = "EN: <br> keyIdNr" + "DE: <br> keyIdNr") @Valid @RequestParam(
                            value = "keyIdNr",
                            required = true) String keyIdNr,
            @Digits(integer = 4, fraction = 0, message = "Maximum allowed size will be 4") @Parameter(
                    description = "EN: <br> keyMeldejahr" + "DE: <br> keyMeldejahr") @Valid @RequestParam(
                            value = "keyMeldejahr",
                            required = true) long keyMeldejahr,
            @Size(min = 6, max = 8, message = "Maximum allowed size will be 8") @Parameter(
                    description = "EN: <br> keyMuster" + "DE: <br> keyMuster") @Valid @RequestParam(
                            value = "keyMuster",
                            required = true) String keyMuster,
            @Digits(integer = 15, fraction = 0, message = "Maximum allowed size will be 15") @Parameter(
                    description = "EN: <br> keyLaufnummer" + "DE: <br> keyLaufnummer") @Valid @RequestParam(
                            value = "keyLaufnummer",
                            required = true) long keyLaufnummer,
            @Digits(integer = 8, fraction = 0, message = "Maximum allowed size will be 8") @Parameter(
                    description = "EN: <br> keySysDatum" + "DE: <br> keySysDatum") @Valid @RequestParam(
                            value = "keySysDatum",
                            required = true) long keySysDatum,
            @Digits(integer = 6, fraction = 0, message = "Maximum allowed size will be 6") @Parameter(
                    description = "EN: <br> keyUhrzeit" + "DE: <br> keyUhrzeit") @Valid @RequestParam(
                            value = "keyUhrzeit",
                            required = true) long keyUhrzeit,
            HttpServletResponse response)
            throws RecordNotFoundException, ValidationResultsException, IOException, ClassNotFoundException {
        mandSl = mandSl != null ? mandSl.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;
        keyIdNr = keyIdNr != null ? keyIdNr.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;
        keyMuster = keyMuster != null ? keyMuster.replaceAll("[^a-zA-Z0-9\\-\\s]+", CommonConstants.EMPTY_VALUE) : null;

        LOG.trace(
                "[TaxReportingController] [deleteHistoricizedTaxReportingCertificate] Enter into delete historicized tax report method");

        ValidationResults validationResults = taxReportingHistoricizedDeletionService
                .deleteHistoricizedTaxReportingData(mandSl, keyIdNr, keyMeldejahr, keyMuster, keyLaufnummer,
                        keySysDatum, keyUhrzeit);

        LOG.trace("[TaxReportingController] [deleteHistoricizedTaxReportingCertificate] Exit delete tax report method");

        return new ResponseEntity<>(validationResults, HttpStatus.OK);
    }
}
