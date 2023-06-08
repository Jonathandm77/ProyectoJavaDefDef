
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

//si se abre una seccion se cierran las demas

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

	//Manejar seleccion de temas estilos
		var cuadradoContainers = document.getElementsByClassName("cuadrado");

		function seleccionarCuadrado() {
			for (var i = 0; i < cuadradoContainers.length; i++) {
				cuadradoContainers[i].classList.remove("seleccionado");
			}

			this.classList.add("seleccionado");
		}

		for (var i = 0; i < cuadradoContainers.length; i++) {
			cuadradoContainers[i].addEventListener("click", seleccionarCuadrado);
		}

		document.getElementById("tema-form").addEventListener("submit", function (event) {
		  event.preventDefault(); 
		
		  var temaSeleccionado = document.querySelector('input[name="tema"]:checked').value;
		
		  var request = new XMLHttpRequest();
		
		  var url = "/seguridad/password/cambioTema";
		
		  request.open("POST", url, true);//true es asincrona
		
		  request.addEventListener("load", function () {
		    if (request.status >= 200 && request.status < 400) {
		     
		      localStorage.setItem("formularioEnviado", "true");//datos persistentes, aunque recargue o cierre el navegador, se mantiene, así a pesar de que el controlador devuelva la página, la sección se mantiene
		
		      // Recargar la página
		      location.reload();
		    }
		  });
		  request.send(temaSeleccionado);
		});
		
		// Redirigir a seccion preferencias si se envio formulario
		window.onload = function () {
		  if (localStorage.getItem("formularioEnviado") === "true") {
		    localStorage.removeItem("formularioEnviado");
		    location.href = "#preferencias";
		  }
		};
