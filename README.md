# LPAV-sockets
Trabalho da disciplina LPAV (Lab. de Programação Avançada). O objetivo é a criação de um chat simples utilizando sockets TCP e múltiplas threads.
## Modelo Cliente-Servidor
Cada cliente possui duas threads: _MessageSender_ e _MessageReceiver_. Elas controlam o fluxo de mensagens enviadas e recebidas pelos clientes.
O servidor gerencia uma thread para cada cliente conectado à ele via socket. Assim, o servidor recebe as mensagens dos clientes, e as propaga para os demais clientes.

## Usage

1. Após clonar o repositório, compile as classes cliente e servidor.
```
javac src/ChatClient/ChatClient.java
javac src/ChatServer/ChatServer.java
```

2. Inicialize o servidor em um terminal.
```
cd src/ChatServer
java src/ChatServer/ChatServer
```

3. Inicialize um ou mais clientes como a seguir. Será necessário um terminal para cada cliente.
```
cd src/ChatClient
NOME=Joao
java ChatClient $NOME localhost 5000
```

4. Pronto. Agora basta digitar no terminal de algum dos clientes e teclar "Enter" para que a mensagem seja propagada para todos os demais clientes do chat.

