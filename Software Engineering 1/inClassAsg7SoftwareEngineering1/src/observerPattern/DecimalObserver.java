package observerPattern;

public class DecimalObserver extends Observer{

	public DecimalObserver(Subject subject){
		this.subject=subject;
		this.subject.attach(this);
	}
	@Override
	public void update() {
		System.out.println("Decimal String " + Float.toString(subject.getState()));
		System.out.println("Carlos Alpuche");
	}
	
	

}
