class windowsservice implements Serializable {
    def _windowsservice = new br.com.centaurotech.windowsservice()
    
    def isInstalled(Map map = [:], hostName, serviceName) {
        _windowsservice.isInstalled(map, hostName, serviceName)
    }

    def getStatus(Map map = [:], hostName, serviceName) {
         _windowsservice.getStatus(map, hostName, serviceName)
    }

    def start(Map map = [:], hostName, serviceName) {
         _windowsservice.start(map, hostName, serviceName)
    }

    def stop(Map map = [:], hostName, serviceName) {
        _windowsservice.stop(map, hostName, serviceName)
    }
}