package adapterDesignPattern;

public class Socket {
	public Volt getVolt(){
		return new Volt(240);
	}
}
