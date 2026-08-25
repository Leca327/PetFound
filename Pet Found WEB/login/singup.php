<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="logcad.css">
    <link rel="stylesheet" href="../lib/padrao.css">
    <title>Pet Found - Cadastro</title>

    <script>
        function voltarPagina() {
            window.history.back();
        }
    </script>
</head>

<body>

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <?php
    session_start();
    if (!empty($_SESSION["usuario"])) {
        echo "<script>
                        Swal.fire(
                        'Logado',  
                        'Deslogue para cadastrar uma conta',
                        'error'
                        ).then(() => {
                        window.location.href='../index.php';
                        });
                        </script>";
    } else if (!empty($_SESSION["admin"])) {
        echo "<script>
                        Swal.fire(
                        'Logado como Admin',  
                        'Deslogue para cadastrar uma conta',
                        'error'
                        ).then(() => {
                        window.location.href='../index.php';
                        });
                        </script>";
    } else {
    }
    ?>

    <div class="main-login">
        <div class="left-login">
            <a href="javascript:void(0);" onclick="voltarPagina();">
                <h4 class="inicio">◄ Voltar</h4>
            </a>
            <h1 class="frase">Faça o cadastro</h1>
            <h1> E entre para o nosso time</h1>
            <img src="../assets/logo.png" class="left-login-img" alt="doge">
            <br><br>
            <h1 class="pet">PetFound</h1>
        </div>
        <div class="right-login">
            <form id="cad" action="inserir.php" method="POST">
                <h1 class="titulo">Cadastro pessoa Física</h1>
                <br>
                <div class="card-info">
                    <div class="info-login">
                        <div class="textfield">
                            <label class="usuario" for="usuario"></label>
                            <input type="text" id="nick" name="usuario" placeholder="Usuário" required>
                        </div>
                        <div class="textfield">
                            <label class="usuario" for="usuario"></label>
                            <input type="email" id="email" name="email" placeholder="E-mail" required>
                        </div>
                        <div class="textfield">
                            <label class="senha" for="senha"></label>
                            <input type="password" id="senha" name="senha" placeholder="senha" required>
                        </div>

                        <div class="textfield">
                            <label class="csenha" for="csenha"></label>
                            <img src="../assets/ocultar.png" id="toggle-button" class="mose3">
                            <input type="password" id="csenha" name="csenha" placeholder="confirme a senha" required>
                        </div>



                        <script type="text/javascript">
                            var imgAtual = "../assets/mostrar.png";
                            var imgAnterior = "../assets/ocultar.png";


                            const senhaInput = document.getElementById('senha');
                            const csenhaInput = document.getElementById('csenha');
                            const toggleButton = document.getElementById('toggle-button');

                            toggleButton.addEventListener('click', () => {
                                if (senhaInput.type === 'password' && csenhaInput.type === 'password') {
                                    senhaInput.type = 'text';
                                    csenhaInput.type = 'text';
                                } else {
                                    senhaInput.type = 'password';
                                    csenhaInput.type = 'password';
                                }
                            });
                        </script>
                    </div>
                </div>
                <br>

                <div class="card-info">
                    <div class="info">

                        <div class="textfield">
                            <label class="usuario" for="nome"></label>
                            <input type="text" name="nome" placeholder="Nome" required>
                        </div>
                        <div class="textfield">
                            <label class="usuario" for="nome"></label>
                            <input type="text" name="Snome" placeholder="Sobrenome" required>
                        </div>


                        <div class="textfield">
                            <label class="usuario" for="dt"></label>
                            <input type="date" name="dt" id="dt" placeholder="Data de Nascimento" required>
                        </div>

                        <script src="mask.js"></script>

                        <div class="textfield">
                            <label class="usuario" for="celular"></label>
                            <input type="text" name="celular" id="celular" onkeyup="maskCelular(this)" placeholder="Celular ou telefone" required>

                        </div>
                        <div class="textfield">
                            <select name="sexo">

                                <option class="usuario" value="M"> Masculino</option>
                                <option class="usuario" value="F"> Feminino</option>
                                <option class="usuario" value="O"> Outro</option>

                            </select>
                        </div>
                    </div>
                </div>
                <br>

                <div class="card-ende">
                    <a href="https://buscacepinter.correios.com.br/app/endereco/index.php" target="_blank">
                        <h6 class="frasecep">Não sei meu CEP</h6>
                    </a>
                    <div class="firtline">
                        <div class="textfield">
                            <label class="usuario" for="cep"></label>
                            <input type="text" name="cep" id="cep" onblur="pesquisacep(this.value);" placeholder="CEP" required>
                        </div>
                        <div class="textfield">
                            <label class="usuario" for="cidade"></label>
                            <input type="text" name="city" id="cidade" placeholder="Cidade" required>
                        </div>
                        <div class="textfield">
                            <select name="uf" id="uf" readonly style="pointer-events: none">
                                <option value="">Selecione</option>
                                <option value="AC">AC</option>
                                <option value="AL">AL</option>
                                <option value="AP">AP</option>
                                <option value="AM">AM</option>
                                <option value="BA">BA</option>
                                <option value="CE">CE</option>
                                <option value="DF">DF</option>
                                <option value="ES">ES</option>
                                <option value="GO">GO</option>
                                <option value="MA">MA</option>
                                <option value="MS">MS</option>
                                <option value="MT">MT</option>
                                <option value="MG">MG</option>
                                <option value="PA">PA</option>
                                <option value="PB">PB</option>
                                <option value="PR">PR</option>
                                <option value="PE">PE</option>
                                <option value="PI">PI</option>
                                <option value="RJ">RJ</option>
                                <option value="RN">RN</option>
                                <option value="RS">RS</option>
                                <option value="RO">RO</option>
                                <option value="RR">RR</option>
                                <option value="SC">SC</option>
                                <option value="SP">SP</option>
                                <option value="SE">SE</option>
                                <option value="TO">TO</option>
                            </select>
                        </div>
                    </div>
                    <div class="midline">
                        <div class="textfield">
                            <label class="usuario" for="endereco"></label>
                            <input type="text" name="endereco" id="endereco" placeholder="Endereço" required>
                        </div>
                    </div>

                    <div class="secondline">
                        <div class="textfield">
                            <label class="usuario" for="bairro"></label>
                            <input type="text" name="brr" id="bairro" placeholder="Bairro" required>
                        </div>
                        <div class="textfield">
                            <label class="usuario" for="numero"></label>
                            <input type="text" name="nm" id="numero" placeholder="Número" oninput="validateNumberInput(this)">
                        </div>
                        <div class="textfield">
                            <label class="usuario" for="complemento"></label>
                            <input type="text" name="cpmt" id="complemento" placeholder="Complemento" required>
                        </div>

                    </div>
                </div>

                <script>
                    function pesquisacep(cep) {
                        cep = cep.replace(/\D/g, '');

                        if (cep.length === 8) {
                            fetch(`https://viacep.com.br/ws/${cep}/json/`)
                                .then(response => response.json())
                                .then(data => {
                                    if (!data.erro) {
                                        document.getElementById('uf').value = data.uf;
                                        document.getElementById('cidade').value = data.localidade;
                                        document.getElementById('bairro').value = data.bairro;
                                        document.getElementById('endereco').value = data.logradouro;
                                        if (data.complemento) {
                                            document.getElementById('numero').value = data.complemento;
                                        } else {
                                            document.getElementById('numero').readOnly = false;
                                        }
                                    }
                                })
                                .catch(error => {
                                    console.log(error);
                                    alert('Erro ao buscar o CEP. Verifique se o CEP é válido e tente novamente.');
                                });

                            document.getElementById('uf').readOnly = true;
                            document.getElementById('cidade').readOnly = true;
                            document.getElementById('bairro').readOnly = true;
                            document.getElementById('endereco').readOnly = true;
                        }
                    }

                    function validateNumberInput(input) {
                        input.value = input.value.replace(/\D/g, '');
                    }
                </script>


                <br>


                <div class="info-btn">


                    <button type="reset" class="btn-singup">Limpar</button>
                    <button type="submit" class="btn-singup">Cadastrar-se</button>

                    <a href="singupong.php">
                        <h6 class="frasedbtn">Cadastro para Pessoa Jurídica</h6>
                    </a>
                    <br>
                    <br>
                    <a href="login.php">
                        <h6 class="frasedbtn1">Já possui uma conta?</h6>
                    </a>



                    <!--<button type="submit" class="btn-singup">Próximo➤</button>-->
            </form>


        </div>




    </div>
    </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</body>


