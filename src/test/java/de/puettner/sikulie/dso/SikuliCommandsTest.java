package de.puettner.sikulie.dso;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.sikuli.script.Commands;
import org.sikuli.script.Key;
import org.sikuli.script.Location;

import static java.awt.event.KeyEvent.VK_ENTER;

/**
 * Created by joerg.puettner on 19.08.2018.
 */
public class SikuliCommandsTest {

    private final SikuliCommands sikuliCmd = SikuliCommands.build();

    @Before
    public void before() {
        sikuliCmd.switchToBrowser();
    }

    @Test
//    @Ignore
    public void test5() {
        System.out.println(sikuliCmd.exists("Ok-Button-0.png"));
        System.out.println(sikuliCmd.exists("Ok-Button-1.png"));
        System.out.println(sikuliCmd.exists("Ok-Button-2.png"));
//        sikuliCmd.clickOkButton(0);
//        sikuliCmd.clickSmallOkButton(1);
//        sikuliCmd.clickSmallOkButton(2);
    }

    @Test
    @Ignore
    public void test4() {
        sikuliCmd.type(Key.ESC);
    }

    @Test
    @Ignore
    public void test3() {
        sikuliCmd.highlightAppRegions();
    }

    @Test
    @Ignore
    public void test2() {
        Location location = Commands.click(new Location(100, 1000));
        sikuliCmd.type("Sikuli Test " + System.currentTimeMillis() + Key.ENTER); // java.lang.IllegalArgumentException: Invalid key code
        // wegen ":"
        sikuliCmd.paste("entdeck|kundsch|geolo");
        System.out.println(VK_ENTER);
    }

    @Test
    @Ignore
    public void test1() {
        //        sikuliCmd.existsLetsPlayButton();
        //        sikuliCmd.clickByLocation(new Location(100, 1000));
        //sikuliCmd.type("a", Key.CTRL);

    }

}
//[log] CLICK on L(100,1000)@S(0)[0,0 1920x1080] (21 msec)
//        [log]  TYPE "Sikuli Test 1534708727640#ENTER."
//        [log]  TYPE "entdeck|kundsch|geolo"
//
//        java.lang.IllegalArgumentException: Invalid key code
//
//        at sun.awt.windows.WRobotPeer.keyPress(Native Method)
//        at java.awt.Robot.keyPress(Robot.java:358)
//        at org.sikuli.script.RobotDesktop.doKeyPress(RobotDesktop.java:100)
//        at org.sikuli.script.RobotDesktop.doType(RobotDesktop.java:363)
//        at org.sikuli.script.RobotDesktop.typeChar(RobotDesktop.java:377)
//        at org.sikuli.script.Region.keyin(Region.java:4399)
//        at org.sikuli.script.Region.type(Region.java:4267)
//        at de.puettner.sikulie.dso.SikuliCommands.type(SikuliCommands.java:68)
//        at de.puettner.sikulie.dso.SikuliCommands.type(SikuliCommands.java:62)
//        at de.puettner.sikulie.dso.SikuliCommandsTest.test(SikuliCommandsTest.java:25)
//        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
//        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
//        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
//        at java.lang.reflect.Method.invoke(Method.java:483)
//        at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
//        at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
//        at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
//        at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
//        at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
//        at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)
//        at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)
//        at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
//        at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
//        at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
//        at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
//        at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
//        at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
//        at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
//        at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:119)
//        at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:42)
//        at com.intellij.rt.execution.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:234)
//        at com.intellij.rt.execution.junit.JUnitStarter.main(JUnitStarter.java:74)
//        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
//        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
//        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
//        at java.lang.reflect.Method.invoke(Method.java:483)
//        at com.intellij.rt.execution.application.AppMain.main(AppMain.java:144)
//
