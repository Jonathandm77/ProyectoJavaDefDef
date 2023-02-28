const inputs = document.querySelectorAll(".notes");

for(let input of inputs){
  input.addEventListener('focus', () => {
    input.value = input.placeholder;

})}

const formEdit=document.querySelectorAll(".editModal")

for(let form of formEdit){
	form.addEventListener('submit',(e)=>{
		
		const name=e.target.querySelector(".exampleInputName")
		const notas=e.target.querySelector(".notes")
		
		if(name.value=="" || notas.value==""){
		if(name.value==""){
		name.value=name.placeholder
		}
		if(notas.value==""){
		notas.value=notas.placeholder
		}
		return false;
		}
		return true;
	})
}