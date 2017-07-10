class filesystem implements Serializable {
    def _filesystem = new br.com.centaurotech.filesystem()
    
    def copyFile(Map map = [:], fromPath, toServer, toPath) {
        _filesystem.copyFile(map, fromPath, toServer, toPath)
    }

    def copyFolder(Map map = [:], fromPath, toServer, toPath, extensions) {
         _filesystem.copyFolder(map, fromPath, toServer, toPath, extensions)
    }
}