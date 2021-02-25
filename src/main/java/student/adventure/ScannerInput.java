package student.adventure;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ScannerInput {
    private static AdventureDesign currentDesign;
    private static GameEngine gameEngine;
    private static  Room currentRoom;
    private static String startingRoom;
    private static String endingRoom;
    public static void main(String[] args) {
        File file = new File("src/main/resources/BreakingBad.json");
        GameEngine gameEngine = new GameEngine(file);
        System.out.println(gameEngine.getMessage());
        System.out.println(gameEngine.displayRoom(gameEngine.getCurrentRoom()));
        while(true) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            gameEngine.inputFromWeb(input);
            System.out.println(gameEngine.getMessage());
        }
    }

}
