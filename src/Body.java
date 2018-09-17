
public class Body {
	//comments in front of all methods
	//variables in methods- private?
	
	private double myXPos;
	private double myYPos;
	private double myXVel;
	private double myYVel;
	private double myMass;
	private String myFileName;
	
	/** 
	 * Constructor
	 * @param xp initial x position
	 * @param yp initial y position
	 * @param xv initial x velocity
	 * @param yv initial y velocity 
	 * @param mass of object
	 * @param fileName of image for object animation
	 */
	public Body(double xp, double yp, double xv, double yv, double mass, String fileName) {
		myXPos = xp;
		myYPos = yp;
		myXVel = xv;
		myYVel = yv;
		myMass = mass;
		myFileName = fileName;
	}
	
	/**
	 * Copy Constructor: Copy instance variables from one body to this body 
	 * @param b used to initialize this body
	 */
	public Body(Body b) {
		myXPos = b.myXPos;
		myYPos = b.myYPos;
		myXVel = b.myXVel;
		myYVel = b.myYVel;
		myMass = b.myMass;
		myFileName = b.myFileName;
	}
	
	
	/**
	 * Getter
	 * @return myXPos variable 
	 */
	public double getX() {
		return myXPos;
	}
	
	/**
	 * Getter
	 * @return myYPos variable
	 */
	public double getY() {
		return myYPos;
	}
	
	/**
	 * Getter
	 * @return myXVel variable
	 */
	public double getXVel() {
		return myXVel;
	}
	
	/**
	 * Getter
	 * @return myYVel variable 
	 */
	public double getYVel() {
		return myYVel;
	}
	
	/**
	 * Getter
	 * @return myMass variable 
	 */
	public double getMass() {
		return myMass;
	}
	
	/** 
	 * Getter
	 * @return myFileName variable 
	 */
	public String getName() {
		return myFileName;
	}
	
	/**
	 * Return the distance between this body and another
	 * @param b the other body to which the distance is calculated 
	 * @return distance between this body and b 
	 */
	public double calcDistance(Body b) {
		double dx = myXPos - b.myXPos;//difference between x's
		double dy = myYPos - b.myYPos;//difference between y's
		return Math.sqrt(dx*dx + dy*dy);//distance formula 
	}
	
	/**
	 * Return the force exerted by one body on another 
	 * @param b the other body to which the force is calculated from 
	 * @return the force exerted by this body and b 
	 */
	public double calcForceExertedBy(Body b) {
		double G = 6.67*1e-11;//Gravitational Physics Constant 
		double r = this.calcDistance(b);//distance between two bodies 
		return G*((myMass*b.myMass)/(r*r));//implement formula as on lab sheet
	}
	
	/**
	 * Calculate the force in the x direction by this body on another
	 * @param b the other body to which the force is calculated from 
	 * @return the force exerted in the x direction between this body and b 
	 */
	public double calcForceExertedByX(Body b) {
		//implements formula as listed on lab sheet to get force in x direction
		return this.calcForceExertedBy(b)*((b.myXPos-myXPos)/(this.calcDistance(b)));
	}
	
	/**
	 * Calculate the force in the y direction by this body on another
	 * @param b the other body to which the force is calculated from 
	 * @return the force exerted in the y direction between this body and b 
	 */
	public double calcForceExertedByY(Body b) {
		//implements formula as listed on lab sheet to get force in y direction
		return this.calcForceExertedBy(b)*((b.myYPos-myYPos)/(this.calcDistance(b)));
	}
	
	/**
	 * Calculate the overall force in the x direction based on an array of bodies
	 * @param bodies an array of bodies from which the net force is calculated from 
	 * @return the overall force exerted in the x direction by all bodies in an array
	 */
	public double calcNetForceExertedByX(Body[] bodies) {
		double xTotalForce =0;//xforce initialized to 0
		for(Body b : bodies) {// goes through all bodies in Body[] array
			if(! b.equals(this)) {//ensures the force of body on itself is not calculated
				xTotalForce += this.calcForceExertedByX(b);//sum xforces exerted on body
			}
		}
		return xTotalForce;//returns forces in x direction
	}
	
	/**
	 * Calculate the overall force in the y direction based on an array of bodies
	 * @param bodies an array of bodies from which the net force is calculated from 
	 * @return the overall force exerted in the y direction by all bodies in an array
	 */
	public double calcNetForceExertedByY(Body[] bodies) {
		double yTotalForce =0;//yforce initialized to 0
		for(Body b : bodies) {// loops through all bodies in Body[] array
			if(! b.equals(this)) {//ensure the force of body on itself is not calculated
				yTotalForce += this.calcForceExertedByY(b);//sum y forces exerted on body
			}
		}
		return yTotalForce;// returns total forces in y direction
	}
	
	/**
	 * Updates the instance variables based on force and small amounts of time 
	 * @param deltaT the change in time being applied during the update
	 * @param xforce the force acting on x, calculated by calNetForceExertedByX method
	 * @param yforce the force acting on y, calculated by calNetForceExertedByY method
	 */
	public void update(double deltaT, double xforce, double yforce) {
		//Step 1
		double xAcel = xforce/myMass;//calculate x direction acel by doing force/mass
		double yAcel = yforce/myMass;//calculate y direction acel by doing force/mass
		//Step 2
		double nvx = myXVel + deltaT*xAcel;//new xVel by adding time*accel to current
		double nvy = myYVel + deltaT*yAcel;//new yVel by adding time*accel to current
		//step 3
		double nx = myXPos + deltaT*nvx;//new xPos by adding time*velocity to current
		double ny = myYPos + deltaT*nvy;//new ypos by adding time*velocity to current 
		//Step 4- assign new variables to instance 
		myXPos = nx;
		myYPos = ny;
		myXVel = nvx;
		myYVel = nvy;
	}
	/**
	 * Code given to us, draws an image of the universe
	 */
	public void draw() {
		StdDraw.picture(myXPos, myYPos, "images/"+myFileName);
	}
	
}