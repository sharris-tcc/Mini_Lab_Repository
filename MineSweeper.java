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

        // Prompt user for number of rows and columns
        System.out.print("How many rows would you like: ");
        int r = input.nextInt();
        System.out.print("How many columns would you like: ");
        int c = input.nextInt();

        // Initialize game board using input values
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
                    // Throw exception if input exceeds row or column size
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
                // Catch invalid input.  Must be an integer only
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

    /**
     * Mines are generated using random numbers
     * If number is <= square root of total spaces
     * element is considered a mine
     */
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
                            // Call recursive until a number or mine is found
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


/*
1. The constructor of the MineSweeper class requires rows and columns to be input. These rows and columns will define
   the limit size of the board. The class member Board, as well as gameBoard and displayBoard are created by 2D character
   array objects based on the given rows and columns.
2. The exception handling is implemented in the PlayGame() method in the program. The first and priority exception,
   which is a pure MismatchExceptionError, checks if the entered values are not integer numbers. The second exception,
   which is the general Exception or can be implied as a logic error, checks if the entered value is not in the range
   of the length of the row and column of the Board. This exception has a man-made throwing statement. I guess the
   purpose is to prevent index out-of-bounds errors caused by accessing the space in the array that does not exist.
3. There is one recursive method implemented in the ChangeBoard() method. The main base case of the method is when the
   displayBoard[r + i][c + j] is not an asterisk ‘*’ or when the gameBoard[r + i][c + j] is a bomb ‘B’. I assume that
   checking the borderline of the Board is also a base case because it will not recall the function once the position
   ([r + i][c + j]) is not in the range of the Board.
4. Mines are generated on Board using the GenerateMines() method in the program. Firstly, each index of the Board[][]
   array will be assigned a random number between 0 and its size “totalSpaces” using Random generator. If any value in
   indexes in the array is less than or equal to the square root of its size, that indexes in the gameBoard[][] array
   will be assigned with the mines ‘B’. Otherwise, it will be a space ‘*’.
5. The interesting thing I found analyzing the code is the recursive method. This recursive method reminds me of Depth
   First Search (DFS) algorithm that I used to know. The main idea here is to flood the board until there is nothing
   more to reach. I guess in this situation, assigning displayBoard[r + i][c + j] with space ‘ ‘ here also means to
   mark the index that the method has gone through in the board. Therefore, if the recursive method is called with the
   same position again, the if statement will ignore the position because it has been assigned with a space ‘ ‘ and
   without an asterisk ‘*’. I have never thought of using this algorithm or paradigm like DFS in many real-life
   applications or apps but only use it to solve many programming questions. However, now I knew how it is useful in
   creating wonderful games like MineSweeper.

 */