![Logo](https://i.imgur.com/DqzzanC.png)
<h1> Extension To <a href= "https://github.com/PreetPatel/RobotAssignment">RobotAssignment</a> </h1>
This is an extension to the Robot Assignment repo. It uses the different types of robots created in that repo to create a graphical application which uses JSwing and multiple design patterns. RobotGUI is a model/view application that presents three views of a shared model. Such applications are commonplace and introduce the need for views to be mutually consistent and synchronised with a model whose state changes at run-time.

<h2> Screenshots </h2>

![alt text](https://i.imgur.com/DqzzanC.png)

<h2> Usage </h2>
With this GUI, you can add as many robots and customise their characteristics. Robots with custom images are able to be loaded as well and properties such as speed and size can be set individually.
On  startup  the  Robot  application  attempts  to  read  configuration  information  from  the  filenamedrobot.properties.   You  should  locate  this  file  within  your  logon  directory.   For  homePCs running Windows, the logon directory isC:/Users/username.  You should ensure that the robot classes property  lists  the  fully  qualified  names  of Robot subclasses  that  you  want  the application to use.  Essentially,  the combo box on the GUI is populated with the names of the classes you specify for the robot classes property.  Hence when using the application you can specify the kind of robot you want to add to the animation.  In specifying class names for this property, each name should be separated by whitespace; note however that if names are spread across multiple lines a \ character should end each line except the last.  For example, the following specifies that two classes should be loaded:
<pre>robotclasses = robot.WheeledRobot\<br />
  robot.CarrierRobot</pre>
