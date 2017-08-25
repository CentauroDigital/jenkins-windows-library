class iis implements Serializable {

    def _iis = new br.com.centaurotech.iis()
    
    def restart(Map map = [:], server) {
         _iis.restart(map, server)
    }

    def start(Map map = [:], server) {
         _iis.start(map, server)
    }

    def stop(Map map = [:], server) {
        _iis.stop(map, server)
    }

    def startAppPool(Map map = [:], pool, server) {
        _iis.startAppPool(map, pool, server)
    }

    def restartAppPool(Map map = [:], pool, server) {
        _iis.restartAppPool(map, pool, server)
    }

    def stopAppPool(Map map = [:], pool, server) {
        _iis.stopAppPool(map, pool, server)
    }

    def newAppPool(Map map = [:], pool, server) {
        _iis.newAppPool(map, pool, server)
    }

    def removeAppPool(Map map = [:], pool, server) {
        _iis.removeAppPool(map, pool,server)
    }

    def appPoolExist(Map map = [:], pool, server) {
        _iis.appPoolExist(map, pool, server)
    }

    def getAppPoolState(Map map = [:], pool, server) {
        _iis.getAppPoolState(map, pool, server)
    }

    def newWebSite(Map map = [:], site, server,pool) {
        _iis.newWebSite(map,site,server,pool)
    }

    def removeWebSite(Map map = [:], site, server){
        _iis.removeWebSite(map,site,server)
    }

    def stopWebSite(Map map = [:], site, server){
        _iis.stopWebSite(map,site,server)
    }

    def startWebSite(Map map = [:], site, server){
        _iis.startWebSite(map,site,server)
    }

    def getWebSiteState(Map map = [:], site, server){
        _iis.getWebSiteState(map,site,server)
    }

}