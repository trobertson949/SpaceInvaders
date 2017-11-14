import java.awt.Color;
import java.awt.Graphics;
import java.lang.Math;

public class Projectile extends GraphicsObject {
    Color projectile_color;
    int projectile_height;
    int projectile_width;

    public Projectile(double x, double y) {
        super(x, y);
        projectile_color = Color.yellow;
        projectile_height = 15;
        projectile_width = 2;

    }

    public void draw(Graphics g) {
        // change the color of the pen
        g.setColor(this.projectile_color);
        // draw the rectangle
        g.fillRect((int)Math.round(this.x), (int)Math.round(this.y) , projectile_width, projectile_height);
    }

    public void update(int pic_width, int pic_height, int frame) {
        // let the superclass' update function handle the actual changes to x and y
        super.update(pic_width, pic_height, frame);
    }
}

