package br.com.centaurotech

def start(Map map = [:], server) {
    def debug =  map.debug ?: false

    if (server == null) {
        if (debug) echo "[jenkins-windows-library iis] [DEBUG] start method called. Command: Start-Service W3SVC"
        powershell "Start-Service W3SVC"
    } else {
        if (debug) echo "[jenkins-windows-library iis] [DEBUG] start method called. Command: Invoke-Command -ComputerName \"$server\" -ScriptBlock { Start-Service W3SVC }"
        powershell "Invoke-Command -ComputerName \"$server\" -ScriptBlock { Start-Service W3SVC }"
    }
}

def stop(Map map = [:], server) {
    def debug =  map.debug ?: false

    if (server == null) {
        if (debug) echo "[jenkins-windows-library iis] [DEBUG] stop method called. Command: Stop-Service W3SVC"
        powershell "Stop-Service W3SVC"
    } else {
        if (debug) echo "[jenkins-windows-library iis] [DEBUG] stop method called. Command: Invoke-Command -ComputerName \"$server\" -ScriptBlock { Stop-Service W3SVC }"
        powershell "Invoke-Command -ComputerName \"$server\" -ScriptBlock { Stop-Service W3SVC }"
    }
}

def restart(Map map = [:], server) {
    if (debug) echo "[jenkins-windows-library iis] [DEBUG] restart method called."

    stop(map, server)
    start(map, server)
}

def startAppPool(Map map = [:], pool, server) {
    def debug =  map.debug ?: false
    def common = new common()

    def exist = appPoolExist(map, pool, server)
    if (exist == false) {
        if (debug) echo "[jenkins-windows-library iis] [DEBUG] start app pool method called. AppPool: $pool . Message: AppPool not exists"
    } else {
        def statusToStart = common.getstatusToStart()
        def status = getAppPoolState(map, pool, server)

        if (common.isStatusInArray(status, statusToStart)) {
            if (server == null) {
                if (debug) echo "[jenkins-windows-library iis] [DEBUG] start app pool method called. AppPool: $pool . Command: Start-WebAppPool -Name \"$pool\""
                powershell "Start-WebAppPool -Name \"$pool\""    
            } else {
                if (debug) echo "[jenkins-windows-library iis] [DEBUG] start app pool method called. AppPool: $pool . Command: Invoke-Command -ComputerName \"$server\" -ScriptBlock { Start-WebAppPool -Name \"$pool\" }"
                powershell "Invoke-Command -ComputerName \"$server\" -ScriptBlock { Start-WebAppPool -Name \"$pool\" }"
            }
        } else {
             if (debug) echo "[jenkins-windows-library iis] [DEBUG] start app pool method called. AppPool: $pool . Message: AppPool alerady running"
            return
        }
    }
}

def restartAppPool(Map map = [:], pool, server) {
    def debug =  map.debug ?: false
    def common = new common()

    def exist = appPoolExist(map, pool, server)
    if (exist == false) {
        if (debug) echo "[jenkins-windows-library iis] [DEBUG] restart app pool method called. AppPool: $pool . Message: AppPool not exists"
    } else {
        def status = getAppPoolState(map, pool, server)

        if (status == "Running") {
           if (server == null) {
                if (debug) echo "[jenkins-windows-library iis] [DEBUG] restart app pool method called. AppPool: $pool . Command: Restart-WebAppPool -Name \"$pool\""
                powershell "Restart-WebAppPool -Name \"$pool\""    
            } else {
                if (debug) echo "[jenkins-windows-library iis] [DEBUG] restart app pool method called. AppPool: $pool . Command:Invoke-Command -ComputerName \"$server\" -ScriptBlock { Restart-WebAppPool -Name \"$pool\" }"
                powershell "Invoke-Command -ComputerName \"$server\" -ScriptBlock { Restart-WebAppPool -Name \"$pool\" }"
            }
        } else {
            if (debug) echo "[jenkins-windows-library iis] [DEBUG] restart app pool method called. AppPool: $pool . Message: AppPool is not running"
            return
        }
    }
}

