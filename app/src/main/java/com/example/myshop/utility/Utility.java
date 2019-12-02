package com.example.myshop.utility;

import java.text.NumberFormat;

public class Utility {

    public static NumberFormat addCommaInToNumber(){
        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);
        return myFormat;
    }
}
