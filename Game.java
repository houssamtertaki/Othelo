

public class Game 
{
    final static int BLACK = 1;          // Declare state of each square
    final static int WHITE = 2;
    final static int EMPTY = 0;
    final static int WIDTH = 10;
    final static int HEIGHT = 10;
    final int board[][] = new int[WIDTH][HEIGHT];
	
	/**
     *  Default constructor
     */
    public Game() 
	{
	}
	
	/**
     *  Creates a copy of the game
     *
     *  @param    another    The game to be copied
     *
     */
    public Game(Game another) 
	{
		for (int i = 0; i < HEIGHT; i++)
		{
			for (int j = 0; j < WIDTH; j++)
			{
				this.board[i][j] = another.board[i][j];
			}
		}
	}
	
    /**
     *  Decide if the move is legal
     *
     *  @param    r          row in the game matrix
     *  @param    c          column in the game matrix
     *  @param    color      color of the player - Black or White
     *  @param    flip       true if the player wants to flip the discs
     *
     *  @return              true if the move is legal, else false
     */
    public boolean legalMove(int r, int c, int color, boolean flip) 
	{
		// Initialize boolean legal as false
		boolean legal = false;
		
		// si la case est vide on commence la recherche
		// si la case n'est pas vide on a pas besoin de rien vérifier 
		if (board[r][c] == 0)
		{   //le cas la case est vide
			// Initialize variables
			int posX;
			int posY;
			boolean found;
			int current;
			
			// Searches in each direction
			// x and y describe a given direction in 9 directions
			// 0, 0 est redundante 
			for (int x = -1; x <= 1; x++)
			{
				for (int y = -1; y <= 1; y++)
				{
					// Variables to keep track of where the algorithm is and
					// whether it has found a valid move
					posX = c + x;
					posY = r + y;
					found = false;
					current = board[posY][posX];
					
					// Check the first cell in the direction specified by x and y
					// If the cell is empty, out of bounds or contains the same color
					// skip the rest of the algorithm to begin checking another direction
					if (current == -1 || current == 0 || current == color)
					{
						continue;
					}
					
					// Otherwise, check along that direction
					while (!found)
					{
						posX += x;
						posY += y;
						current = board[posY][posX];
						
						// If the algorithm finds another piece of the same color along a direction
						// end the loop to check a new direction, and set legal to true
						if (current == color)
						{
							found = true;
							legal = true;
							
							// If flip is true, reverse the directions and start flipping until 
							// the algorithm reaches the original location
							if (flip)
							{
								posX -= x;
								posY -= y;
								current = board[posY][posX];
								
								while(current != 0)
								{
									board[posY][posX] = color;
									posX -= x;
									posY -= y;
									current = board[posY][posX];
								}
							}
						}
						// If the algorithm reaches an out of bounds area or an empty space
						// end the loop to check a new direction, but do not set legal to true yet
						else if (current == -1 || current == 0)
						{
							found = true;
						}
					}
				}
			}
		}

        return legal;
    }
	
	
}
