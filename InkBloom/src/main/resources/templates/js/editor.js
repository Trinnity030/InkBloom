// Inicializa Quill
const editor = new Quill('#editor', {
    theme: 'snow'
  });
  
  // Carga el contenido inicial desde el servidor
  const contenido = document.getElementById('editor').dataset.documento;
  editor.root.innerHTML = contenido;
  
  // Función para guardar los cambios
  async function guardarCambios() {
    const contenido = editor.root.innerHTML;
    await fetch('/documento/agregar', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ texto: contenido }),
    });
    alert('Documento guardado!');
  }
  
  // Función para deshacer cambios
  function deshacer() {
    editor.history.undo();
  }
  
  // Función para rehacer cambios
  function rehacer() {
    editor.history.redo();
  }
  