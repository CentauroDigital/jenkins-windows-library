package br.com.centaurotech

def printMessage(mesage) {
	echo mesage	
}

def concat(mesages) {
	def con = ''

	for (mesage in mesages) {
		con += mesage + ' '
	}

	echo con
}