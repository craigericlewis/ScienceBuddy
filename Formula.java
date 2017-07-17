
public class Formula {
	private String name ="";
	private int degree = 0;
	private String[] variables;
	private char[]operations;
	private String formula="";
	public Formula(String name, int degree, char[]opers, String[] vars, String frm) {
		this.name = name;
		this.degree = degree;
		this.operations = opers;
		this.variables = vars;
		this.formula = frm;
	}
	//Calculates the answer of a formula given user inputs
	public float Compute(float[]vars){
		float result = vars[0];
		for(int i=1; i <degree; i++){
			if (operations[i-1] == '+'){
				result = result + vars[i];
			}
			else if (operations[i-1] == '-'){
				result = result - vars[i];
			}
			else if (operations[i-1] == '*'){
				result = result * vars[i];
			}
			else if (operations[i-1] == '/'){
				result = result / vars[i];
			}
		}
		
		return result;
		
	}
	
	public int getDegree() {
		return degree;
	}
	public void setDegree(int degree) {
		this.degree = degree;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public String[] getVariables() {
		return variables;
	}
	public void setVariables(String[] variables) {
		this.variables = variables;
	}
	public char[] getOperations() {
		return operations;
	}
	public void setOperations(char[] operations) {
		this.operations = operations;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Formula() {
		// TODO Auto-generated constructor stub
	}
	public String getName(){
		return this.name;
		
	}

}
