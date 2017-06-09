class print implements Serializable {
    def _print = new br.com.centaurotech.print()

    def printMessage(message) {
        _print.printMessage(message)
    }
}