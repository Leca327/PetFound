function mascara(i){
   
    var v = i.value;
    
    if(isNaN(v[v.length-1])){ // impede entrar outro caractere que não seja número
       i.value = v.substring(0, v.length-1);
       return;
    }
    
    i.setAttribute("maxlength", "14");
    if (v.length == 3 || v.length == 7) i.value += ".";
    if (v.length == 11) i.value += "-";
 
 }

 function maskCEP(cep) {
    cep.value = cep.value.replace(/\D/g, ''); // Remove caracteres não numéricos
    cep.value = cep.value.substring(0, 8); // Limita a 8 dígitos
    cep.value = cep.value.replace(/^(\d{5})(\d{3})$/, '$1-$2'); // Formata como XXXXX-XXX
  }
  
  function maskCelular(celular) {
   celular.value = celular.value.replace(/\D/g, ''); // Remove caracteres não numéricos
   celular.value = celular.value.substring(0, 11); // Limita a 11 dígitos (DDD + número)
   celular.value = celular.value.replace(/^(\d{2})(\d{5})(\d{4})$/, '($1) $2-$3'); // Formata como (XX) XXXXX-XXXX
 }

 function maskCNPJ(cnpj) {
   cnpj.value = cnpj.value.replace(/\D/g, ''); // Remove caracteres não numéricos
   cnpj.value = cnpj.value.substring(0, 14); // Limita a 14 dígitos (número do CNPJ)
   cnpj.value = cnpj.value.replace(/^(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})$/, '$1.$2.$3/$4-$5'); // Formata como XX.XXX.XXX/YYYY-ZZ
 }
 
 
  

