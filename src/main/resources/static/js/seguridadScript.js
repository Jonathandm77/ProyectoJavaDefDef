let elemDatos=document.getElementById("datos")
let elemPasswd=document.getElementById("passwd")
let buttonDatos=document.getElementById("data")
let buttonContras=document.getElementById("contras")

elemPasswd.style.display="none";

buttonDatos.addEventListener('focus',function(){
    elemDatos.style.display="block"
    elemPasswd.style.display="none"
})

buttonContras.addEventListener('focus',function(){
    elemPasswd.style.display="block"
    elemDatos.style.display="none"
})



