package com.anniyamtech.bbps.responseDto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillerDetailsResponseBiller {
private String billerId;

private String billerName;

private String billerAliasName;

private String billerCategoryName;

private String billerMode;


private String billerAcceptsAdhoc;


private String parentBiller;

private String parentBillerID;

private String billerOwnerShip;

private String billerCoverage;

private String fetchRequirement;
private String paymentAmountExactness;
private String supportBillValidation;

private Date billerEffectiveFrom;

private Date billerEffectiveTo;

private String BillerTempDeactivationStart;

private String BillerTempDeactivationEnd;

private List<BillerDetailsBillerPaymentModesDto>billerPaymentModes;

private List<BillerDetailsBillerPaymentChannelsDto>billerPaymentChannels;

private List<BillerDetailsBillerCustomerParams>billerCustomerParams;

private BillerDetailsBillerResponseParams billerResponseParams;

private List <BillerDetailsBillerAdditionalInfoDto>billerAdditionalInfo;


private List <BillerDetailsInterChangeFeeConfDto>interchangeFeeConf;

private List <BillerDetailsInterChangeFeeDto>interchangeFee ;



private String Status;

private String billerDescription;

private String supportDeemed;

private String supportPendingStatus;

private int billerTimeout;

private String billerResponseType;
private String planMdmRequirement;
private  List<BilerDetailsBillerAdditionalInfoPayment>billerAdditionalInfoPayment ;
private  List<BillerDetailsPlanAdditionalInfo>planAdditionalInfo ;
private  BillerDetailsBillerPlanResponseParams billerPlanResponseParams ;

private Boolean active;
}
