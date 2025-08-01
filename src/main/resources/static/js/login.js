document.addEventListener("DOMContentLoaded", () => {
  const checkbox = document.getElementById("reg-log");

  // Animación 3D: Girar la tarjeta
  checkbox?.addEventListener("change", () => {
    const wrapper = document.querySelector(".card-3d-wrapper");
    if (wrapper) {
      wrapper.style.transition = "transform 0.6s ease";
      wrapper.style.transform = checkbox.checked ? "rotateY(180deg)" : "rotateY(0deg)";
    }
  });

  // Botón Iniciar Sesión
  const loginBtn = document.querySelector(".btn-login");
  if (loginBtn) {
    loginBtn.addEventListener("click", (e) => {
      e.preventDefault();
      const correo = document.querySelector("input[name='correo']")?.value.trim();
      const clave = document.querySelector("input[name='clave']")?.value.trim();

      if (!correo || !clave) {
        alert("Por favor, completa todos los campos de inicio de sesión.");
        return;
      }

      // Crear y enviar el formulario POST
      const form = document.createElement("form");
      form.method = "POST";
      form.action = "/login";

      const inputCorreo = document.createElement("input");
      inputCorreo.name = "correo";
      inputCorreo.value = correo;
      form.appendChild(inputCorreo);

      const inputClave = document.createElement("input");
      inputClave.name = "clave";
      inputClave.value = clave;
      form.appendChild(inputClave);

      document.body.appendChild(form);
      form.submit();
    });
  }

  // Botón Registrarse
  const registerBtn = document.querySelector(".btn-register");
  if (registerBtn) {
    registerBtn.addEventListener("click", (e) => {
      e.preventDefault();
      const nombre = document.querySelector("input[name='nombre']")?.value.trim();
      const correo = document.querySelector("input#correo2")?.value.trim();
      const clave = document.querySelector("input#contraseña2")?.value.trim();

      if (!nombre || !correo || !clave) {
        alert("Por favor, completa todos los campos de registro.");
        return;
      }

      // Crear y enviar el formulario POST
      const form = document.createElement("form");
      form.method = "POST";
      form.action = "/registro";

      const inputNombre = document.createElement("input");
      inputNombre.name = "nombre";
      inputNombre.value = nombre;
      form.appendChild(inputNombre);

      const inputCorreo = document.createElement("input");
      inputCorreo.name = "correo";
      inputCorreo.value = correo;
      form.appendChild(inputCorreo);

      const inputClave = document.createElement("input");
      inputClave.name = "clave";
      inputClave.value = clave;
      form.appendChild(inputClave);

      document.body.appendChild(form);
      form.submit();
    });
  }
});
