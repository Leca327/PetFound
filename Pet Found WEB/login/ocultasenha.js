// JavaScript Document


var imgAtual = "../assets/mostrar.png";
var imgAnterior = "../assets/ocultar.png";

function trocarImagem(){    
	document.getElementById("ms").src = imgAtual;
	let aux = imgAtual;
	imgAtual = imgAnterior;
	imgAnterior = aux;
}

function mostrarOcultarSenha(){
	var senha=document.getElementById("senha");
	if(senha.type=="password"){
		senha.type="text";
	}else{
		senha.type="password";
	}
}

var imgAtual = "../assets/mostrar.png";
var imgAnterior = "../assets/ocultar.png";

function trocarImagem1(){    
	document.getElementById("ms1").src = imgAtual;
	let aux = imgAtual;
	imgAtual = imgAnterior;
	imgAnterior = aux;
}

function mostrarOcultarSenha1(){
	var senha=document.getElementById("csenha");
	if(senha.type=="password"){
		senha.type="text";
	}else{
		senha.type="password";
	}
}



