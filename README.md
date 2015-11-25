#Chat com RMI-IIOP

##Tutorial

####Compilação

 1. Limpe e Construa o código usando o comando maven `mvn clean install`.
 2. Construa o Stub e Tier, vá na pasta `target/classes` do projeto e execute o comando `rmic -iiop br.edu.ifpb.pod.service.RMIServiceImpl`
 3. E depois copie o Stub da interface remota para a pasta `target/classes/br/edu/ifpb/pod/interfaces` do módulo cliente

####Execução

1. Execute o comando `orb -ORBInitialPort 1050&`.
2. Execute a o método main do módulo server
3. Execute a o método main do módulo cliente
