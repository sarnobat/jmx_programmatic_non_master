import java.util.HashMap;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * Single Bean attribute
 */
public class PrintMBeanAttr {

    public static void main(String[] args) throws Exception {
        HashMap<String, String[]> environment = new HashMap<String, String[]>();
        environment.put(JMXConnector.CREDENTIALS,
                new String[] { System.getProperty("user", "jmxuser"), System.getProperty("pwd", "jmxpassword") });

        String serviceURL = System.getProperty("url", "service:jmx:rmi:///jndi/rmi://myserver:8999/jmxrmi");
        MBeanServerConnection mBeanServerConnection = JMXConnectorFactory
                .connect(new JMXServiceURL(serviceURL), environment).getMBeanServerConnection();
        String name = System.getProperty("object", "MyObject");
        ObjectName objectName = mBeanServerConnection.getObjectInstance(new ObjectName(name)).getObjectName();
        try {
            String attrName = System.getProperty("attr", "MyAttr");
            Object attribute = mBeanServerConnection.getAttribute(objectName, attrName);
            System.out.println(objectName.getCanonicalName() + "  " + attrName + " = " + attribute);
        } catch (Exception e) {
            System.err.println("PrintMBeans.main() " + e.getMessage().replaceAll("\n", " "));
        }
    }

}
