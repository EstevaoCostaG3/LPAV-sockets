# LPAV-sockets
Trabalho da disciplina LPAV(Lab. de Programação Avançada). O objetivo é a criação de um chat simples utilizando sockets TCP e múltiplas threads.
## Modelo Cliente-Servidor
Cada cliente possui duas threads: _MessageSender_ e _MessageReceiver_. Elas controlam o fluxo de mensagens enviadas e recebidas pelos clientes.
O servidor gerencia uma thread para cada cliente conectado à ele via socket. Assim, o servidor recebe as mensagens dos clientes, e as propaga para os demais clientes.

## Usage

_loading..._
