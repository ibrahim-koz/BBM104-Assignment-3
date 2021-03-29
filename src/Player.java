import java.util.ArrayList;

public class Player extends Person {
    private int location = 0;
    private int ID;
    private int countdown = 0;
    private int countdownv2 = 1;
    private boolean bankrupt = false;
    private ArrayList<Property> properties = new ArrayList<Property>();
    private String lastMove = "";

    public Player(String name, int ID) {
        super(name, 15000);
        this.setID(ID);
    }

    public void move(int numberOfDice, GameFlow gameFlow) {
        setLocation(getLocation() + numberOfDice);
        if (getLocation() > 39) {
            tradeOff(200);
            gameFlow.getBanker().tradeOff(-200);
            setLocation((getLocation() % 39) - 1);
        }
    }

    public void pickACard(String cardType, int number, GameFlow gameFlow) {
        if (cardType.equals("chanceCard")) {
            switch (number) {
                case 0:
                    tradeOff(200);
                    gameFlow.getBanker().tradeOff(-200);
                    setLocation(0);
                    lastMove = getName() + " draw Advance to Go (Collect $200)";
                    break;
                case 1:
                    if (getLocation() > 25) {
                        tradeOff(200);
                        gameFlow.getBanker().tradeOff(-200);
                    }
                    setLocation(26);
                    lastMove = getName() + " draw Advance to Leicester Square";
                    buy((Property) gameFlow.getSquares()[getLocation()],gameFlow.getPlayers()[1 - getID()], 0,gameFlow, true);
                    break;
                case 2:
                    if (getLocation() == 0) {
                        setLocation(37);
                    } else if (getLocation() == 1) {
                        setLocation(38);
                    } else if (getLocation() == 2) {
                        setLocation(39);
                    } else {
                        setLocation(getLocation() - 3); // will be edit.
                    }
                    lastMove = getName() + " draw Go back 3 spaces";
                    break;
                case 3:
                    if (getMoney() >= 15) {
                        tradeOff(-15);
                        gameFlow.getBanker().tradeOff(15);
                    }
                    lastMove = getName() + " draw Pay poor tax of $15";
                    break;
                case 4:
                    tradeOff(150);
                    gameFlow.getBanker().tradeOff(-150);
                    lastMove = getName() + " draw Your building loan matures - collect $150";
                    break;
                case 5:
                    tradeOff(100);
                    gameFlow.getBanker().tradeOff(-100);
                    lastMove = getName() + " draw You have won a crossword competition - collect $100";
                    break;
            }
        } else if (cardType.equals("communityChanceCard")) {
            switch (number) {
                case 0:
                    tradeOff(200);
                    gameFlow.getBanker().tradeOff(-200);
                    setLocation(0);
                    lastMove = getName() + " draw Advance to Go (Collect $200)";
                    break;
                case 1:
                    tradeOff(75);
                    gameFlow.getBanker().tradeOff(-75);
                    lastMove = getName() + " draw Bank error in your favor - collect $75";
                    break;
                case 2:
                    tradeOff(-50);
                    gameFlow.getBanker().tradeOff(50);
                    lastMove = getName() + " draw Doctor's fees - Pay $50";
                    break;
                case 3:
                    tradeOff(10);
                    gameFlow.getPlayers()[1 - getID()].tradeOff(-10);
                    lastMove = getName() + " draw It is your birthday Collect $10 from each player";
                    break;
                case 4:
                    tradeOff(50);
                    gameFlow.getPlayers()[1 - getID()].tradeOff(-50);
                    lastMove = getName() + " draw Grand Opera Night - collect $50 from every player for opening night seats";
                    break;
                case 5:
                    tradeOff(20);
                    gameFlow.getBanker().tradeOff(-20);
                    lastMove = getName() + " draw Income Tax Refund - collect $20";
                    break;
                case 6:
                    tradeOff(100);
                    gameFlow.getBanker().tradeOff(-100);
                    lastMove = getName() + " draw Life Insurance Matures - collect $20";
                    break;
                case 7:
                    tradeOff(-100);
                    gameFlow.getBanker().tradeOff(100);
                    lastMove = getName() + " draw Pay Hospital Fees of $100";
                    break;
                case 8:
                    tradeOff(-50);
                    gameFlow.getBanker().tradeOff(50);
                    lastMove = getName() + " draw Pay School Fees of $50";
                    break;
                case 9:
                    tradeOff(100);
                    gameFlow.getBanker().tradeOff(-100);
                    lastMove = getName() + " draw You inherit $100";
                    break;
                case 10:
                    tradeOff(50);
                    gameFlow.getBanker().tradeOff(-50);
                    lastMove = getName() + " draw From sale of stock you get $50";
                    break;
            }
        }
    }

