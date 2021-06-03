


public class White 
{

	
    /** 
     *  This method calls the appropriate strategy.
     *
     *  @param    game    the current state of the game
     *  @param    done    true if the player cannot move anywhere
     *  @param    color   the color (Black or White) of the player
     *
     *  @return   game    the resulting state of the game
     */
    public Game strategy(Game game, boolean done, int color) {
        
        return randStrategy(game,done,color);
    }

    /**
     *  Take a turn using a random strategy.
     *
     *  @param    game    the current state of the game
     *  @param    done    true if the player cannot move anywhere
     *  @param    color   the color (Black or White) of the player
     *
     *  @return   game    the resulting state of the game
     */
    public Game randStrategy(Game game, boolean done, int color) {

        int row = (int)(Math.random()*(game.HEIGHT-2)) + 1;
        int column = (int)(Math.random()*(game.WIDTH-2)) + 1;
        
        while (!done && !game.legalMove(row,column,color,true)) {
            row = (int)(Math.random()*(game.HEIGHT-2)) + 1;
            column = (int)(Math.random()*(game.WIDTH-2)) + 1;
        }
        
        if (!done) 
            game.board[row][column] = color;

        return game;
    }

  
}
