import java.util.Random;

public class GameFlow {
    private Player[] players = new Player[2];
    private Banker banker = new Banker("null");
    private Square[] squares = new Square[40];
    private String[] chanceCards = new String[6];
    private String[] communityChestCards = new String[11];
    private int[] indexesCards = new int[6];
    private int[] indexesOthers = new int[6];
    private int numberPurchasedProperties = 0;
    private int lastIDChanceCard = 0;
    private int lastIDCommunityChanceCard = 0;

    public GameFlow() {
        //initialize

        for (int i = 0; i < 2; i++) {
            getPlayers()[i] = new Player("null", i);
        }

        for (int i = 0; i < 40; i++) {
            getSquares()[i] = new Square("null");
        }

        //initialize squares

        getSquares()[0] = new Other("GO");
        getSquares()[1] = new Land("Old Kent Road", 600);
        getSquares()[2] = new Card("communityChanceCard");
        getSquares()[3] = new Land("Whitechapel Road", 600);
        getSquares()[4] = new Other("Income Tax");
        getSquares()[5] = new RailRoad("King Cross Station", 2000);
        getSquares()[6] = new Land("The Angel Islington", 1000);
        getSquares()[7] = new Card("chanceCard");
        getSquares()[8] = new Land("Euston Road", 1000);
        getSquares()[9] = new Land("Pentonville Road", 1200);
        getSquares()[10] = new Other("Jail");
        getSquares()[11] = new Land("Pall Mall", 1400);
        getSquares()[12] = new Company("Electric Company", 1500);
        getSquares()[13] = new Land("Whitehall", 1400);
        getSquares()[14] = new Land("Northumberland Avenue", 1600);
        getSquares()[15] = new RailRoad("Marylebone Station", 2000);
        getSquares()[16] = new Land("Bow Street", 1800);
        getSquares()[17] = new Card("communityChanceCard");
        getSquares()[18] = new Land("Marlborough Street", 1800);
        getSquares()[19] = new Land("Vine Street", 2000);
        getSquares()[20] = new Other("Free Parking");
        getSquares()[21] = new Land("The Strand", 2200);
        getSquares()[22] = new Card("chanceCard");
        getSquares()[23] = new Land("Fleet Street", 2200);
        getSquares()[24] = new Land("Trafalgar Square", 2400);
        getSquares()[25] = new RailRoad("Fenchurch Station", 2000);
        getSquares()[26] = new Land("Leicester Square", 2600);
        getSquares()[27] = new Land("Coventry Street", 2600);
        getSquares()[28] = new Company("Water Works", 1500);
        getSquares()[29] = new Land("Piccadilly", 2800);
        getSquares()[30] = new Other("Go to Jail");
        getSquares()[31] = new Land("Regent Street", 3000);
        getSquares()[32] = new Land("Oxford Street", 3000);
        getSquares()[33] = new Card("communityChanceCard");
        getSquares()[34] = new Land("Bond Street", 3200);
        getSquares()[35] = new RailRoad("Liverpool Street Station", 2000);
        getSquares()[36] = new Card("chanceCard");
        getSquares()[37] = new Land("Park Lane", 3500);
        getSquares()[38] = new Other("Super Tax");
        getSquares()[39] = new Land("Mayfair", 4000);

        //initialize chanceCards

        getChanceCards()[0] = "Advance to Go (Collect $200)";
        getChanceCards()[1] = "Advance to Leicester Square";
        getChanceCards()[2] = "Go back 3 spaces";
        getChanceCards()[3] = "Pay poor tax of $15";
        getChanceCards()[4] = "Your building loan matures - collect $150";
        getChanceCards()[5] = "You have won a crossword competition - collect $100";

        //initialize communityChestCards

        getCommunityChestCards()[0] = "Advance to Go (Collect $200)";
        getCommunityChestCards()[1] = "Bank error in your favor - collect $75";
        getCommunityChestCards()[2] = "Doctor's fees - Pay $50";
        getCommunityChestCards()[3] = "It is your birthday Collect $10 from each player";
        getCommunityChestCards()[4] = "Grand Opera Night - collect $50 from every player for opening night seats";
        getCommunityChestCards()[5] = "Income Tax Refund - collect $20";
        getCommunityChestCards()[6] = "Life Insurance Matures - collect $20";
        getCommunityChestCards()[7] = "Pay Hospital Fees of $100";
        getCommunityChestCards()[8] = "Pay School Fees of $50";
        getCommunityChestCards()[9] = "You inherit $100";
        getCommunityChestCards()[10] = "From sale of stock you get $50";

    }

