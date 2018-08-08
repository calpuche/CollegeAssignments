package iteratorPattern;

public class IntRepository implements Container {
	public Integer ints[] = {1,2,3,4,5,6,7,8,9};
	
	@Override
	public Iterator getIterator() {
		return new IntIterator();
	}
	private class IntIterator implements Iterator{
		
		int index;

		@Override
		public boolean hasNext() {
			if(index < ints.length){
	            return true;
	         }
	         return false;
		}

		@Override
		public Object next() {
			if(this.hasNext()){
	            return ints[index++];
	         }
	         return null;
		}
		
	}
}
