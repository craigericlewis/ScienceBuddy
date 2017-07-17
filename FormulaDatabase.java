
public class FormulaDatabase {
	private Formula[] formulas;
	
	public FormulaDatabase(Formula[] forms) {
		this.formulas = forms;
		
	}
	public Formula[] getFormulas() {
		return this.formulas;
	}
	public void setFormulas(Formula[] formulas) {
		this.formulas = formulas;
	}
	public String[] getNames(){
		String[] result = new String[formulas.length];
		for (int i=0; i < formulas.length;i++){
			result[i] = formulas[i].getName();
		}
		return result;
		
	}
	public void bubbleSort(){
		Formula temp;
		boolean sorted = true;
		
		//Keeps sorting while the database is unsorted
		while (sorted){
			
			sorted = false;
			// Bubbles over formulas that are out of place 
			for (int i = 0; i < this.formulas.length-1;i++){
								if (this.formulas[i].getName().compareToIgnoreCase(this.formulas[i+1].getName()) > 0){
					temp = this.formulas[i];
					this.formulas[i] = this.formulas[i+1];
					this.formulas[i+1] = temp;
					//Keeps sorting as a bubble has occurred
					sorted = true;
				}
			}
		}
		
	}
	//Binary search
	public Formula search(String search){
		Formula result = new Formula();
		int low = 0;
		int high = formulas.length-1;
		boolean searching = true;
		while (searching){
			int location = ((high+low)/2);
			if ((this.formulas[location].getName().compareToIgnoreCase(search) == 0)){
				result = formulas[location];
				searching = false;
			}
			else if ((this.formulas[location].getName().compareToIgnoreCase(search) < 0)){
				low = location;
			}
			else if ((this.formulas[location].getName().compareToIgnoreCase(search) > 0)){
				high = location;
			}
			else if (high == formulas.length -1 && low == formulas.length -2){
				low = high;
			}
			else if(low == high-1 && this.formulas[location].getName().compareToIgnoreCase(search) != 0 ){
				searching = false;
				result = null;
			}
		}
		return result;
	}

}
