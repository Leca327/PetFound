<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="./login/loading.css">
</head>

<body bgcolor="#ff6600">

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    <div class="loading-card">
        <div class="loading-message">Redirecionando</div>
        <div class="loading-spinner-container">
            <div class="loading-spinner"></div>
        </div>
    </div>
</body>

<?php
session_start();

// Destruir todas as variáveis de sessão
$_SESSION = array();

// Finalizar a sessão
session_destroy();

// Redirecionar de volta para a página inicial
header("Location: ./index.php");
exit();
?>