def stopAppPool(Map map = [:], pool, server) {
    def debug =  map.debug ?: false
    def common = new common()

    def exist = appPoolExist(map, pool, server)
    if (exist == false) {
        if (debug) echo "[jenkins-windows-library iis] [DEBUG] stop app pool method called. AppPool: $pool . Message: AppPool not exists"
    } else {
        def statusToStop = common.getStatusToStop()
        def status = getAppPoolState(map, pool, server)

        if (common.isStatusInArray(status, statusToStop)) {
           if (server == null) {
                if (debug) echo "[jenkins-windows-library iis] [DEBUG] stop app pool method called. AppPool: $pool . Command: Stop-WebAppPool -Name \"$pool\""
                powershell "Stop-WebAppPool -Name \"$pool\""    
            } else {
                if (debug) echo "[jenkins-windows-library iis] [DEBUG] stop app pool method called. AppPool: $pool . Command:Invoke-Command -ComputerName \"$server\" -ScriptBlock { Stop-WebAppPool -Name \"$pool\" }"
                powershell "Invoke-Command -ComputerName \"$server\" -ScriptBlock { Stop-WebAppPool -Name \"$pool\" }"
            }
        } else {
            if (debug) echo "[jenkins-windows-library iis] [DEBUG] stop app pool method called. AppPool: $pool . Message: AppPool alerady stopped"
            return
        }
    }
}

def newAppPool(Map map = [:], pool, server) {
    def debug =  map.debug ?: false
    if (debug) echo "Server name: $server"
   
    def exist = appPoolExist(map, pool, server)
    if (debug) echo "Server exists: $exist"
    
    if (exist == true) {
        if (debug) echo "[jenkins-windows-library iis] [DEBUG] new app pool method called. AppPool: $pool . Message: AppPool already exists"
    } else {
        if (!server) {
            if (debug) echo "[jenkins-windows-library iis] [DEBUG] new app pool method called. AppPool: $pool . Command: New-WebAppPool -Name \"$pool\""
            powershell "New-WebAppPool -Name \"$pool\""    
        } else {
            if (debug) echo "[jenkins-windows-library iis] [DEBUG] new app pool method called. AppPool: $pool . Command: Invoke-Command -ComputerName \"$server\" -ScriptBlock { New-WebAppPool -Name \"$pool\" }"
            powershell "Invoke-Command -ComputerName \"$server\" -ScriptBlock { New-WebAppPool -Name \"$pool\" }"
        }
    }
}

def removeAppPool(Map map = [:], pool, server) {
    def debug =  map.debug ?: false

    def exist = appPoolExist(map, pool, server)
    if (exist == false) {
        if (debug) echo "[jenkins-windows-library iis] [DEBUG] remove app pool method called. AppPool: $pool . Message: AppPool not exists"
    } else {
        if (server == null) {
            if (debug) echo "[jenkins-windows-library iis] [DEBUG] remove app pool method called. AppPool: $pool . Command: Remove-WebAppPool -Name \"$pool\""
            powershell "Remove-WebAppPool -Name \"$pool\""    
        } else {
            if (debug) echo "[jenkins-windows-library iis] [DEBUG] remove app pool method called. AppPool: $pool . Command: Invoke-Command -ComputerName \"$server\" -ScriptBlock { Remove-WebAppPool -Name \"$pool\" }"
            powershell "Invoke-Command -ComputerName \"$server\" -ScriptBlock { Remove-WebAppPool -Name \"$pool\" }"
        }
    }
}

