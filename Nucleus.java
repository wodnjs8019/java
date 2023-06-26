import java.util.HashMap;

public abstract class Nucleus {
	private HashMap rna;
	private Label label;
	
	public Nucleus() {
		rna=new HashMap();
		System.out.println("rna생성");
		System.out.println(rna);
	}
	protected void setLabel(Label label) {		
		this.label=label;
		System.out.println("label접합");
	}

	protected void registField(String fieldName, Object initValue) {
		rna.put(fieldName, initValue);
	}
	public String getName() {
		return label.getName();
	}
	public String getKey() {
		return label.getKey();
	}
}
