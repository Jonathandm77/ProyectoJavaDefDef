let notasField=document.getElementById("notas")
let submitBtn=document.getElementById("sent")
let nombreField=document.getElementById("exampleInputName")

notasField.addEventListener('focus',function(){
let placeholderValue=notasField.placeholder
notasField.value=placeholderValue})

submitBtn.addEventListener('click', function(){
    let placeholderValue=nombreField.placeholder
    if(nombreField.value=="")
    nombreField.value=placeholderValue
})