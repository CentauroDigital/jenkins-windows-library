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

def newAppPool(Map map = [:], pool, server = null) {
    def debug =  map.debug ?: false

    def exist = appPoolExist(map, pool, server)
    if (exist == true) {
        if (debug) echo "[jenkins-windows-library iis] [DEBUG] new app pool method called. AppPool: $pool . Message: AppPool already exists"
    } else {
        if (server == null) {
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
        if (debug) echo "[jenkins-windows-library iis] [DEBUG] app pool exist method called. AppPool: $pool . Command: Invoke-Command -ComputerName \"$server\" -ScriptBlock { Test-Path \"IIS:\\\\AppPools\\$pool\" }"

        def pwret = powershell returnStdout: true, script:"Invoke-Command -ComputerName \"$server\" -ScriptBlock { Test-Path \"IIS:\\\\AppPools\\$pool\" }"
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