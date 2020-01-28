package com.company.gui.port;

import com.company.gui.Controller;
import com.company.logic.IGameModel;

public interface UiPort {
    Ui ui(IGameModel model);
}
