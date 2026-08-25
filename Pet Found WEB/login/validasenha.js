var senha = document.getElementById("senha");
var confirmar_senha = document.getElementById("csenha");

function validarSenha() {
    if (senha.value != confirmar_senha.value) {
        confirmar_senha.setCustomValidity("As senhas não conferem. Tente novamente.");
    } else {
        confirmar_senha.setCustomValidity("");
    }
}

senha.onchange = validarSenha;
confirmar_senha.onkeyup = validarSenha;