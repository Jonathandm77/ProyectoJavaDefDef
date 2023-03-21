
    const esProfesorCheckbox = document.querySelector('#esProfesor');
    const selectProfesorDiv = document.querySelector('#selectProfesor');

    esProfesorCheckbox.addEventListener('change', () => {
        if (esProfesorCheckbox.checked) {
            selectProfesorDiv.style.display = 'block';
        } else {
            selectProfesorDiv.style.display = 'none';
        }
    });

