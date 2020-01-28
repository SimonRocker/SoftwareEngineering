package com.company.gui;

import com.company.gui.port.Ui;
import com.company.gui.port.UiPort;
import com.company.logic.GameModel;
import com.company.logic.IGameModel;

public class GuiFactoryImpl implements GuiFactory, UiPort, Ui {
    private ObserverView ui;
    private IGameModel gameModel;
    private Controller controller;

    private void mkUi() {
        if (this.ui == null) {
            this.ui = new ObserverView(this.gameModel);
            Controller controller = new Controller(this.gameModel, this.ui);
            this.controller = controller;
        }
        this.controller.run();
    }

    @Override
    public synchronized UiPort uiPort() {
        return this;
    }

    @Override
    public synchronized void startEventLoop(IGameModel model) {
    this.ui.startEventLoop(model);
    }

    @Override
    public synchronized Ui ui(IGameModel model) {
        this.gameModel = model;


        this.mkUi();
        return this;
    }


}
