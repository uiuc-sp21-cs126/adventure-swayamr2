package student.adventure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class AdventureTest {
    private static AdventureDesign testDesign;
    @Before
    public void setUp() {
        File file = new File("src/main/resources/BreakingBad.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            testDesign = mapper.readValue(file, AdventureDesign.class);
        } catch (Exception e) {
            System.out.println("Please try again!");
            System.exit(0);
        }
    }
    @Test
    public void testNumberOfRooms() {
        Room[] rooms = testDesign.getRooms();
        assertEquals(10, rooms.length);
    }
    @Test
    public void testNumberOfDirections() {
        int countDirections = 0;
        for (Room r : testDesign.getRooms()) {
            for (DirectionsOnMap directions : r.getDirections()) {
                countDirections++;
            }
        }
        assertEquals(22, countDirections);
    }
    @Test
    public void testNumberOfItems() {
        int countItems = 0;
        for(Room r : testDesign.getRooms()) {
            for(String item : r.getItems()) {
                countItems++;
            }
        }
        assertEquals(22, countItems);
    }
    @Test
    public void testStartingRoom() {
        String startingRoom = testDesign.getStartingRoom();
        assertEquals(startingRoom, "Walter White Residence");
    }
    @Test
    public void testEndingRoom() {
        String startingRoom = testDesign.getEndingRoom();
        assertEquals(startingRoom, "DEA Office");
    }
    @Test
    public void testGoValidInput() {
        Room[] rooms = testDesign.getRooms();
        Room CurrentRoom = rooms[0]; //Walter White Residence
        Room newRoom  = UserMoves.updateRoom(CurrentRoom, "east", testDesign);
        assertEquals("Pinkman Residence", newRoom.getName());
    }
    @Test
    public void testGoIrregularSpelling() {
        Room[] rooms = testDesign.getRooms();
        Room CurrentRoom = rooms[1]; //Pinkman Residence
        Room newRoom  = UserMoves.updateRoom(CurrentRoom, "nORTheAsT", testDesign);
        assertEquals("Los Pollos Hermanos", newRoom.getName());
    }
    @Test
    public void testGoIrregularSpacing() {
        Room[] rooms = testDesign.getRooms();
        Room CurrentRoom = rooms[2]; //Los Pollos Hermanos
        Room newRoom  = UserMoves.updateRoom(CurrentRoom, "     nOrtH     ", testDesign);
        assertEquals("Saul Office", newRoom.getName());
    }
    @Test
    public void testGoInvalidDirection() {
        Room[] rooms = testDesign.getRooms();
        Room CurrentRoom = rooms[3]; //Saul Office
        Room newRoom  = UserMoves.updateRoom(CurrentRoom, "West", testDesign);
        assertEquals("Saul Office", newRoom.getName());
    }
    @Test
    public void testGoNullInput()  {
        Room[] rooms = testDesign.getRooms();
        Room CurrentRoom = rooms[4]; //Walt RV
        UserMoves.updateRoom(CurrentRoom, null, testDesign);
        assertEquals(CurrentRoom, UserMoves.updateRoom(CurrentRoom, null, testDesign));
    }
    @Test
    public void testTakeValidInput() {
        Room[] rooms = testDesign.getRooms();
        ArrayList<String> userTest = new ArrayList<>();
        Room currentRoom = rooms[0]; //Walter White Residence
        UserMoves.takeItem(currentRoom, "money", userTest);
        UserMoves.takeItem(currentRoom, "hat", userTest);
        assertEquals(2, userTest.size());
    }
    @Test
    public void testTakeIrregularSpelling() {
        Room[] rooms = testDesign.getRooms();
        ArrayList<String> userTest = new ArrayList<>();
        Room currentRoom = rooms[1]; //Jesse Pinkman Residence
        UserMoves.takeItem(currentRoom, "dRuGs", userTest);
        UserMoves.takeItem(currentRoom, "viDEo GAME", userTest);
        assertEquals(2, userTest.size());
    }
    @Test
    public void testTakeIrregularSpacing() {
        Room[] rooms = testDesign.getRooms();
        ArrayList<String> userTest = new ArrayList<>();
        Room currentRoom = rooms[2]; //Los Pollos Hermanos
        UserMoves.takeItem(currentRoom, "      chicken", userTest);
        UserMoves.takeItem(currentRoom, "methylamine     ", userTest);
        UserMoves.takeItem(currentRoom, "    fry batter        ", userTest);
        assertEquals(3, userTest.size());
    }
    @Test
    public void testTakeInvalidInput() {
        Room[] rooms = testDesign.getRooms();
        ArrayList<String> userTest = new ArrayList<>();
        Room currentRoom = rooms[3]; //Saul Office
        UserMoves.takeItem(currentRoom, "hello", userTest);
        assertEquals(0, userTest.size());
    }
}
