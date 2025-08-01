// main.js - PackExpress

document.addEventListener("DOMContentLoaded", () => {
  const buttons = document.querySelectorAll("button, .btn");
  buttons.forEach(btn => {
    btn.addEventListener("mouseenter", () => {
      btn.style.transform = "scale(1.05)";
    });
    btn.addEventListener("mouseleave", () => {
      btn.style.transform = "scale(1.0)";
    });
  });

  // Smooth scroll para anclas (si agregas más adelante)
  document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
      e.preventDefault();
      const target = document.querySelector(this.getAttribute('href'));
      if (target) {
        target.scrollIntoView({ behavior: 'smooth' });
      }
    });
  });
});
// main.js - Animaciones y funcionalidades PackExpress

document.addEventListener("DOMContentLoaded", () => {
  // Animación de entrada para secciones
  const secciones = document.querySelectorAll("main section");
  secciones.forEach((sec, i) => {
    sec.style.opacity = 0;
    sec.style.transform = "translateY(20px)";
    setTimeout(() => {
      sec.style.transition = "all 0.6s ease-out";
      sec.style.opacity = 1;
      sec.style.transform = "translateY(0)";
    }, 100 * i);
  });

  // Smooth scroll para enlaces internos
  document.querySelectorAll("a[href^='#']").forEach(el => {
    el.addEventListener("click", function (e) {
      e.preventDefault();
      const destino = document.querySelector(this.getAttribute("href"));
      if (destino) {
        destino.scrollIntoView({ behavior: "smooth" });
      }
    });
  });

  // Simulación de botón de carrito
  document.querySelectorAll(".btn").forEach(btn => {
    if (btn.innerText.includes("carrito") || btn.innerText.includes("Ordenar")) {
      btn.addEventListener("click", () => {
        alert("Producto agregado al carrito 🛒");
      });
    }
  });

  // Agitar botón de WhatsApp después de unos segundos
  const whatsappBtn = document.querySelector(".whatsapp-float");
  if (whatsappBtn) {
    setTimeout(() => {
      whatsappBtn.style.animation = "shake 0.5s ease-in-out";
    }, 5000);
  }
});

// Extra animación personalizada
const style = document.createElement("style");
style.innerHTML = `
  @keyframes shake {
    0% { transform: translate(0, 0); }
    25% { transform: translate(3px, 0); }
    50% { transform: translate(-3px, 0); }
    75% { transform: translate(3px, 0); }
    100% { transform: translate(0, 0); }
  }
`;
document.head.appendChild(style);
(function ($) {

  "use strict";


  $(document).ready(function () {



    //testimonial swiper

    var swiper = new Swiper(".testimonial-swiper", {
      slidesPerView: 3,
      spaceBetween: 20,
      pagination: {
        el: ".swiper-pagination",
        dynamicBullets: true,
      },
      breakpoints: {
        0: {
          slidesPerView: 1,
          spaceBetween: 20,
        },
        768: {
          slidesPerView: 2,
          spaceBetween: 30,
        },
        1400: {
          slidesPerView: 3,
          spaceBetween: 30,
        },
      }
    });

    window.addEventListener("load", (event) => {
      //isotope
      $('.isotope-container').isotope({
        // options
        itemSelector: '.item',
        layoutMode: 'masonry',
      });



      // Initialize Isotope
      var $container = $('.isotope-container').isotope({
        // options
        itemSelector: '.item',
        layoutMode: 'masonry',
      });

      $(document).ready(function () {
        //active button
        $('.filter-button').click(function () {
          $('.filter-button').removeClass('active');
          $(this).addClass('active');
        });
      });

      // Filter items on button click
      $('.filter-button').click(function () {
        var filterValue = $(this).attr('data-filter');
        if (filterValue === '*') {
          // Show all items
          $container.isotope({ filter: '*' });
        } else {
          // Show filtered items
          $container.isotope({ filter: filterValue });
        }
      });

    });



  });


})(jQuery);
document.getElementById("btnBuscar").addEventListener("click", () => {
  const valor = document.getElementById("campoBuscar").value;
  if (valor.trim() !== "") {
    window.location.href = `/buscar?q=${encodeURIComponent(valor)}`;
  }
});
