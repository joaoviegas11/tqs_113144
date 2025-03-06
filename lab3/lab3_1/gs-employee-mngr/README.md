
### a)
**AssertJ** permite escrever verificações encadeadas de forma fluida e expressiva. No contexto dos testes mencionados, exemplos de verificações são:  

```java
assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());

mvc.perform(
    get("/api/employees").contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$", hasSize(3)))
    .andExpect(jsonPath("$[0].name", is(alex.getName())))
    .andExpect(jsonPath("$[1].name", is(john.getName())))
    .andExpect(jsonPath("$[2].name", is(bob.getName())));
```

### b)
A anotação `@DataJpaTest` inclui as seguintes anotações transitivas:  

- **`@Transactional`** → Cada teste é executado dentro de uma transação e será revertido no final do teste.  

- **`@AutoConfigureTestDatabase`** → Configura automaticamente uma base de dados embutida (como H2) se estiver disponível.  
- **`@BootstrapWith(SpringBootTestContextBootstrapper.class)`** → Ajuda a configurar o contexto para testes Spring Boot.  

### c)
Um exemplo onde se simula (`mock`) o comportamento do repositório, evitando o uso de base de dados, pode ser encontrado no **B_EmployeeService_UnitTest**. Neste caso, o `EmployeeRepository` é simulado com `Mockito`:  

```java
@@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class B_EmployeeService_UnitTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setUp() {

        //these expectations provide an alternative to the use of the repository
        Employee john = new Employee("john", "john@deti.com");
        john.setId(111L);

        Employee bob = new Employee("bob", "bob@deti.com");
        Employee alex = new Employee("alex", "alex@deti.com");

        List<Employee> allEmployees = Arrays.asList(john, bob, alex);

        Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
        Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
        Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
        Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
        Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
        Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
    }

    @Test
     void whenSearchValidName_thenEmployeeShouldBeFound() {
        String name = "alex";
        Employee found = employeeService.getEmployeeByName(name);

        assertThat(found.getName()).isEqualTo(name);
    }
}
```

Neste caso, a base de dados não é envolvida porque o `EmployeeRepository` é substituído por um `mock`.


### d)
A principal diferença entre `@Mock` e `@MockBean` reside no contexto em que são utilizados nos testes em aplicações Spring Boot.  

#### **`@Mock`**  
- Utilizado em **testes unitários** (JUnit + Mockito).  
- Cria um objeto `mock` para simular o comportamento de dependências.  
- **Não** é integrado no contexto Spring, ou seja, o Spring Boot não reconhece automaticamente este `mock`.  

**Exemplo de uso de `@Mock` em testes unitários:**  
```java
@Mock
private EmployeeRepository employeeRepository;
```  

#### **`@MockBean`**  
- Utilizado em **testes de integração** (`@SpringBootTest`, `@WebMvcTest`).  
- Substitui um `bean` real do contexto Spring por um `mock`, garantindo que todos os componentes dependentes utilizem essa versão simulada.  
- Permite realizar testes mais realistas dentro do ecossistema Spring, sem necessidade de carregar serviços completos.  

**Exemplo de uso de `@MockBean` em testes de integração:**  
```java
@MockBean
private EmployeeService employeeService;
```  

Com `@MockBean`, qualquer componente Spring que dependa de `EmployeeService` receberá automaticamente a versão simulada nos testes. Isso é útil para testar controladores (`@RestController`) sem carregar toda a lógica de serviços e repositórios reais.



### e)
O ficheiro `application-integrationtest.properties` é utilizado para definir **configurações específicas para testes de integração**, garantindo que os testes sejam executados num ambiente controlado, sem afetar a configuração da aplicação em produção.  

#### **Função principal:**  
- Configura a **base de dados para testes**, podendo substituir a base de dados real por uma alternativa mais leve (exemplo: usar **H2** em vez de **PostgreSQL/MySQL**).  
- **Desativa ou ajusta configurações** que possam interferir nos testes, como logs detalhados ou mecanismos de caching.  
- Define **portas específicas** para evitar conflitos com a aplicação real.  

#### **Quando será utilizado?**  
Este ficheiro será aplicado sempre que os testes de integração forem executados e explicitamente referenciado através da anotação `@TestPropertySource`:  

```java
@TestPropertySource(locations = "application-integrationtest.properties")
class E_EmployeeRestControllerTemplateIT {
    // Testes de integração que usam estas configurações específicas
}
```

#### **Exemplo de configuração (`application-integrationtest.properties`)**  
```properties
spring.datasource.url=jdbc:mysql://localhost:33060/tqsdemo
spring.jpa.hibernate.ddl-auto=create-drop
spring.datasource.username=demo
spring.datasource.password=demo
```

Nesta configuração:  
- O Spring Boot utilizará uma base de dados **MySQL** específica para os testes (`tqsdemo`).  
- O Hibernate criará e eliminará automaticamente as tabelas no início e fim do teste (`ddl-auto=create-drop`).  
- As credenciais `username` e `password` (`demo/demo`) são definidas para a autenticação da base de dados.  

Assim, o ficheiro `application-integrationtest.properties` garante que os testes sejam executados num ambiente **isolado e previsível**, sem afetar a configuração principal da aplicação.

### f)
O projeto apresenta três abordagens distintas para testar a API desenvolvida em Spring Boot, cada uma com um nível diferente de isolamento e realismo.

A primeira abordagem (C) foca-se exclusivamente no controlador da API, utilizando `@WebMvcTest`. Este método permite testar a camada de apresentação sem carregar o contexto completo da aplicação. Para garantir a independência dos testes, a lógica de serviço é simulada através de `@MockBean`, e `MockMvc` é usado para realizar chamadas HTTP. Esta estratégia é extremamente rápida, uma vez que evita a interação com a base de dados e mantém os testes focados no comportamento do controlador.

A segunda abordagem (D) corresponde a um teste de integração que abrange a aplicação desde o controlador até ao repositório. Aqui, `@SpringBootTest` é utilizado para carregar o contexto completo da aplicação, incluindo a base de dados, enquanto `MockMvc` é novamente usado para realizar chamadas HTTP dentro deste ambiente. Diferente do primeiro método, este tipo de teste assegura que todas as camadas da aplicação interagem corretamente, permitindo a execução da lógica de negócio real e verificando a persistência dos dados.

A terceira abordagem (E) realiza um teste de integração completo, simulando o comportamento de um cliente externo que consome a API. Para isso, a aplicação é iniciada numa porta real, e `TestRestTemplate` é utilizado para efetuar chamadas HTTP reais, garantindo que os pedidos e respostas são processados de forma semelhante a um ambiente de produção. Este método testa a API de ponta a ponta, incluindo a comunicação com a base de dados e a serialização das respostas.

Em resumo, a abordagem C é a mais rápida e isolada, focando-se apenas no controlador; a abordagem D testa a integração entre as camadas internas da aplicação, enquanto a abordagem E simula um cenário mais realista, testando a API como um cliente externo faria.