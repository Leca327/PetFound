<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="./config.css">
    <link rel="stylesheet" href="./lib/padrao.css">
    <link rel="stylesheet" href="./lib/footer.css">
    <link rel="stylesheet" href="./lib/navbar.css">
    <link rel="stylesheet" href="./lib/responsivonavbar.css">
    <link rel="stylesheet" href="doacoes.css">

</head>

<body>
    <?php
    include('./lib/navbar.php');
    include('./lib/dbconnect.php');
    ?>



    <div class="msgdoa">
    <img class="imgheart" src="./assets/donate.png" alt="">

        <h2 class="">Nos ajude a manter nosso site no ar</h2>
        <br>
        <h3 class=""> faça sua doação de qualquer valor!</h3>
        <br><br>
        <div class="credit">
            <a class="cardcredit" href="https://buy.stripe.com/7sI6pF5ff1bJ8H69AA">
                <i class="fa-solid fa-credit-card"></i>
                Cartão de crédito</a><br>
            <h2 class="">Ou</h2>
            <a id="linkAbrirImagem" class="cardcredit" href="https://buy.stripe.com/7sI6pF5ff1bJ8H69AA">
                <i class="fa-brands fa-pix"></i>
                Pagamento via Pix</a>
                <img id="imagemExibida" src="./assets/donatepix.png" class="imgdonate" style="display: none;">
                


        </div>
    </div>
   
    <br><br><br><br>

    <?php include('./lib/footer.php'); ?>
</body>
<script>
    // Elementos
    var linkAbrirImagem = document.getElementById("linkAbrirImagem");
    var imagemExibida = document.getElementById("imagemExibida");

    // URL da imagem
    var imagemURL = "./assets/donatepix.png"; // Substitua pelo URL da sua imagem

    var imagemVisivel = false; // Variável de controle para rastrear a visibilidade da imagem

    // Ação ao clicar no link
    linkAbrirImagem.addEventListener("click", function(event) {
        event.preventDefault(); // Impede que o link direcione para outra página

        if (imagemVisivel) {
            // Se a imagem estiver visível, oculte-a
            imagemExibida.style.display = "none";
        } else {
            // Se a imagem estiver oculta, exiba-a
            imagemExibida.src = imagemURL;
            imagemExibida.style.display = "block";
        }

        // Alterne o estado da imagem
        imagemVisivel = !imagemVisivel;
    });
</script>


</html>