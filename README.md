#  Chat Multicast em Java
**João Marcos Lourenço dos Santos**

## Requisitos
* LISTAR SALAS
* LISTAR MEMBROS DA SALA
* CRIAR SALA
* ENVIAR MENSAGEM
* ENTRAR NA SALA(ID_SALA)
* SAIR DA SALA

Seguirão abaixo orientações do uso do projeto  

## Utilização

### Execução do server 
* PORTA = 4845
 
```sh
cd  /PROJETO/out/production/PROJETO
java infra.Server 
```

### Execução do Client
* PORTA = 4845
* PORTA MULTICAST = 6789

```sh
cd  /PROJETO/out/production/PROJETO
java infra.Client "CREATE_ROOM"
```

#### Ações do cliente

*  Acionar menu
```sh
java infra.Client "MENU"
```

*  Listar salas
```sh
java infra.Client "LIST_ALL_ROOMS"
```

*  Listar usuários das salas, ou de uma sala específica (passando o InetAddress)
```sh
java infra.Client "LIST_ALL_ROOMS"
java infra.Client "LIST_ALL_USERS // 219.205.65.84"
```

*  Criar uma nova sala com o sem o nome do usuário
```sh
java infra.Client "CREATE_ROOM" 
java infra.Client "CREATE_ROOM // João Marcos" 
```

*  Enviar mensagem 
```sh 
O programa fica em loop requisitando a inserção de mensagens no console, sendo assim, toda mensagem digitada após a inicialização será automaticamente enviada.
```

*  Entrar em uma sala passando sempre o InetAddress da sala e o nome do usuário e automaticamente você será incluido no chat
```sh 
java infra.Client "JOIN_ROOM // 219.205.65.84 // João" 
```

*  Sair de uma sala 
```sh 
O programa fica em loop requisitando a inserção de mensagens no console, sendo assim, para sair do programa é necessário digitar a palavra "Exit", para que o programa finalize a connexão e o socket seja encerrado.
```
 
 

#### LABORATÓRIO DE DESENVOLVIMENTO DE APLICAÇÕES MÓVEIS E DISTRIBUÍDAS

**20 de Fevereiro de 2021**
 
