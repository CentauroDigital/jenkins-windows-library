package br.com.centaurotech

def isInstalled(Map map = [:], hostName, serviceName) {
	def debug =  map.debug ?: false
	def supressOutput =  map.supressOutput ?: false
	if (debug) supressOutput = false;

	if (debug) echo "[jenkins-windows-library windows service] [DEBUG] isInstalled method called: hostName:\"$hostName\", serviceName:\"$serviceName\""
	
	if(!supressOutput) echo "[jenkins-windows-library windows service] Checking if the service $serviceName is installed on $hostName host."

	def pwret = powershell returnStdout: true, script:"(get-service -ComputerName $hostName | Where-Object {\$_.name -eq \"$serviceName\"})"
	def installed = (pwret.trim().length() > 0)

	if (installed){
		if(!supressOutput) echo "[jenkins-windows-library windows service] The service $serviceName is installed on $hostName host."
	}else{
		if(!supressOutput) echo "[jenkins-windows-library windows service] The service $serviceName is not installed on $hostName host."
	}

	if (debug) {
		if (debug) echo "[jenkins-windows-library windows service] [DEBUG] $serviceName is installed: $installed."
		if (installed) echo pwret
	}

	return installed
}

def getStatus(Map map = [:], hostName, serviceName) {
	def debug =  map.debug ?: false
	def supressOutput =  map.supressOutput ?: false
	if (debug) supressOutput = false;

	if (debug) echo "[jenkins-windows-library windows service] [DEBUG] getStatus method called: hostName:\"$hostName\", serviceName:\"$serviceName\", debug:\"$debug\""

	if(!supressOutput) echo "[jenkins-windows-library windows service] Getting the status of the service $serviceName on $hostName host."

	if(!isInstalled(hostName, serviceName, debug: debug, supressOutput: true)) error "The service $serviceName is not installed on $hostName host."

	if (debug) echo "[jenkins-windows-library windows service] [DEBUG] Checking $serviceName status."
	def pwret =  powershell returnStdout: true, script:"(get-service -ComputerName $hostName -Name $serviceName).Status"
	pwret = pwret.trim();

	if (debug) echo "[jenkins-windows-library windows service] [DEBUG] $serviceName is $pwret"

	if(!supressOutput) echo "[jenkins-windows-library windows service] The service $serviceName is $pwret on $hostName host."

	return pwret;
}

def start(Map map = [:], hostName, serviceName) {
	def debug =  map.debug ?: false
	def timeout =  map.timeout ?: 60
	def common = new common()

	if (debug) echo "[jenkins-windows-library windows service] [DEBUG] stop method called: hostName:\"$hostName\", serviceName:\"$serviceName\", timeout:\"$timeout\", debug:\"$debug\""

	if(!isInstalled(hostName, serviceName, debug: debug, supressOutput: true)) error "The service $serviceName is not installed on $hostName host."

	def status = getStatus(hostName, serviceName, debug: debug, supressOutput: true)

	def statusToStart = common.getstatusToStart()

	if(common.isStatusInArray(status, statusToStart)){
		echo "[jenkins-windows-library windows service] Starting the service $serviceName on $hostName host."
		powershell "(get-service -ComputerName $hostName -Name $serviceName).Start()"
	}else{
		echo "[jenkins-windows-library windows service] Service $serviceName alerady running on $hostName host."
		return
	}

	powershell "(get-service -ComputerName $hostName -Name $serviceName).WaitForStatus(\"Running\")"
	echo "[jenkins-windows-library windows service] Service $serviceName started on $hostName host."
}

def stop(Map map = [:], hostName, serviceName) {
	def debug =  map.debug ?: false
	def timeout =  map.timeout ?: 60
	def force =  map.force ?: false
	def common = new common()

	if (debug) echo "[jenkins-windows-library windows service] [DEBUG] stop method called: hostName:\"$hostName\", serviceName:\"$serviceName\", force:\"$force\", timeout:\"$timeout\", debug:\"$debug\""

	if(!isInstalled(hostName, serviceName, debug: debug, supressOutput: true)) error "The service $serviceName is not installed on $hostName host."
	
	def status = getStatus(hostName, serviceName, debug: debug, supressOutput: true)
	
	def statusToStop = common.getStatusToStop()

	if(common.isStatusInArray(status, statusToStop)){
		echo "[jenkins-windows-library windows service] Stopping the service $serviceName on $hostName host and Killing the Process!."
		powershell "Invoke-Command -ComputerName $hostName -ScriptBlock { (get-process -Name $serviceName).Kill() }"
		
	}else{
		echo "[jenkins-windows-library windows service] Service $serviceName alerady stopped on $hostName host."
		return
	}

	powershell "(get-service -ComputerName $hostName -Name $serviceName).WaitForStatus(\"Stopped\")"
	echo "[jenkins-windows-library windows service] Service $serviceName stopped on $hostName host."
}

