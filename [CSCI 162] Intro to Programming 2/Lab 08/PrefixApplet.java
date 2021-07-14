import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JApplet;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;

/**
 * A simple graphical program for evaluating prefix expressions.
 * 
 * @author Beth Katz, Chad Hogg
 */
public class PrefixApplet extends JApplet implements ActionListener { 
	/** The version number. */
	private static final long serialVersionUID = 1L;
	/** The desired width of the window. */
	private static final int APPLET_WIDTH = 400;
	/** The desired height of the window. */
	private static final int APPLET_HEIGHT = 200;
	/** The text with instructions. */
	private static final String LABEL_TEXT = "Enter prefix expression";
	/** The height of the output area. */
	private static final int OUTPUT_ROWS = 5;
	/** The width of the output area. */
	private static final int OUTPUT_COLUMNS = 25;
	/** The size of the input field. */
	private static final int TEXT_SIZE = 14;
	/** The area where the user can type input. */
	private JTextField inputField;
	/** The area where the result is displayed. */
	private JTextArea outputField;

	/**
	 * Initializes the applet.
	 * 
	 * @postcondition All of the graphical components have been created and arranged.
	 */
	public void init() {
		// set up layout of components
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		createTextItems(contentPane);
		contentPane.setOpaque(true);
		setContentPane(contentPane);
		setSize(APPLET_WIDTH, APPLET_HEIGHT);
	}

	/**
	 * Creates the text items including places to enter text and labels.
	 * Labels cannot be changed by the user.
	 * Text can be changed by the user unless it's not editable.
	 * 
	 * @param widgetPanel The JPanel that will hold the text items.
	 * @postcondition All of the text elements have been created and arranged.
	 */
	private void createTextItems(JPanel widgetPanel) {
		widgetPanel.add(Box.createRigidArea(new Dimension(0,5)));

		JPanel panel = new JPanel();
		widgetPanel.add(panel);

		JLabel inputLabel = new JLabel();
		panel.add(inputLabel);
		inputLabel.setText(LABEL_TEXT);
		inputLabel.setFont(new Font("Times", Font.BOLD, 18));

		inputField = new JTextField("", OUTPUT_COLUMNS - 5);
		panel.add(inputField);
		inputField.setFont(new Font("Times", Font.PLAIN, TEXT_SIZE));
		inputField.setEditable(true);
		inputField.addActionListener(this);

		outputField = new JTextArea("", OUTPUT_ROWS, OUTPUT_COLUMNS);
		panel.add(outputField);
		outputField.setEditable(false);
		outputField.setFont(new Font("Times", Font.PLAIN, TEXT_SIZE));
		outputField.setBorder(BorderFactory.createLineBorder(Color.black));
		outputField.setLineWrap(true);
		outputField.setWrapStyleWord(true);
	}

	/**
	 * Responds to the user pressing enter.
	 * 
	 * @param evt The event that occurred.
	 * @postcondition The text in the input field has been evaluated, and its result is displayed
	 *   in the output field.
	 */
	public void actionPerformed(ActionEvent evt) {
		String text = inputField.getText();
		inputField.selectAll();
		outputField.setText( text + "\nresults in\n" + PrefixEvaluator.evaluate(text));
	}

}
