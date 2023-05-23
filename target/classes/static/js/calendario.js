
document.addEventListener('DOMContentLoaded', function() {
	var calendarEl = document.getElementById('calendar');
	var url = new URL(window.location.href);

	// Obtener los parámetros de la URL
	var searchParams = new URLSearchParams(url.search);

	// Obtener el valor del parámetro "fechaActual"
	var fechaActual = searchParams.get('fechaActual');
	if (fechaActual != null)
		var fechaActualInt = parseInt(fechaActual);
	else
		var fechaActualInt = null
	var calendar = new FullCalendar.Calendar(calendarEl, {
		initialView: 'dayGridMonth',
		displayEventTime: false,
		initialDate: fechaActualInt,
		locale: 'es',
		eventClassNames: 'event-custom',
		eventColor: 'transparent',
		buttonText: {
			today: 'Hoy' // Cambiar el texto del botón Today
		},
		dateClick: function(info) {
			var modal = document.getElementById('crearClase');
			var modalInstance = new bootstrap.Modal(modal);

			var modalMessage = modal.querySelector('.error-text');
			modalMessage.textContent = 'Crear una clase para el día ' + info.dateStr;

			var form = modal.querySelector('form');
			var actionUrl = '/calendario/add';
			var dateParam = 'fecha=' + info.dateStr;

			form.setAttribute('action', actionUrl + '?' + dateParam);

			var botonCancelCreate = document.getElementById("cancelCreateModal")
			var botonSuperiorCancelCreate = document.getElementById("aboveCancelCreate")
			botonCancelCreate.addEventListener('click', function() {
				modalInstance.hide()
			})
			botonSuperiorCancelCreate.addEventListener('click', function() {
				modalInstance.hide()
			})


			modalInstance.show();

		},
		eventSources: [{
			url: '/calendario/clases',
			method: 'GET',
			failure: function() {
				console.log('Error al cargar las clases.');
			},
			success: function(response) {
				var events = [];

				// Convertir las clases a objetos de evento compatibles con el calendario
				for (var i = 0; i < response.length; i++) {
					var clase = response[i];
					var evento = {
						title: clase.hora + ': ' + clase.alumno.nombre + ' ' + clase.alumno.apellidos, // Título 
						start: new Date(clase.fecha), // Fecha de inicio 
						id: clase.id,
					};
					events.push(evento);
				}

				calendar.getEventSources().forEach(function(eventSource) {
					eventSource.remove();
				});
				calendar.addEventSource(events);

			}
		}],
		eventClick: function(info) {
			var modal = document.getElementById('delete-modal');
			var modalInstance = new bootstrap.Modal(modal);

			var form = modal.querySelector('form');
			var actionUrl = '/calendario/delete/';
			var idParam = 'id=' + info.event.id;

			form.setAttribute('action', actionUrl + '?' + idParam);

			var botonCancelDelete = document.getElementById("cancelDeleteModal")
			var botonSuperiorCancelDelete = document.getElementById("aboveCancelDelete")
			botonCancelDelete.addEventListener('click', function() {
				modalInstance.hide()
			})
			botonSuperiorCancelDelete.addEventListener('click', function() {
				modalInstance.hide()
			})
			

			modalInstance.show();
		}
	});
	calendar.render();

	var nextMonthBtn = document.querySelector('.fc-next-button');
	var prevMonthBtn = document.querySelector('.fc-prev-button');

	nextMonthBtn.addEventListener('click', vaciarEventSource);
	prevMonthBtn.addEventListener('click', vaciarEventSource);

	function vaciarEventSource() {
		calendar.refetchEvents();
	}


});

