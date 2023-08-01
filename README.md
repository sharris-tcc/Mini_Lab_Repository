# Mini_Lab_Repository

# Minesweeper

Objective - find/select all spaces not labeled as a mine.  Avoid selecting any spaces labeled as mines or 'B'.

How to play - select a row and column coordinate via input.  Repeat until all spaces not labeled as a mine have been revealed.

Win if all spaces not labeled as a mine are revealed

Lose if a space labeled as a mine has been selected



1.  The user inputs how many rows and column they want for the board

2.  In the PlayGame() method.  They are checking for invalid inputs such as texts and numbers that are negative or are greater than the maximum size of the board.

3.  In the ChangeBoard() method.  If the space is no longer empty.

4.  The tile will be a mine if the tile is less than or equal to the square root of the board.

5.  Using square roots to generate the mines was pretty clever.  The number of mines scale with the size of the board despite the values assigned to the tiles being random. 