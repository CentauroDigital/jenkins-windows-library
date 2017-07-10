class filesystem implements Serializable {
    def _filesystem = new br.com.centaurotech.filesystem()
    
    def copy(Map map = [:], fromPath, toPath, extensions) {
        _filesystem.copy(map, "\"$fromPath\"", "\"$toPath\"", extensions)
    }

     def copy(Map map = [:], fromPath, toPath) {
        _filesystem.copy(map,  "\"$fromPath\"", "\"$toPath\"", null)
    }

    def fileExist(Map map = [:], path) {
        _filesystem.fileExist(map, path)
    }
}