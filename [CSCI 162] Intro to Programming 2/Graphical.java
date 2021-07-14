import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * An interface for playing the game of Life in a graphical window.
 * 
 * @author David Hutchens, Chad Hogg
 */
public class Graphical extends JPanel implements ActionListener, ChangeListener {
	/** The singleton instance of this class. */
	private static Graphical sharedApp = null;
	/** The serial version of this class. */
	private static final long serialVersionUID = 1L;
	/** The size of a Life cell in the window. */
	private static final int SQUARE_SIZE = 5;
	/** The size of the border around the game. */
	private static final int BORDER_SIZE = 5;

	/** The text inside the run button. */
	private static final String RUN_BUTTON_TEXT = "Run";
	/** The label for the slider that controls birthMin. */
	private static final String BIRTH_MIN_SLIDER_NAME = "Birth Minimum";
	/** The label for the slider that controls birthMax. */
	private static final String BIRTH_MAX_SLIDER_NAME = "Birth Maximum";
	/** The label for the slider that controls liveMin. */
	private static final String LIVE_MIN_SLIDER_NAME = "Live Minimum";
	/** The label for the slider that controls liveMax. */
	private static final String LIVE_MAX_SLIDER_NAME = "Live Maximum";

	/** The timer that controls when updates occur. */
	private Timer myTimer;
	/** The frame on which everything is drawn. */
	private JFrame theFrame;
	/** The Life instance that is being displayed. */
	private Life myLife;
	/** The minimum neighborhood size for new birth. */
	private int birthMin;
	/** The maximum neighborhood size for new birth. */
	private int birthMax;
	/** The minimum neighborhood size for continued life. */
	private int liveMin;
	/** The maximum neighborhood size for continued life. */
	private int liveMax;

	/**
	 * Constructs the object, creating the model and view (with the
	 * frames and widgets) and starts the timer.
	 */
	private Graphical() {
		//Set the look and feel (for Macs too).
		if (System.getProperty("mrj.version") != null) {
			System.setProperty("apple.laf.useScreenMenuBar","true");
		}
		JFrame.setDefaultLookAndFeelDecorated(true);

		myLife = null;
		birthMin = 4;
		birthMax = 6;
		liveMin = 3;
		liveMax = 5;

		theFrame = new JFrame("Life");
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel widgetPanel = setupLayout();
		theFrame.getContentPane().add(widgetPanel, BorderLayout.CENTER);
		//createTextItems(widgetPanel);
		createButtons(widgetPanel);

		theFrame.pack();
		theFrame.setVisible(true);
		
		myTimer = new Timer(1000, this);
		myTimer.start();

	}

	/**
	 * Sets up the layout for the interface.
	 * 
	 * @return The JPanel that will hold the widgets and this UI.
	 */
	private JPanel setupLayout() {
		JPanel widgetPanel = new JPanel();
		widgetPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		widgetPanel.setLayout(new BoxLayout(widgetPanel, BoxLayout.PAGE_AXIS));
		widgetPanel.add(this);

		Dimension puzzleDrawingSize = new Dimension(100 * SQUARE_SIZE + 2 * BORDER_SIZE, 100 * SQUARE_SIZE + 2 * BORDER_SIZE);
		setMinimumSize(puzzleDrawingSize);
		setPreferredSize(puzzleDrawingSize);
		setMaximumSize(puzzleDrawingSize);
		setBorder(BorderFactory.createLineBorder(Color.lightGray, 2));
		return widgetPanel;
	}

	/**
	 * Creates the buttons to control action.
	 * 
	 * @param widgetPanel The JPanel that will hold the buttons.
	 */
	private void createButtons(JPanel widgetPanel) {
		JButton runButton = new JButton(RUN_BUTTON_TEXT);
		runButton.addActionListener(this);
		widgetPanel.add(runButton);
		runButton.setAlignmentX(CENTER_ALIGNMENT);
		createSlider(widgetPanel, BIRTH_MIN_SLIDER_NAME, birthMin);
		createSlider(widgetPanel, BIRTH_MAX_SLIDER_NAME, birthMax);
		createSlider(widgetPanel, LIVE_MIN_SLIDER_NAME, liveMin);
		createSlider(widgetPanel, LIVE_MAX_SLIDER_NAME, liveMax);
	}
	
