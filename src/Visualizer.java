import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;


public class Visualizer {
	public static float pointsize = 8.0f;
	public static float speed = 0.001f;
	public static boolean skipAnimation = false;
	private List<Vertex> vertices;
	private List<Path> allPaths;
	private List<Integer> path;
	private long lastFrame;
	public Visualizer(List<Vertex> vertices, String path)
	{
		this.vertices = vertices;
		String[] split = path.split("->");
		this.path = new ArrayList<Integer>(split.length);
		
		for(int i = 0; i < split.length; i++)
		{
			this.path.add(Integer.parseInt(split[i]));
		}
		
		
		allPaths = new ArrayList<Path>();
		
		for(int i = 0; i < vertices.size(); i++)
		{
			List<Path> paths = vertices.get(i).getPaths();
			for(int x = 0; x < paths.size(); x++)
			{
				if(!allPaths.contains(paths.get(x)))
				{
					allPaths.add(paths.get(x));
					points.add(getPoint(paths.get(x).getStart()));
				}
			}
		}
		
		try {
		    Display.setDisplayMode(new DisplayMode(600,600));
		    Display.create();
		} catch (LWJGLException e) {
		    e.printStackTrace();
		    System.exit(0);
		}
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glPointSize(pointsize);
		
		while (!Display.isCloseRequested()) {
			
			this.delta = (float) calculateDelta();
			
		    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);	
			
		   if(this.inAnimationFinished)
		   {
		    drawPaths();
		    drawPoints();
		    drawAnimatedEuler();
		   }
		   else
		   {
			   drawInAnimation();
		   }
		    
		    Display.update();
		    Display.sync(60);
		}
	 
		Display.destroy();
	}
	
	public static long getTime()
	{
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public double calculateDelta() 
	{
		long currentTime = getTime();
		double delta = (double) currentTime - (double) lastFrame;
		this.lastFrame = getTime();
		currentTime = 0;
		return delta;
	}
	
	public static float degToRad(float deg)
	{
		return deg*((float)Math.PI/180.0f);
	}
	
	private void drawPoints()
	{
	    	
	    // draw quad
	    GL11.glBegin(GL11.GL_POINTS);
	    
	    GL11.glColor3f(1.0f, 1.0f, 1.0f);
	    for(int i = 0; i < vertices.size(); i++)
	    {
	    	Float[] c = getPoint(vertices.get(i).getId());
	    	GL11.glVertex3f(c[0], c[1], 0.0f);
	    }
	    
	    GL11.glEnd();
	}
	
	private Float[] getPoint(int id)
	{
		return new Float[]{(float) (0.9*Math.cos(degToRad((float) ((360.0/vertices.size())*(id-1))))), (float) (0.9*Math.sin(degToRad((float) ((360.0/vertices.size())*(id-1)))))};
	}
	
	private void drawPaths()
	{
		GL11.glBegin(GL11.GL_LINES);
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		for(int i = 0; i < allPaths.size(); i++)
		{
			Float[] pstart = getPoint(allPaths.get(i).getStart());
			Float[] pend = getPoint(allPaths.get(i).getEnd());
			GL11.glVertex3f(pstart[0], pstart[1], 0.0f);
			GL11.glVertex3f(pend[0], pend[1], 0.0f);
		}
		
		GL11.glEnd();
	}
	
	private int current = 1;
	private Float[] pos = new Float[]{0f,0f};
	private float delta = 0;
	private boolean xbg = false;
	private boolean ybg = false;
	private int timer = 5000;
	private boolean firstTime = true;
	private void drawAnimatedEuler()
	{
		GL11.glBegin(GL11.GL_LINE_STRIP);
		GL11.glColor3f(1.0f, 0.0f, 0.0f);
		
		for(int i = 0; i < current; i++)
		{
			if(current == 1)
				break;
			Float[] p = getPoint(path.get(i));
			GL11.glVertex3f(p[0], p[1], 0.0f);
		}
		
		GL11.glEnd();
		
		
		if(current < path.size())
		{
			GL11.glBegin(GL11.GL_LINES);
			GL11.glColor3f(1.0f, 0.0f, 0.0f);
			Float[] p = getPoint(path.get(current-1));
			if(firstTime)
			{
				pos = p;
				firstTime = false;
			}
			
			GL11.glVertex3f(p[0], p[1], 0.0f);
			
			Float[] dest = getPoint(path.get(current));
			float rad = (float) Math.atan2(dest[0]-pos[0], dest[1]-pos[1]);
			
			xbg = pos[0] > dest[0];
			ybg = pos[1] > dest[1];
			
			pos[0] += (float) (speed*Math.sin(rad)*delta);
			pos[1] += (float) (speed*Math.cos(rad)*delta);
			
			GL11.glVertex3f(pos[0], pos[1], 0.0f);
			
			GL11.glEnd();
			
			GL11.glBegin(GL11.GL_POINTS);
			GL11.glColor3f(1.0f, 0.0f, 0.0f);
			GL11.glVertex3f(pos[0], pos[1], 0.0f);
			GL11.glEnd();
			
			if((pos[0] <= dest[0] && xbg) || (pos[0] >= dest[0] && !xbg) || 
			   (pos[1] <= dest[1] && ybg) || (pos[1] >= dest[1] && !ybg))
			{
				current++;
				pos = getPoint(path.get(current-1));
			}
			
		}
		else
		{
			this.timer  -= this.delta;
			if(timer <= 0)
			{
				current = 1;
				firstTime = true;
				timer = 5000;
			}
		}
		
		
	}
	
	private int pointsDrawn = 0;
	private int cooldown = 500;
	private boolean inAnimationFinished = false;
	private List<Float[]> points = new ArrayList<Float[]>();
	private void drawInAnimation()
	{
		if(pointsDrawn == vertices.size())
		{
			GL11.glBegin(GL11.GL_LINES);
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
			
			for(int i = 0; i < this.allPaths.size(); i++)
			{
				
				Float[] p = getPoint(allPaths.get(i).getStart());
				
				GL11.glVertex3f(p[0], p[1], 0.0f);
				
				Float[] dest = getPoint(allPaths.get(i).getEnd());
				
				
				if(Math.sqrt((dest[0]-points.get(i)[0])*(dest[0]-points.get(i)[0])+
						(dest[1]-points.get(i)[1])*(dest[1]-points.get(i)[1])) <= 0.01f)
				{
					if(i == 0) inAnimationFinished = true;
					
					points.get(i)[0] = dest[0];
					points.get(i)[1] = dest[1];
					
					GL11.glVertex3f(dest[0], dest[1], 0.0f);
				}
				else
				{
					inAnimationFinished = false;
					float rad = (float) Math.atan2(dest[0]-points.get(i)[0], dest[1]-points.get(i)[1]);
					
					points.get(i)[0] += (float) (speed*Math.sin(rad)*delta);
					points.get(i)[1] += (float) (speed*Math.cos(rad)*delta);
					GL11.glVertex3f(points.get(i)[0], points.get(i)[1], 0.0f);
				}
				
				
			}
			
			GL11.glEnd();
		}
		else
		{
			if(cooldown <= 0)
			{
				pointsDrawn++;
				cooldown = (500/6) * (6/vertices.size());
			}
		}
		
		GL11.glBegin(GL11.GL_POINTS);
	    GL11.glColor3f(1.0f, 1.0f, 1.0f);
		
		for(int i = 0; i < pointsDrawn; i++)
		{
			Float[] c = getPoint(vertices.get(i).getId());
	    	GL11.glVertex3f(c[0], c[1], 0.0f);
		}
		
		GL11.glEnd();
		
		cooldown -= delta;
	}
}
