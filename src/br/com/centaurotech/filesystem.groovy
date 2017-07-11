package br.com.centaurotech

def copy(Map map = [:], fromPath, toPath) {
    def debug =  map.debug ?: false

    def exist = fileExist(map, fromPath)
    if (exist) {
        if (debug) echo "[jenkins-windows-library file system] [DEBUG] copy method called: from path: $fromPath, to path: $toPath .Command: Copy-Item \"$fromPath\" -Destination \"$toPath\" -Recurse -Force"
        powershell "Copy-Item \"$fromPath\" -Destination \"$toPath\" -Recurse -Force"
    } else {
        if (debug) echo "[jenkins-windows-library file system] [DEBUG] copy file method called: Error - $fromPath not exist"
    }
}

def fileExist(Map map = [:], path) {
    def debug =  map.debug ?: false
    if (debug) echo "[jenkins-windows-library file system] [DEBUG] file exist method called: path: $path"

    def pwret = powershell returnStdout: true, script:"Test-Path \"$path\""
    if (debug) echo "[jenkins-windows-library file system] [DEBUG] file exist method called: exist: $pwret"

	def exist =  false
    if (pwret != null) {
        if (pwret.trim() == "True") {
            exist = true
        }
    }
    return exist
}