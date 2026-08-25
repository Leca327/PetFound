<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pet Found</title>
    <link rel="stylesheet" href="padrao.css">
    <link rel="stylesheet" href="footer.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" integrity="sha512-MV7K8+y+gLIBoVD59lQIYicR65iaqukzvf/nwasF0nqhPay5w/9lJmVM2hMDcnK1OnMGCdVK+iQrJ7lzPJQd1w==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel='stylesheet' href='https://npmcdn.com/flickity@1.1/dist/flickity.min.css'>

</head>

<footer>
    <!--<main></main>-->
    <div id="footer_content">
        <div id="footer_contacts">
            <h2>PetFound</h2>
            <p>Fazendo aumigos pela vida.</p>

            <div id="footer_social_media">
                <a href="https://www.instagram.com/bomfim7274/" class="footer-link" id="instagram">
                    <i class="fa-brands fa-instagram"></i>
                </a>

                <a href="#" class="footer-link" id="facebook">
                    <i class="fa-brands fa-facebook-f"></i>
                </a>

                <a href="#" class="footer-link" id="twitter">
                    <i class="fa-brands fa-twitter"></i>
                </a>
            </div>
            <br>
            <ul class="footer-list">
                <a href="#" class="footer-link">Termos de Uso e Pol&iacute;ticas de Privacidade</a>
            </ul>
        </div>
        <?php

        $caminhoArquivo5 = './cadpetserv/searchpet.php';
        $caminhoArquivo6 = '../cadpetserv/searchpet.php';
        $caminhoArquivo7 = './searchpet.php';
        $caminhoArquivo8 = './config.php';
        $caminhoArquivo9 = './perfil.php';

        if (file_exists($caminhoArquivo6)) {
            $url7 = '../cadpetserv/searchpet.php?typepet=allespe&genderpet=allgender&Porte=allporte&idade=allidade&tipanunc=Adocao&state=allstates&citypet=allcities&ordenacao=mais_novos';
            $url8 = '../cadpetserv/searchpet.php?typepet=allespe&genderpet=allgender&Porte=allporte&idade=allidade&tipanunc=Padrinho&state=allstates&citypet=allcities&ordenacao=mais_novos';
            $url9 = '../cadpetserv/searchserv.php';
            $url10 = '../config.php';
            $url11 = '../perfil.php';
            $url6 = '../doacoes.php';
            $url5 = '../sobrenos.php';
            $url4 = '../faleconosco.php';
            
        } else {
            $url7 = './cadpetserv/searchpet.php?typepet=allespe&genderpet=allgender&Porte=allporte&idade=allidade&tipanunc=Adocao&state=allstates&citypet=allcities&ordenacao=mais_novos';
            $url8 = './cadpetserv/searchpet.php?typepet=allespe&genderpet=allgender&Porte=allporte&idade=allidade&tipanunc=Padrinho&state=allstates&citypet=allcities&ordenacao=mais_novos';
            $url9 = './cadpetserv/searchserv.php';
            $url10 = './config.php';
            $url11 = './perfil.php';
            $url6 = './doacoes.php';
            $url5 = './sobrenos.php';
            $url4 = './faleconosco.php';
        }

        ?>

        <ul class="footer-list">
            <li>
                <h3>Pets e Serviços</h3>
            </li>
            <li>
                <a href=<?php echo "'$url7' " ?>class="footer-link">Quero adotar</a>
            </li>
            <li>
                <a href=<?php echo "'$url8' " ?> class="footer-link">Quero Apadrinhar</a>
            </li>
            <li>
                <a href=<?Php echo "'$url9' " ?> class="footer-link">Quero Servi&ccedil;os</a>
            </li>
            <li>
                <a href="#" class="footer-link" onclick="openPopup()">Quero divulgar</a>
            </li>
        </ul>

        <ul class="footer-list">
            <li>
                <h3>Informações</h3>
            </li>
            <li>
                <a href="<?php echo $url6; ?>" class="footer-link">Contribua para o PetFound</a>
            </li>
            <li>
                <a href="<?php echo $url5; ?>#sbnos" class="footer-link">Sobre n&oacute;s</a>
            </li>
            <li>
                <a href="<?php echo $url5; ?>#obj" class="footer-link">Objetivos</a>
            </li>
            <li>
                <a href="<?php echo $url4; ?>" class="footer-link">Entrar em contato</a>
            </li>
        </ul>


        <!--<ul class="footer-list">
            <li>
                <h3>Parceria</h3>
            </li>
            <li>
                <a href="#" class="footer-link">Vire um Parceiro</a>
            </li>
            <li>
                <a href="#" class="footer-link">Conheça nossos Parceiros</a>
            </li>
            <li>
                <a href="#" class="footer-link">Compre nas lojas Parceiras</a>
            </li>

        </ul>

        <div id="footer_subscribe">
            <br>
            <h3>Inscrever-se</h3>
            <p>Digite seu e-mail para receber as novidades!</p>

            <div id="input_group">
                <input type="email" id="email" size="100" />

                <button>
                    <i class="fa-regular fa-envelope"></i>
                </button>
            </div>
        </div>-->

        <ul class="footer-list">
            <li>
                <h3>Perfil</h3>
            </li>
            <li>
                <a href=<?php echo "'$url11' " ?>class="footer-link">Meu Perfil</a>
            </li>
            <li>
                <a href=<?php echo "'$url10' " ?>class="footer-link">Redefina a Senha</a>
            </li>
            <!--<li>
                <a href="#" class="footer-link">Mercado de pontos</a>
            </li>-->
            <li>
                <a href="#" class="footer-link"></a>
            </li>
        </ul>

    </div>



    <div id="footer_copyright">
        &#169;
        2023 Todos os direitos reservados
    </div>

    <script>
        function openPopup() {
            document.getElementById('popup').style.display = 'block';
            document.addEventListener('keydown', handleEscapeKey);

        }

        function closePopup() {
            document.getElementById('popup').style.display = 'none';
            document.removeEventListener('keydown', handleEscapeKey);
        }

        function handleEscapeKey(event) {
            if (event.key === 'Escape') {
                closePopup();
            }
        }
    </script>

</footer>