class powershell implements Serializable {
    def _powershell = new br.com.centaurotech.powershell()
    
    def exec(Map map = [:], command) {
        _powershell.exec(map, command)
    }
}