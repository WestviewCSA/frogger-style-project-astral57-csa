import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {
	
	public static boolean debugging = false;
	
	//Timer related variables
	int waveTimer = 5; //each wave of enemies is 20s
	long ellapseTime = 0;
	Font timeFont = new Font("Courier", Font.BOLD, 70);
	int level = 0;
	
	
	Font myFont = new Font("Courier", Font.BOLD, 40);
	SimpleAudioPlayer backgroundMusic = new SimpleAudioPlayer("scifi.wav", false);
	SimpleAudioPlayer laser = new SimpleAudioPlayer("laser.wav", false);
	SimpleAudioPlayer hit = new SimpleAudioPlayer("hit.wav", false);
//	Music soundBang = new Music("bang.wav", false);
//	Music soundHaha = new Music("haha.wav", false);
	
	SpaceMarine spaceMarine = new SpaceMarine();
	SpaceMarine spaceMarine2 = new SpaceMarine(100,200);
	Background bgr = new Background ();
	TyranidDeath tyrDeath = new TyranidDeath(0,-30);
	TSonDeath sonDeath = new TSonDeath(0,-30);
	LavaDeath lavaDeath = new LavaDeath(0,-30);
	Aquila aquila = new Aquila(0,-30);
	//a row of space marine scrolling objects
	Tyranid[] row1 = new Tyranid[6];
	ArrayList<Tyranid>row1List = new ArrayList<Tyranid>();
	ArrayList<LifeImage>livesList = new ArrayList<LifeImage>();
	TSonScroll[] row2 = new TSonScroll[10];
	RockScroll[] row3 = new RockScroll[10];
	RockScroll2[] row4 = new RockScroll2[3];
	RockScroll3[] row5 = new RockScroll3[10];
	RockScroll4[] row6 = new RockScroll4[3];
	RockScroll5[] row7 = new RockScroll5[10];
	Laser[] row8 = new Laser[1];
	//frame width/height
	int width = 600;
	int height = 600;	
	
	public Font newFont = new Font("Serif",Font.BOLD,12);
	//scoretrack
	int score = 0;
	int lives = 1;
	int tLives = 1;
	int sonLives = 1;
	int lavaLives = 1;
	int laserLives = 6;

	public void paint(Graphics g) {
		super.paintComponent(g);
		bgr.paint(g);
		//paint the other objects on the screen
		
		//spaceMarine2.paint(g);
		boolean l = false;
//		boolean tyrCollide = false;
//		boolean tSonCollide = false;
//		boolean lava = false;
		//collision
		
		for(LifeImage obj : livesList) {
			obj.paint(g);
		}
		
		for(Laser obj : row8) {
			//invoke collided method for the class
			//pass the main character as the argument
			if(obj.collided(spaceMarine)) {
				livesLost();
				laserLives--;
				hit.play();
			}
		}
		
		for(Tyranid obj : row1) {
			//invoke collided method for the class
			//pass the main character as the argument
			if(obj.collided(spaceMarine)) {
				
				if(score > 0) {
					System.out.println("Geneseed destroyed by the hive!");
					score = 0;
				}
				spaceMarine.y = 510;
				spaceMarine.x = 600/2 - spaceMarine.width/2;
				tLives--;
				lives--;
				hit.play();
			}
		}
		
		for(TSonScroll obj : row2) {
			//invoke collided method for the class
			//pass the main character as the argument
			if(obj.collided(spaceMarine)) {
				if(score > 0) {
					System.out.println("Geneseed stolen by the forces of chaos!");
					score = 0;
				}
				spaceMarine.y = 510;
				spaceMarine.x = 600/2 - spaceMarine.width/2;
				sonLives--;
				lives--;
				hit.play();
			}
		}
		
		for(RockScroll obj : row3) {
			//invoke collided method for the class
			//pass the main character as the argument
			if(obj.collided(spaceMarine)) {
				l = true;
				//System.out.println("row1");
				spaceMarine.vx = 2;
			}
		}
		
		for(RockScroll2 obj : row4) {
			//invoke collided method for the class
			//pass the main character as the argument
			if(obj.collided(spaceMarine)) {
				l = true;
				//System.out.println("row1 - 2");
				spaceMarine.vx = 2;
			}
		}
		
		for(RockScroll3 obj : row5) {
			//invoke collided method for the class
			//pass the main character as the argument
			if(obj.collided(spaceMarine)) {
				l = true;
				//System.out.println("row2");
				spaceMarine.vx = -3;
			}
		}
		
		for(RockScroll5 obj : row7) {
			//invoke collided method for the class
			//pass the main character as the argument
			if(obj.collided(spaceMarine)) {
				l = true;
				//System.out.println("row3");
				spaceMarine.vx = 5;
			}
		}
		
		
		
		if(spaceMarine.getY() < 230 && spaceMarine.getY()>= 110) {
			if(l == false) {
				lavaLives--;
				lives--;
				spaceMarine.y = 510;
				spaceMarine.x = 600/2 - spaceMarine.width/2;
				score = 0;
				
				if(score > 0) {
					System.out.println("Geneseed lost!");
				}
			}
		}
		
		//score count / reset when win
		if(spaceMarine.getY() < 60) {
			spaceMarine.vx = 0;
			System.out.println("Geneseed recovered!");
			score++;
			spaceMarine.y = 510;
			spaceMarine.x = 600/2 - spaceMarine.width/2;
			spaceMarine.vy = 0;
		}
		
		
		
		//scoretrack
		g.setFont(newFont);
		g.setColor(Color.lightGray);
		g.drawString("Geneseed recovered: "+ score, 450, 20);
		//g.drawString("Lives: " + lives, 10, 35);
		
		//paint the row1 objects paint on the screen
		
		
		
		for(Tyranid obj : row1) {
			obj.paint(g);
		}
		for(Tyranid obj : row1List) {
			obj.paint(g);
		}
		for(TSonScroll obj : row2) {
			obj.paint(g);
		}
		for(RockScroll2 obj : row4) {
			obj.paint(g);
		}
		for(RockScroll obj : row3) {
			obj.paint(g);
		}
		for(RockScroll4 obj : row6) {
			obj.paint(g);
		}
		for(RockScroll3 obj : row5) {
			obj.paint(g);
		}
		for(RockScroll5 obj : row7) {
			obj.paint(g);
		}
		for(Laser obj : row8) {
			obj.paint(g);
		}
		spaceMarine.paint(g);
		
		
		//death / win screens
		
		if(tLives <= 0 || laserLives <= 0) {
			tyrDeath.paint(g);
		}
		if(sonLives <= 0) {
			sonDeath.paint(g);
		}
		if(lavaLives <= 0) {
			lavaDeath.paint(g);
		}
		if(score == 3) {
			aquila.paint(g);
			System.out.println("FOR THE EMPEROR!!!");
			
		}
	}
	//lose lives - remove from ArrayList
	
	public void livesLost() {
		if(livesList.size()>0) {
			livesList.remove(livesList.size()-1);
		}
	}
	//reset lives
	public void livesReset() {
		for(int i = 1; i < 7; i++) {
			this.livesList.add(new LifeImage(i*30, 10));
		}
	}
	
	
	public static void main(String[] arg) {
		Frame f = new Frame();
		
	}
	
	public Frame() {
		JFrame f = new JFrame("FrogHammer");
		f.setSize(new Dimension(width, height));
		f.setBackground(Color.white);
		f.add(this);
		f.setResizable(false);
 		f.addMouseListener(this);
		f.addKeyListener(this);
	
		//backgroundMusic.play();
		
		/*
		 * setup any 1d array here! - create the objects that go in them
		 */
		//traverse array
		
		for(int i = 1; i < 7; i++) {
			this.livesList.add(new LifeImage(i*30, 10));
		}
		
		
		for(int i = 0; i < row1.length; i++) {
			row1[i] = new Tyranid(i*200 , 430); 
		}
		//practice for arraylist
//		for(int i = 0; i < 10; i++) {
//			//run the body 10x
//			this.row1List.add(new Tyranid(i*20, 250));
//		}
		
		for(int i = 0; i < row2.length; i++) {
			row2[i] = new TSonScroll(i*200 , 340); 
		}
		
		for(int i = 0; i < row3.length; i++) {
			row3[i] = new RockScroll(i*200 , 210); 
		}
		
		for(int i = 0; i < row4.length; i++) {
			row4[i] = new RockScroll2(i*200 , 210); 
		}
		
		for(int i = 0; i < row5.length; i++) {
			row5[i] = new RockScroll3(i*200 , 160); 
		}
		
		for(int i = 0; i < row6.length; i++) {
			row6[i] = new RockScroll4(i*200 , 160); 
		}
		
		for(int i = 0; i < row7.length; i++) {
			row7[i] = new RockScroll5(i*200 , 110); 
		}
		for(int i = 0; i < row8.length; i++) {
			row8[i] = new Laser(i*200 , -100);
		}
		
		
		
		//the cursor image must be outside of the src folder
		//you will need to import a couple of classes to make it fully 
		//functional! use eclipse quick-fixes
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("torch.png").getImage(),
				new Point(0,0),"custom cursor"));	
		
		
		Timer t = new Timer(16, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	
	
	//key inputs
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent m) {
		
	
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println(arg0.getKeyCode());
		if(arg0.getKeyCode()==83) {
			//move char up
			spaceMarine.move2(0);
		}
		else if(arg0.getKeyCode()==87) {
			//move down
			spaceMarine.move2(1);
		}
		else if(arg0.getKeyCode()==65) {
			//move left
			spaceMarine.move2(2);
		}
		else if(arg0.getKeyCode()==68) {
			//move right
			spaceMarine.move2(3);
		}
		else if(arg0.getKeyCode()==82) {
			lives = 1;
			tLives = 1;
			sonLives = 1;
			lavaLives = 1;
			laserLives = 6;
			livesReset();
			spaceMarine.y = 510;
			spaceMarine.x = 600/2 - spaceMarine.width/2;
			score = 0;
			System.out.println("Only in death does duty end");
			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println(arg0.getKeyCode());
		if(arg0.getKeyCode()==83) {
			//move char up
			spaceMarine.move2(4);
		}
		else if(arg0.getKeyCode()==87) {
			//move down
			spaceMarine.move2(4);
		}
		else if(arg0.getKeyCode()==65) {
			//move left
			spaceMarine.move2(5);
		}
		else if(arg0.getKeyCode()==68) {
			//move right
			spaceMarine.move2(5);
		}
		
	}
	

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
