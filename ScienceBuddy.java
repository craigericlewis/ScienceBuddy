import java.awt.EventQueue;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Color;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.SwingConstants;
import Jama.Matrix;


public class ScienceBuddy {
	final JTextField txtSearch = new JTextField();
	int fieldLength = 0;
	int cfieldLength = 0;
	private JFrame frmChembud;
	private JFrame frmMain;
	private JTextField txtChembud;
	private JList list;
	private JList listC;
	private JFrame frmConst;
	private JTextField txtConst;
	private JTextField txtCSearch;
	private JScrollPane scrollPane;
	private JTable table= null;
	static String[] constants = new String[28];
	static Formula[] formulas = new Formula[32];
	JTextField[] inputs = null;
	JTextField result = new JTextField();
	int index = 0;
	String name = "";
	Formula dformula = null;
	JFrame frmMenu = new JFrame();
	private final Action action8 = new SwingAction8();
	private final Action action7 = new SwingAction7();
	private final Action action6 = new SwingAction6();
	private final Action action5 = new SwingAction5();
	private final Action action4 = new SwingAction4();
	private final Action action3 = new SwingAction3();
	private final Action action2 = new SwingAction2();
	private DefaultListModel model = null;
	private DefaultListModel filteredModel = null;
	private JTextField usernameTextField = null;
	JTextField txtInput = new JTextField();
	JTextField txtOutput = new JTextField();
	private JFrame frmBalancer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ScienceBuddy window = new ScienceBuddy();
					window.frmMain.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws FileNotFoundException
	 */
	public ScienceBuddy() throws FileNotFoundException {

		startup();
		FormulaDatabase database = new FormulaDatabase(formulas);
		database.bubbleSort();
		mainMenu();
		balancer();
		constantMenu(constants);
		listMenu(database);
	}
	//Inputs and split formulas and constants from their respective txt files
	public static void startup() throws FileNotFoundException {
		int frmNum = 0;
		FileReader file = new FileReader("Formulas.txt");
		FileReader file2 = new FileReader("Constants.txt");
		Scanner sc = new Scanner(file);
		Scanner sc2 = new Scanner(file2);
		//Iterates till every formula has been inputted
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			String[] splitLine = line.split(",");
			int degree = 0;
			String name = "";
			String[] vars = null;
			char[] opers = null;
			String frm = "";
			for (int i = 0; i < Integer.parseInt(splitLine[1]) + 3; i++) {
				if (i == 0) {
					name = splitLine[i];
				} else if (i == 1) {
					degree = Integer.parseInt(splitLine[i]);
					opers = new char[degree - 1];

				} else if (i == 2) {
					vars = splitLine[2].split(" ");
				} else if (i == 3) {
					frm = splitLine[3];
				} else {
					opers[i - 4] = splitLine[4].charAt(i - 4);
				}
			}
			formulas[frmNum] = (new Formula(name, degree, opers, vars, frm));
			frmNum++;
		}
		//Iterates until every constant has been inputted
		for (int i = 0; i < 28; i++){
			String constLine = sc2.nextLine();
				constants[i] = constLine;
				
			}
		}
	

	

	public static ArrayList<String> parser(String eqn) {
		Matcher m = null;
		Matcher j = null;
		Matcher k = null;
		Matcher l = null;
		Matcher n = null;
		ArrayList<String> terms = new ArrayList<String>();

		ArrayList<String> sorted = new ArrayList<String>();
		ArrayList<String> number = new ArrayList<String>();
		//Checks if a compound with a factor exists
		if (eqn.contains("(")) {
			m = Pattern.compile("[(]*[A-Z]+[a-z]*[1-9]*[)]*[1-9]*").matcher(eqn);

		} else {
			m = Pattern.compile("[(]*[A-Z][a-z]*[1-9]*[)]*[1-9]*").matcher(eqn);
		}
		while (m.find()) {

			terms.add(m.group());
		}
		//Iterates for each compound in the term
		for (int i = 0; i < terms.size(); i++) {
			ArrayList<String> elements = new ArrayList<String>();
			int factor = 1;
			ArrayList<String> compounds = new ArrayList<String>();
			if (terms.get(i).contains("(")) {
				//Locates the factor after the closing bracket
				n = Pattern.compile("\\)\\d").matcher(terms.get(i));
				//Splits the compounds into elements
				j = Pattern.compile("[A-Z][a-z]*[1-9]*").matcher(terms.get(i));

				while (n.find()) {

					factor = Integer.parseInt(n.group().substring(1));

				}

				while (j.find()) {

					compounds.add(j.group());
				}
				//Splits the elements into the element letter and number after the letter
				for (int h = 0; h < compounds.size(); h++) {
					int num = 1;
					String elementSt = "";
					k = Pattern.compile("[A-Z]+[a-z]*").matcher(compounds.get(h));
					while (k.find()) {

						elementSt = (k.group());
					}
					l = Pattern.compile("[1-9]").matcher(compounds.get(h));
					while (l.find()) {
						num = Integer.parseInt(l.group());

					}

					elements.add(elementSt + num * factor);
				}

			} else {

				j = Pattern.compile("[A-Z][a-z]*[1-9]*").matcher(terms.get(i));

				while (j.find()) {

					compounds.add(j.group());
				}
				//Splits the elements into the element letter and number after the letter
				for (int h = 0; h < compounds.size(); h++) {
					int num = 1;
					String elementSt = "";
					k = Pattern.compile("[A-Z]+[a-z]*").matcher(compounds.get(h));
					while (k.find()) {

						elementSt = (k.group());
					}
					l = Pattern.compile("[1-9]").matcher(compounds.get(h));
					while (l.find()) {
						num = Integer.parseInt(l.group());

					}

					elements.add(elementSt + num * factor);
				}

			}
			for (int o = 0; o < elements.size(); o++) {
				sorted.add(elements.get(o));
			}

		}
		return sorted;

	}
	
	//Initialization for the main menu
	private void mainMenu() {
		frmMain = new JFrame();
		frmMain.setVisible(true);
		frmMain.setTitle("Science Buddy");
		frmMain.setBounds(275, 20, 761, 700);
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMain.getContentPane().setLayout(null);

		JTextField txtMain = new JTextField();
		txtMain.setEditable(false);
		txtMain.setHorizontalAlignment(SwingConstants.CENTER);
		txtMain.setFont(new Font("Tahoma", Font.PLAIN, 48));
		txtMain.setText("Science Buddy");
		txtMain.setBounds(188, 16, 378, 80);
		frmMain.getContentPane().add(txtMain);
		txtMain.setColumns(10);
		
		//Creates button to open formula menu
		JButton btnFormula = new JButton("Formula List");
		btnFormula.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnFormula.setBounds(15, 108, 709, 51);
		btnFormula.setAction(action3);
		frmMain.getContentPane().add(btnFormula);
		
		//Creates button to open constant list
		JButton btnConstantList = new JButton("Constant List");
		btnConstantList.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnConstantList.setBounds(15, 175, 709, 51);
		btnConstantList.setAction(action7);
		frmMain.getContentPane().add(btnConstantList);
		
		//Creates button to open chemical equation balancer
		JButton btnGraphingTool = new JButton("Chemical Equation Balancer");
		btnGraphingTool.setAction(action4);
		btnGraphingTool.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnGraphingTool.setBounds(15, 242, 709, 51);
		frmMain.getContentPane().add(btnGraphingTool);
	}
	
	//Initialization for constant list
	private void constantMenu(String[] cdata) {
		frmConst = new JFrame();
		frmConst.setForeground(Color.WHITE);
		frmConst.setTitle("Science Buddy");
		frmConst.setBounds(275, 20, 761, 700);
		frmConst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmConst.getContentPane().setLayout(null);
		frmConst.setVisible(false);

		txtConst = new JTextField();
		txtConst.setForeground(Color.BLACK);
		txtConst.setEditable(false);
		txtConst.setHorizontalAlignment(SwingConstants.CENTER);
		txtConst.setFont(new Font("Tahoma", Font.PLAIN, 48));
		txtConst.setText("Constant List");
		txtConst.setBounds(15, 16, 378, 80);
		frmConst.getContentPane().add(txtConst);
		txtConst.setColumns(10);
		
		
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(15, 150, 709, 462);
		frmConst.getContentPane().add(scrollPane2);

		final DefaultListModel model = new DefaultListModel();
		for (int j = 0; j < cdata.length ; j++) {
			model.addElement(cdata[j]);
		}
		//Creates list of constants
		listC = new JList(model);
		listC.setForeground(Color.BLACK);
		listC.setFont(new Font("Tahoma", Font.PLAIN, 20));
		scrollPane2.setViewportView(listC);
		
		//Back button to return to main menu
		JButton back = new JButton("Back");
		back.setAction(action8);
		back.setFont(new Font("Tahoma", Font.PLAIN, 28));
		back.setBounds(574, 16, 150, 80);
		frmConst.getContentPane().add(back);
		
		txtCSearch = new JTextField();
		txtCSearch.setForeground(Color.BLACK);
		txtCSearch.setHorizontalAlignment(SwingConstants.CENTER);
		txtCSearch.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txtCSearch.setBounds(15, 100, 710, 45);
		txtCSearch.setVisible(true);
		frmConst.getContentPane().add(txtCSearch);
		
		//Search bar filters list based on input
		txtCSearch.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e) {
				if (cfieldLength > txtCSearch.getText().length()) {
					listC.setModel(model);
					cfilterList();
				} else {
					cfilterList();
				}

			}

			public void keyPressed(KeyEvent e) {

				cfieldLength = txtCSearch.getText().length();
			}
		});
	}
	//Initialization for formula list menu
	private void listMenu(final FormulaDatabase database) {
		frmChembud = new JFrame();
		frmChembud.setForeground(Color.WHITE);
		frmChembud.setTitle("Science Buddy");
		frmChembud.setBounds(275, 20, 761, 700);
		frmChembud.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChembud.getContentPane().setLayout(null);
		frmChembud.setVisible(false);

		txtChembud = new JTextField();
		txtChembud.setForeground(Color.BLACK);
		txtChembud.setEditable(false);
		txtChembud.setHorizontalAlignment(SwingConstants.CENTER);
		txtChembud.setFont(new Font("Tahoma", Font.PLAIN, 48));
		txtChembud.setText("Formula List");
		txtChembud.setBounds(15, 16, 378, 80);
		frmChembud.getContentPane().add(txtChembud);
		txtChembud.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 150, 709, 462);
		frmChembud.getContentPane().add(scrollPane);

		final String[] formulaNames = database.getNames();
		final DefaultListModel model = new DefaultListModel();
		//Gets all the formula names to add to the formula list
		for (int j = 0; j < formulaNames.length - 1; j++) {
			model.addElement(formulaNames[j]);
		}
		//creates list of formulas
		list = new JList(model);
		list.setForeground(Color.BLACK);
		list.setFont(new Font("Tahoma", Font.PLAIN, 20));
		scrollPane.setViewportView(list);
		
		//Creates back button to return to main menu
		JButton back = new JButton("Back");
		back.setAction(action2);
		back.setFont(new Font("Tahoma", Font.PLAIN, 28));
		back.setBounds(574, 16, 150, 80);
		frmChembud.getContentPane().add(back);
		txtSearch.setForeground(Color.BLACK);
		
		txtSearch.setHorizontalAlignment(SwingConstants.CENTER);
		txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txtSearch.setBounds(15, 100, 710, 45);
		frmChembud.getContentPane().add(txtSearch);
		
		//Checks to see if the user clicks on a formula to open the calculation menu
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList list = (JList) evt.getSource();
				if (evt.getClickCount() == 2) {

					// Double-click detected
					name= (String) list.getSelectedValue();
					frmChembud.setVisible(false);
					dformula =database.search(name);
					calcMenu(dformula);
				} else if (evt.getClickCount() == 3) {

					// Double-click detected
					name= (String) list.getSelectedValue();
					frmChembud.setVisible(false);
					dformula =database.search(name);
					calcMenu(dformula);
				}
			}
		});
		
		//Filters the formula list based on user input
		txtSearch.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e) {
				if (fieldLength > txtSearch.getText().length()) {
					list.setModel(model);
					filterList();
				} else {
					filterList();
				}

			}

			public void keyPressed(KeyEvent e) {

				fieldLength = txtSearch.getText().length();
			}
		});
	}

	
	//Initialization for the calculation menu
	private void calcMenu(Formula formula) {

		frmMenu.setVisible(true);
		frmMenu.setTitle(formula.getName());
		frmMenu.setBounds(275, 20, 761, 700);
		frmMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMenu.getContentPane().setLayout(null);
		frmMenu.setTitle("Science Buddy");
		JTextField txtfrmMenu = new JTextField();
		txtfrmMenu.setEditable(false);
		txtfrmMenu.setHorizontalAlignment(SwingConstants.CENTER);
		txtfrmMenu.setFont(new Font("Tahoma", Font.PLAIN, 48));
		txtfrmMenu.setText(formula.getName() + " " + formula.getFormula());
		txtfrmMenu.setBounds(15, 16, 710, 80);
		frmMenu.getContentPane().add(txtfrmMenu);
		txtfrmMenu.setColumns(10);

		inputs = new JTextField[formula.getDegree()];
		
		//Iterates for the number of variables the formula has
		for (int i = 0; i < formula.getDegree(); i++) {

			JTextField option = new JTextField();
			option.setColumns(10);
			option.setBounds(380, 113 + 65 * i, 223, 49);
			option.setFont(new Font("Tahoma", Font.PLAIN, 30));
			option.setHorizontalAlignment(SwingConstants.CENTER);
			frmMenu.getContentPane().add(option);

			JTextField variables = new JTextField(formula.getVariables()[i]);
			variables.setEditable(false);
			variables.setBounds(142, 113 + 65 * i, 223, 49);
			variables.setFont(new Font("Tahoma", Font.PLAIN, 30));
			variables.setHorizontalAlignment(SwingConstants.CENTER);
			frmMenu.getContentPane().add(variables);

			inputs[i] = option;
		}
		
		//Creates button that takes user input for the variables and calculates answer to formula
		JButton btnNewButton = new JButton("Calculate");
		final Action action = new SwingAction();
		final Action action1 = new SwingAction1();
		btnNewButton.setAction(action);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 28));
		btnNewButton.setBounds(142, 113 + (formula.getDegree()) * 65, 223, 59);
		frmMenu.getContentPane().add(btnNewButton);
		
		//Create back button to return to previous menu
		JButton back = new JButton("Back");
		back.setAction(action1);
		back.setFont(new Font("Tahoma", Font.PLAIN, 28));
		back.setBounds(240, 120 + (formula.getDegree() + 1) * 65, 223, 59);
		frmMenu.getContentPane().add(back);

		result.setText("");
		result.setEditable(false);
		result.setColumns(10);
		result.setBounds(380, 113 + (formula.getDegree()) * 65, 223, 59);
		result.setFont(new Font("Tahoma", Font.PLAIN, 30));
		result.setHorizontalAlignment(SwingConstants.CENTER);
		frmMenu.getContentPane().add(result);

	}

	//Initialization for the balancing menu
	private void balancer() {
		frmBalancer = new JFrame();
		frmBalancer.setTitle("Equation Balancer");
		frmBalancer.setBounds(275, 20, 761, 700);
		frmBalancer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBalancer.getContentPane().setLayout(null);
		frmBalancer.setTitle("Science Buddy");

		JTextField txtBalancer = new JTextField();
		txtBalancer.setEditable(false);
		txtBalancer.setText("Chemical Equation Balancer");
		txtBalancer.setHorizontalAlignment(SwingConstants.CENTER);
		txtBalancer.setFont(new Font("Tahoma", Font.PLAIN, 48));
		txtBalancer.setBounds(15, 16, 710, 80);
		frmBalancer.getContentPane().add(txtBalancer);

		txtInput = new JTextField();
		txtInput.setHorizontalAlignment(SwingConstants.CENTER);
		txtInput.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtInput.setBounds(15, 120, 710, 60);
		frmBalancer.getContentPane().add(txtInput);

		txtOutput = new JTextField();
		txtOutput.setEditable(false);
		txtOutput.setHorizontalAlignment(SwingConstants.CENTER);
		txtOutput.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtOutput.setBounds(15, 200, 710, 60);
		frmBalancer.getContentPane().add(txtOutput);

		//Creates balance button which initiates the balancing process on the user inputted unbalanced formula
		JButton btnNewButton = new JButton("Balance");

		btnNewButton.setAction(action5);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 28));
		btnNewButton.setBounds(15, 280, 710, 59);
		frmBalancer.getContentPane().add(btnNewButton);
		
		//Creates back button to return to previous menu
		JButton back = new JButton("Back");
		back.setAction(action6);
		back.setFont(new Font("Tahoma", Font.PLAIN, 28));
		back.setBounds(15, 360,710, 60);
		frmBalancer.getContentPane().add(back);
		
	}
	
	//Converts decimals to fractions
	public static int toFraction(double decimal) {
		int limit = 12;
		int dnms[] = new int[limit + 1];
		int nmr, dnm = 0, temp;
		int max = 100;
		int i = 0;
		while (i < limit + 1) {
			dnms[i] = (int) decimal;
			decimal = 1.0 / (decimal - dnms[i]);
			i = i + 1;
		}
		int last = 0;
		while (last < limit) {
			nmr = 1;
			dnm = 1;
			temp = 0;
			int current = last;
			while (current >= 0) {
				dnm = nmr;
				nmr = (nmr * dnms[current]) + temp;
				temp = dnm;
				current = current - 1;
			}
			last = last + 1;
			int denom = dnms[last];
			if (Math.abs(denom) > max)
				break;
		}
		return dnm;
	}
	
	//Finds greatest common denominator between two numbers
	private static double gcd(double a, double b) {
		while (b > 0) {
			double temp = b;
			b = a % b;
			a = temp;
		}
		return a;
	}
	
	//Finds the lcm
	public static double lcm(double a, double b) {
		return a * (b / gcd(a, b));
	}

	/* computes the least common multiple of an array of integers. */
	public static double lcm(double[] input) {
		double result = input[0];
		for (int i = 0; i < input.length; i++) {
			result = lcm(result, input[i]);
		}
		return result;
	}
	//Filters the formula list
	private void filterList() {
		int start = 0;
		int item = 0;
		ArrayList<String> results = new ArrayList<>();

		filteredModel = new DefaultListModel();
		String prefix = txtSearch.getText();

		javax.swing.text.Position.Bias direction = javax.swing.text.Position.Bias.Forward;

		for (int i = 0; i < list.getModel().getSize(); i++) {
			item = list.getNextMatch(prefix, i, direction);
			if (i == 0) {
				try {
					results.add((String) list.getModel().getElementAt(item));
				} catch (ArrayIndexOutOfBoundsException e) {

					return;
				}

			} else if (i > 0) {
				if ((String) (list.getModel().getElementAt(item)) != results.get(0)) {
					results.add((String) (list.getModel().getElementAt(item)));
				}
			}

		}
		for (int i = 0; i < results.size(); i++) {
			filteredModel.addElement(results.get(i));
		}
		// Setting the model to the list again
		list.setModel(filteredModel);

	}
	
	//Filters the constant list
	private void cfilterList() {
		int start = 0;
		int item = 0;
		ArrayList<String> results = new ArrayList<>();

		filteredModel = new DefaultListModel();
		String prefix = txtCSearch.getText();

		javax.swing.text.Position.Bias direction = javax.swing.text.Position.Bias.Forward;

		for (int i = 0; i < listC.getModel().getSize(); i++) {
			item = listC.getNextMatch(prefix, i, direction);
			if (i == 0) {
				try {
					results.add((String) listC.getModel().getElementAt(item));
				} catch (ArrayIndexOutOfBoundsException e) {

					return;
				}

			} else if (i > 0) {
				if ((String) (listC.getModel().getElementAt(item)) != results.get(0)) {
					results.add((String) (listC.getModel().getElementAt(item)));
				}
			}

		}
		for (int i = 0; i < results.size(); i++) {
			filteredModel.addElement(results.get(i));
		}
		// Setting the model to the list again
		listC.setModel(filteredModel);

	}
	
	class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Calculate");
			putValue(SHORT_DESCRIPTION, "Calculates result given user values");
		}

		public void actionPerformed(ActionEvent e) {
			boolean finished = true;
			try{
				
			float[] variables = new float[dformula.getDegree()];
			boolean calculate = true;
			for (int i = 0; i < dformula.getDegree(); i++) {
					variables[i] = Float.parseFloat(inputs[i].getText());
				
			}
			
			float answer = dformula.Compute(variables);
			String ans = String.valueOf(answer);
			result.setText(ans);
			finished = false;
			}finally{
				if (finished){
				JOptionPane.showMessageDialog(frmMenu, "Make sure to enter proper variable values");
				}
			}
		}

		private boolean isFloat(float parseFloat) {
			// TODO Auto-generated method stub
			return false;
		}

	}
	class SwingAction7 extends AbstractAction {
		public SwingAction7() {
			putValue(NAME, "Constant List");
			putValue(SHORT_DESCRIPTION, "Open Constant List");
		}

		public void actionPerformed(ActionEvent e) {
			frmMain.setVisible(false);
			frmConst.setVisible(true);
		}

	}
	class SwingAction1 extends AbstractAction {
		public SwingAction1() {
			putValue(NAME, "Back");
			putValue(SHORT_DESCRIPTION, "Return to previous page");
		}

		public void actionPerformed(ActionEvent e) {
			frmChembud.setVisible(true);
			frmMenu.setVisible(false);
			frmMenu.getContentPane().removeAll();
		}

	}
	
	class SwingAction6 extends AbstractAction {
		public SwingAction6() {
			putValue(NAME, "Back");
			putValue(SHORT_DESCRIPTION, "Return to previous page");
		}

		public void actionPerformed(ActionEvent e) {
			frmBalancer.setVisible(false);
			frmMain.setVisible(true);
			
		}

	}

	class SwingAction3 extends AbstractAction {
		public SwingAction3() {
			putValue(NAME, "Formula List");
			putValue(SHORT_DESCRIPTION, "Open Formula Database");
		}

		public void actionPerformed(ActionEvent e) {
			frmChembud.setVisible(true);
			frmMain.setVisible(false);
		}

	}

	class SwingAction2 extends AbstractAction {
		public SwingAction2() {
			putValue(NAME, "Back");
			putValue(SHORT_DESCRIPTION, "Return to Main Menu");
		}

		public void actionPerformed(ActionEvent e) {
			frmChembud.setVisible(false);
			frmMain.setVisible(true);
		}

	}

	class SwingAction4 extends AbstractAction {
		public SwingAction4() {
			putValue(NAME, "Chemical Equation Balancer");
			putValue(SHORT_DESCRIPTION, "Open Chemical Balancer");
		}

		public void actionPerformed(ActionEvent e) {
			frmMain.setVisible(false);
			frmBalancer.setVisible(true);
		}

	}
	
	class SwingAction5 extends AbstractAction {
		public SwingAction5() {
			putValue(NAME, "Balance");
			putValue(SHORT_DESCRIPTION, "Balance the Equation");
		}

		public void actionPerformed(ActionEvent e) {
			boolean finished = true;
			try{
			txtOutput.setText(Balance(txtInput.getText()));
			finished  = false;
			}
			finally{
				if (finished){
					JOptionPane.showMessageDialog(frmMenu, "Make sure to enter a proper formula!");
				}
			}
		}
	}
	class SwingAction8 extends AbstractAction {
		public SwingAction8() {
			putValue(NAME, "Back");
			putValue(SHORT_DESCRIPTION, "Return to Main Menu");
		}

		public void actionPerformed(ActionEvent e) {
			frmConst.setVisible(false);
			frmMain.setVisible(true);
		}

	}
		//Balances an unbalanced chemical equation
		public String Balance(String input) {

			ArrayList<ArrayList<String>> parsed = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> left = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> right = new ArrayList<ArrayList<String>>();

			input = input.replaceAll(" ", "");
			String[] eqn = input.split("=");
			String[] reactants = eqn[0].trim().split("\\+");
			String[] products = eqn[1].trim().split("\\+");

			for (int i = 0; i < reactants.length; i++) {
				ArrayList<String> temp = parser(reactants[i]);
				left.add(temp);
				parsed.add(temp);

			}

			for (int i = 0; i < products.length; i++) {
				ArrayList<String> temp = parser(products[i]);
				right.add(temp);
				parsed.add(temp);

			}

			ArrayList<String> elements = new ArrayList<String>();
			//Creates a list of all the elements present in the chemical reaction
			for (int i = 0; i < parsed.size(); i++) {
				for (int j = 0; j < parsed.get(i).size(); j++) {
					Matcher m = Pattern.compile("[A-Z]+[a-z]*").matcher(parsed.get(i).get(j));
					while (m.find()) {
						int hits = 0;
						for (int k = 0; k < elements.size(); k++) {
							if ((elements.get(k).contains(m.group()))) {
								hits++;
							}
						}
						if (hits == 0) {
							elements.add(m.group());
						}
					}
				}
			}
			double[][] lhsA = new double[elements.size()][left.size() + right.size() - 1];
			double[][] rhsA = new double[elements.size()][1];
			//Creates two matrices with the values of the occurrences of each elements in each term
			for (int i = 0; i < elements.size(); i++) {
				double digi = 0;
				for (int j = 0; j < parsed.get((parsed.size() - 1)).size(); j++) {
					if (parsed.get((parsed.size() - 1)).get(j).contains(elements.get(i))) {
						String digit = "0";

						Matcher m = Pattern.compile("[1-9]").matcher((parsed.get(parsed.size() - 1).get(j)));
						while (m.find()) {
							digit += m.group();
						}
						if (digit.equals("0")) {
							digit = "1";
						}
						digi += Double.parseDouble(digit);

					}

				}
				rhsA[i][0] = digi;

			}

			for (int i = 0; i < (right.size() + left.size() - 1); i++) {

				for (int j = 0; j < elements.size(); j++) {
					double digi = 0;
					for (int k = 0; k < parsed.get(i).size(); k++) {
						if (parsed.get(i).get(k).contains(elements.get(j))) {
							String digit = "0";

							Matcher m = Pattern.compile("[1-9]").matcher((parsed.get(i).get(k)));
							while (m.find()) {
								digit += m.group();
							}
							if (digit.equals("0")) {
								digit = "1";
							}
							digi += Double.parseDouble(digit);

						}

					}
					if (i >= reactants.length && digi != 0) {
						digi = digi * -1;
					}
					lhsA[j][i] = digi;
				}
			}
			
			Matrix lhs = new Matrix(lhsA);
			Matrix rhs = new Matrix(rhsA);
			lhs = lhs.inverse();
			//Solves the matrix
			Matrix balance = lhs.times(rhs);
			double[] coef = new double[balance.getRowDimension()];
			double[] coefde = new double[balance.getRowDimension()];
			for (int i = 0; i < balance.getRowDimension(); i++) {
				coef[i] = (balance.get(i, 0));
				coefde[i] = toFraction((balance.get(i, 0)));
			}
			//Converts decimal numbers into whole numbers
			double factor = lcm(coefde);
			int fin[] = new int[coef.length];
			for (int i = 0; i < coef.length; i++) {
				coef[i] = coef[i] * factor;
				fin[i] = (int) Math.rint((coef[i]));

			}
			//Creates final balanced equation to output to user
			String balanced = "";
			for (int i = 0; i < reactants.length; i++) {
				balanced += String.valueOf(fin[i]) + reactants[i] + "+";
			}
			balanced = balanced.substring(0, balanced.length() - 1) + "=";
			for (int i = 0; i < products.length - 1; i++) {
				balanced += String.valueOf(fin[i + reactants.length]) + products[i] + "+";
			}
			balanced = balanced + String.valueOf((int) factor) + products[products.length - 1];
			return (balanced);
		}

	}


	