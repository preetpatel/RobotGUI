package robot.views;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import robot.CarrierRobot;
import robot.Robot;
import robot.RobotModel;

public class Task1 implements TreeModel {
	private RobotModel _adaptee;
	public Task1(RobotModel model) {
		_adaptee = model;
	}
	@Override
	public void addTreeModelListener(TreeModelListener TMListner) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getChild(Object _parent, int index) {
		if (_parent instanceof CarrierRobot) {
			try {
			return ((CarrierRobot) _parent).robotAt(index);
			} catch (IndexOutOfBoundsException e) {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public int getChildCount(Object robot) {
		if (robot instanceof CarrierRobot) {
			return ((CarrierRobot) robot).robotCount();
		} else {
			return 0;
		}
	}

	@Override
	public int getIndexOfChild(Object _parent, Object _child) {
		if (_parent instanceof CarrierRobot && _child instanceof Robot) {
			CarrierRobot parent = (CarrierRobot) _parent;
			Robot child = (Robot) _child;
			return parent.indexOf(child);
		} else {
			return -1;
		}
	}

	@Override
	public Object getRoot() {
		// TODO Auto-generated method stub
		return _adaptee.root();
	}

	@Override
	public boolean isLeaf(Object robot) {
		if (robot instanceof CarrierRobot) {
			return false;
		} else if (robot instanceof Robot) {
				return true;
			} else {
				throw new ClassCastException();
			}
		
	}

	@Override
	public void removeTreeModelListener(TreeModelListener arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void valueForPathChanged(TreePath arg0, Object arg1) {
		throw new UnsupportedOperationException();
	}

}
