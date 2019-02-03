import java.util.List;

public class ResultViewer {

    private Account[] acc;
    private List<Transaction> tr;

    public ResultViewer(Account[] acc, List<Transaction> tr) {
        this.acc = acc;
        this.tr = tr;
    }

    public Account[] getAcc() {
        return acc;
    }

    public void setAcc(Account[] acc) {
        this.acc = acc;
    }

    public List<Transaction> getTr() {
        return tr;
    }

    public void setTr(List<Transaction> tr) {
        this.tr = tr;
    }

    public void showResult() {
        for (int i = 0; i < acc.length; i++) {
            System.out.println("Счет: " + acc[i].getId() + ", сумма: " + String.format("%.2f", acc[i].getMoney()));
            for (Transaction tr: tr) {
                if (tr.getIdAccountFrom() == Integer.parseInt(acc[i].getId())) {
                    System.out.println((tr.getDate().toString()
                            + " cписана со счета " + tr.getIdAccountFrom()
                            + " сумма в размере " + String.format("%.2f", tr.getAmount())));
                }
                if (tr.getIdAccountTo() == Integer.parseInt(acc[i].getId())) {
                    System.out.println((tr.getDate().toString()
                            + " зачислена на счет " + tr.getIdAccountTo()
                            + " сумма в размере " + String.format("%.2f", tr.getAmount())));
                }
            }

        }
    }
}
