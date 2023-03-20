package com.ag.scratchwinrealcash;

public class UserData {

    String Name;
    String Upi;
    String Amount;
    String Value;

    public UserData(String name, String upi, String amount, String value) {
        Name = name;
        Upi = upi;
        Amount = amount;
        Value = value;
    }

    public String getName() {
        return Name;
    }

    public String getUpi() {
        return Upi;
    }

    public String getAmount() {
        return Amount;
    }

    public String getValue() {
        return Value;
    }
}
