package property;

public class Payment {
    private boolean paid;
    private double feeAmount;
    private int periodOfTime;

    public Payment(boolean paid, double feeAmount, int periodOfTime) {
        this.paid = paid;
        this.feeAmount = feeAmount;
        this.periodOfTime = periodOfTime;
    }

    public boolean isPaid() {
        return this.paid;
    }

    public boolean getPaid() {
        return this.paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public double getFeeAmount() {
        return this.feeAmount;
    }

    public void setFeeAmount(double feeAmount) {
        this.feeAmount = feeAmount;
    }

    public int getPeriodOfTime() {
        return this.periodOfTime;
    }

    public void setPeriodOfTime(int periodOfTime) {
        this.periodOfTime = periodOfTime;
    }


}
