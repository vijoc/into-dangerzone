public class ConstantProvider<E> implements InputProvider<E> {
	private E constant;
	
	public ConstantProvider(E value) {
		this.constant = value;
	}
	
	@Override
	public E readInput() {
		return this.constant;
	}
}
