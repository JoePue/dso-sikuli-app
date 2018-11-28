package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

public enum IslandButtons implements MenuButton {

    CollectableIconOne(pattern("CollectablePinkyIcon_1.png").targetOffset(0, 0)),
    //    CollectableIconTwo(pattern("CollectablePinkyIcon_2.png").similar(052f).targetOffset(0, 0)),
    CollectableIconThree(pattern("CollectablePinkyIcon_3.png").targetOffset(0, 0)),
    CollectableIconFour(pattern("CollectablePinkyIcon-4.png").exact().targetOffset(0, 0)),
    BookbinderBuilding(pattern("BookBinderBuilding.png").similar(0.80f)),
    Avatar(pattern("Avatar.png").similar(0.80f)),
    StarButton(pattern("StarButton.png").targetOffset(-2, -25)),
    QuestBookIcon(pattern("Questbook-icon.png")),
    SolvedQuestArrow(pattern("SolvedQuestArrow.png").similar(0.96f)),
    NewQuestArrow(pattern("NewQuestArrow.png").similar(0.95f)),
    NotificationInfoBox(pattern("notification-frame.png").similar(0.80f));

    public final Pattern pattern;

    IslandButtons(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