def appPoolExist(Map map = [:], pool, server) {
  
    def debug =  map.debug ?: false
    def exist =  false

    if (server == null) {
        if (debug) echo "[jenkins-windows-library iis] [DEBUG] app pool exist method called. AppPool: $pool . Command: Test-Path \"IIS:\\\\AppPools\\$pool\""

        def pwret = powershell returnStdout: true, script:"Test-Path \"IIS:\\\\AppPools\\$pool\""
        if (pwret.trim() == "True") {
            exist = true
        }
    } else {
        if (debug) echo "[jenkins-windows-library iis] [DEBUG] app pool exist method called. AppPool: $pool . Command: Invoke-Command -ComputerName \"$server\" -ScriptBlock { import-module webadministration  Test-Path \"IIS:\\\\AppPools\\$pool\" }"

        def pwret = powershell returnStdout: true, script:"Invoke-Command -ComputerName \"$server\" -ScriptBlock { import-module webadministration \n Test-Path \"IIS:\\\\AppPools\\$pool\" }"
        if(debug) echo "AppPoolExist? " + pwret.trim()
        if (pwret.trim() == "True") {
            exist = true
        }
    }

    return exist
}

def getAppPoolState(Map map = [:], pool, server) {
    def debug =  map.debug ?: false

    def exist = appPoolExist(map, pool, server)
    if (exist == false) {
        if (debug) echo "[jenkins-windows-library iis] [DEBUG] get app pool state method called. AppPool: $pool . Message: AppPool not exists"
    } else {
        if (server == null) {
            if (debug) echo "[jenkins-windows-library iis] [DEBUG] get app pool state method called. AppPool: $pool . Command: Get-WebAppPoolState -Name \"$pool\""

            def pwret = powershell returnStdout: true, script:"Get-WebAppPoolState -Name \"$pool\""
            def state = pwret.trim()
            return state
        } else {
            if (debug) echo "[jenkins-windows-library iis] [DEBUG] get app pool state method called. AppPool: $pool . Command: Invoke-Command -ComputerName \"$server\" -ScriptBlock { Get-WebAppPoolState -Name \"$pool\" }"

            def pwret = powershell returnStdout: true, script: "Invoke-Command -ComputerName \"$server\" -ScriptBlock { Get-WebAppPoolState -Name \"$pool\" }"
            def state = pwret.trim()
            return state
        }
    }
}


def webSiteExist(Map map = [:], site, server) {
    def debug =  map.debug ?: false
    def exist =  false

    if (server == null) {
        if (debug) echo "[jenkins-windows-library iis] [DEBUG] WebSite exist method called. WebSite: $site . Command: Test-Path \"IIS:\\\\Sites\\$site\""
 
        def pwret = powershell returnStdout: true, script:"Test-Path \"IIS:\\\\Sites\\$site\""
        if (pwret.trim() == "True") {
            exist = true
        }
    } else {
        if (debug) echo "[jenkins-windows-library iis] [DEBUG] WebSite exist method called. WebSite: $site . Command: Invoke-Command -ComputerName \"$server\" -ScriptBlock { import-module webadministration \n Test-Path \"IIS:\\\\Sites\\$site\" }"

        def pwret = powershell returnStdout: true, script:"Invoke-Command -ComputerName \"$server\" -ScriptBlock {import-module webadministration \n Test-Path \"IIS:\\\\Sites\\$site\" }"
        if (pwret.trim() == "True") {
            exist = true
        }
    }

    return exist
}

def newWebSite(Map map = [:], site, server, pool){
    def debug =  map.debug ?: false

    def exist = webSiteExist(map, site, server)
    if (exist == true) {
        if (debug) echo "[jenkins-windows-library iis] [DEBUG] new Web Site method called. Web Site: $site . Message: Web Site already exists"
    } else {
        if (server == null) {
            if (debug) echo "[jenkins-windows-library iis] [DEBUG] new Web Site method called. Web Site: $site . Command: New-WebSite -Name \"$site\""
            powershell "New-WebSite -Name \"$site\""    
        } else {
            if (debug) echo "[jenkins-windows-library iis] [DEBUG] new Web Site method called. WebSite: $site . Command: Invoke-Command -ComputerName \"$server\" -ScriptBlock { New-WebSite -Name \"$site\"-ApplicationPool \"$pool\" -force }"
            powershell "Invoke-Command -ComputerName \"$server\" -ScriptBlock { New-WebSite -Name \"$site\" -ApplicationPool \"$pool\" -force }"
        }
    }

}


