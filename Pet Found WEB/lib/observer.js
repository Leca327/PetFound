const observer = new IntersectionObserver( entries =>{
    console.log(entries)
    entries[0].target.classList.add('init-hidden-off')
},{
    threshold: [1]
})

Array.from(document.querySelectorAll('.init-hidden')).forEach(Element => {
    observer.observe(Element)
})

/*sistema de estrela*/

var stars = document.querySelectorAll('.star-icon');
                  
document.addEventListener('click', function(e){
  var classStar = e.target.classList;
  if(!classStar.contains('ativo')){
    stars.forEach(function(star){
      star.classList.remove('ativo');
    });
    classStar.add('ativo');
    console.log(e.target.getAttribute('data-avaliacao'));
  }
});