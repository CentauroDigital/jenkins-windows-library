package br.com.centaurotech

def isStatusInArray(status, statusArray) {
	for (item in statusArray) {
		if (status == item) return true
	}

	return false
}

def getstatusToStart() {
    return ["Stopped", "StopPending", "ContinuePending", "PausePending", "Paused"]
}

def getStatusToStop() {
    return ["Running", "StartPending", "ContinuePending", "PausePending", "Paused"]
}