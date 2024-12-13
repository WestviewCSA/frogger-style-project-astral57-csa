import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Aquila{
	
	
	
	private Image forward; //backward, left, right; 	
	private AffineTransform tx;
	
	int dir = 0; 					//0-forward, 1-backward, 2-left, 3-right
	int width, height;
	int x, y;						//position of the object
	int vx, vy;						//movement variables
	double scaleWidth = 1;		//change to scale image
	double scaleHeight = 1; 		//change to scale image

	public Aquila() {
		forward 	= getImage("/imgs/"+"Aquila.png"); //load the image for Tree
		

		//width and height for hitbox
		width = 600;
		height = 600;
		//used for placement on the j frame
		x = 0;
		y = 0;
		//if movement will not be hopping
		vx = 0;
		vy = 0;
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 				//initialize the location of the image
									//use your variables
		
		
		
		
	}
	//2nd constructor allows setting x and y
	public Aquila(int x, int y) {
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
			URL imageURL = Aquila.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}
