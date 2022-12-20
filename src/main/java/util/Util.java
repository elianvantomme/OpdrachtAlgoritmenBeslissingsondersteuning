package util;

public class Util {

    public static double calcContainerPickupX(int containerLength, int slotX){
        return slotX + (double) containerLength/2;
    }

}
