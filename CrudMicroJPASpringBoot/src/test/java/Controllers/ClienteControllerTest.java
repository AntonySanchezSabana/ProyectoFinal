package Controllers;

import com.api.crud.controllers.ClienteController;
import com.api.crud.models.Mysql.ClienteModel;
import com.api.crud.services.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetClientes() {
        // Configurar el comportamiento del servicio simulado
        ArrayList<ClienteModel> clientes = new ArrayList<>();
        ClienteModel cliente = new ClienteModel();
        cliente.setClienteId(1L);
        cliente.setNombre("Juan");
        clientes.add(cliente);

        when(clienteService.getClientes()).thenReturn(clientes);

        // Llamar al método del controlador
        ArrayList<ClienteModel> resultado = clienteController.getCLientes();

        // Verificar que se obtuvieron los resultados esperados
        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());

        // Verificar que el servicio se haya llamado una vez
        verify(clienteService, times(1)).getClientes();
    }

    @Test
    void testSaveCliente() {
        // Crear cliente de prueba
        ClienteModel cliente = new ClienteModel();
        cliente.setClienteId(1L);
        cliente.setNombre("Pedro");

        when(clienteService.saveCliente(any(ClienteModel.class))).thenReturn(cliente);

        // Llamar al método del controlador
        ResponseEntity<ClienteModel> response = clienteController.saveCliente(cliente);

        // Verificar el código de estado y el cuerpo de la respuesta
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Pedro", response.getBody().getNombre());

        // Verificar que el servicio se haya llamado una vez
        verify(clienteService, times(1)).saveCliente(any(ClienteModel.class));
    }

    @Test
    void testGetClienteById() {
        // Configurar el comportamiento del servicio simulado
        ClienteModel cliente = new ClienteModel();
        cliente.setClienteId(1L);
        cliente.setNombre("Ana");

        when(clienteService.getById(1L)).thenReturn(Optional.of(cliente));

        // Llamar al método del controlador
        Optional<ClienteModel> resultado = clienteController.getClienteById(1L);

        // Verificar que se obtuvo el cliente correcto
        assertEquals(true, resultado.isPresent());
        assertEquals("Ana", resultado.get().getNombre());

        // Verificar que el servicio se haya llamado una vez
        verify(clienteService, times(1)).getById(1L);
    }

    @Test
    void testUpdateClienteById() {
        // Crear cliente de prueba
        ClienteModel cliente = new ClienteModel();
        cliente.getClienteId(1L);
        cliente.setNombre("Carlos");

        when(clienteService.updateById(any(ClienteModel.class), eq(1L))).thenReturn(cliente);

        // Llamar al método del controlador
        ClienteModel resultado = clienteController.updateClienteById(cliente, 1L);

        // Verificar que se actualizó el cliente correctamente
        assertEquals("Carlos", resultado.getNombre());

        // Verificar que el servicio se haya llamado una vez
        verify(clienteService, times(1)).updateById(any(ClienteModel.class), eq(1L));
    }

    @Test
    void testDeleteClienteById() {
        // Configurar el comportamiento del servicio simulado
        when(clienteService.deleteCliente(1L)).thenReturn(true);

        // Llamar al método del controlador
        String resultado = clienteController.deleteClienteById(1L);

        // Verificar la respuesta correcta
        assertEquals("cliente con id 1 eliminado", resultado);

        // Verificar que el servicio se haya llamado una vez
        verify(clienteService, times(1)).deleteCliente(1L);
    }

}