    public void activateOther(String actionName, GameFlow gameFlow) {
        if (actionName.equals("GO")) {
            ;
        } else if (actionName.equals("Income Tax")) {
            tradeOff(-100);
            gameFlow.getBanker().tradeOff(100);
            lastMove = getName() + " paid Income Tax";
        } else if (actionName.equals("Jail")) {
            setCountdown(getCountdown() + 3);
            lastMove = getName() + " went to jail";
        } else if (actionName.equals("Free Parking")) {
            lastMove = getName() + " is in Free Parking";
        } else if (actionName.equals("Go to Jail")) {
            setLocation(10);
            setCountdown(getCountdown() + 3);
            lastMove = getName() + " went to jail";
        } else if (actionName.equals("Super Tax")) {
            tradeOff(-100);
            gameFlow.getBanker().tradeOff(100);
            lastMove = getName() + " paid Super Tax";
        }

    }


    public void setLocation(int newLocation) {
        location = newLocation;
    }

    public int getLocation() {
        return location;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    public void buy(Property p, Player player, int numberOfDice, GameFlow gameFlow, boolean key) {
        if (getMoney() >= p.getCost()) {
            int c = p.sell(getName());
            if (c == 2) {
                getProperties().add(p);
                tradeOff(-p.getCost());
                gameFlow.getBanker().tradeOff(p.getCost());
                gameFlow.setNumberPurchasedProperties(gameFlow.getNumberPurchasedProperties() + 1);
                if (key){
                    lastMove += " " + getName() + " bought " + p.getName();
                }
                else{
                    setLastMove(getName() + " bought " + p.getName());
                }
            } else if (c == 1) {
                lastMove = getName() + " is in own property";
            } else if (c == 0) {
                payRent(p, player, gameFlow.getNumberPurchasedProperties(), numberOfDice);
                if (key) {
                    lastMove += " " + getName() + " paid rent for " + p.getName();
                }
                else{
                    setLastMove(getName() + " paid rent for " + p.getName());
                }
            }
        } else {
            if (p.getOwner().equals("null")) {
                lastMove = getName() + " goes bankrupt";
                bankrupt = true;
            }
            else if(p.getOwner().equals(getName())){
                lastMove = getName() + " is in own property";
            }
            else {
                payRent(p, player, gameFlow.getNumberPurchasedProperties(), numberOfDice);
                if (key){
                    lastMove += " " + getName() + " paid rent for " + p.getName();
                }
                else{
                    setLastMove(getName() + " paid rent for " + p.getName());
                }
            }
        }
    }

    public void payRent(Property p, Player player, int numberPurchasedProperties, int numberOfDice) {
        if (p instanceof Land) {
            double rent = 0;
            if (p.getCost() < 2000) {
                rent = p.getCost() * 0.4;
            } else if (p.getCost() < 3000) {
                rent = p.getCost() * 0.3;
            } else if (p.getCost() < 4000) {
                rent = p.getCost() * 0.35;
            }
            tradeOff(-rent);
            player.tradeOff(rent);

        } else if (p instanceof Company) {
            double rent = 4 * numberOfDice;
            tradeOff(-rent);
            player.tradeOff(rent);
        } else if (p instanceof RailRoad) {
            double rent = 25 * numberPurchasedProperties;
            tradeOff(-rent);
            player.tradeOff(rent);
        }
    }

    public void passARound(){
        countdown -= 1;
        lastMove = getName() + " is in jail (count=" + countdownv2 + ")";
        countdownv2 += 1;
    }

    public void reset(){
        countdownv2 = 1;
    }

    public ArrayList<Property> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<Property> properties) {
        this.properties = properties;
    }

    public String getLastMove() {
        return lastMove;
    }

    public void setLastMove(String lastMove) {
        this.lastMove = lastMove;
    }

    public boolean getBankrupt(){
        return bankrupt;
    }
}
