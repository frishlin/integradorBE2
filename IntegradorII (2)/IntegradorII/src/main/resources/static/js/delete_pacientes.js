function deleteBy(id)
{
          const url = '/paciente/eliminar/'+ id;
          const settings = {
              method: 'DELETE'
          }
          fetch(url,settings)
          .then(response => response.text())

          let row_id = "#tr_" + id;
          document.querySelector(row_id).remove();

}