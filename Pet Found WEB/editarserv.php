<?php
include('./lib/dbconnect.php');

if (isset($_GET["codigo"]) && !empty($_GET["codigo"])) {

    $query = "select * from servico where servcod = '" . $_GET["codigo"] . "'";
    $resultado = mysqli_query($mysqli, $query);

    $dado = mysqli_fetch_array($resultado);
} else {
    header("Location: regis.php?mensagem=Selecione um usuario para editar.");
    exit();
}

?>

<!DOCTYPE html>
<html lang="PT-BR">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edição de Pet</title>
    <link rel="stylesheet" href=" ./editarpetserv.css">
    <link rel="stylesheet" href="./lib/padrao.css">
</head>

<body>

    <?php

    if (!empty($_GET['codigo'])) {
        // Use prepared statements to prevent SQL injection
        $sqlSelect = "SELECT * FROM servico WHERE servcod = '" . $_GET['codigo'] . "'";
        $result = mysqli_query($mysqli, $sqlSelect);

        if ($result->num_rows > 0) {
            // Assuming 'nomepet' is a column in the 'pet' table
            $row = $result->fetch_assoc();

            $codpet = $_GET['codigo'];
            $nome = $row['nomeserv'];
            $data = $row['dts'];
            $dataObj = date_create($data);
            $dtf = date_format($dataObj, "d/m/Y");
            $est = $row['estados'];
            $cid = $row['cidades'];
            $descpet = $row['descserv'];
            $pessoa = $row['pessoa_codp'];
            $admapv = $row['admin_codadmn'];
            $apv = $row['aprovacaoserv'];
            $preco = $row['preco'];
            $preco = str_replace('.', ',', $preco);
            $sql_imagem = "SELECT * FROM imagem WHERE servicocodserv='" . $_GET['codigo'] . "';";
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
        } else {
            echo "Nenhum serviço encontrado com esse código.";
        }
    }
    ?>

    <?php
    /* echo "
            <label class='picture' for='picture__input' tabIndex='0'>
                <span class='picture__image'></span></label>
            <input type='file' name='picture__input' id='picture__input'>
            <script  class='imgpet' src='data:$imageType;base64," . base64_encode($imageData) . "'></script>
            <script src='./img.js'></script>
            "; 
            <label class='picture' for='picture__input' tabIndex='0'>
    <span class='picture__image'>
    <input type='file' name='picture__input' id='picture__input'>
        <img class='picture__img' src='data:$imageType;base64," . base64_encode($imageData) . "'>
        <script src='./img.js'></script>
    </span>
</label>
            
            */
    echo "<form method='post' action='editserv.php' enctype='multipart/form-data' onsubmit='prepareAndSubmit(event)'>
            <div class='main-login'>
    
                <div class='left-login'>

                    <header>Editar dados do serviço $nome</header>
                    <a href='javascript:history.back();'>
                    <h4 class='inicio'>◄ Voltar</h4></a>
          
            <label class='picture' for='picture__input' tabIndex='0'>
            <span class='picture__image'>
            <img class='imgpet' id='petImage' src='data:$imageType;base64," . base64_encode($imageData) . "' alt='Pet Image'>                    
            <input type='file' name='picture__input' id='picture__input' disabled ></span></label>    

            <script src='./img.js'></script>

           
            <div class='textfieldL'>
                <label class='usuario' for='nome'>Nome</label>
                <input type='text' placeholder='Nome do Serviço' id='nm' name='nm' value='$nome' required disabled/>
            </div>
            </div>

            <button id='alterar-btn' class='btn-singup' onclick='enableFields(event)'>Alterar</button>
            <button type='submit' class='btn-singup' id='enviar-btn' name='enviar-btn' style='display: none;'>Enviar</button>
    <div class='right-login'>
            <div class='textfield'>
                <label class='usuario' for='preco'>Preço</label>
                <input type='text' placeholder='R$' id='preco' name='preco' value='$preco' required disabled />
            </div>
        

           
       ";
    ?>
    <script>
        const precoInput = document.getElementById('preco');

        precoInput.addEventListener('input', (event) => {
            let value = event.target.value.replace(/\D/g, '');
            value = value.replace(/(\d)(\d{2})$/, '$1,$2');
            value = value.replace(/(?=(\d{3})+(\D))\B/g, '.');

            precoInput.value = `${value}`;
        });
    </script>

        <div class='textfield'>
            <label class='usuario' for='end'>Estado</label>
            <select placeholder='Estado' id='estpet' name='estpet' required disabled>
                <option value='AC' <?php if ($est == 'AC') echo 'selected'; ?>>AC</option>
                <option value='AL' <?php if ($est == 'AL') echo 'selected'; ?>>AL</option>
                <option value='AP' <?php if ($est == 'AP') echo 'selected'; ?>>AP</option>
                <option value='AM' <?php if ($est == 'AM') echo 'selected'; ?>>AM</option>
                <option value='BA' <?php if ($est == 'BA') echo 'selected'; ?>>BA</option>
                <option value='CE' <?php if ($est == 'CE') echo 'selected'; ?>>CE</option>
                <option value='DF' <?php if ($est == 'DF') echo 'selected'; ?>>DF</option>
                <option value='ES' <?php if ($est == 'ES') echo 'selected'; ?>>ES</option>
                <option value='GO' <?php if ($est == 'GO') echo 'selected'; ?>>GO</option>
                <option value='MA' <?php if ($est == 'MA') echo 'selected'; ?>>MA</option>
                <option value='MS' <?php if ($est == 'MS') echo 'selected'; ?>>MS</option>
                <option value='MT' <?php if ($est == 'MT') echo 'selected'; ?>>MT</option>
                <option value='MG' <?php if ($est == 'MG') echo 'selected'; ?>>MG</option>
                <option value='PA' <?php if ($est == 'PA') echo 'selected'; ?>>PA</option>
                <option value='PB' <?php if ($est == 'PB') echo 'selected'; ?>>PB</option>
                <option value='PR' <?php if ($est == 'PR') echo 'selected'; ?>>PR</option>
                <option value='PE' <?php if ($est == 'PE') echo 'selected'; ?>>PE</option>
                <option value='PI' <?php if ($est == 'PI') echo 'selected'; ?>>PI</option>
                <option value='RJ' <?php if ($est == 'RJ') echo 'selected'; ?>>RJ</option>
                <option value='RN' <?php if ($est == 'RN') echo 'selected'; ?>>RN</option>
                <option value='RS' <?php if ($est == 'RS') echo 'selected'; ?>>RS</option>
                <option value='RO' <?php if ($est == 'RO') echo 'selected'; ?>>RO</option>
                <option value='RR' <?php if ($est == 'RR') echo 'selected'; ?>>RR</option>
                <option value='SC' <?php if ($est == 'SC') echo 'selected'; ?>>SC</option>
                <option value='SP' <?php if ($est == 'SP') echo 'selected'; ?>>SP</option>
                <option value='SE' <?php if ($est == 'SE') echo 'selected'; ?>>SE</option>
                <option value='TO' <?php if ($est == 'TO') echo 'selected'; ?>>TO</option>
            </select>
        </div>


        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
            $(document).ready(function() {
                // Função para preencher as cidades
                function fillCities() {
                    var estadoSelecionado = $('#estpet').val();
                    $('#citypet').empty(); // Limpar opções anteriores

                    if (estadoSelecionado !== 'allstates') {
                        // Faça a solicitação à API para obter as cidades
                        $.getJSON('https://servicodados.ibge.gov.br/api/v1/localidades/estados/' + estadoSelecionado + '/municipios', function(data) {
                            $('#citypet').append('<option value="">Selecione sua cidade</option>');
                            $.each(data, function(index, cidade) {
                                $('#citypet').append('<option value="' + cidade.nome + '">' + cidade.nome + '</option>');
                            });

                            // Defina a cidade selecionada com base em $cid
                            var selectedCity = "<?php echo $cid; ?>";
                            $('#citypet').val(selectedCity);
                        });
                    }
                }

                // Chame a função para preencher as cidades no carregamento da página
                fillCities();

                // Chame a função novamente quando o select de estado é alterado
                $('#estpet').change(fillCities);
            });
        </script>

        <div class='textfield'>
            <label class="usuario" for="end">Cidade</label>
            <select placeholder='Cidade' name="citypet" id="citypet" required disabled>
                <option value="">Selecione sua cidade</option>
            </select>
        </div>

        <?php

        /*
            $sqlSelect = "SELECT * FROM pet WHERE petcod = '" . $_GET["codigo"] . "'";
            $result = mysqli_query($mysqli, $sqlSelect);

            if ($result->num_rows > 0) {
                $row = $result->fetch_assoc();
                $dono = $row['pessoacodp'];

                if (isset($dono)) {

                    $sqlSelect = "SELECT * FROM pessoa WHERE pcod = '" . $dono . "'";
                    $result = mysqli_query($mysqli, $sqlSelect);

                    if ($result->num_rows > 0) {

                        $row = $result->fetch_assoc();
                        $logado = $row['pcod'];
                        $lognm = $row['nomep'];
                        $lognick = $row['nickname'];
                        $ini = substr($logado, 0, 2);

                        if ($ini === "PF") {
                            echo "<div class='textfield'>
            <label class='usuario' for='fnl'>Finalidade</label>
            <select id='fnl' name='fnl' disabled required>
                <option value='Adocao' " . ($anunc == 'Adocao' ? 'selected' : '') . ">Adoção</option>
            </select>
        </div> ";
                        } else if ($ini === "PJ") {
                            $sqlSelect2 = "SELECT * FROM juridica WHERE cod_p = '" . $dono . "'";
                            $result2 = mysqli_query($mysqli, $sqlSelect2);

                            if ($result2->num_rows > 0) {
                                echo "fez";
                                $row2 = $result2->fetch_assoc();
                                $tp = $row2['tipoj'];
                                if ($tp === "Empresa") {
                                    echo "<div class='textfield'>
            <label class='usuario' for='fnl'>Finalidade</label>
            <select id='fnl' name='fnl' disabled required>
                <option value='Adocao' " . ($anunc == 'Adocao' ? 'selected' : '') . ">Adoção</option>
            </select>
        </div>";
                                } else if ($tp === "ONG") {
                                    echo "<div class='textfield'>
            <label class='usuario' for='fnl'>Finalidade</label>
            <select id='fnl' name='fnl' disabled required>
                <option value='Adocao' " . ($anunc == 'Adocao' ? 'selected' : '') . ">Adoção</option>
                <option value='Padrinho' " . ($anunc == 'Padrinho' ? 'selected' : '') . ">Apadrinhamento</option>
                <option value='Pad_Ado' " . ($anunc == 'Pad_Ado' ? 'selected' : '') . ">Adoção e Apadrinhamento</option>
            </select>
        </div>";
                                }
                            }
                        }
                    }
                }
            }*/

        echo "
    <div class='textfield'>
                <label class='usuario' for='desc'>Descrição do Serviço</label>
                <textarea class='no-resize' type='text' placeholder='Conte-nos detalhes sobre o serviço' id='desc' name='desc' disabled required>$descpet</textarea>
                </div> 

                <div class='textfield'>
                </div>

                <div class='textfield'>
            <input type='hidden' placeholder='Estado' id='petcod' name='petcod' value='$codpet' required disabled />                      
            </div>
    </form>

