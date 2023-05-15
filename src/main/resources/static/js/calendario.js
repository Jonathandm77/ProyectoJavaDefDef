
document.addEventListener('DOMContentLoaded', function() {
	var calendarEl = document.getElementById('calendar');
	var calendar = new FullCalendar.Calendar(calendarEl, {
		initialView: 'dayGridMonth',
		themeSystem: 'bootstrap5',
		displayEventTime: false,
		buttonText: {
			today: 'Hoy' // Cambia el texto del botón "Today" a "Hoy"
		},
		dateClick: function(info) {
			var modal = document.getElementById('crearClase');
			var modalInstance = new bootstrap.Modal(modal);

			var modalMessage = modal.querySelector('.error-text');
			modalMessage.textContent = 'Crear una clase para el día ' + info.dateStr;

			var form = modal.querySelector('form');
			var actionUrl = form.getAttribute('action');
			var dateParam = 'fecha=' + info.dateStr;

			form.setAttribute('action', actionUrl + '?' + dateParam);

			modalInstance.show();

		},
		eventSources: [{
			url: '/calendario/clases', // URL para obtener las clases desde el servidor
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
						title: clase.alumno.nombre, // Título de la clase
						start: new Date(clase.fecha), // Fecha de inicio de la clase
						// Otras propiedades del evento si las tienes
					};
					events.push(evento);
				}

				calendar.getEventSources().forEach(function(eventSource) {
					eventSource.remove();
				});
				calendar.addEventSource(events);

			}
		}]
	});
	calendar.render();

	var nextMonthBtn = document.querySelector('.fc-next-button');
	var prevMonthBtn = document.querySelector('.fc-prev-button');

	// Agrega eventos de clic a los botones
	nextMonthBtn.addEventListener('click', vaciarEventSource);
	prevMonthBtn.addEventListener('click', vaciarEventSource);

	// Función para vaciar el EventSource y recargar los eventos
	function vaciarEventSource() {
		calendar.refetchEvents();
	}

});

