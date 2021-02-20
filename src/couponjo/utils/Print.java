package couponjo.utils;

public class Print {

    public static void sepereation() {
        System.out.println(ConsoleColors.CYAN_BOLD + "********************************************************************************" +
                "**********************************************************************************************************************" + ConsoleColors.RESET);
    }

    public static void exception(String errorMsg) {
        System.out.println(ConsoleColors.RED+ errorMsg + ConsoleColors.RESET);
    }

}
