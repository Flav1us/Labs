package com.company;

import java.io.Serializable;

public class Value implements Serializable {
	public int amount;

    public Value() {
    }

    public Value( Value that ) {
        this.amount = that.amount;
    }

    public boolean equals( Object o ) {
        if ( o == this ) return true;
        if ( !( o instanceof Value ) ) return false;
        Value that = ( Value ) o;
        return that.amount == this.amount;
    }
}
