import java.io.IOException;
import java.util.HashMap;

import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class PrintMBean {

	public static void main(String[] args) {

		// inputs
		String serviceURL = System.getProperty("url", "service:jmx:rmi:///jndi/rmi://localhost:8999/jmxrmi");
		String objectNameStr = System.getProperty("object", "MyObject");

		HashMap<String, String[]> environment = new HashMap<String, String[]>();
		environment.put(JMXConnector.CREDENTIALS,
				new String[] { System.getProperty("user", "jmxuser"), System.getProperty("pwd", "jmxpassword") });

		MBeanServerConnection mBeanServerConnection;
		try {
			mBeanServerConnection = JMXConnectorFactory.connect(new JMXServiceURL(serviceURL), environment)
					.getMBeanServerConnection();

			ObjectName objectName1 = mBeanServerConnection.getObjectInstance(new ObjectName(objectNameStr))
					.getObjectName();

			for (ObjectName objectName : mBeanServerConnection.queryNames(objectName1, null)) {
				MBeanInfo beanInfo = mBeanServerConnection.getMBeanInfo(objectName);
				for (MBeanAttributeInfo attr : beanInfo.getAttributes()) {
					try {
						Object attribute = mBeanServerConnection.getAttribute(objectName, attr.getName());
						System.out.println(objectName + "\t::\t" + attr.getName() + "\t::\t" + attribute);
					} catch (Exception e) {
						System.err.println("PrintMBeans.main() " + e.getMessage().replaceAll("\n", " "));
					}
				}
				System.out.println();
			}
		} catch (IOException | InstanceNotFoundException | MalformedObjectNameException | ReflectionException
				| IntrospectionException e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}

}
