import de.puettner.sikulie.dso.Constants;
import de.puettner.sikulie.dso.WindowsPlatformHelper;

import static junit.framework.Assert.assertTrue;

public class WindowsPlatformHelperTest {

    private WindowsPlatformHelper sut = new WindowsPlatformHelper();

    @org.junit.Test
    public void isProcessRunning() {
        assertTrue(sut.isProcessRunning(Constants.CHROME_EXE));
    }

    @org.junit.Test
    public void getTaskList() {
        String taskList = sut.getTaskList();
        System.out.println(taskList);
    }

}
