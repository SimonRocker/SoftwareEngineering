package com.company.factory;

import com.company.logic.Field;

public class FieldFactory {

    public Field build(int id){
        return new Field(id);
    }
}
