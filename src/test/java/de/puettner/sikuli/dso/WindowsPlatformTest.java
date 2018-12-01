package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.os.CmdowOuput;
import de.puettner.sikuli.dso.commands.os.WindowsPlatform;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class WindowsPlatformTest {

    private WindowsPlatform sut = new WindowsPlatform();

    @Test
    public void isProcessRunning() {
        assertTrue(sut.isDsoBrowserRunning());
    }

    @Test
    public void maximizeBrowserWindow() {
        sut.maximizeBrowserWindow();
    }

    @Test
    public void restoreBrowserWindow() {
        sut.restoreBrowserWindow();
    }

    @Test
    public void getChromeDimension() {
        CmdowOuput rect = sut.getDsoBrowserDimension();
        assertNotNull(rect);
        System.out.println(rect);
    }

    @Test
    public void getTaskList() {
        String taskList = sut.getTaskList();
        System.out.println(taskList);
    }

    @Test
    public void standby() {
        sut.standby();
    }

}
