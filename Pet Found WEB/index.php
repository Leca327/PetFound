<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pet Found</title>
    <link rel="stylesheet" href="./lib/padrao.css">
    <link rel="stylesheet" href="./index.css">
    <link rel="stylesheet" href="./cards1.css">
    <link rel="stylesheet" href="./lib/footer.css">
    <link rel="stylesheet" href="./lib/navbar.css">
    <link rel="stylesheet" href="./lib/responsivonavbar.css">
</head>

<body>
    <?php include('./lib/navbar.php'); ?>
    
    <!-- FULL WIDTH SLIDER -->
    <section class="paralax">

    </section>

    <section class="info">
        <article class="col-1_4 init-hidden">
            <img class="ncimg" src="assets/cartoonadt.png" alt="" />
            <div class="pqadt">

                <h1 class="hpqadt">Por que adotar?</h1>
                <br>
                <p>1. Nesse instante existe diversos animais sem um lar prontos para receber muito amor e carinho que só
                    esperam você. </p>
                <br>
                <p>2. E não tem algo melhor do que ter alguém sempre ao seu lado e vê-lo todos os dias, alegres
                    esperando só um carinho e amor. Além de ver ele se transformar com muito cuidado e amor no seu amigo
                    pra vida. </p>
                <br>
                <p>3. Mas a pergunta mesmo é: Por que não mudar sua vida pra melhor e ainda receber muito amor e
                    lambejos a cada bom dia?</p>
                <a href="./cadpetserv/searchpet.php?typepet=allespe&genderpet=allgender&Porte=allporte&idade=allidade&tipanunc=Adocao&state=allstates&citypet=allcities&ordenacao=mais_novos">
                    <button class="adotar-button">Quero adotar!</button>
                </a>

            </div>
        </article>

        <article class="col-1_4 init-hidden">

            <img class="apimg" src="assets/cartoonapd.png" alt="" />

            <div class="pqapd">

                <h1 class="hpqapd">Por que apadrinhar?</h1>

                <br>
                <p>1. Muitos animais ficam meses até anos a procura de um lar, aumento os gastos que ONG possui para se
                    manter e ajudar os peludos. </p>
                <br>
                <p>2. Então você que se sensibiliza pela causa, ajude com uma quantia que fará a diferença na vida
                    desses animais.</p>
                <br>
                <p>3. Você ajudando um animal permite que a ONG ajude muitos outros.</p>

                <a href="./cadpetserv/searchpet.php?typepet=allespe&genderpet=allgender&Porte=allporte&idade=allidade&tipanunc=Padrinho&state=allstates&citypet=allcities&ordenacao=mais_novos">
                    <button class="padrin-button">Vire padrinho ou madrinha</button>
                </a>
            </div>
        </article>
    </section>

    <section class="nvpet">
        <br><br>
        <h1 class="hnvpets">Novos amigo de quatro patas por aqui</h1>
        <div class="adocao">
            <?php
            include('./lib/dbconnect.php');

            $sql = "SELECT * FROM pet WHERE aprovacaopet = true 
            AND petcod NOT IN (
                SELECT DISTINCT petcodpet
                FROM contatopet
                WHERE adotou = true
            ) ORDER BY CONCAT(pet.dtp, ' ', pet.hrp) DESC;";
            $result = $mysqli->query($sql);
            $counter = 0; // Variável para contar as iterações

            echo "<div class='gallery'>"; // Abre a div principal
            
            while ($pet_data = mysqli_fetch_assoc($result)) {
                $petcodpet = $pet_data['petcod'];
                $sql_imagem = "SELECT * FROM imagem WHERE petcodpet='$petcodpet';";
                $result_imagem = $mysqli->query($sql_imagem);

                if ($row_imagem = mysqli_fetch_assoc($result_imagem)) {
                    $imageData = $row_imagem['img'];
                    $imageType = 'image/jpeg';
                } else {
                    // Defina uma imagem padrão caso nenhuma imagem seja encontrada
                    $imagePath = './assets/semimg.png';
                    $imageData = file_get_contents($imagePath);
                    $imageType = mime_content_type($imagePath);
                }
                if ($pet_data['finalidade'] == "Adocao") {
                    $anun = "Adote-me";
                } else if ($pet_data['finalidade'] == "Padrinho") {
                    $anun = "Apadrinhe-me";
                } else if ($pet_data['finalidade'] == "Pad_Ado") {
                    $anun = "Ajude-me";
                }

                echo '<div class="cards1">'; // Abre um card
                echo '<h3 style="color: black;">' . $pet_data['nomepet'] . '</h3>';
                echo '<img class="imgcardpet" src="data:' . $imageType . ';base64,' . base64_encode($imageData) . '" alt="Pet Image">';
                echo '<p class="title"></p>';
                echo '<h3 style="color: black;">' . $pet_data['nomepet'] . '</h3>';
                echo '<h4 style="color: black;">' . $pet_data['estadop'] . ", " . $pet_data['cidadep'] . '</h4>';
                echo '<button class="butcardpet"><a href="./anuncio/perfilpet.php?petcod=' . $pet_data['petcod'] . '">' . $anun . '</a></button>';
                echo '</div>'; // Fecha o card

                $counter++; // Incrementa o contador

                if ($counter === 6) {
                    break; // Encerra o loop após 6 iterações
                }
            }

            echo "</div>"; // Fecha a div principal

            if ($counter === 0) {
                echo '<br><button class="petlar">Todos os Pets por aqui estão com um lar </button><br><br><br><br>';
            }
            ?>
            <a href='./cadpetserv/searchpet.php?typepet=allespe&genderpet=allgender&Porte=allporte&idade=allidade&tipanunc=allfinal&state=allstates&citypet=allcities&ordenacao=mais_novos'>
                <button class="semlar">Aumigos sem lar a muito tempo.</button>
            </a>
            <br><br><br>
        </div>
        <!--
            <div class="card">
                <img class="cards" src="assets/tuneco.jpeg" alt="">
                <p class="title">Coquinha gelada</p>
                <a class="aa" href="#">Adote-me</a>
            </div>
            <div class="card">
                <img class="cards" src="assets/srbb.jpeg" alt="">
                <p class="title">Sr BB</p>
                <a class="aa" href="">Adote-me</a>
            </div>
            <div class="card">
                <img class="cards" src="assets/srbb2.jpeg" alt="">
                <p class="title">Sr bb depressivo</p>
                <a class="aa" href="">Adote-me</a>
            </div>
            <div class="card">
                <img class="cards" src="assets/cachorro4.jpeg" alt="">
                <p class="title">escócia</p>
                <a class="aa" href="">Adote-me</a>
            </div>
        -->
        <script src="./lib/responsivenavbar.js"></script>
    </section>

    <?php include('./lib/footer.php'); ?>
    <script src="./lib/observer.js"></script>

    <script>
    // Função para abrir o link
    function abrirLink() {
        window.open("https://www.youtube.com/watch?v=rIgmb9DBvNA&pp=ygUJRE1FREVJUk9T", "_blank");
    }

    // Sequência de teclas a ser detectada (ArrowUp, ArrowUp, ArrowDown, ArrowDown, ArrowLeft, ArrowRight, ArrowLeft, ArrowRight, b, a)
    var sequenciaTeclas = [38, 38, 40, 40, 37, 39, 37, 39, 66, 65];
    var teclaAtual = 0;

    // Adiciona um ouvinte de evento de teclado à página
    document.addEventListener("keydown", function(event) {
        if (event.keyCode === sequenciaTeclas[teclaAtual]) {
            teclaAtual++;
            if (teclaAtual === sequenciaTeclas.length) {
                abrirLink();
                teclaAtual = 0; // Reinicia a sequência
            }
        } else {
            teclaAtual = 0; // Reinicia a sequência se a tecla pressionada não coincidir
        }
    });
</script>

</body>

</html>