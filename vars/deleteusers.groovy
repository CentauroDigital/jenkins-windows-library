class deleteusers implements Serializable {
    def _deleteusers = new br.com.centaurotech.deleteusers()
    
    def delete(Map map = [:], server) {
        def debug =  map.debug ?: false
        _deleteusers.delete(map, server)
    }
}