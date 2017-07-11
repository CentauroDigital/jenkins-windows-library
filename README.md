# jenkins-windows-library
Windows system support library for jenkins pipeline.

## Intent
The main intent of this library is to help a configuration management team to manage windows environments easly without worring about handling the operational systems integrations.

This library is being initialy designed all around the powershell capabilities. So it initialy will only support running on windows based nodes.

## How to use it
### Dependencies
This library relies on the jenkins powershell plugin. Your jenkins must have this the plugin installed. To know more about the plugin visit [PowerShell Plugin](https://wiki.jenkins.io/display/JENKINS/PowerShell+Plugin) page.
### Installing
To use this library you must add the library github source on your jenkins. Follow the instructions from this page: [Using libraries](https://jenkins.io/doc/book/pipeline/shared-libraries/#using-libraries)

### Using
To use the library in one job add the line below at the start of your plugin.
```groovy
@Library('my-library-name') _
```
> Note that the library name must be the same added on the jenkins.

If you plan on using the library on your entire Jenkins at the library settings select the _Load implicitly_ option.

## Features

### Windows services
Manage windows services on remote machines inside a jenkins pipeline.

#### isInstalled
Verifies if a windows service is installed on the server
```groovy
def installed = windowsservice.isInstalled 'localhost', 'wmiApSrv'
//or
def installed = windowsservice.isInstalled('localhost', 'wmiApSrv')
```

#### getStatus
Get the current status of a windows service.
```groovy
def status = windowsservice.getStatus 'localhost', 'wmiApSrv'
//or
def status = windowsservice.getStatus('localhost', 'wmiApSrv')
```

#### start
Start a windows service.
```groovy
windowsservice.start 'localhost', 'wmiApSrv'
//or
windowsservice.start('localhost', 'wmiApSrv')
```

#### stop
Stop a windows service.
```groovy
windowsservice.stop 'localhost', 'wmiApSrv'
//or
windowsservice.stop('localhost', 'wmiApSrv')
```

### File System
Manage Windows files on remote machines.

#### Example
```groovy
filesystem.copy fromPath, toPath
//or with debug param 
filesystem.copy debug:true, fromPath, toPath
```

#### copy file
Copy file to other directory
```groovy
filesystem.copy 'C:\\Users\\Default\\file.txt', 'C:\\Users\\DefaultCopy'
//or with debug param 
filesystem.copy debug:true, 'C:\\Users\\Default\\file.txt', 'C:\\Users\\DefaultCopy'
```

#### copy folder
Copy folder to other directory
```groovy
filesystem.copy 'C:\\Users\\Default', 'C:\\Users\\DefaultCopy'
//or with debug param 
filesystem.copy debug:true, 'C:\\Users\\Default', 'C:\\Users\\DefaultCopy'
```

#### copy files by extension
Copy all files in the folder according to the extension
```groovy
filesystem.copy 'C:\\Users\\Default\\*.txt', 'C:\\Users\\DefaultCopy'
//or with debug param 
filesystem.copy debug:true, 'C:\\Users\\Default\\*.txt', 'C:\\Users\\DefaultCopy'
```

#### copy files from remote machine
Copy files from remote machine to local directory
```groovy
filesystem.copy '\\\\RemoteMachine\\Default\\RemoteFile.txt', 'C:\\Users\\Default'
//or with debug param 
filesystem.copy debug:true, '\\\\RemoteMachine\\Default\\RemoteFile.txt', 'C:\\Users\\Default'
```

## Roadmap
#### Windows services
- [x] Start
- [x] Stop
- [x] Check if exists
- [x] Uninstall
- [ ] Install
#### File System
- [x] Manage files and folders in the windows node
- [x] Manage files and folders on a remote windows host
- [x] Copy files from the windows node to a windows remote machine
- [ ] Copy artifacts from a job to a windows remote machine
#### Internet Information Services (IIS)
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

## Access control
Since the library is executed on the context of the jenkins windows service node, the user running the server must have the correct rights to execute the actions on the remote machine. On a domain based network use a domain user to run the jenkins windows service and give this user the needed access on the managed hosts. If you are not in a domain based network just create a user with the same username and password on the managed machines and give the rights needed access to this user.