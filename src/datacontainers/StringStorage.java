package datacontainers;

public class StringStorage {
	String string;
	
	public StringStorage () {
		string = "";
	}
	
	public StringStorage (String str) {
		string = str;
	}
	
	public void set (String str) {
		string = str;
	}
	
	public String get () {
		return string;
	}
	
	public String toString () {
		return string;
	}
}