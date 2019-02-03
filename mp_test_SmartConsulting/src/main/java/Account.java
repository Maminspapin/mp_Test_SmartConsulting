public class Account {

    private String id;
    private float money;

    public Account(String id, Float money) {
        this.id = id;
        this.money = money;
    }

    public String getId() {
        return id;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }
}
