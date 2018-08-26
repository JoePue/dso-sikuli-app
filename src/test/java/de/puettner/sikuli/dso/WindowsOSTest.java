package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.os.CmdowOuput;
import de.puettner.sikuli.dso.commands.os.WindowsOS;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class WindowsOSTest {

    private WindowsOS sut = new WindowsOS();

    @Test
    public void isProcessRunning() {
        assertTrue(sut.isProcessRunning(WindowsOS.CHROME_EXE));
    }

    @Test
    public void getChromeDimension() {
        CmdowOuput rect = sut.getDsoBrowserDimension();
        assertNotNull(rect);
    }

    @Test
    public void getTaskList() {
        String taskList = sut.getTaskList();
        System.out.println(taskList);
    }

}
