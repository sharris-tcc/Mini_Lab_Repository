public class MineSweeperGame {

    public static void main(String[] args) {

        System.out.println("""
                  __  __ _            ____                                  \s
                 |  \\/  (_)_ __   ___/ ___|_      _____  ___ _ __   ___ _ __\s
                 | |\\/| | | '_ \\ / _ \\___ \\ \\ /\\ / / _ \\/ _ \\ '_ \\ / _ \\ '__|
                 | |  | | | | | |  __/___) \\ V  V /  __/  __/ |_) |  __/ |  \s
                 |_|  |_|_|_| |_|\\___|____/ \\_/\\_/ \\___|\\___| .__/ \\___|_|  \s
                                                            |_|

                """);
        MineSweeper m = new MineSweeper();
        m.PlayGame();
    }
}
