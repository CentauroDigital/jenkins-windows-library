package br.com.centaurotech

def isInstalled(Map map = [:], hostName, serviceName) {
	def powershell = new br.com.centaurotech.powershell()

	def debug =  map.debug ?: false

	if (debug) echo "[DEBUG] isInstalled method called: hostName:\"$hostName\", serviceName:\"$serviceName\""
	
	def pwret = powershell.exec("(get-service -ComputerName $hostName | Where-Object {\$_.name -eq \\\"$serviceName\\\"})", debug: debug)
	def installed = (pwret.trim().length() > 0)

	if (debug) {
		if (debug) echo "[DEBUG] $serviceName is installed: $installed."
		if (installed) echo pwret
	}

	return installed
}

def getStatus(Map map = [:], hostName, serviceName) {
	def powershell = new br.com.centaurotech.powershell()

	def debug =  map.debug ?: false

	if (debug) echo "[DEBUG] getStatus method called: hostName:\"$hostName\", serviceName:\"$serviceName\", debug:\"$debug\""

	if(!isInstalled(hostName, serviceName, debug: debug)) error "Windows service $serviceName is not installed on $hostName"

	if (debug) echo "[DEBUG] Checking $serviceName status."
	def pwret = powershell.exec("(get-service -ComputerName $hostName -Name $serviceName).Status", debug: debug)

	if (debug) echo "[DEBUG] $serviceName is $pwret"

	return pwret.trim();
}

def start(Map map = [:], hostName, serviceName) {
	def powershell = new br.com.centaurotech.powershell()

	def debug =  map.debug ?: false
	def timeout =  map.timeout ?: 60

	if (debug) echo "[DEBUG] stop method called: hostName:\"$hostName\", serviceName:\"$serviceName\", timeout:\"$timeout\", debug:\"$debug\""

	if(!isInstalled(hostName, serviceName, debug: debug)) error "Windows service $serviceName is not installed on $hostName"

	def status = getStatus(hostName, serviceName, debug: debug)

	def statusToStart = ["Stopped", "StopPending", "ContinuePending", "PausePending", "Paused"]

	if(isStatusInArray(status, statusToStart)){
		if (debug) echo "[DEBUG] Starting $serviceName..."
		powershell.exec("(get-service -ComputerName $hostName -Name $serviceName).Start()", debug: debug)
	}

	powershell.exec("(get-service -ComputerName $hostName -Name $serviceName).WaitForStatus(\\\"Running\\\")", debug: debug)
}

def stop(Map map = [:], hostName, serviceName) {
	def powershell = new br.com.centaurotech.powershell()

	def debug =  map.debug ?: false
	def timeout =  map.timeout ?: 60
	def force =  map.force ?: false

	if (debug) echo "[DEBUG] stop method called: hostName:\"$hostName\", serviceName:\"$serviceName\", force:\"$force\", timeout:\"$timeout\", debug:\"$debug\""

	if(!isInstalled(hostName, serviceName, debug: debug)) error "Windows service $serviceName is not installed on $hostName"
	
	def status = getStatus(hostName, serviceName, debug: debug)
	
	def statusToStop = ["Running", "StartPending", "ContinuePending", "PausePending", "Paused"]

	if(isStatusInArray(status, statusToStop)){
		if (debug) echo "[DEBUG] Stopping $serviceName..."
		powershell.exec("(get-service -ComputerName $hostName -Name $serviceName).Stop()", debug: debug)
	}

	powershell.exec("(get-service -ComputerName $hostName -Name $serviceName).WaitForStatus(\\\"Stopped\\\")", debug: debug)
}

def isStatusInArray(status, statusArray) {
	for (item in statusArray) {
		if (status == item) return true
	}
	return false
}