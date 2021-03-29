public class Card extends Square {
    private String cardType;

    public Card(String cardType) {
        super("null");
        this.setCardType(cardType);
    }

    public void activate() {

    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
