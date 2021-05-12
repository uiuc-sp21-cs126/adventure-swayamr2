package student.adventure;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * GameEngine class that stores the data of the JSON into a gameEngine project, and then
 * runs it through a String input from the web.
 */
public class GameEngine {
    private AdventureDesign currentDesign;
    private String startingRoom;
    private String endingRoom;
    private Room currentRoom;
    private final ArrayList<String> userItems = new ArrayList<>();
    private final ArrayList<String> historyOfRooms = new ArrayList<>();
    private String message;

    /**
     * Constructor the game engine, takes in a file in its constructor and fills the private variables in this class
     * with the information
     *
     * @param file - the file for this particular JSON
     */
    public GameEngine(File file) {
        file = new File("src/main/resources/BreakingBad.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            currentDesign = mapper.readValue(file, AdventureDesign.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Stores all data of rooms into a Room array
        Room[] rooms = currentDesign.getRooms();
        //currentRoom will be the room used for method-calling, first starting at the starting room and traversing
        currentRoom = new Room();
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
        historyOfRooms.add(startingRoom);
        message = ("Welcome to the World of Breaking Bad!");
    }

    /**
     * This is a boolean method for the Web inputs. It returns false if the game is still occurring, and updates the values
     * accordingly as it goes on. If true, it stops the game.
     *
     * @param input - The input, or command value, from the API
     * @return true/false
     */
    public boolean inputFromWeb(String input) {
        String userInput = input.toLowerCase();
        String[] userArray = userInput.split(" ");
        String name = userArray[0];
        String command = userArray[1];
        switch (name) {
            case "go":
                //Updates the room and returns true/false, depending on if the currentRoom state is the last room
                currentRoom = this.updateRoom(command, currentDesign);
                message = this.displayRoom(currentRoom);
                return currentRoom.getName().equals(endingRoom);
            case "take":
                //Takes the item, stores the main room message in a variable, and returns false
                this.takeItem(command);
                return false;
            case "drop":
                //Drops the item, stores the main room message in a variable, and returns false
                this.dropItem(command);
                return false;
            case "examine":
                //Reiterates the main message
                message = this.displayRoom(currentRoom);
                return false;
            case "history":
                //Displays the history of rooms traversed
                message = ("Here are the rooms you have traveled: " + historyOfRooms);
                return false;
            default:
                message = "I cannot understand " + command + "!" + "\n" + displayRoom(currentRoom);
                return false;
        }
    }

    /**
     * This method takes the entire details of a certain room, including description, items and directions. It returns
     * a string to be stored in private "message", accessed by the GameStatus instance
     * @param currentRoom - The room that is being traversed
     */

    public String displayRoom(Room currentRoom) {
        StringBuilder data = new StringBuilder();
        data.append("\n").append(currentRoom.getDescription());
        if(currentRoom.getName().equals(endingRoom)) {
            data.append("\n").append("Thank you for playing this adventure! Until next time!");
        }
        ArrayList<String> allItems = new ArrayList<>();
        allItems.addAll(Arrays.asList(currentRoom.getItems()));
        data.append("\n").append("Here are the items in this room: ").append(allItems);
        ArrayList<String> allPossibleDirections = new ArrayList<>();
        DirectionsOnMap[] directions = currentRoom.getDirections();
        for (DirectionsOnMap directionName : directions) {
            //Stores all of the possible direction names one can travel in using a String ArrayList
            allPossibleDirections.add(directionName.getDirectionName());
        }
        data.append("\n").append("Here are the directions you can go in: ").append(allPossibleDirections);
        return data.toString();
    }

    /**
     * This method updates the Room, using the direction input from the user and the overall design of
     * the Adventure layout.
     *
     * @param newDirection - The direction the user wants to travel in
     * @param currentDesign  - The overall design filled from JSON
     * @return newRoom - The new room one is in
     */
    public Room updateRoom(String newDirection, AdventureDesign currentDesign) {
        //Checks for null
        if (newDirection == null) {
            throw new IllegalArgumentException("This is a null input");
        }
        DirectionsOnMap[] currentDirections = currentRoom.getDirections();
        //Loops through the directions possible for the room
        for (DirectionsOnMap direction : currentDirections) {
            //If there is a match between the input direction and the possible options, it updates the room name
            if (direction.getDirectionName().equalsIgnoreCase(newDirection.trim())) {
                String futureRoom = direction.getRoom();
                //Using the new Room name, it creates a new updatedRoom that stores the room with the specific name
                for (Room updatedRoom : currentDesign.getRooms())
                    if (updatedRoom.getName().equals(futureRoom)) {
                        if(!historyOfRooms.contains(updatedRoom.getName())) {
                            historyOfRooms.add(updatedRoom.getName());
                        }
                        return updatedRoom;
                    }
            }
        }
        //Stores the current state of the room if it cannot find a match
        message = "I cannot understand " + newDirection;
        return currentRoom;
    }

    /**
     * This method takes an item, stores it into a list of String variables, and removes it from the overall
     * items of the room
     *
     * @param pickedItem  - the item that is being taken
     */
    public void takeItem(String pickedItem) {
        if (pickedItem == null) {
            throw new NullPointerException("This is a null input");
        }
        ArrayList<String> fullItemList = new ArrayList<>();
        //Adds all of the items in the room into a list
        Collections.addAll(fullItemList, currentRoom.getItems());
        for (String item : currentRoom.getItems()) {
            //Loops through the current item state to find a match
            if (item.equalsIgnoreCase(pickedItem.trim())) {
                //Adds the item to the user list of items
                userItems.add(pickedItem.trim().toLowerCase());
                //removes the item room
                fullItemList.remove(pickedItem.trim().toLowerCase());
                String[] newItems = new String[fullItemList.size()];
                fullItemList.toArray(newItems);
                //sets the items in the room to the new, updated version
                currentRoom.setNewItems(newItems);
                message = displayRoom(currentRoom);
                return;
            }
        }
        //Stores the statement if it cannot find item in the room
        message = ("There is no " + pickedItem.trim() + " in this location!") + ("\n") +  displayRoom(currentRoom);
    }

    /**
     * This method drops an item, removes it from a list of String variables, and stores it into the overall
     * items of the room
     *
     * @param pickedItem  - the item that is being taken
     */
    public void dropItem(String pickedItem) {
        ArrayList<String> fullItemList = new ArrayList<>();
        //Adds all of the items in the room into a list
        Collections.addAll(fullItemList, currentRoom.getItems());
        //Loops through USER ITEM LIST to find match
        for (String allItems : userItems) {
            if (allItems.equalsIgnoreCase(pickedItem.trim())) {
                //Removes the item from the user list
                userItems.remove(pickedItem.trim().toLowerCase());
                //Adds it to the list used by the room
                fullItemList.add(pickedItem.trim().toLowerCase());
                String[] newItems = new String[fullItemList.size()];
                fullItemList.toArray(newItems);
                //Sets the list with the added item to the current state of the room
                currentRoom.setNewItems(newItems);
                message = displayRoom(currentRoom);
                return;
            }
        }
        //Stores the statement if it cannot drop the item
        message = ("You do not have a " + pickedItem.trim() + "!") + displayRoom(currentRoom);
    }

    public String getHistoryOfRooms() {
        return "Here are the rooms you have traveled: " + historyOfRooms;
    }
    public List<String> getDirections() {
        List<String> allDirections = new ArrayList<>();
        for(DirectionsOnMap direction : this.currentRoom.getDirections()) {
            allDirections.add(direction.getDirectionName());
        }
        return allDirections;
    }
    public List<String> getRoomItems() {
        List<String> allRoomItems = new ArrayList<>();
        allRoomItems.addAll(Arrays.asList(this.currentRoom.getItems()));
        return allRoomItems;
    }
    public List<String> getUserItems() {
        return this.userItems;
    }

    public String getMessage() {
        return this.message;
    }

    public Room getCurrentRoom() {
        return this.currentRoom;
    }
    public String getImageURL() {
        return this.currentRoom.getImageURL();
    }
}

