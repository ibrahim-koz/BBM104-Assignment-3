import java.io.*;

public class Printer {
    public void print(GameFlow gameFlow, String commandFile) {
        try {
            read(gameFlow, commandFile);
        } catch (IOException e) {

        }

    }

    public void fileClose(BufferedWriter bWriter) throws IOException {
        bWriter.close();
    }

    public void read(GameFlow gameFlow, String commandFile) throws IOException {
        BufferedReader setupFile = new BufferedReader(new FileReader(commandFile));
        String line;
        int i = 0;
        while ((line = setupFile.readLine()) != null) {
            if (!(line.equals("show()"))){
                String[] args = line.trim().split(";");
                if (i < 2){
                    gameFlow.addPlayer(args[0]);
                }
                i++;
                write(args, gameFlow,0, true);
            }
            else{
                write(null,gameFlow, 1,true);
            }
        }
        write(null,gameFlow,1, false);
    }

    public void write(String[] args,GameFlow gameFlow, int key, boolean key2) throws IOException {
        File file = new File("output.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file, true);
        BufferedWriter bWriter = new BufferedWriter(fileWriter);
        if (key == 0) {
            gameFlow.move(args[0], Integer.parseInt(args[1]));
            Player refPlayer = null;
            Player refPlayer2 = null;
            for (Player p: gameFlow.getPlayers()){
                if (p.getName().equals(args[0])){
                    refPlayer = p;
                    break;
                }
            }

            for (Player p: gameFlow.getPlayers()){
                if (!(p.getName().equals(args[0]))){
                    refPlayer2 = p;
                    break;
                }
            }

            //DÜZELT
            int money = (int) gameFlow.getPlayers()[0].getMoney();
            int money2 = (int) gameFlow.getPlayers()[1].getMoney();
            bWriter.append(args[0] + "\t" + args[1] + "\t" + refPlayer.getLocation() + "\t" +
                    money + "\t" + money2 + "\t" +  refPlayer.getLastMove());// düzeltilecek

            bWriter.newLine();
        }
        else if (key == 1){
            bWriter.append("------------------------------------");
            bWriter.newLine();
            int money = (int) gameFlow.getPlayers()[0].getMoney();
            bWriter.append(gameFlow.getPlayers()[0].getName() + "\t" + money +
                    "\t" + "have:" + " ");
            int x = gameFlow.getPlayers()[0].getProperties().size();
            int i = 1;
            for (Property p: gameFlow.getPlayers()[0].getProperties()){
                bWriter.append(p.getName());
                if (i != x){
                    bWriter.append(",");
                }
                i++;
            }

            bWriter.newLine();
            money = (int) gameFlow.getPlayers()[1].getMoney();
            bWriter.append(gameFlow.getPlayers()[1].getName() + "\t" +money +
                    "\t" + "have: ");

            x = gameFlow.getPlayers()[1].getProperties().size();
            i = 1;
            for (Property p: gameFlow.getPlayers()[1].getProperties()){
                bWriter.append(p.getName());
                if (i != x){
                    bWriter.append(",");
                }
                i++;
            }

            bWriter.newLine();

            int bankerMoney = (int)gameFlow.getBanker().getMoney();

            bWriter.append("Banker\t" + bankerMoney);

            bWriter.newLine();

            if (gameFlow.getPlayers()[0].getMoney() > gameFlow.getPlayers()[1].getMoney()){
                bWriter.append("Winner\t" + gameFlow.getPlayers()[0].getName());
            }
            else{
                bWriter.append("Winner\t" + gameFlow.getPlayers()[1].getName());
            }

            bWriter.newLine();

            bWriter.append("------------------------------------");

            if (key2){
                bWriter.newLine();
            }

        }

        boolean q = false;
        if (gameFlow.gameOver() == 1){
            bWriter.append("------------------------------------");
            bWriter.newLine();
            int money = (int) gameFlow.getPlayers()[0].getMoney();
            bWriter.append(gameFlow.getPlayers()[0].getName() + "\t" +money +
                    "\t" + "have:" + " ");
            int x = gameFlow.getPlayers()[0].getProperties().size();
            int i = 1;
            for (Property p: gameFlow.getPlayers()[0].getProperties()){
                bWriter.append(p.getName());
                if (i != x){
                    bWriter.append(",");
                }
                i++;
            }

            bWriter.newLine();
            money = (int) gameFlow.getPlayers()[1].getMoney();
            bWriter.append(gameFlow.getPlayers()[1].getName() + "\t" + money+
                    "\t" + "have: ");

            x = gameFlow.getPlayers()[1].getProperties().size();
            i = 1;
            for (Property p: gameFlow.getPlayers()[1].getProperties()){
                bWriter.append(p.getName());
                if (i != x){
                    bWriter.append(",");
                }
                i++;
            }

            bWriter.newLine();

            int bankerMoney = (int)gameFlow.getBanker().getMoney();

            bWriter.append("Banker\t" + bankerMoney);

            bWriter.newLine();

            if (gameFlow.getPlayers()[0].getMoney() > gameFlow.getPlayers()[1].getMoney()){
                bWriter.append("Winner\t" + gameFlow.getPlayers()[0].getName());
            }
            else{
                bWriter.append("Winner\t" + gameFlow.getPlayers()[1].getName());
            }

            bWriter.newLine();

            bWriter.append("------------------------------------");

            q = true;

        }
        bWriter.close();

        if(q){
            System.exit(0);
        }
    }
}
