package couponjo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Print {

    public static void separation() {
        System.out.println(ConsoleColors.CYAN_BOLD + "********************************************************************************" +
                "**********************************************************************************************************************" + ConsoleColors.RESET);
    }

    public static void exception(String errorMsg) {
        System.out.println(ConsoleColors.RED+ errorMsg + ConsoleColors.RESET);
    }

    public static void thread(String msg) {
        System.out.println(ConsoleColors.YELLOW+ msg + ConsoleColors.RESET);
    }

    public static String formatDate(Date date){
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        return dt.format(date);
    }

}
