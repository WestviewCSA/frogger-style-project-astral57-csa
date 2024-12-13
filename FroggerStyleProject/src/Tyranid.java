import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Tyranid{
	
	
	
	private Image forward; //backward, left, right; 	
	private AffineTransform tx;
	
	int dir = 3; 					//0-forward, 1-backward, 2-left, 3-right
	int width, height;
	int x, y;						//position of the object
	int vx, vy;						//movement variables
	double scaleWidth = .365;		//change to scale image
	double scaleHeight = .365; 		//change to scale image

	public Tyranid() {
		forward 	= getImage("/imgs/"+"Tyranid.png"); //load the image for Tree
		

		//width and height for hitbox
		width = 65;
		height = 53;
		//used for placement on the j frame
		x = -width; //off screen for now
		y = 410;
		//if movement will not be hopping
		vx = 6;
		vy = 0;
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 				//initialize the location of the image
									//use your variables
		
	
		
	}
		
		
	
	//2nd constructor allows setting x and y
	public Tyranid(int x, int y) {
		//call the default constructor
		this(); //invokes default constructor
		//do the specific task for THIS constructor
		this.x = x;
		this.y = y;
	}
	
	public boolean collided(SpaceMarine character) {
		
		Rectangle main = new Rectangle(
				character.getX(),
				character.getY(),
				character.getWidth(),
				character.getHeight());
		
		Rectangle thisObject = new Rectangle(x, y+5, width, height);
		
		return main.intersects(thisObject);
	
	}
	
	
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		//update x and y if using vx and vy variables
		x+=vx;
		y+=vy;	
		
		//infinite scrolling - teleport to other side
		
		if(x > 600) {
			x = -200;
		}
		
		init(x,y);
		
		g2.drawImage(forward, tx, null);
			
		//draw hitbox based on x, y, width, height
		if(Frame.debugging) {
			//draw hitbox only if debugging
			g.setColor(Color.green);
			g.drawRect(x, y+5, width, height);
		}
		
		}

	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Tyranid.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}