</div>";

        ?>
        <script>
            if (!isset($_COOKIE['reload'])) {
                // Define um cookie para indicar que a página já foi recarregada
                setcookie('reload', 'true', time() + 3600, '/');

            }

            function prepareAndSubmit(event) {
                const fieldsToEnable = document.querySelectorAll('#hist, #desc, #petcod,#fnl,#citypet,#estpet,#preco,#sexo,#cor,#raca,#tipet,#nm,#idade,#picture__input,#apv');
                const fieldsToDisable = document.querySelectorAll('#dono, #apv,#cdono,#ndono,#petcod');

                fieldsToEnable.forEach(function(field) {
                    field.removeAttribute('disabled');
                });

                // Remover o atributo "disabled" dos campos "ndono", "cdono" e "dono"
                const ndonoField = document.getElementById('ndono');
                const cdonoField = document.getElementById('cdono');
                const donoField = document.getElementById('dono');
                ndonoField.removeAttribute('disabled');
                cdonoField.removeAttribute('disabled');
                donoField.removeAttribute('disabled');

                // Continue com o envio do formulário
                return true; // Isso permite que o formulário seja enviado
            }

            function enableFields(event) {
                event.preventDefault();

                const fieldsToEnable = document.querySelectorAll('#hist, #desc, #petcod,#fnl,#citypet,#estpet,#preco,#nm,#idade,#picture__input,#apv');
                const fieldsToDisable = document.querySelectorAll('#dono, #apv,#cdono,#ndono,#petcod');

                const alterarBtn = document.getElementById('alterar-btn');
                const enviarBtn = document.getElementById('enviar-btn');

                fieldsToEnable.forEach(function(field) {
                    field.removeAttribute('disabled');
                });

                fieldsToDisable.forEach(function(field) {
                    field.setAttribute('disabled', 'true');
                });

                alterarBtn.style.display = 'none';
                enviarBtn.style.display = 'block';

                // Atualize a imagem quando um novo arquivo for selecionado
                const pictureInput = document.getElementById('picture__input');
                pictureInput.addEventListener('change', function() {
                    const file = pictureInput.files[0];
                    const reader = new FileReader();

                    reader.onloadend = function() {
                        document.getElementById('petImage').src = reader.result;
                    };

                    if (file) {
                        reader.readAsDataURL(file);
                    }
                });
            }

            //-´----
        </script>
        <br>

</body>

</html>