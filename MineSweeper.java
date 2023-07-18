import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class MineSweeper {
    final private int[][]board;
    private int mineCount;
    final private char[][]gameBoard;
    final private char[][]displayBoard;
    private  int spacesFound;
    final private int totalSpaces;

    public MineSweeper() {
        Scanner input = new Scanner(System.in);

        System.out.print("How many rows would you like: ");
        int r = input.nextInt();
        System.out.print("How many columns would you like: ");
        int c = input.nextInt();

        board = new int[r][c];
        mineCount = 0;
        gameBoard = new char[r][c];
        displayBoard = new char[r][c];
        totalSpaces = r * c;
        spacesFound = 0;
    }
    public void PlayGame() {
        GenerateMines();
        GenerateSpaces();
        Scanner input = new Scanner(System.in);
        int r = 0, c = 0;
        do {
            DisplayBoard();
            boolean invalidMove = true;
            do
            {
                try
                {
                    System.out.println("Pick coordinates: ");
                    System.out.print("row: ");
                    r = input.nextInt();
                    if (r >= displayBoard.length || r < 0)
                    {
                        throw new Exception("Invalid Entry...Enter number between 0 and " + displayBoard.length);
                    }
                    System.out.print("column: ");
                    c = input.nextInt();
                    if (c >= displayBoard[0].length || c < 0) {
                        throw new Exception("Invalid Entry...Enter number between 0 and " + displayBoard[0].length);
                    }

                    invalidMove = false;
                }
                catch (InputMismatchException e)
                {
		    input.nextLine();
                    System.out.println("Invalid Entry...Enter a number only");
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }while(invalidMove);
            if (gameBoard[r][c] == 'B')
            {
                displayBoard[r][c] = gameBoard[r][c];
                DisplayGameBoard();
                System.out.println("YOU LOSE :(");
                break;
            }
            else
            {
                ChangeBoard(r, c);
            }

        }while(!CheckBoard());

    }
    public void GenerateMines()
    {
        Random random = new Random();

        for(int row = 0; row < board.length; row++)
        {
            for(int col = 0; col < board[0].length; col++)
            {
                board[row][col] = random.nextInt(totalSpaces);
            }
        }

        for(int row = 0; row < board.length; row++)
        {
            for(int col = 0; col < board[0].length; col++)
            {
                if(board[row][col] <= (int)Math.sqrt(totalSpaces))
                {
                    gameBoard[row][col] = 'B';
                    displayBoard[row][col] = '*';
                    mineCount++;
                }
                else
                {
                    gameBoard[row][col] = '*';
                    displayBoard[row][col] = '*';
                }
            }
        }
    }
    public void GenerateSpaces()
    {
        for(int row = 0; row < board.length; row++)
        {
            for (int col = 0; col < board[0].length; col++)
            {
                if(gameBoard[row][col] != 'B')
                {
                    int proxValue = 0;
                    for(int i = -1; i < 2; i++)
                    {
                        for(int j = -1; j < 2; j++)
                        {
                            if(row - i < 0 || row - i > gameBoard.length - 1
                                    || col - j < 0 || col - j > gameBoard[0].length - 1)
                            {
                            }
                            else
                            {
                                if (gameBoard[row - i][col - j] == 'B')
                                {
                                    String value = String.valueOf (proxValue + 1);
                                    gameBoard[row][col] = value.charAt(0);
                                    proxValue++;
                                }
                                if(proxValue == 0)
                                {
                                    gameBoard[row][col] = ' ';
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public void DisplayGameBoard()
    {
        for(int row = 0; row < gameBoard.length; row++)
        {
            for (int col = 0; col < gameBoard[0].length; col++)
            {
                System.out.print(gameBoard[row][col] + "\t");
            }
            System.out.println();
        }
    }
    public void DisplayBoard()
    {
        for(int i = 0; i < displayBoard[0].length; i++)
        {
            System.out.print("\t" + i );
        }
        System.out.println();
        System.out.print("  ");
        for(int i = 0; i < displayBoard[0].length; i++)
        {
            System.out.print("----");
        }
        System.out.println();
        for(int row = 0; row < displayBoard.length; row++) {
            System.out.print(row + "|  ");
            for (int col = 0; col < displayBoard[0].length; col++)
            {
                if(displayBoard.length > 10 && row <= 9 && col == 0)
                {
                    System.out.print(" " + displayBoard[row][col] + "\t");
                }
                else
                {
                    System.out.print(displayBoard[row][col] + "\t");
                }

            }
            System.out.println();
        }
        System.out.println("Mine Count: " + mineCount);
        System.out.println("Total Spaces: " + totalSpaces);
        System.out.println("Spaces remaining: " + (totalSpaces - mineCount - spacesFound));
    }
    public void ChangeBoard(int r, int c)
    {
        boolean spaceFound;
        do
        {
            spaceFound = false;
            for(int i = -1; i < 2; i++)
            {
                for(int j = -1; j < 2; j++)
                {
                    if(r+i >= 0 && r+i <= gameBoard.length - 1 && c+j >=0 && c+j <= gameBoard.length - 1)
                    {
                        if(gameBoard[r + i][c + j] != 'B' && displayBoard[r + i][c + j] == '*')
                        {
                            displayBoard[r + i][c + j] = gameBoard[r + i][c + j];
                            spaceFound = true;
                            spacesFound++;
                            if(displayBoard[r + i][c + j] == ' ')
                            {
                                ChangeBoard(r+i,c+j);
                            }
                        }
                    }

                }
            }
        }while(spaceFound);
    }
    public boolean CheckBoard()
    {
        for(int row = 0; row < gameBoard.length; row++)
        {
            for (int col = 0; col < gameBoard[0].length; col++)
            {
                if(displayBoard[row][col] == '*' && gameBoard[row][col] != 'B')
                {
                    return false;
                }
            }
        }
        DisplayGameBoard();
        System.out.println("YOU WIN!!!!");
        return true;
    }
}
