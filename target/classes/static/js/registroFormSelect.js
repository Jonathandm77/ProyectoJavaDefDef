
    const esProfesorCheckbox = document.querySelector('#esProfesor');
    const selectProfesorDiv = document.querySelector('#selectProfesor');
    
    const esAlumnoCheckbox = document.querySelector('#esAlumno');
    const selectAlumnoDiv = document.querySelector('#selectAlumno');

    esProfesorCheckbox.addEventListener('change', () => {
        if (esProfesorCheckbox.checked) {
            selectProfesorDiv.style.display = 'block';
             selectAlumnoDiv.style.display = 'none';
             esAlumnoCheckbox.checked=false
        } else {
            selectProfesorDiv.style.display = 'none';
        }
    });
    
        esAlumnoCheckbox.addEventListener('change', () => {
        if (esAlumnoCheckbox.checked) {
            selectAlumnoDiv.style.display = 'block';
            selectProfesorDiv.style.display= 'none';
            esProfesorCheckbox.checked=false
        } else {
            selectAlumnoDiv.style.display = 'none';
        }
    });

