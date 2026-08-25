const inputFile = document.querySelector("#picture__input");
const petImage = document.querySelector("#petImage");
const pictureImage = document.querySelector(".picture__image");
const pictureImageTxt = "Escolha uma Imagem";

inputFile.addEventListener("change", function (e) {
  const inputTarget = e.target;
  const file = inputTarget.files[0];

  if (file) {
    const reader = new FileReader();

    reader.addEventListener("load", function (e) {
      const readerTarget = e.target;

      // Define o atributo "src" da imagem do pet com a imagem escolhida
      petImage.src = readerTarget.result;

      // Exibe a imagem do pet
      petImage.style.display = "block";
    });

    reader.readAsDataURL(file);
  } else {
    // Se nenhum arquivo for selecionado, exibe a imagem do pet armazenada no banco de dados
    petImage.style.display = "block";
  }
});




