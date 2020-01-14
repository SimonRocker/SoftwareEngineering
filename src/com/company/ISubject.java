package com.company;

import com.company.gui.IObserver;

public interface ISubject {

    void attach(IObserver observer);
}
