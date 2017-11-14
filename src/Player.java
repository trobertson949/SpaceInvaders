import javax.swing.*;
import java.awt.*;
import java.lang.Math;

public class Player extends GraphicsObject {
    Color player_color;
    int player_width;
    int player_height;

    public Player(double x, double y) {
        super(x, y);
        this.player_width = 20;
        this.player_height = 25;
        this.player_color = Color.RED;
    }


    public void draw(Graphics g) {
        // change the color of the pen
        g.setColor(this.player_color);
        // draw the rectangle
        g.fillRect((int) Math.round(this.x), (int) Math.round(this.y), player_width, player_height);
    }


    public void update(int pic_width, int pic_height, int frame) {
        // this will make rectangles bounce off the side of the window

        if (this.x < 0 || this.x + this.player_width > pic_width) {
            this.speed_x = -this.speed_x;
        }
        if (this.y < 0 || this.y + this.player_height > pic_height) {
                this.speed_y = -this.speed_y;
            }

            // let the superclass' update function handle the actual changes to x and y
            super.update(pic_width, pic_height, frame);

        }

    }
