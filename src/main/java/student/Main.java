package student;

import com.fasterxml.jackson.databind.ObjectMapper;
import student.adventure.AdventureDesign;
import student.adventure.Room;
import student.adventure.UserMoves;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static AdventureDesign currentDesign;
    private static String startingRoom;
    private static String endingRoom;

    public static void main(String[] args) {
        File file = new File("src/main/resources/BreakingBad.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            currentDesign = mapper.readValue(file, AdventureDesign.class);
        } catch (Exception e) {
            System.out.println("Please try again!");
            System.exit(0);
        }
        Room[] rooms = currentDesign.getRooms();
        Room currentRoom = new Room();
        for (Room room : rooms) {
            if (room.getName().equalsIgnoreCase(currentDesign.getStartingRoom())) {
                currentRoom = room;
                startingRoom = room.getName();
            }
            if (room.getName().equalsIgnoreCase(currentDesign.getEndingRoom())) {
                endingRoom = room.getName();
            }
        }
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> userItems = new ArrayList<>();
        ArrayList<String> historyOfRooms = new ArrayList<>();
        historyOfRooms.add(currentRoom.getName());
        UserMoves.beginningOrEnd(currentRoom, startingRoom, endingRoom);
        UserMoves.roomDetails(currentRoom);
        while (true) {
            String input = scanner.nextLine();
            input = input.toLowerCase();
            if (input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye! We hope to see you soon!");
                System.exit(0);
            } else if (input.contains("go")) {
                String[] moves = input.trim().split("go");
                currentRoom = UserMoves.updateRoom(currentRoom, moves[1], currentDesign);
                UserMoves.roomDetails(currentRoom);
                UserMoves.beginningOrEnd(currentRoom, startingRoom, endingRoom);
                if(!historyOfRooms.contains(currentRoom.getName())) {
                    historyOfRooms.add(currentRoom.getName());
                }
            } else if (input.contains("examine")) {
                UserMoves.examine(currentRoom);
            } else if (input.contains("take")) {
                String[] items = input.split("take");
                UserMoves.takeItem(currentRoom, items[1].trim(), userItems);
            } else if (input.contains("drop")) {
                String[] items = input.split("drop");
                UserMoves.dropItem(currentRoom, items[1].trim(), userItems);
            } else if (input.contains("history")) {
                System.out.println("Here are the rooms you have traveled: " + historyOfRooms);
            } else if (input.contains("inventory")) {
                System.out.println("Here are your items: " + userItems);
            }
        }
    }
}
