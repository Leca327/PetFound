<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pet Found</title>
    <link rel="stylesheet" href="./lib/padrao.css">
    <link rel="stylesheet" href="./lib/footer.css">
    <link rel="stylesheet" href="./lib/navbar.css">
    <link rel="stylesheet" href="./sobre.css">
    <link rel="stylesheet" href="./lib/responsivonavbar.css">
</head>

<body>
    <span id="sbnos"></span>
    <?php include('./lib/navbar.php'); ?>

    <div class="nos">
        <h4 class="sntittle">Sobre nós</h4>
        <h2 class="tittle">Resumo</h2>
        <div class="sobre">
            <h1 class="slogan">salvando cada dia mais animais!</h1>
            <img class="imgcentro" src="./assets/sobrenoss.png" alt="">
            <h5 class="intro">O “Pet Found” é um projeto que busca melhorar a vida dos animais que não têm um lar, aumentando o número de adoções e diminuindo o número de animais nas ruas e abrigos de ONGs. Com o objetivo de promover a adoção responsável, o sistema também busca estabelecer padrinhos e madrinhas para animais com necessidades especiais, contribuindo para melhorar a qualidade de vida desses animais. É uma iniciativa que aumenta a visibilidade das ações de adoção e conscientização, ajudando as ONGs a se manterem ativas para ajudar outros animais. Por meio deste projeto, os animais recebem o cuidado e amor necessários para ter uma vida saudável e feliz, além de melhorar a efetividade das campanhas de conscientização sobre a importância da adoção responsável. Neste projeto o diferencial é a oportunidade de juntar todas as ongs, pessoas e empresas em um único lugar.</h5>


        </div>

        <br><br><br><br>
        <h4 class="intittle">integrantes</h4>

        <div class="imgint">

            <div class="textfield">
                <img src="./assets/leca.jpeg" alt="">
                <h5 style="text-align: center; font-size: 2.5vh;">Leticia<br> Full-stack</h5>
            </div>

            <div class="textfield">
                <img src="./assets/bomfim.jpg" alt="">
                <h5 style="text-align: center; font-size: 2.5vh;">Bomfim<br> Front-end</h5>
            </div>

            <div class="textfield">
                <img src="./assets/duda.jpg" alt="">
                <h5 style="text-align: center; font-size: 2.5vh;">Duda<br> Java</h5>
            </div>

            <div class="textfield">
                <img src="./assets/bigorna.jpg" alt="">
                <h5 style="text-align: center; font-size: 2.5vh;">Gustavo<br> Front-end</h5>
            </div>
            <span id="obj"></span>
        </div>

        <h4 class="sntittle">Objetivos</h4>


        <div class="objt">
            <h5 class="definicao">O projeto "Pet Found" consiste em um sistema que tem como propósito aprimorar, otimizar e simplificar a comunicação entre possíveis adotantes, padrinhos e madrinhas com as Organizações Não Governamentais (ONGs) e cuidadores temporários. </h5>
            <img class="imgcentro" src="./assets/imgsobre.png" alt="">
            <h4 class="objtgr">O sistema “Pet Found” é uma plataforma digital integrada que tem como objetivo principal promover a adoção responsável de animais sem tutores, além de estabelecer padrinhos e madrinhas para animais com necessidades especiais, contribuindo para melhorar a qualidade de vida desses animais que foram abandonados ou vivem nas ruas sem um lar. Através da rede colaborativa criada pelo “Pet Found” é possível aumentar a visibilidade das iniciativas de adoção, melhorando a efetividade das campanhas de conscientização sobre a importância da adoção responsável e facilitar o encontro entre potenciais adotantes e animais disponíveis para adoção. Com isso, espera-se garantir que os animais recebam o cuidado e o amor necessários, assegurando que sejam inseridos em lares responsáveis e felizes.</h4>
        </div>
    </div>



    <?php include('./lib/footer.php'); ?>
</body>


</html>
