import java.util.HashMap;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * Every single bean
 */
public class PrintMBeans {

    public static void main(String[] args) throws Exception {
        HashMap<String, String[]> environment = new HashMap<String, String[]>();
        environment.put(JMXConnector.CREDENTIALS, new String[] { "jmxuser", "jmxpassword" });

        MBeanServerConnection mBeanServerConnection = JMXConnectorFactory
                .connect(new JMXServiceURL("service:jmx:rmi:///jndi/rmi://myserver:8999/jmxrmi"), environment)
                .getMBeanServerConnection();
        for (ObjectName objectName : mBeanServerConnection.queryNames(new ObjectName("*:*"), null)) {
//            System.out.println("PrintMBeans.main() " + objectName);
            MBeanInfo beanInfo = mBeanServerConnection.getMBeanInfo(objectName);
            for (MBeanAttributeInfo attr : beanInfo.getAttributes()) {
                try {
                    Object attribute = mBeanServerConnection.getAttribute(objectName, attr.getName());
                    System.out.println(
                            "PrintMBeans.main() " + objectName + "\t::\t" + attr.getName() + "\t::\t" + attribute);
                } catch (Exception e) {
                    System.err.println("PrintMBeans.main() " + e.getMessage().replaceAll("\n", " "));
                }
            }
        }
    }

}
