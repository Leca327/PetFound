<?php
include('./lib/dbconnect.php');


if (isset($_GET["codigo"]) && !empty($_GET["codigo"])) {

    $query = "select * from pet where petcod = '" . $_GET["codigo"] . "'";
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
        $sqlSelect = "SELECT * FROM pet WHERE petcod = '" . $_GET['codigo'] . "'";
        $result = mysqli_query($mysqli, $sqlSelect);

        if ($result->num_rows > 0) {
            // Assuming 'nomepet' is a column in the 'pet' table
            $row = $result->fetch_assoc();
            $sexo = $row['sexo'];
            if ($sexo === "F") {
                $sexo = "Fêmea";
            } else if ($sexo === "M") {
                $sexo = "Macho";
            }

            $codpet = $_GET['codigo'];
            $nome = $row['nomepet'];
            $porte = $row['porte'];
            if ($porte === "Medio") {
                $porte = "Médio";
            }
            $cor = $row['cor_pel'];
            $idade = $row['fai_ida'];
            $data = $row['dtp'];
            $dataObj = date_create($data);
            $dtf = date_format($dataObj, "d/m/Y");
            $raca = $row['raca'];
            $est = $row['estadop'];
            $cid = $row['cidadep'];
            $historia = $row['historia'];
            $descpet = $row['descpet'];
            $pessoa = $row['pessoacodp'];
            $anunc = $row['finalidade'];
            $tipopet = $row['tipop'];
            $admapv = $row['admincodadmn'];
            $apv = $row['aprovacaopet'];
            $sql_imagem = "SELECT * FROM imagem WHERE petcodpet='" . $_GET['codigo'] . "';";
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
            echo "Nenhum pet encontrado com esse código.";
        }
    }
    ?> 

    <?php
    echo"<form method='post' action='editpet.php' enctype='multipart/form-data' onsubmit='prepareAndSubmit(event)'>
     <div class='main-login'>
    
        <div class='left-login'>

                <header>Editar dados do Pet $nome</header>
                <a href='javascript:history.back();'>
                <h4 class='inicio'>◄ Voltar</h4></a>
                
                    
                    <label class='picture1' for='picture__input' tabIndex='0'>
                        <span class='picture__image'>
                            <img class='imgpet' id='petImage' src='data:$imageType;base64," . base64_encode($imageData) . "' alt='Pet Image'>                    
                    <input type='file' name='picture__input' id='picture__input' disabled ></span></label>      

                <div class='textfieldL'>
                <label class='usuario' for='nome'>Nome</label>
                <input type='text' placeholder='Nome do pet' id='nm' name='nm' value='$nome' required disabled/>
              </div>
           
        <div class='textfieldL'>
        <label class='usuario' for='hist'>História do pet</label>
        <textarea class='no-resize2' type='text' placeholder='conte-nos um pouco a História do pet' id='hist' name='hist'  required disabled>$historia</textarea>
      </div>

        </div>

        <button id='alterar-btn' class='btn-singup' onclick='enableFields(event)'>Alterar</button>
        <button type='submit' class='btn-singup' id='enviar-btn' name='enviar-btn' style='display: none;'>Enviar</button>

            <div class='right-login'>

            <div class='textfield'>
                    <label class='usuario' for='tipet'>Tipo de Pet</label>
                    <select id='tipet' name='tipet' required disabled >
                        <option value='Cachorro' " . ($tipopet == 'Cachorro' ? 'selected' : '') . ">Cachorro</option>
                        <option value='Gato'" . ($tipopet == 'Gato' ? 'selected' : '') . ">Gato</option>
                        <option value='Passaro' " . ($tipopet == 'Passaro' ? 'selected' : '') . ">Pássaro</option>
                        <option value='Roedor' " . ($tipopet == 'Roedor' ? 'selected' : '') . ">Roedor</option>
                        <option value='Reptil' " . ($tipopet == 'Reptil' ? 'selected' : '') . ">Réptil</option>
                    </select>
                </div>
                

                    <div class='textfield'>
                        <label class='usuario' for='idade'>Faixa etária</label>
                            <select id='idade' name='idade' required disabled >
                            <option value='Filhote' " . ($idade == 'Filhote' ? 'selected' : '') . ">Filhote</option>
                            <option value='Jovem' " . ($idade == 'Jovem' ? 'selected' : '') . ">Jovem</option>
                            <option value='Adulto' " . ($idade == 'Adulto' ? 'selected' : '') . ">Adulto</option>
                            <option value='Senior' " . ($idade == 'Senior' ? 'selected' : '') . ">Sênior</option>
                            <option value='Idoso' " . ($idade == 'Idoso' ? 'selected' : '') . ">Idoso</option>
                        </select>
                    </div>

                    <div class='textfield'>
                        <label class='usuario' for='raca'>Raça</label>
                        <input type='text' placeholder='Raça do pet' id='raca' name='raca' value='$raca' required disabled />
                    </div>
 

                    <div class='textfield'>
                    <label class='usuario' for='Cor'>Cor</label>
                    <input type='text' placeholder='Cor da pelagem' id='cor' name='cor' value='$cor' required disabled />
                    </div>
   
                    <div class='textfield'>
                    <label class='usuario' for='sexo'>Sexo</label>
                    <select id='sexo' name='sexo' required disabled>
                        <option value='M' " . ($sexo == 'Macho' ? 'selected' : '') . ">Macho</option>
                        <option value='F' " . ($sexo == 'Fêmea' ? 'selected' : '') . ">Fêmea</option>
                    </select>
                    </div>
 
                    <div class='textfield'>
            <label class='usuario' for='porte'>Porte</label>
            <select id='porte' name='porte' required disabled>
                <option value='Grande' " . ($porte == 'Grande' ? 'selected' : '') . ">Grande</option>
                <option value='Medio' " . ($porte == 'Medio' ? 'selected' : '') . ">Médio</option>
                <option value='Pequeno' " . ($porte == 'Pequeno' ? 'selected' : '') . ">Pequeno</option>
            </select>
            </div>
        
        ";

    ?>

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

<div class='textfield'>
</div>


    <?php
    echo "
    
    <div class='textfield'>
                <label class='usuario' for='desc'>Descrição do pet</label> 
                <textarea class='no-resize' type='text' placeholder='Conte-nos detalhes sobre o pet' id='desc' name='desc' disabled required>$descpet</textarea>
           </div>

           <div class='textfield'>
            <input type='hidden' placeholder='Estado' id='petcod' name='petcod' value='$codpet' required disabled />                      
        </div>
        
       
        </div> 

    </form>

";

    ?>

    <script>
        function prepareAndSubmit(event) {
            const fieldsToEnable = document.querySelectorAll('#hist, #desc, #petcod,#fnl,#citypet,#estpet,#porte,#sexo,#cor,#raca,#tipet,#nm,#idade,#picture__input,#apv');
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

            const fieldsToEnable = document.querySelectorAll('#hist, #desc, #petcod,#fnl,#citypet,#estpet,#porte,#sexo,#cor,#raca,#tipet,#nm,#idade,#picture__input,#apv');
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