<script>
    function validarData() {
        var inputDate = document.getElementById("dt").value;

        var dataAtual = new Date();
        var ano = dataAtual.getFullYear(); // Obtém o ano atual
        var mes = (dataAtual.getMonth() + 1).toString().padStart(2, '0'); // Obtém o mês atual (lembre-se que os meses são indexados a partir de zero)
        var dia = dataAtual.getDate().toString().padStart(2, '0'); // Obtém o dia atual

        var valorDataAtual = ano + "-" + mes + "-" + dia;

        var dataAtualObj = new Date(valorDataAtual);
        var inputDataObj = new Date(inputDate);

        var intervaloMilissegundos = dataAtualObj - inputDataObj;

        // Convertendo o intervalo de milissegundos para anos
        var intervaloAnos = intervaloMilissegundos / (1000 * 60 * 60 * 24 * 365.25);

        // Arredondando o resultado para um número inteiro
        intervaloAnos = Math.floor(intervaloAnos);

        var idade = document.getElementById("dt");

        if (intervaloAnos < 18) {
            idade.setCustomValidity("Você precisa ter mais de 18 anos.");
        } else {
            idade.setCustomValidity("");
        }
    }

    var idade = document.getElementById("dt");
    idade.onkeyup = validarData;

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

    var campo = document.getElementById('city');
    campo.readOnly = true;
    campo.disabled = true;
</script>

</html>



<!--while ($row = $result->fetch_assoc()) {
                if ($row['emailp'] == $email) {
                    echo "<script> var email = document.getElementById('email'); email.setCustomValidity('Email já cadastrado');</script>";
                }
                if ($row['contatop'] == $celular) {
                    echo "<script> var celular = document.getElementById('celular'); celular.setCustomValidity('Celular já está cadastrado');</script>";
                }
                if ($row['nickname'] == $usuario) {
                    echo "<script> var usuario = document.getElementById('nick'); usuario.setCustomValidity('Usuário já existe');</script>";
                }
            }