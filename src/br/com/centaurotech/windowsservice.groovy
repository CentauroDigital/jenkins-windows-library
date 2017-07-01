package br.com.centaurotech

def isInstalled(Map map = [:], hostName, serviceName) {
	def debug =  map.debug ?: false
	def supressOutput =  map.supressOutput ?: false
	if (debug) supressOutput = false;

	if (debug) echo "[DEBUG] isInstalled method called: hostName:\"$hostName\", serviceName:\"$serviceName\""
	
	echo "Checking if the service $serviceName is installed on $hostName host."

	def pwret = powershell returnStdout: true, script:"(get-service -ComputerName $hostName | Where-Object {\$_.name -eq \"$serviceName\"})"
	def installed = (pwret.trim().length() > 0)

	if (installed){
		if(!supressOutput) echo "The service $serviceName is installed on $hostName host."
	}else{
		if(!supressOutput) echo "The service $serviceName is not installed on $hostName host."
	}

	if (debug) {
		if (debug) echo "[DEBUG] $serviceName is installed: $installed."
		if (installed) echo pwret
	}

	return installed
}

def getStatus(Map map = [:], hostName, serviceName) {
	def debug =  map.debug ?: false
	def supressOutput =  map.supressOutput ?: false
	if (debug) supressOutput = false;

	if (debug) echo "[DEBUG] getStatus method called: hostName:\"$hostName\", serviceName:\"$serviceName\", debug:\"$debug\""

	if(!supressOutput) echo "Getting the status of the service $serviceName on $hostName host."

	if(!isInstalled(hostName, serviceName, debug: debug, supressOutput: true)) error "The service $serviceName is not installed on $hostName host."

	if (debug) echo "[DEBUG] Checking $serviceName status."
	def pwret =  powershell returnStdout: true, script:"(get-service -ComputerName $hostName -Name $serviceName).Status"
	pwret = pwret.trim();

	if (debug) echo "[DEBUG] $serviceName is $pwret"

	if(!supressOutput) echo "The service $serviceName is $pwret on $hostName host."

	return pwret;
}

def start(Map map = [:], hostName, serviceName) {
	def debug =  map.debug ?: false
	def timeout =  map.timeout ?: 60

	if (debug) echo "[DEBUG] stop method called: hostName:\"$hostName\", serviceName:\"$serviceName\", timeout:\"$timeout\", debug:\"$debug\""

	if(!isInstalled(hostName, serviceName, debug: debug, supressOutput: true)) error "The service $serviceName is not installed on $hostName host."

	def status = getStatus(hostName, serviceName, debug: debug, supressOutput: true)

	def statusToStart = ["Stopped", "StopPending", "ContinuePending", "PausePending", "Paused"]

	if(isStatusInArray(status, statusToStart)){
		echo "Starting the service $serviceName on $hostName host."
		powershell "(get-service -ComputerName $hostName -Name $serviceName).Start()"
	}else{
		echo "Service $serviceName alerady running on $hostName host."
	}

	powershell "(get-service -ComputerName $hostName -Name $serviceName).WaitForStatus(\"Running\")"
	echo "Service $serviceName started on $hostName host."
}

def stop(Map map = [:], hostName, serviceName) {
	def debug =  map.debug ?: false
	def timeout =  map.timeout ?: 60
	def force =  map.force ?: false

	if (debug) echo "[DEBUG] stop method called: hostName:\"$hostName\", serviceName:\"$serviceName\", force:\"$force\", timeout:\"$timeout\", debug:\"$debug\""

	if(!isInstalled(hostName, serviceName, debug: debug, supressOutput: true)) error "The service $serviceName is not installed on $hostName host."
	
	def status = getStatus(hostName, serviceName, debug: debug, supressOutput: true)
	
	def statusToStop = ["Running", "StartPending", "ContinuePending", "PausePending", "Paused"]

	if(isStatusInArray(status, statusToStop)){
		echo "Starting the service $serviceName on $hostName host."
		powershell "(get-service -ComputerName $hostName -Name $serviceName).Stop()"
	}else{
		echo "Service $serviceName alerady stopped on $hostName host."
	}

	powershell "(get-service -ComputerName $hostName -Name $serviceName).WaitForStatus(\"Stopped\")"
	echo "Service $serviceName stopped on $hostName host."
}

def isStatusInArray(status, statusArray) {
	for (item in statusArray) {
		if (status == item) return true
	}
	return false
}