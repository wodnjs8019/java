
public class Ob extends Nucleus {
	public Ob() {
		
		super();
		System.out.println("Ob생성");
		Label label=new Label("Ob");
		setLabel(label);	
		registField("field01","value01");
		registField("field02","value02");
		registField("field03","value03");
	}
	
}
