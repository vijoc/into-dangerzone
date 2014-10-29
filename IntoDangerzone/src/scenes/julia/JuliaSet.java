package scenes.julia;

import math.Complex;

public class JuliaSet {
	
	private Complex parameterC = new Complex(0.5f, 0.5f);
	private ComplexFunction fn;

	private int iterations = 10;
	
	public JuliaSet() {
		
		this.fn = new ComplexFunction() {
			public Complex f(Complex z, Complex c) {
				return Complex.add(z.squared(), c);
			}
		};
	}
	
	public Complex getC() { return this.parameterC; }
	
	public void setC(Complex c) {
		if(!parameterC.equals(c)) {
			parameterC = c;
		}
	}
	
	public void setCx(float cx) {
		setC(new Complex(cx, parameterC.y()));
	}
	
	public void setCy(float cy) {
		setC(new Complex(parameterC.x(), cy));
	}
	
	public void setFunction(ComplexFunction fn) {
		this.fn = fn;
	}
	
	public void setIterations(int i) {
		this.iterations = i;
	}
	
	public int getIterations() {
		return this.iterations;
	}
	
	public int lastIterationContaining(Complex z) {
		int result = -1;
		
		for(int i = 0; i < iterations; i++) {
			z = fn.f(z, parameterC);
			
			if(z.magnitude() < 2) {
				result = i;
			}
		}
		
		return result;
	}
	
	public boolean lastIterationContains(Complex z) {
		for(int i = 0; i < iterations; i++) {
			z = fn.f(z, parameterC);
		}
		return z.magnitude() < 2;
	}
}
