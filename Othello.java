


import java.awt.*;        
import java.awt.event.*;
import javax.swing.*;



public class Othello extends JPanel 
{
    final static int BLACK = 1;          // Declare state of each square
    final static int WHITE = 2;
    final static int EMPTY = 0;
    final static int OFFBOARD = -1;

    Black black = new Black();          // The players
    White white = new White();

    private Game game = new Game();     // Game state
    private int turn = BLACK;
    private boolean black_done = false; 
    private boolean white_done = false;
    
    

    /**
     *  This constructor sets up the initial game configuration, 
     *  and starts the timer with a user specified delay.
     *
     */
    public Othello() {

       //initialise le jeu
        initGame(game);
       
       //mettre la couleur du tableau en vert
            setBackground(Color.GREEN);
            //pour récuperer la case cliqué par le joueur noir et effectuer le move
            addMouseListener( new MouseAdapter() {
                    public void mousePressed(MouseEvent evt) {
                        // Find out which square was clicked
                        int x = evt.getX();
                        int y = evt.getY();
                        int screenWidth = getWidth();
                        int screenHeight = getHeight();
                        int column = (x*(game.WIDTH-2))/screenWidth+1;
                        int row = (y*(game.HEIGHT-2))/screenHeight+1;
                        
                        //si la case cliqué ne correspond pas à un légal move
                        if (!game.legalMove(row,column,BLACK,true)) 
                           System.out.println("Not a legal move - try again!");
                        else {
                            game.board[row][column] = BLACK;
                            //on fait la mise à jour du tableau
                            repaint();
                            //on test s'il reste encore des move possible pour le noir
                            black_done = true;
                            for (int i=1; i<game.HEIGHT-1; i++)
                                for (int j=1; j<game.WIDTH-1; j++)
                                    if (game.legalMove(i,j,BLACK,false) )
                                    black_done=false;
                            //on done la main au blanc
                                    whiteMove();
                        }
                    }
                });
        

      
    }
   
    /** 
     *  Initialize the game state
     *
     *  @param    game    the Game state
     */
    public void initGame(Game game) {

        //le noire qui commence toujours
        turn = BLACK;
        
        
         // Initialize off-board squares
        for (int i=0; i<game.WIDTH; i++) {     
            game.board[i][0] = OFFBOARD;
            game.board[i][game.WIDTH-1] = OFFBOARD;
            game.board[0][i] = OFFBOARD;
            game.board[game.HEIGHT-1][i] = OFFBOARD;
        }

        // Initialize game board to be empty except for initial setup
        for (int i=1; i<game.HEIGHT-1; i++)        
            for (int j=1; j<game.WIDTH-1; j++)
			   game.board[i][j] = EMPTY;
       
               //on initialise les case du milieu
        game.board[game.HEIGHT/2-1][game.WIDTH/2-1] = WHITE;        
        game.board[game.HEIGHT/2][game.WIDTH/2-1] = BLACK;
        game.board[game.HEIGHT/2-1][game.WIDTH/2] = BLACK;
        game.board[game.HEIGHT/2][game.WIDTH/2] = WHITE;
    }



    

    /**
     *  White takes a turn.
     */
    public void whiteMove() {

        // on vérifie d'abord si le blanc pour faire un move encore
        white_done = true;
        for (int i=1; i<game.HEIGHT-1; i++)
            for (int j=1; j<game.WIDTH-1; j++)
                if (game.legalMove(i,j,WHITE,false) )
                    white_done=false;
        
        //bouger le blanc            
        game = white.strategy(game, white_done, WHITE);
    }

   /**
    *  Draw the board and the current state of the game. 
    *
    *  @param    g    the graphics context of the game
    */
   public void paintComponent(Graphics g) {
      
       super.paintComponent(g);  // Fill panel with background color
       
       int width = getWidth();
       int height = getHeight();
       int xoff = width/(game.WIDTH-2);
       int yoff = height/(game.HEIGHT-2);

       int bCount=0;                     
       int wCount=0;                        

       // dessiner lines noires qui séparent les cases
       g.setColor(Color.BLACK);
       for (int i=1; i<=game.HEIGHT-2; i++) {        
           g.drawLine(i*xoff, 0, i*xoff, height);
           g.drawLine(0, i*yoff, width, i*yoff);
       }

       // dessiner les discs noirs et blancs et les petits point qui indique qu'on peut bouger vers telle case
       for (int i=1; i<game.HEIGHT-1; i++) {        
           for (int j=1; j<game.WIDTH-1; j++) {
               // dessiner les discs noirs et blancs
               if (game.board[i][j] == BLACK) {       
                   g.setColor(Color.BLACK);
                   g.fillOval((j*yoff)-yoff+7,(i*xoff)-xoff+7,50,50); 
                   bCount++;
               }
               else if (game.board[i][j] == WHITE) {  
                   g.setColor(Color.WHITE);
                   g.fillOval((j*yoff)-yoff+                                                            7,(i*xoff)-xoff+7,50,50);
                   wCount++;
               }
               // dessiner les petits points qui indique qu'on peut bouger vers telle case
               if (turn == BLACK && game.legalMove(i,j,BLACK,false)) {
                   g.setColor(Color.BLACK);
                   g.fillOval((j*yoff+29)-yoff,(i*xoff+29)-xoff,6,6);
               }
               // If other player cannot move, current player cleans up
               if (turn == WHITE && game.legalMove(i,j,WHITE,false)) {
                   g.setColor(Color.WHITE);
                   g.fillOval((j*yoff+29)-yoff,(i*xoff+29)-xoff,6,6);
               }
           }
       }
 
       // on vérifie si l'un des deux peut bouger encore 
       boolean done = true;
       for (int i=1; i<game.HEIGHT-1; i++)
           for (int j=1; j<game.WIDTH-1; j++)
               if ((game.legalMove(i,j,BLACK,false)) ||
                   (game.legalMove(i,j,WHITE,false)))
                   done=false;

                   //afficher le resultat avec le rouge selon les cas possible
       g.setColor(Color.RED);
       if (done) {
           if (wCount > bCount)
               g.drawString("White won with " + wCount + " discs.",10,20);
           else if (bCount > wCount)
               g.drawString("Black won with " + bCount + " discs.",10,20);
           else g.drawString("Tied game",10,20);
       }
       else {     
           if (wCount > bCount)
               g.drawString("White is winning with " + wCount+" discs",10,20);
           else if (bCount > wCount)
               g.drawString("Black is winning with " + bCount+" discs",10,20);
           else g.drawString("Currently tied",10,20);
       }
      
   }

    /**
     * The main program.
     *
     */
    public static void main(String[] args) {
		
        Othello content;

        
           
            content = new Othello();
            
                JFrame window = new JFrame("Othello Game");
                window.setContentPane(content);
                window.setSize(530,557);
                window.setLocation(100,100);
                window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                window.setVisible(true);
        
    }
}  // Othello