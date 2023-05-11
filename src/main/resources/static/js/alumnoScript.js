$(document).ready(function() {
	$("#editAjax").click(function(event) {
		console.log("Hola");


		event.preventDefault();

		fire_ajax_submit();

	})
})

function fire_ajax_submit() {


	var alumnoDTO = {};
	alumnoDTO["nombre"] = $("#nombreAjax").val();
	alumnoDTO["id"] = $("#idAjax").text();

	console.log(alumnoDTO);
	$("#editAjax").prop("disable", true);

	console.log(JSON.stringify(alumnoDTO));


	$.ajax({

		type: "POST",
		contentType: "application/json",
		url: "/alumnos/guardarAjax",
		data: JSON.stringify(alumnoDTO),
		datatype: "json",
		cache: false,
		timeout: 100000,
		success: function(data) {

			var json = "<h5>Respuesta Ajax</h5></br>" +

				JSON.stringify(data, null, 4);

			$("#respuestaAjax").html(json);
		},
		error: function(e) {
			console.log("ERROR", e);
		}
	})

}

const btnBorrar = document.getElementById('btn-borrar');
let accordionItems = document.querySelectorAll('.accordion-item');
const body = document.querySelectorAll("body")[0]
let modoEliminacion = false;


btnBorrar.addEventListener('click', () => {
	modoEliminacion = !modoEliminacion;
	if (modoEliminacion) {
		btnBorrar.textContent = 'Cancelar modo';
		btnBorrar.setAttribute('class', 'btn btn-secondary mt-3')
		accordionItems.forEach(item => {
			item.style.border = "1px solid red"
			item.setAttribute('class', 'accordion-item')
		})
	} else {
		btnBorrar.textContent = 'Borrar modo';
		btnBorrar.setAttribute('class', 'btn btn-danger mt-3')
		accordionItems.forEach(item => {
			item.style.border = ""
			item.setAttribute('class', 'accordion-item border')
		})
		accordionItems = document.querySelectorAll('.accordion-item');
		if (accordionItems.length == 0) {
			btnBorrar.style.display = "none"
		}
	}
});

accordionItems.forEach(item => {
	item.addEventListener('click', () => {
		if (modoEliminacion) {
			const id = item.getAttribute('name');
			const url = `http://localhost:8095/clases/delete/${id}`;
			fetch(url, { method: 'GET' })
				.then(() => {
					item.remove();
				})
				.catch(error => {
					console.error(`Error al eliminar clase ${id}:`, error);
				});
		}
	});
});