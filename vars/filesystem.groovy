class filesystem implements Serializable {
    def _filesystem = new br.com.centaurotech.filesystem()
    
    def copy(Map map = [:], fromPath, toServer, toPath, extensions) {
        _filesystem.copyFile(map, fromPath, toServer, toPath, extensions)
    }

    def fileExist((Map map = [:], path) {
        _filesystem.fileExist(path)
    }
}