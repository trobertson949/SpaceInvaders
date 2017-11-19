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
    public int num_aliens_per_list;
    public int alienbullets;

    private ArrayList<Alien> joshi;
    private ArrayList<Alien> conner;
    private ArrayList<Alien> goldhammer;

    private ArrayList<Projectile> shooty;
    private ArrayList<alienProjectile> splat;
    private ArrayList<GraphicsObject> objects;

    public int random_alien;




    // FIXME list your game objects here

    public SpaceInvaders() {
        // fix the window size and background color
        this.canvasWidth = 600;
        this.canvasHeight = 400;
        this.backgroundColor = Color.BLACK;
        setPreferredSize(new Dimension(this.canvasWidth, this.canvasHeight));

        // set the drawing timer
        this.timer = new Timer(msPerFrame, this);

        this.num_aliens_per_list = 10;
        this.alienbullets = 10;

        this.joshi = new ArrayList<>();
        int num_aliens = 0;
        int i = 30;
        while (num_aliens < num_aliens_per_list) {
            joshi.add(new Alien( i, 0, Color.green));
            num_aliens += 1;
            i += 60;
        }
        joshi.add(new Alien (0, 0, Color.BLACK));

        this.conner = new ArrayList<>();
        int alienz = 0;
        int spaceinbetween = 30;
        while (alienz < num_aliens_per_list) {
            conner.add(new Alien(spaceinbetween, 0, Color.BLUE));
            alienz += 1;
            spaceinbetween += 60;
        }
        conner.add(new Alien (0, 0, Color.BLACK));

        this.goldhammer = new ArrayList<>();
        int poop = 0;
        int ploop = 30;
        while (poop < num_aliens_per_list) {
            goldhammer.add(new Alien(ploop, 0, Color.pink));
            poop += 1;
            ploop += 60;
        }
        goldhammer.add(new Alien (0, 0, Color.BLACK));

        this.objects = new ArrayList<GraphicsObject>();
        this.shooty = new ArrayList<Projectile>();
        this.splat = new ArrayList<alienProjectile>();

        this.me = new Player(300, 375);
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
            // FIXME what happens when pace bar is pressed
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

        if (joshi.get(0).y + this.alien_height > 120) {
            for (Alien w : this.conner){
                w.update(this.canvasWidth, this.canvasHeight, frame);
            }
        }
        if (conner.get(0).y + this.alien_height >120) {
            for (Alien w : this.goldhammer) {
                w.update(this.canvasWidth, this.canvasHeight, frame);
            }
        }

        for (GraphicsObject obj : this.objects) {
            obj.update(this.canvasWidth, this.canvasHeight, frame);
        }

        for (alienProjectile w: this.splat) {
            w.update(this.canvasWidth, this.canvasHeight, frame);
        }

        if (joshi.get(0).y + this.alien_height == 60 && joshi.get(0).x < 1) {
            int attack = 0;
            while (attack < 4) {
                random_alien = (int) (Math.random() * (joshi.size()));
                alienProjectile new_splat = new alienProjectile((int) (joshi.get(random_alien).x), (int) (joshi.get(random_alien).y));
                splat.add(new_splat);
                attack += 1;
            }
        }

        if (conner.get(0).y + this.alien_height == 60 && conner.get(0).x < 1) {
            int attack = 0;
            while (attack < 4) {
                random_alien = (int) (Math.random() * (conner.size()));
                alienProjectile new_splat = new alienProjectile((int) (conner.get(random_alien).x), (int)(conner.get(random_alien).y));
                splat.add(new_splat);
                attack += 1;
            }
        }

        if (goldhammer.get(0).y + this.alien_height == 60 && goldhammer.get(0).x < 1) {
            int attack = 0;
            while (attack < 4) {
                random_alien = (int) (Math.random() * (goldhammer.size()));
                alienProjectile new_splat = new alienProjectile((int)(goldhammer.get(random_alien).x), (int) (goldhammer.get(random_alien).y));
                splat.add(new_splat);
                attack += 1;
            }
        }


        if (!joshi.isEmpty() && joshi!=null) {
            Projectile_touching_Alien(joshi, shooty);
        }

        // FIXME update game objects here
    }

    /* Check if the player has lost the game
     *
     * @returns  true if the player has lost, false otherwise
     */
    private boolean hasLostGame() {
        for (int i = 0; i < splat.size(); i++) {
            if (this.splat.get(i).y + 15 >= this.me.y && this.splat.get(i).y <= this.me.y + 25
                    && this.splat.get(i).x >= this.me.x && this.splat.get(i).x <= this.me.x + 20) {
                alienbullets = 1;
            }
        }

        for (int i = 1; i < joshi.size(); i++) {
                if (this.joshi.get(i).y + 30 >= this.me.y && this.joshi.get(i).y <= this.me.y + 25
                        && this.joshi.get(i).x + 30 >= this.me.x && this.joshi.get(i).x <= this.me.x + 20) {
                    alienbullets = 1;
                }
            }

        for (int i = 1; i < conner.size(); i++) {
            if (this.conner.get(i).y + 30 >= this.me.y && this.conner.get(i).y <= this.me.y + 25
                    && this.conner.get(i).x + 30 >= this.me.x && this.conner.get(i).x <= this.me.x + 20) {
                alienbullets = 1;
            }
        }

        for (int i = 1; i < goldhammer.size(); i++) {
            if (this.goldhammer.get(i).y + 30 >= this.me.y && this.goldhammer.get(i).y <= this.me.y + 25
                    && this.goldhammer.get(i).x + 30 >= this.me.x && this.goldhammer.get(i).x <= this.me.x + 20) {
                alienbullets = 1;
            }
        }

        if (alienbullets == 1) {
            return true;
        }

        return false;
        // FIXME delete this when ready
    }


    /* Check if the player has won the game
     *
     * @returns  true if the player has won, false otherwise
     */
    private boolean hasWonGame() {
        int joshi_list_length = this.joshi.size();
        int conner_list_length = this.conner.size();
        int goldhammer_list_length = this.goldhammer.size();
        if ((joshi_list_length == 1) && (conner_list_length == 1) && (goldhammer_list_length == 1)) {
            return true;
        } else {
            return false; // FIXME delete this when ready
        }
    }

    private void Projectile_touching_Alien(ArrayList<Alien> joshi, ArrayList<Projectile> shooty) {

        for (int i = 0; i < this.joshi.size() - 1; i++) {
            for (int j = 0; j < this.shooty.size() - 1; j++) {
                if ((this.shooty.get(j).y >= this.joshi.get(i).y) && (this.shooty.get(j).y <= this.joshi.get(i).y + 30) &&
                        (this.shooty.get(j).x >= this.joshi.get(i).x) && (this.shooty.get(j).x <= this.joshi.get(i).x + 30)) {
                    Alien gone_joshi = this.joshi.get(i);
                    this.joshi.remove(gone_joshi);
                    Projectile gone_shooty = this.shooty.get(j);
                    this.shooty.remove(gone_shooty);
                }
            }
        }

        if (this.joshi.get(0).y + this.alien_height > 120) {
            for (int i = 0; i < this.conner.size() - 1; i++) {
                for (int j = 0; j < this.shooty.size() - 1; j++) {
                    if ((this.shooty.get(j).y >= this.conner.get(i).y) && (this.shooty.get(j).y <= this.conner.get(i).y + 30) &&
                            (this.shooty.get(j).x >= this.conner.get(i).x) && (this.shooty.get(j).x <= this.conner.get(i).x + 30)) {
                        Alien gone_conner = this.conner.get(i);
                        this.conner.remove(gone_conner);
                        Projectile gone_shooty = this.shooty.get(j);
                        this.shooty.remove(gone_shooty);
                    }
                }
            }
        }
        if (this.conner.get(0).y + this.alien_height > 120) {
            for (int i = 0; i < this.goldhammer.size() - 1; i++) {
                for (int j = 0; j < this.shooty.size() - 1; j++) {
                    if ((this.shooty.get(j).y >= this.goldhammer.get(i).y) && (this.shooty.get(j).y <= this.goldhammer.get(i).y + 30) &&
                            (this.shooty.get(j).x >= this.goldhammer.get(i).x) && (this.shooty.get(j).x) <= this.goldhammer.get(i).x + 30) {
                        Alien gone_goldhammer = this.goldhammer.get(i);
                        this.goldhammer.remove(gone_goldhammer);
                        Projectile gone_shooty = this.shooty.get(j);
                        this.shooty.remove(gone_shooty);
                    }
                }
            }
        }
    }

