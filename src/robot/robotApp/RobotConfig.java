package robot.robotApp;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import robot.Robot;


/**
 * Singleton class that allows easy access to properties specified in the 
 * Robot application's properties file, named robot.properties. This file
 * should be stored in the user's home directory. On Windows this is:
 * C:/Users/<username>. 
 * 
 * @author Craig Sutherland
 *
 */
public class RobotConfig {
	// Location of properties file.
	private static final String FILE_LOCATION = "user.home";

	// Name of properties file.
	private static final String FILE_NAME = "robot.properties";
	
	// Property default values.
	public static final int DEFAULT_ANIMATION_WIDTH = 500;
	public static final int DEFAULT_ANIMATION_HEIGHT = 500;
	public static final int MAX_ANIMATION_WIDTH = 1000;
	public static final int MAX_ANIMATION_HEIGHT = 1000;
	
	// Property names (keys).
	private static final String ANIMATION_WIDTH = "animation_width";
	private static final String ANIMATION_HEIGHT = "animation_height";
	private static final String ROBOTS = "robot_classes";
	
	// Property values.
	private Dimension _bounds;
	private String[] _robotClassNames;
	private List<Class<? extends Robot>> _robotClasses;
	
	// Singleton instance.
	private static RobotConfig instance;
	
	/**
	 * Returns a reference to the RobotConfig object.
	 */
	public static RobotConfig instance() {
		if(instance == null) {
			instance = new RobotConfig();
		}
		return instance;
	}
	
	/*
	 * Hidden constructor that populates the RobotConfig instance with 
	 * property values. 
	 */
	private RobotConfig() {
		//System.setProperty("user.home", "H://");
		String path = System.getProperty(FILE_LOCATION);
		System.out.println("path " + path);
		File file = new File(path, FILE_NAME);
	
		// Create a new properties object.
		Properties props = new Properties();
	
		try {
			// Attempt to read property values from file.
			InputStream in = new FileInputStream(file);
			props.load(in);
			in.close();
		} catch(IOException e) {
			// Properties file not found, or an error has occurred reading the file.
			// No action necessary.
		} finally {
			// Read bounds property.
			int width = getBound(ANIMATION_WIDTH, DEFAULT_ANIMATION_WIDTH, props);
			int height = getBound(ANIMATION_HEIGHT, DEFAULT_ANIMATION_HEIGHT, props);
			
			/*
			 * Restore bounds to default values if invalid values have been 
			 * read from the properties file.
			 */
			if(width < DEFAULT_ANIMATION_WIDTH || width > MAX_ANIMATION_WIDTH) {
				width = DEFAULT_ANIMATION_WIDTH;
			} 
			if(height < DEFAULT_ANIMATION_HEIGHT || width > MAX_ANIMATION_HEIGHT) {
				height = DEFAULT_ANIMATION_HEIGHT;
			}
			_bounds = new Dimension(width, height);
			
			// Set robots property.
			String robotTypes = props.getProperty(ROBOTS);
			if(robotTypes == null) {
				// Create an empty array if no robot names are given.
				_robotClassNames = new String[0];
			} else {
				_robotClassNames = robotTypes.split("\\s+");
			}
		}
	} 
	
	/**
	 * Returns the bounds of the world in which robots move around. This method
	 * returns bounds in the range DEFAULT_ANIMATION_WIDTH/HEIGHT .. 
	 * MAX_ANIMATION_WIDTH/HEIGHT. In the properties file specifies bounds 
	 * outside of this range, they are ignored and DEFAULT_ANIMATION_WIDTH/
	 * HEIGHT are returned.
	 */
	public Dimension getAnimationBounds() {
		return _bounds;
	}
	
	/**
	 * Returns an array of strings containing names of robot classes. If no 
	 * robot classes are named in the properties file, this method returns an 
	 * empty array.
	 */
	public List<String> getRobotClassNames() {
		List<String> result = Arrays.asList(_robotClassNames);
		Collections.sort(result);
		
		return result;
	}
	
	public List<Class<? extends Robot>> getRobotClasses() {
		// If the List of Robot classes has already been created, simply return
		// it.
		if(_robotClasses != null) {
			return _robotClasses;
		}
		
		// Initialise the List of classes.
		_robotClasses = new ArrayList<Class<? extends Robot>>();
		
		for(int i = 0; i < _robotClassNames.length; i++) {
			String className = _robotClassNames[i];
			
			try {
				@SuppressWarnings("unchecked")
				Class<? extends Robot> newClass = (Class<? extends Robot>) Class.forName(className);
				_robotClasses.add(newClass);
			} catch(ClassNotFoundException | ClassCastException e) {
				// Ignore the class loading error. The class either doesn't 
				// exist or isn't a Robot subclass.
			}
		}
		
		// Sort the classes based on name.
		Collections.sort(_robotClasses, new Comparator<Class<? extends Robot>>() {
			@Override
			public int compare(Class<? extends Robot> class1,
					Class<? extends Robot> class2) {
				return class1.getName().compareTo(class2.getName());
			}
		});
		
		
		// Return an unmodifiable collection so that clients cannot change the 
		// contents of the returned list (which is part of the state of the 
		// RobotConfig object).
		return Collections.unmodifiableList(_robotClasses);
	}
	
	/*
	 * Implementation method to read/validate bound properties.
	 */
	private int getBound(String propertyName, int defaultValue, Properties props) {
		int property = defaultValue;
		String propertyStr = props.getProperty(propertyName);
		if(propertyStr != null) {
			try {
				property = Integer.parseInt(propertyStr);
			} catch(NumberFormatException e) {
				// No action necessary - fall back on default setting for property.
			}
		}
		return property;
	}
	
	/**
	 * Main method to simply output the properties held by a RobotConfig 
	 * object.
	 */
	public static void main(String[] args) {
		RobotConfig config = RobotConfig.instance();
		List<Class<? extends Robot>> classes = config.getRobotClasses();
		
		System.out.println("Animation bounds ...");
		System.out.println("  " + config.getAnimationBounds());
		
		System.out.println("Robot class names ... ");
		for(String className : config.getRobotClassNames()) {
			System.out.println("  " + className);
		}
		
		System.out.println("Robot subclasses successfully loaded ...");
		for(Class<? extends Robot> cls : classes) {
			System.out.println("  " + cls.getName());
		}
	}
}
