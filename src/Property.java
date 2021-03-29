public class Property extends Square {
    private int cost;
    private String owner = "null";
    public Property(String name, int cost) {
        super(name);
        this.setCost(cost);
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int sell(String newOwner){
        if (getOwner().equals("null")){
            setOwner(newOwner);
            System.out.println("Property sold.");
            return 2;
        }
        else if (newOwner.equals(getOwner())){
            System.out.println("This property is yours.");
            return 1;
        }
        else{
            System.out.println("Property already sold earlier.");
            return 0;
        }
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
