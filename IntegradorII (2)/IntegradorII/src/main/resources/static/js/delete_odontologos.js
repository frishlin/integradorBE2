function deleteBy(id) {
    const url = '/odontologo/eliminar/' + id;
    const settings = {
        method: 'DELETE'
    };

    fetch(url, settings)
        .then(response => {
            if (!response.ok) {
                // Si la respuesta no es OK (como un 400), mostramos el mensaje de error
                return response.text().then(text => { throw new Error(text); });
            }
            return response.text(); // Continuamos si la respuesta fue exitosa
        })
        .then(data => {
            // Si se eliminó correctamente, actualizamos la lista o notificamos éxito
            Swal.fire('Eliminado', 'El odontólogo ha sido eliminado', 'success');
            // Lógica para eliminar el odontólogo de la lista visual (si tienes esto implementado)
            let row_id = "#tr_" + id;
            document.querySelector(row_id).remove();
        })
        .catch(error => {
            // Si hay un error (como BadRequestException), mostramos el mensaje de error
            Swal.fire('Error', error.message, 'error');
        });
}