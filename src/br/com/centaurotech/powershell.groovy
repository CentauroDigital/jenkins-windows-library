package br.com.centaurotech

class powershell implements Serializable {
	def exec(Map map = [:], command) {
		
		def debug =  map.debug ?: false

		if (debug) echo "[DEBUG] powershell method called with parameter: \n $command"

		def returnFileName = new Date().format("yyyyMMddHHmmssSSS")
		if (debug) echo "[DEBUG] The return file name will is $returnFileName"
		
		def pwCommand =  "powershell.exe -ExecutionPolicy Bypass -NoLogo -NonInteractive -NoProfile  -Command \"$command\" > $returnFileName"
		if(debug) echo "[DEBUG] The following command will be executed: \n $pwCommand"
		bat pwCommand
		
		if(debug) echo "[DEBUG] Reading the file $returnFileName"
		def pwret = readFile "$returnFileName"
		pwret = pwret.trim()
		
		if(debug) echo "[DEBUG] Deleting the file $returnFileName"
		bat "del $returnFileName"
		
		return pwret
	}
}


