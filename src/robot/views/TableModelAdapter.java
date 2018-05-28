package robot.views;

import javax.swing.table.AbstractTableModel;

import robot.CarrierRobot;
import robot.Robot;
import robot.RobotModelEvent;
import robot.RobotModelListener;

/**
 * Adapter class that adapts Robot/CarrierRobot to the TableModel target 
 * interface. An instance of TableModelAdapter thus allows a Robot/CarrierRobot
 * object to be displayed by a JTable Swing component. To save implementing the
 * TableModel interface from scratch this class extends AbstractTableModel and
 * simply overrides selected methods as necessary.
 * 
 * @author Craig Sutherland
 * 
 */
@SuppressWarnings("serial")
public class TableModelAdapter extends AbstractTableModel implements RobotModelListener {
	/*
	 *  Adaptee, a reference to a Robot or a CarrierRobot object that is to be 
	 *  represented by this TableModel implementation.
	 */
	private Robot _adaptee;
	
	 // Column names for table.
	private static final String[] _columnNames = {"Type", "X-pos", "Y-pos", "X-delta", "Y-delta", "Width", "Height", "Text"};

	/**
	 * Creates a TableModelAdapter and sets Robot/CarrierRobot that is to be 
	 * represented by this TableModelAdapter.
	 */
	public TableModelAdapter(Robot robot) {
		_adaptee = robot;
	}
	
	/**
	 * Returns the number of columns in this TableModel implementation. A 
	 * JTable component will call this method when configured with an instance
	 * of this class. 
	 */
	@Override
	public int getColumnCount() {
		return _columnNames.length;
	}

	/**
	 * Returns the name of a specified column.
	 */
	@Override
	public String getColumnName(int col) {
		return _columnNames[col];
	}
	
	/**
	 * Returns the number of rows in this TableModel implementation. If this 
	 * TableModelAdapter object represents a simple Robot, this method will 
	 * return 1 as a single row is sufficient to display one simple Robot's
	 * attributes. If this TableModelAdapter is set with a CarrierRobot, this
	 * method returns its number of children; one row is then used for each
	 * child Robot. 
	 */
	@Override
	public int getRowCount() {
		int rowCount = 1;
		
		if(_adaptee instanceof CarrierRobot) {
			CarrierRobot nestingRobot = (CarrierRobot)_adaptee;
			rowCount = nestingRobot.robotCount();
		}
		return rowCount;
	}

	/**
	 * Returns the value at a particular cell within this TableModel 
	 * implementation.
	 */
	@Override
	public Object getValueAt(int row, int col) {
		Robot targetRobot = _adaptee;
		Object result = null;
		
		if(_adaptee instanceof CarrierRobot) {
			CarrierRobot nestingRobot = (CarrierRobot)_adaptee;
			targetRobot = nestingRobot.robotAt(row);
		}
		
		switch(col) {
		case 0: // Type
			result = targetRobot.toString();
			break;
		case 1: // X-Pos
			result = targetRobot.x();
			break;
		case 2: // Y-Pos.
			result = targetRobot.y();
			break;
		case 3: // X-delta.
			result = targetRobot.deltaX();
			break;
		case 4: // Y-delta.
			result = targetRobot.deltaY();
			break;
		case 5: // Width.
			result = targetRobot.width();
			break;
		case 6: // Height.
			result = targetRobot.height();
			break;
		case 7: // Text.
			result = targetRobot.text();
			break;
		}
		return result;
	}
	
	/**
	 * Sets the adaptee Robot/CarrierRobot object that should be represented by 
	 * this TableModelAdapter instance.
	 */
	public void setAdaptee(Robot robot) {
		_adaptee = robot;
		
		/*
		 * Cause any TableModelListeners (e.g. a JTable component) to be  
		 * notified that the data stored in this TableModelAdapter has changed.
		 * A JTable component would respond by rebuilding its view of the model.
		 */
		fireTableDataChanged();
	}

	/**
	 * Called by a RobotModel object when it has changed in some way. In 
	 * response to an update() call this TableModelAdapter updates itself
	 * if necessary and notifies its TableModelListeners.
	 */
	@Override
	public void update(RobotModelEvent event) {
		// Unpack event.
		RobotModelEvent.EventType eventType = event.eventType();
		Robot robot = event.operand();
		
		if(eventType == RobotModelEvent.EventType.RobotAdded) {
			CarrierRobot parent = robot.parent();
			if(parent == _adaptee) {
				// The new robots's parent is represented by this TableModel,
				// so the view will need to be updated to show the new robot.
				fireTableRowsInserted(parent.robotCount() - 1, parent.robotCount() - 1);
			}
		} else if(eventType == RobotModelEvent.EventType.RobotRemoved) {
			CarrierRobot parent = event.parent();
			if(parent == _adaptee) {
				// The removed robot's former parent is represented by this 
				// TableModel. Notify the view so that it will no longer show
				// removed robot.
				fireTableDataChanged();
			} 
		} else {
			// Processing a RobotMoved event.
			fireTableDataChanged();
		}
	}
	
}
