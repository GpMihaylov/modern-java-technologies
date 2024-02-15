package bg.sofia.uni.fmi.mjt.dungeons.online.server.command.manager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandCreatorTest {

    @Test
    public void testCreateNewCommandWithNoArgs() {
        Command cmd = CommandCreator.newCommand("attack");

        assertEquals("attack", cmd.command(), "\"attack\" command name expected");
        assertEquals(0, cmd.arguments().length, "No command arguments expected");
    }

    @Test
    public void testCreateNewCommandWithOneArg() {
        Command cmd = CommandCreator.newCommand("move up");

        assertEquals("move", cmd.command(), "\"move\" command name expected");
        assertEquals(1, cmd.arguments().length, "One command argument expected");
        assertEquals("up", cmd.arguments()[0], "Expected \"up\" as argument");
    }


    @Test
    public void testCreateNewCommandWithTwoArgs() {
        Command cmd = CommandCreator.newCommand("send sword 5");

        assertEquals("send", cmd.command(), "send\" command name expected");
        assertEquals(2, cmd.arguments().length, "Two command arguments expected");
        assertEquals("sword", cmd.arguments()[0], "Expected \"sword\" as first argument");
        assertEquals("5", cmd.arguments()[1], "Expected \"5\" as second argument");
    }

}