//        if (this.joshi.size() == 1) {
//            for (int j = 0; j < this.shooty.size(); j++) {
//                if ((this.shooty.get(j).y >= this.goldhammer.get(0).y) && (this.shooty.get(j).y <= this.goldhammer.get(0).y + 30) &&
//                        (this.shooty.get(j).x >= this.goldhammer.get(0).x) && (this.shooty.get(j).x) <= this.goldhammer.get(0).x + 30) {
//                    Alien gone_goldhammer = this.goldhammer.get(0);
//                    this.goldhammer.remove(gone_goldhammer);
//                    Projectile gone_shooty = this.shooty.get(j);
//                    this.shooty.remove(gone_shooty);
//                }
//            }
//        }


    /* Paint the screen during normal gameplay
     *
     * @param g The Graphics for the JPanel
     */

    private void paintGameScreen(Graphics g) {

        this.me.draw(g);

        if (!joshi.isEmpty()) {
            for (Alien s : joshi) {
                s.draw(g);
            }
        }

        for (Projectile w : shooty) {
            w.draw(g);
        }


        if (joshi.get(0).y + this.alien_height > 120) {
            for (Alien w : conner) {
                w.draw(g);
            }

        }


        if (conner.get(0).y + this.alien_height > 120) {
                for (Alien t : goldhammer) {
                    t.draw(g);
                }
            }

        for (alienProjectile s : splat) {
            s.draw(g);
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
