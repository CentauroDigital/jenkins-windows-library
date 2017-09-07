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

def properties = properties([
    buildDiscarder(logRotator(artifactDaysToKeepStr: '15', artifactNumToKeepStr: '10', daysToKeepStr: '15', numToKeepStr: '10')),
    disableConcurrentBuilds(),
    [$class: 'CopyArtifactPermissionProperty', projectNames: '*'], 
    parameters([string(defaultValue: 'false', description: '', name: 'Debug')]), 
    pipelineTriggers([pollSCM('H/5 * * * *')])])
