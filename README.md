# jenkins-windows-library
Windows system support library for jenkins.

## Intent
The main intent of this library is to help a configuration management team to manage windows environments easly without worring about handling the operational systems integrations.

This library is being initialy designed all around the powershell capabilities. So it initialy will only support running on windows based nodes.

## Features
### Powershell
Support windows powershell commands.

#### Exec
Execute a powershell command on the runnig node.
```groovy
powershell.exec('ls')
```

## Library progress
### Powershell
- [x] Execute commands
### File System
- [ ] Manage files and folders in the windows node
- [ ] Manage files and folders on a remote windows host
- [ ] Copy files from the windows node to a windows remote machine
- [ ] Copy artifacts from a job to a windows remote machine
### Windows services
Add the support to manage windows services in a jenkins pipeline.
- [ ] Start
- [ ] Stop
- [ ] Check if exists
- [ ] Uninstall
- [ ] Install
### Internet Information Services (IIS)
Support on managing a Internet Information Services (IIS) on a jenkins pipeline
- [ ] Start IIS
- [ ] Stop IIS
- [ ] Restart IIS
- [ ] Start application pool
- [ ] Stop application pool
- [ ] Create web site
- [ ] Remove web site
- [ ] Edit web site
- [ ] Create application pool
- [ ] Remove application pool
- [ ] Edis application pool
