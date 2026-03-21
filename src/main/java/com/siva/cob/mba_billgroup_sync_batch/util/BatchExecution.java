package com.siva.cob.mba_billgroup_sync_batch.util;

public class BatchExecution {

    private long totalRecord;
    private long successRecord;
    private long failedRecord;
    private long skippedRecord;
    private String batchName;
    private String mode;
    private String logFile;

    public BatchExecution() {
    }

    public void incrementTotalRecord(){
        totalRecord=totalRecord+1;
    }

    public void incrementTotalSuccess(){
        successRecord=successRecord+1;
    }

    public void incrementTotalFailed(){
        failedRecord=failedRecord+1;
    }

    public void incrementTotalSkipped(){
        skippedRecord=skippedRecord+1;
    }

    public void decrementTotalRecord(){
        totalRecord=totalRecord-1;
    }

    public void decrementTotalSuccess(){
        successRecord=successRecord-1;
    }

    public void decrementTotalFailed(){
        failedRecord=failedRecord-1;
    }

    public void decrementTotalSkipped(){
        skippedRecord=skippedRecord-1;
    }

    public long getFailedRecord() {
        return failedRecord;
    }

    public void setFailedRecord(long failedRecord) {
        this.failedRecord = failedRecord;
    }

    public long getSkippedRecord() {
        return skippedRecord;
    }

    public void setSkippedRecord(long skippedRecord) {
        this.skippedRecord = skippedRecord;
    }

    public long getSuccessRecord() {
        return successRecord;
    }

    public void setSuccessRecord(long successRecord) {
        this.successRecord = successRecord;
    }

    public long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {

        StringBuilder sb=new StringBuilder();

        sb.append("------------------------------------------------------------------------------------------------").append("\n");
        sb.append("Batch [ "+batchName+" ] Mode [ "+mode+" ] FileName [ "+logFile+" ]  :Total number of records processed  : "+totalRecord).append("\n");
        sb.append("Batch [ "+batchName+" ] Mode [ "+mode+" ] FileName [ "+logFile+" ]  :Total number of records successful : "+successRecord).append("\n");
        sb.append("Batch [ "+batchName+" ] Mode [ "+mode+" ] FileName [ "+logFile+" ]  :Total number of records failed     : "+failedRecord).append("\n");
        sb.append("Batch [ "+batchName+" ] Mode [ "+mode+" ] FileName [ "+logFile+" ]  :Total number of records skipped    : "+skippedRecord).append("\n");
        sb.append("------------------------------------------------------------------------------------------------");


        return sb.toString();

    }
}
