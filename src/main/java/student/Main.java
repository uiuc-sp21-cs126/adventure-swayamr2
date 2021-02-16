package student;

import com.fasterxml.jackson.databind.ObjectMapper;
import student.adventure.AdventureDesign;
import student.adventure.Room;
import student.adventure.UserMoves;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class that uses a scanner input to call methods, and is used as a UI. Commands include "go", "take",
 * "drop", "examine", and more. Accounts for spelling and spacing errors.
 */
public class Main {
    private static AdventureDesign currentDesign;
    private static String startingRoom;
    private static String endingRoom;

    public static void main(String[] args) {
        File file = new File("src/main/resources/BreakingBad.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            //Store JSON data into an AdventureDesign Object
            currentDesign = mapper.readValue(file, AdventureDesign.class);
        } catch (Exception e) {
            System.out.println("Please try again!");
            System.exit(0);
        }
        //Stores all data of rooms into a Room array
        Room[] rooms = currentDesign.getRooms();
        //currentRoom will be the room used for method-calling, first starting at the starting room and traversing
        Room currentRoom = new Room();
        for (Room room : rooms) {
            if (room.getName().equalsIgnoreCase(currentDesign.getStartingRoom())) {
                currentRoom = room;
                //store beginning room name into variable startingRoom
                startingRoom = room.getName();
            }
            if (room.getName().equalsIgnoreCase(currentDesign.getEndingRoom())) {
                //store beginning room name into variable endingRoom
                endingRoom = room.getName();
            }
        }
        Scanner scanner = new Scanner(System.in);
        //ArrayList that will be used for method-calling, mostly for "take" and "drop" inputs
        ArrayList<String> userItems = new ArrayList<>();
        //ArrayList that will be used to note down the rooms traversed, can be called through "history"
        ArrayList<String> historyOfRooms = new ArrayList<>();
        historyOfRooms.add(currentRoom.getName());
        UserMoves.beginningOrEnd(currentRoom, startingRoom, endingRoom);
        UserMoves.roomDetails(currentRoom);
        while (!(currentRoom.getName().equals(endingRoom))) {
            String input = scanner.nextLine();
            input = input.toLowerCase();
            if (input.contains("go")) {
                //Stores whole input into a String array, trims the spaces, and splits it at "go" to obtain keyword
                String[] moves = input.trim().split("go");
                //updates the currentRoom object through the keyword
                currentRoom = UserMoves.updateRoom(currentRoom, moves[1], currentDesign);
                UserMoves.roomDetails(currentRoom);
                UserMoves.beginningOrEnd(currentRoom, startingRoom, endingRoom);
                //historyOfRooms adds the current state of the room into the list
                if(!historyOfRooms.contains(currentRoom.getName())) {
                    historyOfRooms.add(currentRoom.getName());
                }
            } else if (input.contains("examine")) {
                UserMoves.examine(currentRoom);
            } else if (input.contains("take")) {
                String[] items = input.split("take");
                //Splits the keyword after "take", and runs it through the takeItem method
                UserMoves.takeItem(currentRoom, items[1].trim(), userItems);
            } else if (input.contains("drop")) {
                String[] items = input.split("drop");
                //Splits the keyword after "drop", and runs it through the dropItem method
                UserMoves.dropItem(currentRoom, items[1].trim(), userItems);
            } else if (input.contains("history")) {
                System.out.println("Here are the rooms you have traveled: " + historyOfRooms);
            } else if (input.contains("inventory")) {
                System.out.println("Here are your items: " + userItems);
            } else if (input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("exit")) {
                //Leaves game if "quit" or "exit" is called
                System.out.println("Goodbye! We hope to see you soon!");
                System.exit(0);
            }
        }
    }
}
