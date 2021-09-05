// connect 4 game in java
import java.awt.*;
import java.awt.Image;
import java.awt.image.*;
import java.applet.*;
public class con4 extends Applet
{
	// variables
	private Rectangle dropMarker1,dropMarker2,dropMarker3,dropMarker4,dropMarker5,dropMarker6, dropMarker7, ywindet, rwindet;
	private boolean myTurn;
	private boolean ywins, rwins;
	private boolean [] swp;
	private int [] table;
	// setup
	public void init()
	{
		// init
		ywins = rwins = false;
		// init
		swp = new boolean[8];
		for (int i = 0; i < 7; i++)
			swp[i] = false;
		// init table
		table = new int[50];
		for (int xr = 0; xr < 7; xr++)
			for (int yr = 0; yr < 6; yr++)
				table[xr * 6 + yr] = 0;
		// player first
		myTurn = true;
		// mouse event listeners
		dropMarker1 = new Rectangle(0,0,100,100);
		dropMarker2 = new Rectangle(100,0,100,100);
		dropMarker3 = new Rectangle(200,0,100,100);
		dropMarker4 = new Rectangle(300,0,100,100);
		dropMarker5 = new Rectangle(400,0,100,100);
		dropMarker6 = new Rectangle(500,0,100,100);
		dropMarker7 = new Rectangle(600,0,100,100);
		ywindet = new Rectangle(0,0,700,700);
		rwindet = new Rectangle(0,0,700,700);
	};
	public int getPiece(int xcoord, int ycoord)
	{
		// get piece in specified coordinate
		if ((xcoord < 0) || (ycoord < 0)) return -1;
		if ((xcoord < 7) && (ycoord < 6))
			return table[xcoord * 6 + ycoord];
		else
			return -1;
	}
	public void setPiece(int xcoord, int ycoord, int player)
	{
		// set piece in specified coordinate
		if ((xcoord < 7) && (ycoord < 6))
			table[xcoord * 6 + ycoord] = player;
	}
	public void checkWin(int xr, int yr)
	{
		// checks for the winner
		int count = 0, pc = 0;
		int [] querry = new int[9];
		boolean [] dirblock = new boolean[9];
		for (int i = 0; i < 9; i++)
			dirblock[i] = false;
		if ((xr > 7) && (yr > 6))
			return;
		if (getPiece(xr,yr) > 0)
		{
			pc = getPiece(xr,yr);
			for (int con = 1; con <= 3; con++)
			{
				// check 3 layers
				for (int dir = 0; dir < 8; dir ++)
				{
					// check if direction is not blocked
					if (dirblock[dir]) continue;
					// check all 8 directions
					switch(dir)
					{
						case 0:
						{
							// check N
							if ((yr + con) >= 6) break;
							if (getPiece(xr,yr + con) != pc) dirblock[0] = true;
							querry[0] += (getPiece(xr,yr + con) == pc)? 1:0;
							break;
						}
						case 1:
						{
							// check NE
							if (((xr + con) >= 7) || ((yr + con) >= 6)) break;
							if (getPiece(xr + con,yr + con) != pc) dirblock[1] = true;
							querry[1] += (getPiece(xr + con,yr + con) == pc)? 1:0;
							break;
						}
						case 2:
						{
							// check E
							if (((xr + con) >= 7)) break;
							if (getPiece(xr + con,yr) != pc) dirblock[2] = true;
							querry[2] += (getPiece(xr + con,yr) == pc)? 1:0;
							break;
						}
						case 3:
						{
							// check SE
							if (((xr + con) >= 7) || ((yr - con) < 0)) break;
							if (getPiece(xr + con,yr - con) != pc) dirblock[3] = true;
							querry[3] += (getPiece(xr + con,yr - con) == pc)? 1:0;
							break;
						}
						case 4:
						{
							// check S
							if (((yr - con) < 0)) break;
							if (getPiece(xr,yr - con) != pc) dirblock[4] = true;
							querry[4] += (getPiece(xr,yr - con) == pc)? 1:0;
							break;
						}
						case 5:
						{
							// check SW
							if (((xr - con) < 0) || ((yr - con) < 0)) break;
							if (getPiece(xr - con,yr - con) != pc) dirblock[5] = true;
							querry[5] += (getPiece(xr - con,yr - con) == pc)? 1:0;
							break;
						}
						case 6:
						{
							// check W
							if (((xr - con) < 0)) break;
							if (getPiece(xr - con,yr) != pc) dirblock[6] = true;
							querry[6] += (getPiece(xr - con,yr) == pc)? 1:0;
							break;
						}
						case 7:
						{
							// check NW
							if (((xr - con) < 0) || ((yr + con) >= 6)) break;
							if (getPiece(xr - con,yr + con) != pc) dirblock[7] = true;
							querry[7] += (getPiece(xr - con,yr + con) == pc)? 1:0;
							break;
						}
					}
				}
			}
			// recurse through tallies
			int [] totalStat = new int[5];
			totalStat[0] = querry[0] + querry[4];
			totalStat[1] = querry[1] + querry[5];
			totalStat[2] = querry[2] + querry[6];
			totalStat[3] = querry[3] + querry[7];
			for (int re = 0; re < 4; re++)
			{
				if (totalStat[re] >= 3)
				{
					// winner
					if (pc == 1)
						ywins = true;
					else
						rwins = true;
				}
			}
		}
	}
	// draw things
	public void paint(Graphics main)
	{
		BufferedImage buff = new BufferedImage(700,700, BufferedImage.TYPE_INT_RGB);
		Graphics g = buff.getGraphics();
		g.fillRect(0,0, 700,100);
		// create drop markers
		for (int i = 0; i < 7; i++)
		{
			if (myTurn)
				g.setColor(Color.yellow); // set color
			else
				g.setColor(Color.red);
			g.fillOval((100 * i) + 5, 0, 90,90);
			g.setColor(Color.black); // set color
			g.drawOval((100 * i) + 5,0, 90,90);
		};
		// create the table
		for (int i = 0; i < 7; i++)
			for (int j = 1; j <= 6; j++)
			{
				g.setColor(Color.blue);
				g.fillRect((100 * i),(100 * j),100,100);
				g.setColor(Color.black);
				g.drawRect((100 * i),(100 * j),100,100);
				g.setColor(Color.white);
				g.fillOval((100 * i) + 5,(100 * j) + 5,90,90);
				g.setColor(Color.black);
				g.drawOval((100 * i) + 5,(100 * j) + 5,90,90);
			}
		// redraw pieces
		for (int xr = 0; xr < 7; xr++)
			for (int yr = 0; yr < 6; yr++)
				if (getPiece(xr,yr) != 0)
					drawPiece(g,xr,yr);
		// draw new pieces
		for (int x = 0; x < 7; x++)
		{
			if (swp[x] == true)
			{
				repaint();
				dropPiece(g, x);
				swp[x] = false;
			}
		}
		// draw win frame
		if (ywins)
		{
			// yellow wins
			g.setColor(Color.yellow);
			g.fillRect(0,0,700,700); // pseudo clear screen
			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.PLAIN, 40));
			g.drawString("Yellow Wins!!!", 50,50);
		} else if (rwins)
		{
			// red wins
			g.setColor(Color.red);
			g.fillRect(0,0,700,700); // pseudo clear screen
			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.PLAIN, 40));
			g.drawString("Red Wins!!!", 50,50);
		}
		// draw everything
		Image frame = (Image)buff;
		main.drawImage(frame, 0,0, this);
	};
	public void resetGame()
	{
		// like what it says...
		// reset who won
		ywins = rwins = false;
		// reset piece check
		swp = new boolean[8];
		for (int i = 0; i < 7; i++)
			swp[i] = false;
		// reset table
		table = new int[50];
		for (int xr = 0; xr < 7; xr++)
			for (int yr = 0; yr < 6; yr++)
				table[xr * 6 + yr] = 0;
		// make yellow player first
		myTurn = true;
	}
	public void drawPiece(Graphics g, int xcoord, int ycoord)
	{
		// draws piece
		if (getPiece(xcoord, ycoord) == 1)
			g.setColor(Color.yellow);
		else
			g.setColor(Color.red);
		g.fillOval((100 * (xcoord)) + 5, (600 - (100 * ycoord)) + 5, 90,90);
		g.setColor(Color.black);
		g.drawOval((100 * (xcoord)) + 5, (600 - (100 * ycoord)) + 5, 90,90);
	}
	public void dropPiece(Graphics g, int xc)
	{
		// draws the piece and updates the logic
		for (int i = 0; i < 6; i++)
		{
			if (getPiece(xc, i) == 0)
			{
				if (myTurn)
					setPiece(xc,i,1);
				else
					setPiece(xc,i,2);
				drawPiece(g, xc, i);
				checkWin(xc,i);
				break;
			}
		}
		if (myTurn)
			myTurn = false;
		else
			myTurn = true;
	}
	public boolean mouseDown(Event e, int x, int y)
	{
		if (dropMarker1.inside(x,y))
			swp[0] = true;
		if (dropMarker2.inside(x,y))
			swp[1] = true;
		if (dropMarker3.inside(x,y))
			swp[2] = true;
		if (dropMarker4.inside(x,y))
			swp[3] = true;
		if (dropMarker5.inside(x,y))
			swp[4] = true;
		if (dropMarker6.inside(x,y))
			swp[5] = true;
		if (dropMarker7.inside(x,y))
			swp[6] = true;
		if (ywins && ywindet.inside(x,y))
			resetGame();
		if (rwins && rwindet.inside(x,y))
			resetGame();
		repaint();
		return true;
	}
};