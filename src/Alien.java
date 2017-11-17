import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.Math;

public class Alien extends GraphicsObject {
    Color alien_color;
    int alien_width;
    int alien_height;


    public Alien (double x, double y, Color alien_color) {
        super(x, y);
        speed_x = 3;
        this.alien_color = alien_color;
        alien_width = 30;
        alien_height = 30;
    }


    public void draw(Graphics g) {
        // change the color of the pen
        g.setColor(this.alien_color);
        // draw the rectangle
        g.fillRect((int)Math.round(this.x), (int)Math.round(this.y) , this.alien_width, this.alien_height);
    }


    public void update(int pic_width, int pic_height, int frame) {
        if (this.x < 0 || this.x + this.alien_width > pic_width) {
            this.speed_x = -this.speed_x;
            this.y += 60;
        }

        // let the superclass' update function handle the actual changes to x and y
        super.update(pic_width, pic_height, frame);
    }

}

