package com.mostafa.fci.androidtask.Model.Network;

public class ClientScanResult {

    String string1;
    String string2;
    String string3;
    boolean isReachable;

    public ClientScanResult(String string1, String string2, String string3, boolean isReachable) {
        this.string1 = string1;
        this.string2 = string2;
        this.string3 = string3;
        this.isReachable = isReachable;
    }

    public String getString1() {
        return string1;
    }

    public void setString1(String string1) {
        this.string1 = string1;
    }

    public String getString2() {
        return string2;
    }

    public void setString2(String string2) {
        this.string2 = string2;
    }

    public String getString3() {
        return string3;
    }

    public void setString3(String string3) {
        this.string3 = string3;
    }

    public boolean isReachable() {
        return isReachable;
    }

    public void setReachable(boolean reachable) {
        isReachable = reachable;
    }
}
