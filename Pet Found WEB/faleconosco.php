<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="./faleconosco.css">
    <link rel="stylesheet" href="./lib/navbar.css">
    <link rel="stylesheet" href="./lib/footer.css">
    <link rel="stylesheet" href="./lib/padrao.css">
</head>

<body>
    <?php
    include('./lib/navbar.php'); ?>

    <div class="centro">
        <h1 class="tittle4">Fale conosco</h1>
        <img src="./assets/faleconosco.png" class="imgSac">

        <a class="linkwhats" href="https://wa.me/+5521972410139" target="_blank">
            <h1 class="Whats">
                <i class="fa-brands fa-whatsapp"></i>
                Clique Aqui!
            </h1>
        </a>
        <h1 class="ou">Ou</h1>
        <div class="highlighted-email">
            <h1 class="email">
                <i class="fa-solid fa-envelope"></i>
                PetFound302@outlook.com
            </h1>
        </div>


    </div>
    <form action="ticket.php" method="post">
        <div class="selectlabel">
            <br>
            <h5>Crie um ticket que iremos entrar em contato</h5>
            <br>
            <label id="labelselect" for="assunto">Assunto:</label>
            <select id="assunto" name="assunto" required>
                <option value="Contato de serviço finalizado">Contato de serviço finalizado</option>
                <option value="Contato de pet finalizado">Contato de pet finalizado</option>
                <option value="Erro no cadastro de um anuncio">Erro no cadastro de um anuncio</option>
                <option value="feedback">Feedback</option>
                <option value="outros">Outro</option>
            </select>
            <br>
        </div>
        <div id="formall">
            <div id="formfaleleft">

                <div id="outrosAssunto" style="display:none;">
                    <label class="labelform" for="assunto_outro" style="display: flex;">Especifique:</label>
                    <input class="inputform" type="text" id="assunto_outro" name="assunto_outro">
                </div>

                <br>

                <label class="labelform" id="nomel" for="nome">Nome:</label>
                <input type="text" id="nome" class="inputform" name="nome" required>
                <br>

                <label class="labelform" id="emaill" for="email">E-mail:</label>
                <input type="email" class="inputform" id="email" name="email" required>
                <br>

         
                <br>
            </div>

            <div id="formfaleright">

            <label class="labelform" id="titulol" for="titulo">Título:</label>
                <input type="text" class="inputform" id="titulo" name="titulo" required>

                <label class="labelform" for="mensagem">Mensagem:</label>
                <textarea id="mensagem" name="mensagem" rows="4" style="resize: none;" required></textarea>
                <br>
            </div>
        </div>
        <input type="submit" value="Enviar" class="submit-button">


    </form>



    <br><br><br><br><br><br><br><br><br>
</body>
<?php
include('./lib/footer.php');
?>
<script>
    var selectAssunto = document.getElementById("assunto");
    var outrosAssunto = document.getElementById("outrosAssunto");
    var inputAssuntoOutro = document.getElementById("assunto_outro");

    selectAssunto.addEventListener("change", function() {
        if (selectAssunto.value === "outros") {
            outrosAssunto.style.display = "block";
            inputAssuntoOutro.required = true;
        } else {
            outrosAssunto.style.display = "none";
            inputAssuntoOutro.required = false;
        }
    });
</script>



</html>