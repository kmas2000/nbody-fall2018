/**
 * @author Kenneth Moore III
 * 
 * Simulation program for the NBody assignment
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NBody {
	
	/**
	 * Read the specified file and return the radius
	 * @param fname is name of file that can be open
	 * @return the radius stored in the file
	 * @throws FileNotFoundException if fname cannot be open
	 */
	public static double readRadius(String fname) throws FileNotFoundException  {
		Scanner s = new Scanner(new File(fname));//already here, establish Scanner
		
		int numBodies = s.nextInt();//gets and skips over number of bodies, not  used
		double radius = s.nextDouble();//gets the radius of the world
		
		s.close();//already here
		
		return radius;//return radius read	
	}
	
	/**
	 * Read all data in file, return array of Celestial Bodies
	 * read by creating an array of Body objects from data read.
	 * @param fname is name of file that can be open
	 * @return array of Body objects read
	 * @throws FileNotFoundException if fname cannot be open
	 */
	public static Body[] readBodies(String fname) throws FileNotFoundException {
		
			Scanner s = new Scanner(new File(fname));
			
			int nb = 0; // # bodies to be read
			nb = s.nextInt();//gets number of bodies
			Body[] bodies = new Body[nb]; //creates body array, numBodies long
			s.nextDouble();//skips over how big the world is 
			
			//initialzes variables used when making new body
			double xp, yp, xv, yv, mass;
			String fileName;
			for(int k=0; k < nb; k++) {//loops nb times (for every body in the file)
				// read data for each body
				xp = s.nextDouble();
				yp = s.nextDouble();
				xv = s.nextDouble();
				yv = s.nextDouble();
				mass = s.nextDouble();
				fileName = s.next();
				// construct new body object and add to array
				bodies[k] = new Body(xp, yp, xv, yv, mass, fileName);
			}
			
			s.close();//already here, closes array
			
			// return array of body objects read
			return bodies;
	}
	public static void main(String[] args) throws FileNotFoundException{
		double totalTime = 157788000.0;
		double dt = 25000.0;
		
		String fname= "./data/planets.txt";
		if (args.length > 2) {
			totalTime = Double.parseDouble(args[0]);
			dt = Double.parseDouble(args[1]);
			fname = args[2];
		}	
		
		Body[] bodies = readBodies(fname);
		double radius = readRadius(fname);
		
		StdDraw.setScale(-radius, radius);
		StdDraw.picture(0,0,"images/starfield.jpg");
	
		for(double t = 0.0; t < totalTime; t += dt) {
			
			// create double arrays xforces and yforces to hold forces on each body
			double[] xforces = new double[bodies.length];
			double[] yforces = new double[bodies.length];
			
			//loop over all bodies, calculate net forces and store in xforces and yforces
			for(int k =0; k<bodies.length; k++) {
				xforces[k] = bodies[k].calcNetForceExertedByX(bodies);
				yforces[k] = bodies[k].calcNetForceExertedByY(bodies);
			}
			
			// loop over all bodies and call update
			//with dt and corresponding xforces, yforces values
			for(int k =0; k<bodies.length; k++) {
				bodies[k].update(dt, xforces[k], yforces[k]);
			}
			
			StdDraw.picture(0,0,"images/starfield.jpg");//already here, sets background?
			
			// loop over all bodies and call draw on each one
			for(int k =0; k<bodies.length; k++) {
				bodies[k].draw();
			}
			
			
			StdDraw.show(10);//already here, not sure what does
		}
		
		// prints final values after simulation
		
		System.out.printf("%d\n", bodies.length);
		System.out.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
		    System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		   		              bodies[i].getX(), bodies[i].getY(), 
		                      bodies[i].getXVel(), bodies[i].getYVel(), 
		                      bodies[i].getMass(), bodies[i].getName());	
		}
	}
}
