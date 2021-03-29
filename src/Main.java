public class Main {
    public static void main(String[] args) {
        GameFlow gameFlow = new GameFlow();
        Printer printer = new Printer();
        printer.print(gameFlow, args[0]);
    }
}