let buttons=document.getElementsByClassName("modifyButton")
let notasField = document.getElementsByClassName("notes")
notasField.forEach(function(button) {
	button.addEventListener('focus', function() {
		let placeholderValue = button.placeholder
			button.value = placeholderValue
//		notasField.addEventListener('focus', function() {
//			let placeholderValue = notasField.placeholder
//			alert(placeholderValue)
//			notasField.value = placeholderValue
//		})
//		let submitBtn = document.getElementsByClassName("sent")[0]
//		submitBtn.addEventListener('click', function() {
//			let nombreField = document.getElementsByClassName("exampleInputName")[0]
//			let placeholderValue = nombreField.placeholder
//			if (nombreField.value == ""){
//				nombreField.value = placeholderValue
//				}
//		})
	})
})