def removeWebSite(Map map = [:], site, server){
    def debug =  map.debug ?: false

    def exist = webSiteExist(map, site, server)
    if (exist == false) {
        if (debug) echo "[jenkins-windows-library iis] [DEBUG] remove Web Site method called. Web Site: $site . Message: Web Site not exists"
    } else {
        if (server == null) {
            if (debug) echo "[jenkins-windows-library iis] [DEBUG] remove Web Site method called. Web Site: $site . Command: Remove-WebSite -Name \"$site\""
            powershell "Remove-WebSite -Name \"$site\""    
        } else {
            if (debug) echo "[jenkins-windows-library iis] [DEBUG] remove Web Site method called. WebSite: $site . Command: Invoke-Command -ComputerName \"$server\" -ScriptBlock { Remove-WebSite -Name \"$site\" }"
            powershell "Invoke-Command -ComputerName \"$server\" -ScriptBlock { Remove-WebSite -Name \"$site\" }"
        }
    }

}

def startWebSite(Map map = [:], site, server) {
    def debug =  map.debug ?: false
    def common = new common()

    def exist = webSiteExist(map, site, server)
    if (exist == false) {
        if (debug) echo "[jenkins-windows-library iis] [DEBUG] start Web Site called. WebSite: $site . Message: WebSite not exists"
    } else {
        def statusToStart = common.getstatusToStart()
        def status = getWebSiteState(map, site, server)

        if (common.isStatusInArray(status, statusToStart)) {
            if (server == null) {
                if (debug) echo "[jenkins-windows-library iis] [DEBUG] start Web site method called. Website: $site . Command: Start-WebSite -Name \"$site\""
                powershell "Start-WebSite -Name \"$site\""    
            } else {
                if (debug) echo "[jenkins-windows-library iis] [DEBUG] start Web site method called. WebSite: $site . Command: Invoke-Command -ComputerName \"$server\" -ScriptBlock { Start-WebSite -Name \"$site\" }"
                powershell "Invoke-Command -ComputerName \"$server\" -ScriptBlock { Start-WebSite -Name \"$site\" }"
            }
        } else {
             if (debug) echo "[jenkins-windows-library iis] [DEBUG] start Web Site method called. WebSite: $site . Message: WebSite already running"
            return
        }
    }
}


def stopWebSite(Map map = [:], site, server) {
    def debug =  map.debug ?: false
    def common = new common()

    def exist = webSiteExist(map, site, server)
    if (exist == false) {
        if (debug) echo "[jenkins-windows-library iis] [DEBUG] stop Web Site called. WebSite: $site . Message: WebSite not exists"
    } else {
        def statusToStart = common.getstatusToStart()
        def status = getWebSiteState(map, site, server)

        if (common.isStatusInArray(status, statusToStart)) {
            if (server == null) {
                if (debug) echo "[jenkins-windows-library iis] [DEBUG] stop Web site method called. Website: $site . Command: Stop-WebSite -Name \"$site\""
                powershell "Stop-WebSite -Name \"$site\""    
            } else {
                if (debug) echo "[jenkins-windows-library iis] [DEBUG] stop Web site method called. WebSite: $site . Command: Invoke-Command -ComputerName \"$server\" -ScriptBlock { Stop-WebSite -Name \"$site\" }"
                powershell "Invoke-Command -ComputerName \"$server\" -ScriptBlock { Start-WebSite -Name \"$site\" }"
            }
        } else {
             if (debug) echo "[jenkins-windows-library iis] [DEBUG] stop Web Site method called. WebSite: $site . Message: WebSite already stopped"
            return
        }
    }
}

