package com.siva.cob.mba_billgroup_sync_batch.dto;


public class SupportObjectDTO {

    private String identifier;
    private String address;
    private String channel;
    private String eAddressInvoicetype;

    private String emailConfirmation;
    private String isLegal;
    private String note;
    private String version;

    private String when;
    private String pniAccountIdentifier;
    private String startTimeStamp;
    private String endTimeStamp;

    private String customerId;
    private String billingAccountIdentifier;
    private String billingAccountContext;
    private String billingAccountScope;
    private String billGroup;
    private String pa;

    // ---------- Getters & Setters -----------


    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String geteAddressInvoicetype() {
        return eAddressInvoicetype;
    }

    public void seteAddressInvoicetype(String eAddressInvoicetype) {
        this.eAddressInvoicetype = eAddressInvoicetype;
    }

    public String getEmailConfirmation() {
        return emailConfirmation;
    }

    public void setEmailConfirmation(String emailConfirmation) {
        this.emailConfirmation = emailConfirmation;
    }

    public String getIsLegal() {
        return isLegal;
    }

    public void setIsLegal(String isLegal) {
        this.isLegal = isLegal;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getPniAccountIdentifier() {
        return pniAccountIdentifier;
    }

    public void setPniAccountIdentifier(String pniAccountIdentifier) {
        this.pniAccountIdentifier = pniAccountIdentifier;
    }

    public String getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(String startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public String getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(String endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBillingAccountIdentifier() {
        return billingAccountIdentifier;
    }

    public void setBillingAccountIdentifier(String billingAccountIdentifier) {
        this.billingAccountIdentifier = billingAccountIdentifier;
    }

    public String getBillingAccountContext() {
        return billingAccountContext;
    }

    public void setBillingAccountContext(String billingAccountContext) {
        this.billingAccountContext = billingAccountContext;
    }

    public String getBillingAccountScope() {
        return billingAccountScope;
    }

    public void setBillingAccountScope(String billingAccountScope) {
        this.billingAccountScope = billingAccountScope;
    }

    public String getBillGroup() {
        return billGroup;
    }

    public void setBillGroup(String billGroup) {
        this.billGroup = billGroup;
    }

    @Override
    public String toString() {
        return "SupportObjectDTO{" +
                "address='" + address + '\'' +
                ", identifier='" + identifier + '\'' +
                ", channel='" + channel + '\'' +
                ", eAddressInvoicetype='" + eAddressInvoicetype + '\'' +
                ", emailConfirmation='" + emailConfirmation + '\'' +
                ", isLegal='" + isLegal + '\'' +
                ", note='" + note + '\'' +
                ", version='" + version + '\'' +
                ", when='" + when + '\'' +
                ", pniAccountIdentifier='" + pniAccountIdentifier + '\'' +
                ", startTimeStamp='" + startTimeStamp + '\'' +
                ", endTimeStamp='" + endTimeStamp + '\'' +
                ", customerId='" + customerId + '\'' +
                ", billingAccountIdentifier='" + billingAccountIdentifier + '\'' +
                ", billingAccountContext='" + billingAccountContext + '\'' +
                ", billingAccountScope='" + billingAccountScope + '\'' +
                ", billGroup='" + billGroup + '\'' +
                ", pa='" + pa + '\'' +
                '}';
    }
}

