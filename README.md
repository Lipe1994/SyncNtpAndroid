# SyncNtpAndroid
App construído com Java para acertar data e hora de dispositivos android durante o boot ou quando o mesmo for aberto

Este App fiz em um final de semana para atualizar a data e hora de um TV-box(Aquario ST-2000) pois o mesmo falhava com frequência o atualizar hora e data pela rede...

Tenho a intenção de aprimora-lo, mais por quanto ele trabalha com servidores "a.st1.ntp.br", "ntp.br", "3.br.pool.ntp.org","c.ntp.br", "2.br.pool.ntp.org","d.st1.ntp.br", "1.br.pool.ntp.org","0.br.pool.ntp.org".

Também vou otimizar este código em breve, mais pra qualquer pessoa que esteja procurando algo semelhante para implementar vai ajudar bastante.

*O dispositivo precisa estar roteado porque o comando que eu uso no shell do android "date",precisa de "su".

*O app atualiza hora quando é aberto ou quando dispositivo é ligado.