def getWebSiteState(Map map = [:], site, server) {
    def debug =  map.debug ?: false

    def exist = webSiteExist(map, site, server)
    if (exist == false) {
        if (debug) echo "[jenkins-windows-library iis] [DEBUG] get Web site  state method called. Web site : $site. Message: AppPool not exists"
    } else {
        if (server == null) {
            if (debug) echo "[jenkins-windows-library iis] [DEBUG] get Web site  state method called. Web site : $site . Command: Get-WebSiteState -Name \"$site\""

            def pwret = powershell returnStdout: true, script:"Get-WebSiteState  -Name \"$site\""
            def state = pwret.trim()
            return state
        } else {
            if (debug) echo "[jenkins-windows-library iis] [DEBUG] get Web site  state method called. Web site : $site . Command: Invoke-Command -ComputerName \"$server\" -ScriptBlock { Get-WebSiteState -Name \"$site\" }"

            def pwret = powershell returnStdout: true, script: "Invoke-Command -ComputerName \"$server\" -ScriptBlock { Get-WebSiteState  -Name \"$site\" }"
            def state = pwret.trim()
            return state
        }
    }
}



def editWebSite(Map map = [:], site, server, newSite) {
    def debug =  map.debug ?: false
    def common = new common()

    def exist = webSiteExist(map, site, server)
    if (exist == false) {
        if (debug) echo "[jenkins-windows-library iis] [DEBUG] start Web Site called. WebSite: $site . Message: WebSite not exists"
    } else {
        def statusToStart = common.getstatusToStart()
        def status = getWebSiteState(map, site, server)

        if (common.isStatusInArray(status, statusToStart)) {
            if (server == null) {
                if (debug) echo "[jenkins-windows-library iis] [DEBUG] start Web site method called. Website: $site . Command: Start-WebSite -Name \"$site\""
                powershell "Start-WebSite -Name \"$site\""    
            } else {
                if (debug) echo "[jenkins-windows-library iis] [DEBUG] edit app pool method called. AppPool: $pool . Command: Invoke-Command -ComputerName \"$server\" -ScriptBlock { import-module webadministration \n Set-ItemProperty \"IIS\\\\AppPools\\\"$site\" -Name  aplicationPool\"$site\" -value \"$newPool\" }"
                powershell "Invoke-Command -ComputerName \"$server\" -ScriptBlock { Start-WebSite -Name \"$site\" }"
            }
        } else {
             if (debug) echo "[jenkins-windows-library iis] [DEBUG] start Web Site method called. WebSite: $site . Message: WebSite already running"
            return
        }
    }
}

def editAppPool(Map map = [:], pool, server,newPool,site) {
    def debug =  map.debug ?: false
    if (debug) echo "Server name: $server"
   
    def exist = appPoolExist(map, pool, server)
    if (debug) echo "Server exists: $exist"
    
    if (exist == true) {
        if (debug) echo "[jenkins-windows-library iis] [DEBUG] edit app pool method called. AppPool: $pool . Message: AppPool already exists"
    } else {
        if (!server) {
            if (debug) echo "[jenkins-windows-library iis] [DEBUG] edit app pool method called. AppPool: $pool . Command: New-WebAppPool -Name \"$pool\""
            powershell "New-WebAppPool -Name \"$pool\""    
        } else {
            if (debug) echo "[jenkins-windows-library iis] [DEBUG] edit app pool method called. AppPool: $pool . Command: Invoke-Command -ComputerName \"$server\" -ScriptBlock { import-module webadministration \n Set-ItemProperty \"IIS\\\\Sites\\\"$site\" -Name  aplicationPool\"$pool\" -value \"$newPool\" }"
            powershell "Invoke-Command -ComputerName \"$server\" -ScriptBlock { New-WebAppPool -Name \"$pool\" }"
        }
    }
}