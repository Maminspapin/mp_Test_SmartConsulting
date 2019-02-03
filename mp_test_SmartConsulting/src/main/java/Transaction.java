import java.util.Date;

public class Transaction {

    private Date date;
    private int idAccountFrom;
    private int IdAccountTo;
    private float amount;

    public Transaction(Date date, int idAccountFrom, int idAccountTo, float amount) {
        this.date = date;
        this.idAccountFrom = idAccountFrom;
        IdAccountTo = idAccountTo;
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIdAccountFrom() {
        return idAccountFrom;
    }

    public void setIdAccountFrom(int idAccountFrom) {
        this.idAccountFrom = idAccountFrom;
    }

    public int getIdAccountTo() {
        return IdAccountTo;
    }

    public void setIdAccountTo(int idAccountTo) {
        IdAccountTo = idAccountTo;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

}
