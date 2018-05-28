package robot.robotApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import robot.DynamicWheeledRobot;
import robot.TrackedRobot;
import robot.CarrierRobot;
import robot.FlyingRobot;
import robot.WheeledRobot;
import robot.Robot;
import robot.RobotModel;
import robot.forms.FormResolver;
import robot.forms.util.Form;
import robot.forms.util.FormComponent;
import robot.forms.util.FormHandler;
import robot.views.AnimationView;
import robot.views.TableModelAdapter;
import robot.views.Task2;


/**
 * Main program for Robot application. A RobotApp instance sets up a GUI 
 * comprising three views of a RobotModel: an animation view, a table view and
 * a tree view. In addition the GUI includes buttons and associated event
 * handlers to add new robots to the animation and to remove existing robots. 
 * A RobotApp object uses a Timer to progress the animation; this results in the 
 * RobotModel being sent a clock() message to which it responds by moving its
 * constituent Robot objects and then by notifying the three views 
 * (RobotModelListeners). The application uses a RobotConfig object to read 
 * properties from the robot.properties file, one of which is the name of a
 * RobotFactory implementation class that is used to create Robots on request. 
 * 
 * @author Craig Sutherland
 * 
 */
@SuppressWarnings("serial")
public class RobotApp extends JPanel {
	private static final int DELAY = 25;

	// Underlying model for the application.
	private RobotModel _model;
	
	private RobotClassComboBoxModel _comboBoxModel;
	
	// View instances.
	private JTree _treeView;
	private AnimationView _animationView;
	private JTable _tabularView;
	
	/*
	 * Adapter objects (RobotModelListeners) that transform RobotModelEvents 
	 * into Swing TreeModel and TableModel events. 
	 */ 
	private Task2 _treeModelAdapter;
	private TableModelAdapter _tableModelAdapter;
	
	// Swing components to handle user input.
	private JButton _newRobot;
	private JButton _deleteRobot;
	private JComboBox<Class<? extends Robot>> _robotTypes;
	
	// Robot selected in the JTree view.
	private Robot _robotSelected;

