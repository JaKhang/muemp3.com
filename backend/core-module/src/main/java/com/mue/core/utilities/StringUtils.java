package com.mue.core.utilities;

import jakarta.annotation.Nonnull;
import org.apache.commons.io.FilenameUtils;

public class StringUtils {
    public static boolean isInteger(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isBoolean(String strNum){
        if (strNum == null) {
            return false;
        }
        return strNum.equalsIgnoreCase("true") || strNum.equalsIgnoreCase("false");
    }

    public static boolean isDouble(String strNum){
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
