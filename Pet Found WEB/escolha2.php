<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./lib/padrao.css" />



    <style>
        .containerR_cad {
            float: left;
            color: white;
            position: relative;
            max-width: 700px;
            width: 100%;
            background: var(--fundoprincipal2);
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
        }

        .img {
            background: var(--fundosecundario2);
            height: 40vh;
            border-radius: 8px;
            text-align: center;
        }

        .info-frame {
            display: flex;
            align-items: center;
            margin-bottom: 20px;
        }

        .photo {
            flex-shrink: 0;
            margin-right: 20px;
        }

        .photo img {
            width: 200px;
            height: 200px;
            object-fit: cover;
            border-radius: 50%;
        }

        .info {
            flex-grow: 1;
        }

        .info h3 {
            margin-bottom: 10px;
        }

        .info p {
            margin-bottom: 5px;
        }



        .popup-content .close-button {
            position: absolute;
            margin-top: -13vh;
            margin-left: 25vh;
            font-size: 24px;
            fill: #ff0000;
        }

        .popup {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            z-index: 9999;
        }

        .card-login>h1 {
            /*palavra "login"*/
            color: rgb(255, 102, 0);
            font-weight: 800;
            margin: 0;
        }

        .btn-ong {
            width: 50vh;
            padding: 16px 0px;
            margin: 25px;
            border: none;
            border-radius: 8px;
            outline: none;
            text-transform: uppercase;
            font-weight: 800;
            letter-spacing: 3px;
            color: rgb(43, 17, 0);
            background: var(--fundoprincipal1);
            cursor: pointer;

        }

        .card-login {
            text-align: center;
            margin-right: 5vh;
            width: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            padding: 30px 35px;
            background: rgb(43, 17, 0);
            border-radius: 20px;
            box-shadow: 0px 10px 40px #00000056;

        }

        body * {
            box-sizing: border-box;
            margin: 0;
        }

        .btn-fisica {
            width: 50vh;
            padding: 16px 0px;
            margin: 25px;
            border: none;
            border-radius: 8px;
            outline: none;
            text-transform: uppercase;
            font-weight: 800;
            letter-spacing: 3px;
            color: rgb(43, 17, 0);
            background: rgb(255, 102, 0);
            cursor: pointer;

        }

        .btn-fisica:hover {
            transition: 0.5s;
            background-color: rgb(255, 50, 0);
        }

        .btn-ong:hover {
            transition: 0.5s;
            background-color: rgb(255, 50, 0);
        }

        .popup-content {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: none;
            padding: 20px;
            border-radius: 5px;
        }

        .container_cad {
            float: right;
            color: white;
            position: relative;
            max-width: 700px;
            width: 100%;
            background: var(--fundoprincipal2);
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
        }

        .container_cad input::placeholder {
            color: var(--textoprincipal);
        }

        .container_cad header {
            font-size: 2.5rem;
            color: #ffffff;
            font-weight: 500;
            text-align: center;
        }

        .container_cad .form {
            margin-top: 30px;
        }

        .form .input-box {
            width: 100%;
            margin-top: 20px;
        }

        .input-box label {
            color: #ffffff;
        }

        .form :where(.input-box input, .select-box) {
            position: relative;
            height: 50px;
            width: 100%;
            outline: none;
            font-size: 1rem;
            color: #ffffff;
            background-color: var(--fundosecundario2);
            margin-top: 8px;
            border: 1px solid var(--fundosecundario2);
            border-radius: 6px;
            padding: 0 15px;
        }

        .input-box input:focus {
            box-shadow: 0 1px 0 rgba(0, 0, 0, 0.1);
        }

        header svg {
            margin-left: 1vh;
            fill: var(--fundoprincipal1);
            /* Altere aqui para a cor desejada */
        }

        .form .column {
            display: flex;
            column-gap: 15px;
        }

        .form .gender-box {
            margin-top: 20px;
        }

        .gender-box h3 {
            color: var(--textoprincipal);
            font-size: 1rem;
            font-weight: 400;
            margin-bottom: 8px;
        }

        .form :where(.gender-option, .gender) {
            display: flex;
            align-items: center;
            column-gap: 50px;
            flex-wrap: wrap;
        }

        .form .gender {
            column-gap: 5px;
        }

        .gender input {
            accent-color: var(--fundoprincipal1);
        }

        .form :where(.gender input, .gender label) {
            cursor: pointer;
        }

        .gender label {
            color: var(--textoprincipal);
        }

        .address :where(input, .select-box) {
            margin-top: 15px;
        }

        .select-box select {
            height: 100%;
            width: 100%;
            outline: none;
            border: none;
            color: #707070;
            font-size: 1rem;
        }

        .form button {
            height: 55px;
            width: 100%;
            color: #fff;
            font-size: 1rem;
            font-weight: 400;
            margin-top: 30px;
            border: none;
            cursor: pointer;
            transition: all 0.2s ease;
            background: var(--fundosecundario1);
        }

        .form button:hover {
            background: var(--fundoprincipal1);
        }

        /*Responsive*/
        @media screen and (max-width: 500px) {
            .form .column {
                flex-wrap: wrap;
            }

            .form :where(.gender-option, .gender) {
                row-gap: 15px;
            }
        }


        /* Estilos do pop-up */
        .popup-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 9999;
        }

        .popup-content {
            padding: 20px;
            border-radius: 5px;
            max-width: 600px;
            text-align: center;
        }
    </style>
    <title>Pet Found - Cadastro</title>
</head>

<body>
    <?php
    $caminhoArquivo1 = './cadpetserv/cadpet.php';
    $caminhoArquivo2 = '../cadpetserv/cadpet.php';
    if (file_exists($caminhoArquivo1)) {
        $url1 = './cadpetserv/cadpet.php';
        $url2 = './cadpetserv/cadserv.php';
    } else {
        $url1 = '../cadpetserv/cadpet.php';
        $url2 = '../cadpetserv/cadserv.php';
    }


    echo "<div class='popup-content'>
        <div class='card-login'>
            <h1>O que quer divulgar?</h1>
            <a onclick='openPopup1()' href='" . $url1 . "'>
                <button class='btn-fisica'>Pets</button>
            </a>
            <a href='" . $url2 . "'>
                <button class='btn-ong'>Serviços</button>
            </a>
        </div>
    </div>";
    ?>
    <div id="popup" class="popup">
        <div class="popup-content">
            <script>
                document.close();
            </script>
            <!--<a class="close-button" onclick="closePopup()"> -->
        </div>
    </div>


</body>

</html>