	/**
	 * Creates a RobotApp object.
	 */
	public RobotApp() {
		// Instantiate model and populate it with an initial set of robots.
		RobotConfig config = RobotConfig.instance();
		_model = new RobotModel(config.getAnimationBounds());
		populateModel();
		
		_comboBoxModel = new RobotClassComboBoxModel();
		
		// Instantiate GUI objects and construct GUI.
		buildGUI();
		
		// Register views with models.
		_model.addRobotModelListener(_animationView);
		_model.addRobotModelListener(_tableModelAdapter);
		_model.addRobotModelListener(_treeModelAdapter);
		
		// Setup event handlers to process user input.
		setUpEventHandlers();
		
		// Show GUI and ensure the root robot within the JTree view is selected.
		_treeView.setSelectionPath(new TreePath(_model.root()));
		
		// Start animation.
		Timer timer = new Timer(DELAY, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_model.clock();
			}
		} );
		timer.start();
	}

	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Robot");
		JComponent newContentPane = new RobotApp();
		frame.add(newContentPane);
		frame.pack();
	    frame.setLocationRelativeTo(null);
	    frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}
	
	/*
	 * Adds robots to the model.
	 */
	private void populateModel() {
		CarrierRobot root = _model.root();
		
		_model.add(new WheeledRobot(440, 0, 10, 10, 4, 2), root);
		_model.add(new WheeledRobot(0, 0, 5, 7), root);
		_model.add(new TrackedRobot(20, 20, 4, 4, 200, 20, "Robot"), root);
		_model.add(new WheeledRobot(0, 0, 2, 2, 10, 10), root);
		_model.add(new DynamicWheeledRobot(0, 0, 2, 3, 180, 130, "I change color when I bounce", Color.CYAN), root);
		_model.add(new FlyingRobot(50,110,2,2), root);
		

		CarrierRobot child = new CarrierRobot(10, 10, 2, 2, 100, 100);
		//_model.add(new WheeledRobot(10, 10, 10, 10, 4, 2), child);
		_model.add(new DynamicWheeledRobot(0, 0, 2, 3, 50, 80, Color.RED), child);
		_model.add(new FlyingRobot(10,10,2,2, 60, 60), child);
		_model.add(child, root);
	}
	
	/*
	 * Registers event handlers with Swing components to process user inputs.
	 */
	private void setUpEventHandlers() {
		/*
		 * Event handling code to be executed whenever the users presses the 
		 * "New" button. Based on the Robot type selected in the combo box, a
		 * suitable Form/FormHandler pair is acquired from the FormResolver.
		 * The Form is used by the user to specify attribute values for the 
		 * new Robot to be created, and the FormHandler is responsible for 
		 * instantiating the correct Robot subclass and adding the new instance
		 * to the application's RobotModel. 
		 */
		_newRobot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				Class<? extends Robot> cls = (Class<? extends Robot>)_comboBoxModel.getSelectedItem();
				FormComponent form = FormResolver.getForm(cls);
				FormHandler handler = FormResolver.getFormHandler(cls, _model, (CarrierRobot)_robotSelected);
				form.setFormHandler(handler);
				form.prepare();
				
				// Display the form.
				form.setLocationRelativeTo(null);
				form.setVisible(true);

			}
		});
		
		/*
		 * Event handling code to be executed whenever the user presses the
		 * "Delete" button. The robot that is currently selected in the JTree
		 * view is removed from the model. During removal, the removed robot's
		 * former parent is selected in the JTree.
		 */
		_deleteRobot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Robot selection = _robotSelected;
				CarrierRobot parent = selection.parent();
				
				_treeView.setSelectionPath(new TreePath(parent.path().toArray()));
				_model.remove(selection);
				
			}
		});
		
		/*
		 * Event handling code to be executed whenever the user selects a node
		 * within the JTree view. The event handler records which robot is
		 * selected and in addition enables/disables the "New" and "Delete"
		 * buttons appropriately. In addition, the TableModel representing the
		 * the robot selected in the JTree component is informed of the newly
		 * selected robot.
		 */
		_treeView.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				TreePath selectionPath = _treeView.getSelectionPath();
				_robotSelected = (Robot)selectionPath.getLastPathComponent();
				
				/*
				 * Enable button fNewRobot only if what is selected in the 
				 * JTree is a CarrierRobot. Rationale: new robots can only be
				 * added to CarrierRobot instances. 
				 */
				_newRobot.setEnabled(_robotSelected instanceof CarrierRobot);
				
				/*
				 * Enable button fDeleteRobot only if what is selected in the
				 * JTree is not the root node. Rationale: any robot can be 
				 * removed with the exception of the root.
				 */
				_deleteRobot.setEnabled(_robotSelected != _model.root());
				
				/*
				 * Tell the table model to represent the robot that is now
				 * selected in the JTree component.
				 */
				_tableModelAdapter.setAdaptee(_robotSelected);
			}
		});
	}
	
	/*
	 * Creates and lays out GUI components. Note: there is nothing particularly
	 * interesting about this method - it simply builds up a composition of GUI
	 * components and makes use of borders, scroll bars and layout managers. 
	 */
	@SuppressWarnings("unchecked")
	private void buildGUI() {
		// Create Swing model objects.
		_treeModelAdapter = new Task2(_model);
		_tableModelAdapter = new TableModelAdapter(_model.root());
		
		// Create main Swing components.
		_treeView = new JTree(_treeModelAdapter);
		_treeView.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		_tabularView = new JTable(_tableModelAdapter);
		_animationView = new AnimationView(RobotConfig.instance().getAnimationBounds());
		
		/*
		 * Create a panel to house the JTree component. The panel includes a 
		 * titled border and scrollbars that will be activated when necessary.
		 */
		JPanel treePanel = new JPanel();
		treePanel.setBorder(BorderFactory.createTitledBorder("Robot composition hierarchy"));
		JScrollPane scrollPaneForTree = new JScrollPane(_treeView);
		scrollPaneForTree.setPreferredSize(new Dimension(300,504));
		treePanel.add(scrollPaneForTree);
		
		/*
		 * Create a panel to house the animation view. This panel includes a 
		 * titled border and scroll bars if the animation area exceeds the 
		 * allocated screen space.
		 */
		JPanel animationPanel = new JPanel();
		animationPanel.setBorder(BorderFactory.createTitledBorder("Robot animation"));
		JScrollPane scrollPaneForAnimation = new JScrollPane(_animationView);
		scrollPaneForAnimation.setPreferredSize(new Dimension(504,504));
		animationPanel.add(scrollPaneForAnimation);
		_animationView.setPreferredSize(RobotConfig.instance().getAnimationBounds());

		
		/*
		 * Create a panel to house the tabular view. Again, decorate the 
		 * tabular view with a border and enable automatic activation of 
		 * scroll bars.
		 */
		JPanel tablePanel = new JPanel();
		tablePanel.setBorder(BorderFactory.createTitledBorder("Robot state"));
		JScrollPane scrollPaneForTable = new JScrollPane(_tabularView);
		scrollPaneForTable.setPreferredSize(new Dimension(810,150));
		tablePanel.add(scrollPaneForTable);
		
		/*
		 * Create a control panel housing buttons for creating and destroying 
		 * robots, plus a combo box for selecting the type of robot to create.
		 */
		JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		controlPanel.setBorder(BorderFactory.createTitledBorder("Control panel"));
		_newRobot = new JButton("New");
		_deleteRobot = new JButton("Delete");
		_robotTypes = new JComboBox<Class<? extends Robot>>(_comboBoxModel);
		
		/*
		 * Set up a custom renderer for the Combo box. Instead of displaying 
		 * the fully qualified names (that include packages) of Robot 
		 * subclasses, display onlt the class names (without the package 
		 * prefixes).
		 */
		_robotTypes.setRenderer(new BasicComboBoxRenderer() {
			@Override
			public Component getListCellRendererComponent(@SuppressWarnings("rawtypes") JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				String className = value.toString().substring(value.toString().lastIndexOf('.') + 1);
				return super.getListCellRendererComponent(list, className, index, isSelected, cellHasFocus);
			}
		});
		
		
		controlPanel.add(_newRobot);
		controlPanel.add(_deleteRobot);
		controlPanel.add(_robotTypes);
		
		JPanel top = new JPanel(new BorderLayout());
		top.add(animationPanel, BorderLayout.CENTER);
		top.add(treePanel, BorderLayout.WEST);
		top.add(tablePanel, BorderLayout.SOUTH);
		
		setLayout(new BorderLayout());
		add(top, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);
	}
	
	
	/*
	 * Helper class to define a custom model for the Combo box. This 
	 * ComboBoxModel stores Robot subclasses that are acquired from 
	 * RobotConfig. 
	 */
	private class RobotClassComboBoxModel extends
			DefaultComboBoxModel<Class<? extends Robot>> {

		public RobotClassComboBoxModel() {
			List<Class<? extends Robot>> robotClasses = RobotConfig.instance()
					.getRobotClasses();

			for (Class<? extends Robot> cls : robotClasses) {
				addElement(cls);
			}
		}

	}
}
