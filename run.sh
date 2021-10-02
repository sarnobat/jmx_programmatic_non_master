java -classpath bin/ \
	-Durl="service:jmx:rmi:///jndi/rmi://myserver:8999/jmxrmi" \
	-Dobject="MyObject" \
	-Dattr="MyAttr"  \
	-Dval=${1:-"true"}  \
	SetMBeanAttr
	
repeat ${2:-4000} <<EOF | sh
sleep 1
java -classpath bin/ \
	-Durl="service:jmx:rmi:///jndi/rmi://MyServer:8999/jmxrmi" \
	-Dobject="MyObject" \
	PrintMBeans
EOF
