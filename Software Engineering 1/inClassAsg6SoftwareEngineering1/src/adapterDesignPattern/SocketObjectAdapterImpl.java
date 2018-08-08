package adapterDesignPattern;

public class SocketObjectAdapterImpl implements SocketAdapter{

	//Using Composition for adapter pattern
	private Socket sock = new Socket();
	
	@Override
	public Volt get120Volt() {
		Volt v = sock.getVolt();
		System.out.println("Carlos Alpuche");
		return convertVolt(v,2);
	}

	@Override
	public Volt get12Volt() {
		Volt v= sock.getVolt();
		System.out.println("Carlos Alpuche");
		return convertVolt(v,20);
	}

	@Override
	public Volt get3Volt() {
		Volt v= sock.getVolt();
		System.out.println("Carlos Alpuche");
		return convertVolt(v,80);
	}
	
	private Volt convertVolt(Volt v, int i) {
		return new Volt(v.getVolts()/i);
	}
}
