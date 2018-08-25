package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.os.CmdowOuput;
import de.puettner.sikuli.dso.commands.os.WindowsPlatformHelper;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class WindowsPlatformHelperTest {

    private WindowsPlatformHelper sut = new WindowsPlatformHelper();

    @Test
    public void isProcessRunning() {
        assertTrue(sut.isProcessRunning(WindowsPlatformHelper.CHROME_EXE));
    }

    @Test
    public void getChromeDimension() {
        CmdowOuput rect = sut.getChromeDimension();
        assertNotNull(rect);
    }

    @Test
    public void getTaskList() {
        String taskList = sut.getTaskList();
        System.out.println(taskList);
    }

}
