import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        String command = null;
        String commandLine;
        String[] parameters;
        String item = null;
        int amount = 0;
        bootUp();

        while(command != ".powerOff"){
            System.out.print("# ");
            commandLine = scan.nextLine();
            if(commandLine.length() >= 1){
                parameters = commandParameters(commandLine);
                command = parameters[0];
                if(command.charAt(0) == '.'){
                    switch(command){
                        case ".help":
                            help();
                            break;
                        case ".rocks":
                            rocks();
                            break;
                        case ".addNewRock":
                            addNewRock(parameters);
                            break;
                        case ".addRock":
                            addRock(parameters);
                            break;
                        case ".foundWater":
                            foundWater();
                            break;
                        case ".waterLocation":
                            waterLocation();
                            break;
                        case ".batteryLife":
                            batteryLife();
                            break;
                        case ".chargeBattery":
                            chargeBattery();
                            break;
                        case ".powerOff":
                            powerOff();
                            return;
                        default:
                            System.out.println("Unknown Command");
                    }
                }
            }
        }
        scan.close();
    }

    public static void help(){
        System.out.println("Commands:");
        System.out.println(".help displays what you see now");
        System.out.println(".rocks displays what rocks the rover has found and how many");
        System.out.println(".addNewRock <item> <amount> adds new rocks to inventory");
        System.out.println(".addRock <item> <amount> adds more rocks to current inventory");
        System.out.println(".foundWater creates a file stating water has been found");
        System.out.println(".waterLocation checks to see where water was found, if water was already found.");
        System.out.println(".batteryLife displays the charge of the battery on the rover");
        System.out.println(".chargeBattery charges the battery to 100%");
        System.out.println(".powerOff turns off rover");
    }

    public static void rocks() throws FileNotFoundException {
        Scanner rocks = new Scanner(new File("rocks.txt"));
        String line;
        while(rocks.hasNextLine()){
            line = rocks.nextLine();
            System.out.println(line);
        }
    }

    public static void addNewRock(String[] parameters) throws IOException {
        String item = item(parameters);
        int amount = amount(parameters);
        FileWriter rocks = new FileWriter("rocks.txt");
        rocks.write("\n" + item + ": " + amount);
    }

    public static void addRock(String[] parameters) throws IOException {

    }

    public static void foundWater() throws IOException {
        File file = new File("water.txt");
        FileWriter water = new FileWriter("water.txt");
        Random rand = new Random();
        int x = rand.nextInt(181) - 180;
        int y = rand.nextInt(91) - 90;
        water.write("Water was found at Latitude: " + y + " Longitude: " + x + "\n");
        water.close();

    }

    public static void waterLocation() throws FileNotFoundException {
        Scanner water = new Scanner(new File("water.txt"));
        String line;
        while(water.hasNextLine()){
            line = water.nextLine();
            System.out.println(line);
        }
    }

    public static void batteryLife() throws FileNotFoundException {
        Scanner battery = new Scanner(new File("battery.txt"));
        String line;
        while(battery.hasNextLine()){
            line = battery.nextLine();
            System.out.println(line);
        }
    }

    public static void chargeBattery() throws Exception {
        String battery = "battery.txt";
        String result = fileToString(battery);
        result = result.replaceAll("Power: 91%", "Power: 100%");
        PrintWriter writer = new PrintWriter(new File(battery));
        writer.append(result);
        writer.flush();
        Thread.sleep(3000);
        System.out.println("Battery Fully Charged");
    }

    public static String fileToString(String filePath) throws Exception{
        String input = null;
        Scanner sc = new Scanner(new File(filePath));
        StringBuffer sb = new StringBuffer();
        while (sc.hasNextLine()) {
            input = sc.nextLine();
            sb.append(input);
        }
        return sb.toString();
    }

    public static void powerOff(){
        System.out.println("Powering off...");
    }

    private static String[] commandParameters(String command){
        String item = "";
        String[] commandParameters = {""};
        if(command.contains(" ")){
            commandParameters = command.split(" ");
        } else{
            commandParameters[0] = command;
        }
        return commandParameters;
    }

    private static Integer amount(String[] s){
        int amount = 0;
        for (String value : s) {
            if (integerCheck(value)) {
                amount = Integer.parseInt(value);
            }
        }
        return amount;
    }

    private static String item (String[] s){
        String item = "";
        for (String value : s) {
            if (!integerCheck(value)) {
                item += value + " ";
            }
        }
        return item;
    }

    private static boolean integerCheck(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }


    public static void createRocks() throws IOException, InterruptedException {
        File file = new File("rocks.txt");
        FileWriter rocks = new FileWriter("rocks.txt");
        rocks.write("Basalt: 5\n");
        rocks.write("Shale: 43\n");
        rocks.write("Sandstone: 0\n");
        rocks.write("Conglomerate: 52");
        rocks.close();
        Thread.sleep(1000);
        if (file.createNewFile()){
            System.out.println("33%");
        }
    }

    public static void createBattery() throws IOException, InterruptedException {
        File file = new File("battery.txt");
        FileWriter battery = new FileWriter("battery.txt");
        battery.write("Power: 91%");
        battery.close();
        Thread.sleep(1000);
        if (file.createNewFile()){
            System.out.println("66%");
        }
    }



    public static void bootUp() throws IOException, InterruptedException {
        System.out.println("Welcome to Mars Rover OS!");
        System.out.println("Checking Rover status");
        createRocks();
        createBattery();
        System.out.println();
        System.out.println("Type .help for list of commands");
    }
}
