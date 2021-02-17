package student.adventure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * I created a static class UserMoves, containing all the moves that will be used in the main method.
 * I did this to make the Main method much more concise and cleaner.
 */
public class UserMoves {
    /**
     * This method takes the entire details of a certain room, including description, items and directions.
     * @param currentRoom - The room that is being traversed
     */
    public static void roomDetails(Room currentRoom) {
        System.out.println(currentRoom.getDescription());
        System.out.println("Here are the items in this room: " + Arrays.toString(currentRoom.getItems()));
        ArrayList<String> allPossibleDirections = new ArrayList<>();
        DirectionsOnMap[] directions = currentRoom.getDirections();
        for(DirectionsOnMap directionName: directions) {
            //Stores all of the possible direction names one can travel in using a String ArrayList
            allPossibleDirections.add(directionName.getDirectionName());
        }
        System.out.println("From here, you can go: " + allPossibleDirections);
    }

    /**
     * This method decides whether a room is at the start or end of the adventure, and prints out statements
     * @param currentRoom - the current state of the player
     * @param startingRoom - the starting room of the adventure
     * @param endingRoom - the ending room of the adventure
     */
    public static void beginningOrEnd(Room currentRoom, String startingRoom, String endingRoom) {
        if(currentRoom.getName().equalsIgnoreCase(startingRoom)) {
            //Prints out prompt
            System.out.println("Welcome to the Adventure! Step into the world of Breaking Bad! ");
        }
        if(currentRoom.getName().equalsIgnoreCase(endingRoom)) {
            //Prints out prompt and exits game
            System.out.println("Thank you for playing! Goodbye");
            System.exit(0);
        }
    }
    /**
     * This method updates the Room, using the direction input from the user and the overall design of
     * the Adventure layout.
     * @param currentRoom - The room being traversed
     * @param newDirection - The direction the user wants to travel in
     * @param design - The overall design filled from JSON
     * @return newRoom - The new room one is in
     */
    public static Room updateRoom(Room currentRoom, String newDirection, AdventureDesign design) {
        //Checks for null
        if(newDirection == null) {
            System.out.println("This is a null input");
            return currentRoom;
        }
        DirectionsOnMap[] currentDirections = currentRoom.getDirections();
        //Loops through the directions possible for the room
        for (DirectionsOnMap direction : currentDirections) {
            //If there is a match between the input direction and the possible options, it updates the room name
            if(direction.getDirectionName().equalsIgnoreCase(newDirection.trim())) {
                String futureRoom = direction.getRoom();
                //Using the new Room name, it creates a new updatedRoom that stores the room with the specific name
                for (Room updatedRoom : design.getRooms())
                    if (updatedRoom.getName().equals(futureRoom)) {
                        return updatedRoom;
                    }
            }
        }
        //Returns out the current state of the room if it cannot find a match
        System.out.println("I cannot understand " + newDirection);
        return currentRoom;
    }

    /**
     * This method repeats the details of a room by the keyword "examine"
     * @param currentRoom - the room being traversed
     */
    public static void examine(Room currentRoom) {
        UserMoves.roomDetails(currentRoom);
    }

    /**
     * This method takes an item, stores it into a list of String variables, and removes it from the overall
     * items of the room
     * @param currentRoom - the room being traversed
     * @param pickedItem - the item that is being taken
     * @param itemList - the user list of the items
     */
    public static void takeItem(Room currentRoom, String pickedItem, ArrayList<String> itemList) {
        if(pickedItem == null) {
            System.out.println("This is a null input");
        }
        ArrayList<String> fullItemList = new ArrayList<>();
        //Adds all of the items in the room into a list
        Collections.addAll(fullItemList, currentRoom.getItems());
        for(String item : currentRoom.getItems()) {
            //Loops through the current item state to find a match
            if (item.equalsIgnoreCase(pickedItem.trim())) {
                //Adds the item to the user list of items
                itemList.add(pickedItem);
                //removes the item room
                fullItemList.remove(pickedItem);
                String[] newItems = new String[fullItemList.size()];
                fullItemList.toArray(newItems);
                //sets the items in the room to the new, updated version
                currentRoom.setNewItems(newItems);
                UserMoves.roomDetails(currentRoom);
                return;
            }
        }
        //Prints out statement if it cannot find item in the room
        System.out.println("There is no " + pickedItem + " in this location!");
        UserMoves.roomDetails(currentRoom);
    }
    /**
     * This method drops an item, removes it from a list of String variables, and stores it into the overall
     * items of the room
     * @param currentRoom - the room being traversed
     * @param pickedItem - the item that is being taken
     * @param itemList - the user list of the items
     */
    public static void dropItem(Room currentRoom, String pickedItem, ArrayList<String> itemList) {
        ArrayList<String> fullItemList = new ArrayList<>();
        //Adds all of the items in the room into a list
        Collections.addAll(fullItemList, currentRoom.getItems());
        //Loops through USER ITEM LIST to find match
        for(String allItems : itemList) {
            if (allItems.equalsIgnoreCase(pickedItem)) {
                //Removes the item from the user list
                itemList.remove(pickedItem);
                //Adds it to the list used by the room
                fullItemList.add(pickedItem);
                String[] newItems = new String[fullItemList.size()];
                fullItemList.toArray(newItems);
                //Sets the list with the added item to the current state of the room
                currentRoom.setNewItems(newItems);
                UserMoves.roomDetails(currentRoom);
                break;
            }
        }
        //Prints out statement if it cannot drop the item
        System.out.println("You do not have a " + pickedItem + "!");
        UserMoves.roomDetails(currentRoom);
    }
}
