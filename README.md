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
Manage Windows files inside a jenkins pipeline.

#### Example
```groovy
filesystem.copy fromPath, toPath
//or with debug param 
filesystem.copy debug:true, fromPath, toPath
```

#### copy file
Copy file to other directory
```groovy
filesystem.copy 'C:\\Default\\file.txt', 'C:\\DefaultCopy'
//or with debug param 
filesystem.copy debug:true, 'C:\\Default\\file.txt', 'C:\\DefaultCopy'
```

#### copy folder
Copy folder to other directory
```groovy
filesystem.copy 'C:\\Default', 'C:\\DefaultCopy'
//or with debug param 
filesystem.copy debug:true, 'C:\\Default', 'C:\\DefaultCopy'
```

#### copy files by extension
Copy all files in the folder according to the extension
```groovy
filesystem.copy 'C:\\Default\\*.txt', 'C:\\DefaultCopy'
//or with debug param 
filesystem.copy debug:true, 'C:\\Default\\*.txt', 'C:\\DefaultCopy'
```

#### copy files from remote machine
Copy files from remote machine to local directory
```groovy
filesystem.copy '\\\\RemoteMachine\\Default\\RemoteFile.txt', 'C:\\Default'
//or with debug param 
filesystem.copy debug:true, '\\\\RemoteMachine\\Default\\RemoteFile.txt', 'C:\\Default'
```

### IIS
Manage internet services

#### Start W3svc
Start W3svc from remote machine
```groovy
iis.start 'RemoteMachine'
```groovy
iis.start debug: true, 'RemoteMachine'
```

#### Stop W3svc
Stop W3svc from remote machine
```groovy
iis.stop 'RemoteMachine'
```groovy
//or with debug param
iis.stop debug: true, 'RemoteMachine'
```

#### Restart W3svc
Stop W3svc from remote machine
```groovy
iis.stop 'RemoteMachine'
```groovy
//or with debug param
iis.stop debug: true, 'RemoteMachine'
```

#### Start AplicationPool
Start Aplication Pool from remote machine
```groovy
iis.startAppPool 'AppPoolName','RemoteMachine'
```groovy
//or with debug param
iis.startAppPool debug: true, 'AppPoolName','RemoteMachine'
```

#### Stop AplicationPool
Stop Aplication Pool from remote machine
```groovy
iis.stopAppPool 'AppPoolName','RemoteMachine'
```groovy
//or with debug param
iis.stopAppPool debug: true, 'AppPoolName','RemoteMachine'
```

#### Restart AplicationPool
Restart Aplication Pool from remote machine
```groovy
iis.stopAppPool 'AppPoolName','RemoteMachine'
```groovy
//or with debug param
iis.stopAppPool debug: true, 'AppPoolName','RemoteMachine'
```

#### AplicationPool Exist
Verify if an application pool exist
```groovy
iis.appPoolExist 'AppPoolName','RemoteMachine'
```groovy
//or with debug param
iis.appPoolExist debug: true, 'AppPoolName','RemoteMachine'
```

#### AplicationPool State
Verify if an application pool state
```groovy
iis.getAppPoolState 'AppPoolName','RemoteMachine'
```groovy
//or with debug param
iis.getAppPoolState debug: true, 'AppPoolName','RemoteMachine'
```

#### New AplicationPool
Create a new aplication pool
```groovy
iis.newAppPool 'AppPoolName','RemoteMachine'
```groovy
//or with debug param
iis.newAppPool debug: true, 'AppPoolName','RemoteMachine'
```

#### Remove AplicationPool
Remove an aplication pool
```groovy
iis.removeAppPool 'AppPoolName','RemoteMachine'
```groovy
//or with debug param
iis.removeAppPool debug: true, 'AppPoolName','RemoteMachine'
```

#### Start WebSite
Start Web Site from remote machine
```groovy
iis.startWebSite 'WebSiteName','RemoteMachine'
```groovy
//or with debug param
iis.startWebSite debug: true, 'WebSiteName','RemoteMachine'
```

#### Stop WebSite
Stop Web Site from remote machine
```groovy
iis.stopWebSite 'WebSiteName','RemoteMachine'
```groovy
//or with debug param
iis.stopWebSite debug: true, 'WebSiteName','RemoteMachine'
```


#### New WebSite
Create a new Web Site on a remote machine
```groovy
iis.newWebSite 'WebSiteName','RemoteMachine'
```groovy
//or with debug param
iis.newWebSite debug: true, 'WebSiteName','RemoteMachine'
```


#### New WebSite
Remove a new Web Site from a remote machine
```groovy
iis.removeWebSite 'WebSiteName','RemoteMachine'
```groovy
//or with debug param
iis.removeWebSite debug: true, 'WebSiteName','RemoteMachine'
```

#### WebSite State
Verify WebSite pool state
```groovy
iis.getWebSiteState 'WebSiteName','RemoteMachine'
```groovy
//or with debug param
iis.getWebSiteState debug: true, 'WebSiteName','RemoteMachine'
```

> Note that the "\\" bar is an escape character, so it should be duplicated.

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
- [x] Start IIS
- [x] Stop IIS
- [x] Restart IIS
- [x] Start application pool
- [x] Stop application pool
- [x] Create web site
- [x] Remove web site
- [ ] Edit web site
- [x] Create application pool
- [x] Remove application pool
- [ ] Edit application pool

## Access control
Since the library is executed on the context of the jenkins windows service node, the user running the server must have the correct rights to execute the actions on the remote machine. On a domain based network use a domain user to run the jenkins windows service and give this user the needed access on the managed hosts. If you are not in a domain based network just create a user with the same username and password on the managed machines and give the rights needed access to this user.