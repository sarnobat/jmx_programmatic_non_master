import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import javax.management.Attribute;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class SetMBeanAttr {

    public static void main(String[] args) {

        // inputs
        String serviceURL = System.getProperty("url", "service:jmx:rmi:///jndi/rmi://myserver:8999/jmxrmi");
        String objectNameStr = System.getProperty("object", "MyObject");
        String attrName = System.getProperty("attr", "MyProperty");
        String attrVal = System.getProperty("val", "true");

        HashMap<String, String[]> environment = new HashMap<String, String[]>();
        environment.put(JMXConnector.CREDENTIALS, new String[] { "jmxuser", "jmxpassword" });

        MBeanServerConnection mBeanServerConnection;
        try {
            mBeanServerConnection = JMXConnectorFactory.connect(new JMXServiceURL(serviceURL), environment)
                    .getMBeanServerConnection();

            ObjectName objectName = mBeanServerConnection.getObjectInstance(new ObjectName(objectNameStr))
                    .getObjectName();

            mBeanServerConnection.setAttribute(objectName, new Attribute(attrName,
                    Arrays.asList("true", "false").contains(attrVal) ? Boolean.parseBoolean(attrVal) : attrVal));
        } catch (IOException | InstanceNotFoundException | MalformedObjectNameException | AttributeNotFoundException
                | InvalidAttributeValueException | MBeanException | ReflectionException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
