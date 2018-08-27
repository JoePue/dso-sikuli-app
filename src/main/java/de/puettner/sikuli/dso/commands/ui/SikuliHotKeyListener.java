package de.puettner.sikuli.dso.commands.ui;

import lombok.extern.slf4j.Slf4j;
import org.sikuli.basics.HotkeyEvent;
import org.sikuli.basics.HotkeyListener;

/**
 * Docu.: https://sikulix-2014.readthedocs.io/en/latest/interaction.html#listening-to-global-hotkeys
 */
@Slf4j
public class SikuliHotKeyListener extends HotkeyListener {

    /**
     * Register Hot-Key: STRG+UMSCHALT J
     *
     * @param e
     */
    @Override
    public void hotkeyPressed(HotkeyEvent e) {
        String msg = "hotkeyPressed => System.exit(-1);";
        log.error(msg);
        System.out.println(msg);
        System.err.println(msg);
        System.exit(-1);
    }
}
