package com.khanhpham.tothemoon.utils.text;

public class TextUtils {
    public static String energyToString(int energy) {
        String str = "";
        String fullEnergyString = Integer.toString(energy);
        int energyStringLength = fullEnergyString.length();

        //terra-FE = more than 15 characters
        if (energyStringLength > 15) {
            str = cutPos(fullEnergyString, 15, "TFE");
        } else

            //giga FE = more than 12 character
            if (energyStringLength > 12) {
                str = cutPos(fullEnergyString, 12, "GFE");
            } else
                //Mega FE
                if (energyStringLength > 9) {
                    str = cutPos(fullEnergyString, 9, "MFE");
                } else if (energyStringLength > 6) {
                    str = cutPos(fullEnergyString, 6, "kFE");
                } else str = energy + "FE";

        if (str.isEmpty()) {
            str = "EMPTY";
        }

        return str;
    }

    private static String cutPos(String string, int posToCut, String extension) {
        int dotPos;
        dotPos = string.length() - posToCut;
        String str = copy(string, dotPos);
        str = str.concat('.' + copy(string, dotPos, dotPos + 2) + extension);
        return str;
    }

    private static String copy(String string, int startIndex, int endIndex) {
        String copied = "";
        for (int i = startIndex; i < endIndex; i++) {
            copied = copied.concat(String.valueOf(string.charAt(i)));
        }
        return copied;
    }

    private static String copy(String string, int end) {
        return copy(string, 0, end);
    }

    public static String showPercentage(int i, int cap) {
        return "(" + i * 100 / cap + "%)";
    }
}
