package com.company.gui;

import com.company.gui.port.Ui;
import com.company.gui.port.UiPort;
import com.company.logic.IGameModel;

public interface GuiFactory {
    GuiFactory FACTORY = new GuiFactoryImpl();
    UiPort uiPort();

    void startEventLoop(IGameModel model);

    Ui ui(IGameModel model);
}
