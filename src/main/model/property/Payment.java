package src.main.model.property;

public class Payment {
    private boolean paid;
    private double feeAmount;
    private int periodOfTime;

    //PROMISES: creates Payment object
    //REQUIRES: if paid or not, fee amount and the period of time for the fee
    public Payment(boolean paid, double feeAmount, int periodOfTime) {
        this.paid = paid;
        this.feeAmount = feeAmount;
        this.periodOfTime = periodOfTime;
    }

    //PROMISES: returns true if the property has been paid otherwise it returns false
    public boolean getPaid() {
        return this.paid;
    }

    //PROMSIES: sets the payment status for a property
    //REQUIRES: true if property has been paid otherwise false
    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    //PROMISES: returns the amount of the fee
    public double getFeeAmount() {
        return this.feeAmount;
    }

    //PROMISES: sets the amount of the fee
    //REQUIRES: the amount of the fee
    public void setFeeAmount(double feeAmount) {
        this.feeAmount = feeAmount;
    }

    //PROMISES: returns the period/duration of the fee in months
    public int getPeriodOfTime() {
        return this.periodOfTime;
    }

    //PROMISES: sets the period of the fee in the months
    //REQUIRES: the period of the fee in months
    public void setPeriodOfTime(int periodOfTime) {
        this.periodOfTime = periodOfTime;
    }

}
