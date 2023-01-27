Make sure you don't source-control anything company-secret.

### This is not my latest version. It's just a snapshot with trade secrets removed
The master is found in an identically named repo in my organization.

## Quick Start

```
/usr/java/java/bin/java  -Dusername=me '-Dpassword=secret' -jar jmx_programmatic.jar | tee /tmp/jmx.txt
```

### Old
```
java -cp jmx_programmatic.git.jar PrintMBeans
```

### See also

* jmx-groovy.git (this version has trade secrets)
* jmx_programmatic_java.git (this version has trade secrets)
** This one uses jshell so is better 
