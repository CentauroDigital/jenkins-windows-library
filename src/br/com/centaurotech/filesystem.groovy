package br.com.centaurotech

def copyFile(Map map = [:], fromPath, toServer, toPath) {
    def debug =  map.debug ?: false
    if (debug) echo "[jenkins-windows-library file system] [DEBUG] copy file method called: from path: $fromPath, to server: $toServer, to path: $toPath .Command: Copy-Item $fromPath -Destination \\\\$toServer\\$toPath"

    powershell "Copy-Item $fromPath -Destination \\\\$toServer\\$toPath"
}

def copyFolder(Map map = [:], fromPath, toServer, toPath, extensions) {
    def debug =  map.debug ?: false

    if (extensions == null) {
        if (debug) echo "[jenkins-windows-library file system] [DEBUG] copy folder method called: from path: $fromPath, to server: $toServer, to path: $toPath .Command: Copy-Item $fromPath -Destination \\\\$toServer\\$toPath -recurse"

        powershell "Copy-Item $fromPath -Destination \\\\$toServer\\$toPath -recurse"
    } else {
        if (debug) echo "[jenkins-windows-library file system] [DEBUG] copy folder method called: from path: $fromPath, to server: $toServer, to path: $toPath, extensions: $extensions"
        def extensionsArray = extensions.split(',').collect{ext as String}

        extensionsArray.each { ext ->
            ext = ext.replace(".", "")
            if (debug) echo "[jenkins-windows-library file system] [DEBUG] Execute Command: Copy-Item $fromPath\*.${ext} -Destination \\\\$toServer\\$toPath"

            powershell "Copy-Item $fromPath\\*.${ext} -Destination \\\\$toServer\\$toPath"
        }
    }
}