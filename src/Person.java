public class Person {
    private String name;
    private double money;

    public Person(String name, int money) {
        this.setName(name);
        this.setMoney(money);
    }

    public void tradeOff(double amount){
        setMoney(getMoney() + amount);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
