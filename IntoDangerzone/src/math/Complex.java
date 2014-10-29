package math;

public class Complex {
	
	private final float x;
	private final float y;
	
	public static Complex fromPolar(float r, float phase) {
		return new Complex(
				(float) (r * Math.cos(phase)), 
				(float) (r * Math.sin(phase))
		);
	}
	
	public static Complex add(Complex c1, Complex c2) {
		return new Complex(c1.x + c2.x, c1.y + c2.y);
	}
	
	public static Complex subtract(Complex c1, Complex c2) {
		return new Complex(c1.x - c2.x, c1.y - c2.y);
	}
	
	public static Complex multiply(Complex c1, Complex c2) {
		return new Complex(
				c1.x * c2.x - c1.y * c2.y,
				c1.x * c2.y + c1.y * c2.x
		);
	}
	
	public static Complex multiply(float scalar, Complex c) {
		return new Complex(scalar * c.x, scalar * c.y);
	}
	
	public static Complex exp(Complex c) {
		return Complex.fromPolar((float) Math.exp(c.x), c.y);
	}
	
	public Complex(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float getReal() { return this.x; }
	public float getImaginary() { return this.y; }
	
	public float x() { return getReal(); }
	public float y() { return getImaginary(); }
	
	public float squaredModule() {
		return x*x + y*y;
	}
	
	public float magnitude() {
		return (float) Math.sqrt(squaredModule());
	}
	
	public float phase() {
		if(x == 0 && y == 0) return 0;
		else return (float) Math.atan2(y, x);
	}
	
	public Complex squared() {
		return multiply(this, this);
	}
	
	public boolean equals(Complex other) {
		return (this.x == other.x) && (this.y == other.y); 
	}

}
