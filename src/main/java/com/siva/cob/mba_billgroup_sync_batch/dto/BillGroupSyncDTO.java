package com.siva.cob.mba_billgroup_sync_batch.dto;

public class BillGroupSyncDTO {
    private String customerId;
    private String agreeNr;
    private String billingAccountIdentifier;
    private String billingAccountIdentifierContext;
    private String billGroup;

    public BillGroupSyncDTO() {
    }

    public String getAgreeNr() {
        return agreeNr;
    }

    public void setAgreeNr(String agreeNr) {
        this.agreeNr = agreeNr;
    }

    public String getBillGroup() {
        return billGroup;
    }

    public void setBillGroup(String billGroup) {
        this.billGroup = billGroup;
    }

    public String getBillingAccountIdentifier() {
        return billingAccountIdentifier;
    }

    public void setBillingAccountIdentifier(String billingAccountIdentifier) {
        this.billingAccountIdentifier = billingAccountIdentifier;
    }

    public String getBillingAccountIdentifierContext() {
        return billingAccountIdentifierContext;
    }

    public void setBillingAccountIdentifierContext(String billingAccountIdentifierContext) {
        this.billingAccountIdentifierContext = billingAccountIdentifierContext;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "BillGroupSyncDTO{" +
                "agreeNr='" + agreeNr + '\'' +
                ", customerId='" + customerId + '\'' +
                ", billingAccountIdentifier='" + billingAccountIdentifier + '\'' +
                ", billingAccountIdentifierContext='" + billingAccountIdentifierContext + '\'' +
                ", billGroup='" + billGroup + '\'' +
                '}';
    }
}