	/**
	 * Creates a slider and adds it to the widgetPanel with a label.
	 * 
	 * @param widgetPanel The panel that will contain the slider.
	 * @param name The name of the slider.
	 * @param initialValue The initial value of the slider.
	 */
	private void createSlider(JPanel widgetPanel, String name, int initialValue) {
		JPanel panel = new JPanel();
		panel.setAlignmentX(CENTER_ALIGNMENT);
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		widgetPanel.add(panel);
		
		JLabel inputLabel = new JLabel();
		panel.add(inputLabel);
		inputLabel.setText(name);
		inputLabel.setAlignmentX(RIGHT_ALIGNMENT);
		
		JSlider js = new JSlider(JSlider.HORIZONTAL, 0, 9, initialValue);
		js.setName(name);
		js.addChangeListener(this);
		js.setMajorTickSpacing(1);
		js.setMinorTickSpacing(1);
		js.setPaintTicks(true);
		js.setPaintLabels(true);
		js.setSnapToTicks(true);
		panel.add(js);
	}

	/**
	 * Sets the sizes based on the current world.
	 * 
	 * @param world The description of the world to be displayed.
	 */
	public void setSizes(boolean[][] world) {
		Dimension puzzleDrawingSize = new Dimension (world[0].length * SQUARE_SIZE + BORDER_SIZE * 2,
				world.length * SQUARE_SIZE + BORDER_SIZE * 2);
		setMinimumSize(puzzleDrawingSize);
		setPreferredSize(puzzleDrawingSize);
		setMaximumSize(puzzleDrawingSize);
		revalidate();
		theFrame.pack();
	}


	@Override
	public void paintComponent(Graphics gc) { 
		if (isOpaque()) { //paint background
			gc.setColor(getBackground());
			gc.fillRect(0, 0, getWidth(), getHeight());
		}
		if (myLife == null) {
			return;
		}

		gc.translate(BORDER_SIZE, BORDER_SIZE);
		boolean [][] world = myLife.world();
		Color liveColor = new Color(87, 207, 40);
		Color deadColor = new Color(240, 240, 240);
		for (int row = 0; row < world.length; row++) {
			for (int column = 0; column < world[0].length; column++) {
				if (world[row][column]) {
					gc.setColor(liveColor);	
				} else {
					gc.setColor(deadColor); 	
				}
				gc.fillRect(column * SQUARE_SIZE, row * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
			}
		}
		gc.translate(-BORDER_SIZE, -BORDER_SIZE);
	}
	
	@Override
	public void stateChanged(ChangeEvent ce) {
	    JSlider source = (JSlider)ce.getSource();
	    if (!source.getValueIsAdjusting()) {
	        int value = (int)source.getValue();
	        if (source.getName().equals(BIRTH_MIN_SLIDER_NAME)) {
	        	birthMin = value;
	        } else if (source.getName().equals(BIRTH_MAX_SLIDER_NAME)) {
	        	birthMax = value;
	        } else if (source.getName().equals(LIVE_MIN_SLIDER_NAME)) {
	        	liveMin = value;
	        } else if (source.getName().equals(LIVE_MAX_SLIDER_NAME)) {
	        	liveMax = value;
	        } else {
	        	System.out.println("Unknown stateChange source");
	        }
	    }
	}

	@Override
	public void actionPerformed(ActionEvent se) {
		String command = se.getActionCommand();
		if (se.getSource() == myTimer) {
			if (myLife != null) {
				myLife.update();
				repaint();
			}
		} else if (command.equals(RUN_BUTTON_TEXT)) {
			myLife = new Life(System.currentTimeMillis(), 100, 100, birthMin, birthMax, liveMin, liveMax);
			setSizes(myLife.world());
			repaint();
		} else {
			System.out.println("Unknown action: " + command);
		}
	}

	/**
	 * Creates (if necessary) and returns the singleton instance.
	 * 
	 * @return An instance of this class.
	 */
	public static Graphical sharedInstance() {
		if (sharedApp == null) {
			sharedApp = new Graphical();
		}
		return sharedApp;
	}

	/**
	 * Starts the graphical interface.
	 * 
	 * @param args Not used.
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(
				new Runnable() {
					public void run() {
						sharedInstance();
					}
				}
		);

	}

}
