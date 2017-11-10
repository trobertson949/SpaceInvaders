import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Player extends GraphicsObject {
    int width;
    int height;

    public Player(int x, int y) {
        super(x, y);        }

        public void draw(Graphics g) {
            // change the color of the pen
            g.setColor(Color.red);
            // draw the rectangle
            g.fillRect(this.x, this.y, 10, 20);
        }

        /* Update the state of the rectangle
         *
         * @param pic_width  The width of the canvas
         * @param pic_height The height of the canvas
         * @param frame      The number of frames since the start of the animation
         */
        @Override
        public void update(int pic_width, int pic_height, int frame) {
            // this will make rectangles bounce off the side of the window
            if (this.x < 0 || this.x + this.width > pic_width) {
                this.speed_x = -this.speed_x;
            }
            if (this.y < 0 || this.y + this.height > pic_height) {
                this.speed_y = -this.speed_y;
            }

            // let the superclass' update function handle the actual changes to x and y
            super.update(pic_width, pic_height, frame);
        }

    }

}
