package server.model;

public class ReturnResult {

    private boolean isSuccess;
    private String failReason;

    public ReturnResult() {
    }

    public ReturnResult(boolean isSuccess, String failReason) {
        this.isSuccess = isSuccess;
        this.failReason = failReason;
    }
    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }
}