    public void move(String nameOfPlayer, int numberOfDice) {
        for (Player p : getPlayers()) {
            if (p.getName().equals(nameOfPlayer)) {
                if (p.getCountdown() == 0) {
                    p.reset();
                    p.move(numberOfDice, this);
                    if (getSquares()[p.getLocation()] instanceof Card) {
                        Card card = (Card) getSquares()[p.getLocation()];
                        int n = 0;
                        if (card.getCardType().equals("chanceCard")){
                            n = lastIDChanceCard;
                            lastIDChanceCard++;
                            if (lastIDChanceCard == 6){
                                lastIDChanceCard = 0;
                            }
                        }
                        else if (card.getCardType().equals("communityChanceCard")){
                            n = lastIDCommunityChanceCard;
                            lastIDCommunityChanceCard++;
                            if (lastIDCommunityChanceCard == 11){
                                lastIDCommunityChanceCard = 0;
                            }
                        }
                        p.pickACard(card.getCardType(), n, this);
                    } else if (getSquares()[p.getLocation()] instanceof Other) {
                        Other other = (Other) getSquares()[p.getLocation()];
                        p.activateOther(other.getName(), this);
                    } else
                        p.buy((Property) getSquares()[p.getLocation()], getPlayers()[1 - p.getID()], numberOfDice, this, false);
                } else {
                    p.passARound();
                }
                break;
            }
        }
    }

    public void setLocation(Player p, int newLocation) {
        p.setLocation(newLocation);
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public Banker getBanker() {
        return banker;
    }

    public void setBanker(Banker banker) {
        this.banker = banker;
    }

    public Square[] getSquares() {
        return squares;
    }

    public void setSquares(Square[] squares) {
        this.squares = squares;
    }

    public String[] getChanceCards() {
        return chanceCards;
    }

    public void setChanceCards(String[] chanceCards) {
        this.chanceCards = chanceCards;
    }

    public String[] getCommunityChestCards() {
        return communityChestCards;
    }

    public void setCommunityChestCards(String[] communityChestCards) {
        this.communityChestCards = communityChestCards;
    }

    public int[] getIndexesCards() {
        return indexesCards;
    }

    public void setIndexesCards(int[] indexesCards) {
        this.indexesCards = indexesCards;
    }

    public int[] getIndexesOthers() {
        return indexesOthers;
    }

    public void setIndexesOthers(int[] indexesOthers) {
        this.indexesOthers = indexesOthers;
    }

    public void addPlayer(String name) {
        for (Player p : getPlayers()) {
            if (p.getName().equals("null")) {
                p.setName(name);
                System.out.println("New player was added successfully");
                break;
            }
        }
    }

    public void addBanker(String name) {
        banker.setName(name);
        System.out.println("Banker was added successfully");
    }

    public int gameOver(){
        for (Player p: getPlayers()){
            if (p.getMoney() < 0 || p.getBankrupt()){
                System.out.println("Game over");
                return 1;
            }
        }
        return 0;
    }

    public int getNumberPurchasedProperties() {
        return numberPurchasedProperties;
    }

    public void setNumberPurchasedProperties(int numberPurchasedProperties) {
        this.numberPurchasedProperties = numberPurchasedProperties;
    }
}
