class deleteusers implements Serializable {
    def _deleteusers = new br.com.centaurotech.deleteusers()
    
    def delete(Map map = [:], server) {
        def debug =  map.debug ?: false
        if (debug) echo  "call class deleteusers"

        _deleteusers.delete(map, server)
    }
}