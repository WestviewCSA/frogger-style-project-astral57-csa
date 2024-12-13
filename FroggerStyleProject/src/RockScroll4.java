import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class RockScroll4{
	
	
	
	private Image forward; //backward, left, right; 	
	private AffineTransform tx;
	
	int dir = 0; 					//0-forward, 1-backward, 2-left, 3-right
	int width, height;
	int x, y;						//position of the object
	int vx, vy;						//movement variables
	double scaleWidth = .2;		//change to scale image
	double scaleHeight = .2; 		//change to scale image

	public RockScroll4() {
		forward 	= getImage("/imgs/"+"Sunken1.png"); //load the image for Tree
		

		//width and height for hitbox
		width = 50;
		height = 50;
		//used for placement on the j frame
		x = 200; //off screen for now
		y = 410;
		//if movement will not be hopping
		vx = -1;
		vy = 0;
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 				//initialize the location of the image
									//use your variables
		
		
		
		
	}
	//2nd constructor allows setting x and y
	public RockScroll4(int x, int y) {
		//call the default constructor
		this(); //invokes default constructor
		//do the specific task for THIS constructor
		this.x = x;
		this.y = y;
	}

	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		//update x and y if using vx and vy variables
		x+=vx;
		y+=vy;	
		
		//infinite scrolling - teleport to other side
		
		if(x < -200) {
			x = 700;
		}
		
		init(x,y);
		
		g2.drawImage(forward, tx, null);
			
		//draw hitbox based on x, y, width, height
//		if(Frame.debugging) {
//			//draw hitbox only if debugging
//			g.setColor(Color.green);
//			g.drawRect(x, y, width, height);
//		}
		
		}

	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = RockScroll4.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}
