
public class Label {
	private String name=null;
	private long unique=0;
	public Label(String name) {
		name=new String("name");	
		unique=System.currentTimeMillis();
		System.out.println("Label생성");
		System.out.println(name);
		System.out.println(unique);
	}
	public String getName() {
		return name;
	}
	public String getKey() {		
		return name+String.valueOf(unique);
	}

}
