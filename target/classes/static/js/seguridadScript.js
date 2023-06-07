let elemDatos = document.getElementById("datos")
let elemPasswd = document.getElementById("passwd")
let buttonDatos = document.getElementById("data")
let buttonContras = document.getElementById("contras")
let buttonOp = document.getElementById("operat")
let elemOp = document.getElementById("operaciones")
let buttonPrefer = document.getElementById("preferencias")
let elemPrefer = document.getElementById("preference")
elemPasswd.style.display = "none"
elemOp.style.display = "none"

buttonDatos.addEventListener('focus', function() {
	elemDatos.style.display = "block"
	elemPasswd.style.display = "none"
	elemOp.style.display = "none"
	elemPrefer.style.display="none"
})

buttonContras.addEventListener('focus', function() {
	elemPasswd.style.display = "flex"
	elemDatos.style.display = "none"
	elemOp.style.display = "none"
	elemPrefer.style.display="none"
})

buttonOp.addEventListener('focus', function() {
	elemOp.style.display = "flex"
	elemPasswd.style.display = "none"
	elemDatos.style.display = "none"
	elemPrefer.style.display="none"
})

buttonPrefer.addEventListener('focus', function() {
	elemPrefer.style.display="flex"
	elemOp.style.display = "none"
	elemPasswd.style.display = "none"
	elemDatos.style.display = "none"
})

document.addEventListener('DOMContentLoaded', function() {
	document.title = 'Configuración';
	elemPrefer.style.display="none"
})

