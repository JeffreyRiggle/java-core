package UnitTest;

public class TestClassB {

	private String initVal;
	
	public TestClassB(String initVal) {
		this.initVal = initVal;
	}
	
	public String initVal() {
		return initVal;
	}
	
	public void append(String val) {
		initVal += val;
	}
}
