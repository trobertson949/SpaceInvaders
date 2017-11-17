// utility
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// graphics
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;

// events
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// swing
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SpaceInvaders extends JPanel implements ActionListener, KeyListener, Runnable {

    private final int canvasWidth;
    private final int canvasHeight;
    private final Color backgroundColor;

    private final int framesPerSecond = 25;
    private final int msPerFrame = 1000 / framesPerSecond;
    private Timer timer;
    private int frame = 0;

    private Player me;
    public int alien_height;

    private ArrayList<Alien> joshi;
    private ArrayList<Alien> conner;
    private ArrayList<Alien> goldhammer;
    private ArrayList<Alien> haterz;

    private ArrayList<Projectile> shooty;

    private ArrayList<GraphicsObject> objects;
    // FIXME list your game objects here
    

    /* Constructor for a Space Invaders game
     */
    public SpaceInvaders() {
        // fix the window size and background color
        this.canvasWidth = 600;
        this.canvasHeight = 400;
        this.backgroundColor = Color.BLACK;
        setPreferredSize(new Dimension(this.canvasWidth, this.canvasHeight));

        // set the drawing timer
        this.timer = new Timer(msPerFrame, this);

        this.me = new Player(300, 375);

        this.joshi = new ArrayList<>();
        int num_aliens = 0;
        int i = 0;
        while (num_aliens < 10) {
            joshi.add(new Alien( i, 0));
            num_aliens += 1;
            i += 60;
        }

        this.conner = new ArrayList<>();
        int sproot = 0;
        int toot = 0;
        while (sproot < 10) {
            conner.add(new Alien(toot, 0));
            sproot += 1;
            toot += 60;
        }

        this.goldhammer = new ArrayList<>();
        int poop = 0;
        int ploop = 0;
        while (poop < 10) {
            goldhammer.add(new Alien(ploop, 0));
            poop += 1;
            ploop += 60;
        }

        this.haterz = new ArrayList<>();
        int alienz = 0;
        int spaceinbetween = 0;
        while (alienz < 10) {
            haterz.add(new Alien(spaceinbetween, 0));
            alienz += 1;
            spaceinbetween += 60;
        }

        //this.shooty = new Projectile(me.x, me.y);
        this.objects = new ArrayList<GraphicsObject>();
        shooty = new ArrayList<Projectile>();
        // FIXME initialize your game objects
    }


    /* Start the game
     */
    @Override
    public void run() {
        // show this window
        display();

        // set a timer to redraw the screen regularly
        this.timer.start();
    }

    /* Create the window and display it
     */
    private void display() {
        JFrame jframe = new JFrame();
        jframe.addKeyListener(this);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setContentPane(this);
        jframe.pack();
        jframe.setVisible(true);
    }

    /* Run all timer-based events
     *
     * @param e  An object describing the timer
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // update the game objects
        update();
        // draw every object (this calls paintComponent)
        repaint(0, 0, this.canvasWidth, this.canvasHeight);
        // increment the frame counter
        this.frame++;
    }

    /* Paint/Draw the canvas.
     *
     * This function overrides the paint function in JPanel. This function is
     * automatically called when the panel is made visible.
     *
     * @param g The Graphics for the JPanel
     */
    @Override
    protected void paintComponent(Graphics g) {
        // clear the canvas before painting
        clearCanvas(g);
        if (hasWonGame()) {
            paintWinScreen(g);
        } else if (hasLostGame()) {
            paintLoseScreen(g);
        } else {
            paintGameScreen(g);
        }
    }

    /* Clear the canvas
     *
     * @param g The Graphics representing the canvas
     */
    private void clearCanvas(Graphics g) {
        Color oldColor = g.getColor();
        g.setColor(this.backgroundColor);
        g.fillRect(0, 0, this.canvasWidth, this.canvasHeight);
        g.setColor(oldColor);
    }

    /* Respond to key release events
     *
     * A key release is when you let go of a key
     * 
     * @param e  An object describing what key was released
     */
    public void keyReleased(KeyEvent e) {
        // you can leave this function empty
    }

    /* Respond to key type events
     *
     * A key type is when you press then let go of a key
     * 
     * @param e  An object describing what key was typed
     */
    public void keyTyped(KeyEvent e) {
        // you can leave this function empty
    }

    /* Respond to key press events
     *
     * A key type is when you press then let go of a key
     * 
     * @param e  An object describing what key was typed
     */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            me.speed_x = -7;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            me.speed_x = 7;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            Projectile new_shooty = new Projectile(me.x, me.y);
            new_shooty.speed_y = -7;
            shooty.add(new_shooty);
            // FIXME what happens when space bar is pressed
        }
    }

    /* Update the game objects
     */
    private void update() {
        me.update(this.canvasWidth, this.canvasHeight, frame);
        for (Projectile w : this.shooty) {
            w.update(this.canvasWidth, this.canvasHeight, frame);
        }
        for (Alien s : this.joshi){
            s.update(this.canvasWidth, this.canvasHeight, frame);
        }
        for (GraphicsObject obj : this.objects) {
            obj.update(this.canvasWidth, this.canvasHeight, frame);
        }
        if (joshi.get(0).y + this.alien_height > 120) {
            for (Alien w : this.conner){
                w.update(this.canvasWidth, this.canvasHeight, frame);
            }
        }
        Projectile_touching_Alien(joshi,shooty);
        // FIXME update game objects here
    }

    /* Check if the player has lost the game
     *
     * @returns  true if the player has lost, false otherwise
     */
    private boolean hasLostGame() {
        if (joshi != null && !joshi.isEmpty()) {
            if (joshi.get(0).y + this.alien_height > canvasHeight) {
                return true;
            }
            //when it touches the bottom show lose screen
            //}

        }
        return false; // FIXME delete this when ready
    }


    /* Check if the player has won the game
     * 
     * @returns  true if the player has won, false otherwise
     */
    private boolean hasWonGame() {
        return false; // FIXME delete this when ready
    }

    private void Projectile_touching_Alien(ArrayList<Alien> joshi, ArrayList<Projectile> shooty) {
        for (int i = 0; i < joshi.size(); i++ ) {
            for (int j = 0; j < shooty.size(); j++) {
                if ((shooty.get(j).x + 2 >= joshi.get(i).x) && (shooty.get(j).y <= joshi.get(i).y+2)) {
                    Alien gone_joshi = joshi.get(i);
                    joshi.remove(gone_joshi);
                    Projectile gone_shooty = shooty.get(j);
                    shooty.remove(gone_shooty);
                }
            }
        }
    }

    /* Paint the screen during normal gameplay
     *
     * @param g The Graphics for the JPanel
     */

    private void paintGameScreen(Graphics g) {
        // FIXME draw game objects here
        this.me.draw(g);

        for (Alien s: joshi) {
            s.draw(g);
        }

        for (Projectile w: shooty){
        w.draw(g);
        }

        }


    /* Paint the screen when the player has won
     *
     * @param g The Graphics for the JPanel
     */
    private void paintWinScreen(Graphics g) {
        super.paintComponent(g);
        Graphics win = (Graphics) g;
        win.setColor(Color.yellow);
        win.fillRect(0, 0, canvasWidth, canvasHeight);
        win.setColor(Color.black);
        win.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
        win.drawString("Congratulations!", canvasWidth/6, canvasHeight/4);
        win.drawString(":)))) You Win!!!!!", canvasWidth/8, canvasHeight/2);

        // FIXME draw the win screen here
    }

    /* Paint the screen when the player has lost
     *
     * @param g The Graphics for the JPanel
     */
    private void paintLoseScreen(Graphics g) {
        Font tr = new Font("Dialog", Font.PLAIN, 20);
        Font trb = new Font("Dialog", Font.BOLD, 48);
        g.setColor(Color.yellow);
        g.fillOval(0, 0, canvasWidth, canvasHeight);
        g.setColor(Color.BLACK);
        g.setFont(trb);
        g.drawString("CONGRATULATIONS!!", 8, 180);
        g.setFont(tr);
        g.drawString("You lost :(", 250, 220);
        // FIXME draw the game over screen here
    }

    /*private void paintAlien (Graphics g){
        g.fillRect(Alien);
        g.setColor(Color.BLACK);
    }*/

    public static void main(String[] args) {
        SpaceInvaders invaders = new SpaceInvaders();
        EventQueue.invokeLater(invaders);
    }
}
