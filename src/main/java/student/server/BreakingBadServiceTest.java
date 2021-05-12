package student.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;

import java.io.File;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;

import java.io.File;
import java.util.ArrayList;
import org.junit.Test;
import student.adventure.AdventureDesign;
import student.adventure.GameEngine;
import student.adventure.Room;

import static org.junit.Assert.assertEquals;
public class BreakingBadServiceTest {
    private static AdventureDesign testDesign;
    private static GameEngine gameEngine;
    private Room currentRoom;
    private BreakingBadAdventureService bbService;

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
        for(Room room : testDesign.getRooms()) {
            if(room.getName().equals(testDesign.getStartingRoom())) {
                currentRoom = room;
            }
        }
        gameEngine = new GameEngine(file);
        bbService = new BreakingBadAdventureService();

    }
    /*
    Tests the creation of a new GameStatus object
     */
    @Test
    public void testNewGameEngine() throws AdventureException {
        bbService.reset();
        assertEquals(bbService.newGame(), 1);
    }
    /*
    Tests the destruction of a new GameStatus object
     */
    @Test
    public void testDestroyOnWeb() throws AdventureException {
        bbService.reset();
        bbService.newGame();
        boolean gameOver = bbService.destroyGame(1);
        assertEquals(gameOver, true);
    }
    /*
    Tests the go command on the BreakingBad Adventure Service class
     */
    @Test
    public void TestGoOnWeb() throws AdventureException {
        bbService.reset();
        bbService.newGame();
        Command goCommand = new Command("go", "east");
        bbService.executeCommand(1, goCommand);
        Room testRoom = new Room();
        for(Room r : testDesign.getRooms()) {
            if(r.getName().equals("Pinkman Residence")) {
                testRoom = r;
            }
        }
        String message = gameEngine.displayRoom(testRoom);
        assertEquals(message, bbService.getGame(1).getMessage());
    }
    /*
    Tests the take command on the BreakingBad Adventure Service class
     */
    @Test
    public void TestTakeOnWeb() throws AdventureException {
        bbService.reset();
        bbService.newGame();
        Command takeCommand = new Command("take", "money");
        bbService.executeCommand(1, takeCommand);
        gameEngine.takeItem("money");
        assertEquals(bbService.getGame(1).getCommandOptions().get("take"), gameEngine.getRoomItems());
    }
    /*
    Tests the drop command on the BreakingBad Adventure Service class
     */
    @Test
    public void TestDropOnWeb() throws AdventureException {
        bbService.reset();
        bbService.newGame();
        Command takeCommand = new Command("take", "money");
        Command dropCommand = new Command("drop", "money");
        bbService.executeCommand(1, takeCommand);
        bbService.executeCommand(1, dropCommand);
        gameEngine.takeItem("money");
        gameEngine.dropItem("money");
        assertEquals(bbService.getGame(1).getCommandOptions().get("take"), gameEngine.getRoomItems());
    }
    /*
    Tests the examine command on the BreakingBad Adventure Service class
     */
    @Test
    public void TestExamineOnWeb() throws AdventureException {
        bbService.reset();
        bbService.newGame();
        Command examineCommand = new Command("examine", "room");
        bbService.executeCommand(1, examineCommand);
        assertEquals(bbService.getGame(1).getMessage(), gameEngine.displayRoom(currentRoom));
    }
    /*
   Tests the history command on the BreakingBad Adventure Service class
    */
    @Test
    public void TestHistoryOnWeb() throws AdventureException {
        bbService.reset();
        bbService.newGame();
        Command examineCommand = new Command("history", "rooms");
        bbService.executeCommand(1, examineCommand);
        assertEquals(bbService.getGame(1).getMessage(), gameEngine.getHistoryOfRooms());
    }
    /*
  Tests an invalid input in the executeCommand method on the BreakingBad Adventure Service class
   */
    @Test
    public void TestInvalidInputOnWeb() throws AdventureException {
        bbService.reset();
        bbService.newGame();
        Command weirdCommand = new Command("hi", "bob");
        bbService.executeCommand(1, weirdCommand);
        assertEquals(bbService.getGame(1).getMessage(), "I cannot understand " + weirdCommand.getCommandValue() + "!"
        + "\n" + gameEngine.displayRoom(gameEngine.getCurrentRoom()));
    }
    /*
  Tests an null input in the executeCommand method on the BreakingBad Adventure Service class
   */
    @Test
    public void TestNullInputOnWeb() throws AdventureException {
        bbService.reset();
        bbService.newGame();
        Command weirdCommand = new Command(null, null);
        bbService.executeCommand(1, weirdCommand);
        assertEquals(bbService.getGame(1).getMessage(), "I cannot understand null!" +
                "\n" + gameEngine.displayRoom(gameEngine.getCurrentRoom()));
    }
    /*
    Tests two consecutive commands in the same service and gameStatus object.
     */
    @Test
    public void TestConsecutiveCommands() throws AdventureException {
        bbService.reset();
        bbService.newGame();
        Command takeCommand = new Command("take", "money");
        Command goCommand = new Command("go", "east");
        bbService.executeCommand(1, takeCommand);
        gameEngine.takeItem("money");
        assertEquals(bbService.getGame(1).getCommandOptions().get("take"), gameEngine.getRoomItems());
        bbService.executeCommand(1, goCommand);
        Room newRoom = new Room();
        for(Room r : testDesign.getRooms()) {
            if(r.getName().equals("Pinkman Residence")) {
                newRoom = r;
            }
        }
        assertEquals(bbService.getGame(1).getMessage(),gameEngine.displayRoom(newRoom));
    }
}
