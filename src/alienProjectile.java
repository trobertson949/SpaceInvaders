import java.awt.Color;
import java.awt.Graphics;
import java.lang.Math;

public class alienProjectile extends GraphicsObject {
    Color alien_projectile_color;
    int alien_projectile_height;
    int alien_projectile_width;

    public alienProjectile(double x, double y) {
        super(x, y);
        speed_y = 2;
        alien_projectile_color = Color.CYAN;
        alien_projectile_height = 15;
        alien_projectile_width = 2;
    }

    public void draw(Graphics g) {
        // change the color of the pen
        g.setColor(this.alien_projectile_color);
        // draw the rectangle
        g.fillRect((int)Math.round(this.x), (int)Math.round(this.y) , alien_projectile_width, alien_projectile_height);
    }

    public void update(int pic_width, int pic_height, int frame) {

        // let the superclass' update function handle the actual changes to x and y
        super.update(pic_width, pic_height, frame);
    }
}
