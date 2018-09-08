package de.puettner.sikuli.dso.ui.commands;

import de.puettner.sikuli.dso.commands.ui.MenuBuilder;
import de.puettner.sikuli.dso.commands.ui.MessageBoxMenu;
import org.junit.Before;
import org.junit.Test;

public class MessageBoxMenuTest extends MenuTest {

    private final MessageBoxMenu menu = MenuBuilder.build().buildMessageBoxMenu();

    @Before
    public void before() {
        sikuliCmd.focusBrowser();
    }

    @Test
    public void collectSourcesFromMessageBox() {
        menu.fetchRewardMessages();
    }

    @Test
    public void highlightMenuRegion() {
        menu.highlightMenuRegion();
    }

}
