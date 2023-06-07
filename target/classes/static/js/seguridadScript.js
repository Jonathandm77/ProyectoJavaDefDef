
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

		if (error) {
			const toastLiveExample = document.getElementById('toastDuplicateDNI');
			const toastBootstrap = new bootstrap.Toast(toastLiveExample);
			const toastCloseButton = toastLiveExample.querySelector('.btn-close');

			toastCloseButton.addEventListener('click', function () {
				toastBootstrap.hide();
			});

			toastBootstrap.show();
		}


		if (vacio) {
			const toastLiveExample = document.getElementById('liveToast');
			const toastBootstrap = new bootstrap.Toast(toastLiveExample);
			const toastCloseButton = toastLiveExample.querySelector('.btn-close');

			toastCloseButton.addEventListener('click', function () {
				toastBootstrap.hide();
			});

			toastBootstrap.show();
		}

		if (status == 1) {
			const toastLiveExample = document.getElementById('toastSucess');
			const toastBootstrap = new bootstrap.Toast(toastLiveExample);
			const toastCloseButton = toastLiveExample.querySelector('.btn-close');

			toastCloseButton.addEventListener('click', function () {
				toastBootstrap.hide();
			});

			toastBootstrap.show();
		} else {
			if (status == 2) {
				const toastLiveExample = document.getElementById('toastFailed');
				const toastBootstrap = new bootstrap.Toast(toastLiveExample);
				const toastCloseButton = toastLiveExample.querySelector('.btn-close');

				toastCloseButton.addEventListener('click', function () {
					toastBootstrap.hide();
				});

				toastBootstrap.show();
			}
		}

		console.log(status)

		// Obtener todos los contenedores de cuadrados
		var cuadradoContainers = document.getElementsByClassName("cuadrado");

		// Función para agregar el borde al cuadrado seleccionado
		function seleccionarCuadrado() {
			// Remover el borde de todos los cuadrados
			for (var i = 0; i < cuadradoContainers.length; i++) {
				cuadradoContainers[i].classList.remove("seleccionado");
			}

			// Agregar el borde al cuadrado seleccionado
			this.classList.add("seleccionado");
		}

		// Agregar el evento de clic a cada cuadrado
		for (var i = 0; i < cuadradoContainers.length; i++) {
			cuadradoContainers[i].addEventListener("click", seleccionarCuadrado);
		}

		// Manejar el envío del formulario
		document.getElementById("tema-form").addEventListener("submit", function (event) {
			event.preventDefault(); // Evitar que el formulario se envíe de forma predeterminada

			// Obtener el valor seleccionado
			var temaSeleccionado = document.querySelector('input[name="tema"]:checked').value;

			// Crear el objeto XMLHttpRequest
			var request = new XMLHttpRequest();
			
			var url="/seguridad/password/cambioTema"

			// Configurar la solicitud
			request.open("POST",url, true);

			// Enviar la solicitud con los datos del formulario
			request.send(temaSeleccionado);
			location.href="#preferencias"
		});
		

