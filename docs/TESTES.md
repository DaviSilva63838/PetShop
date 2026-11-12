# Teste no celular ou emulador

Faça esta sequência antes da apresentação. Use nomes fictícios e uma data futura.

1. Abra o aplicativo. Confira o menu, textos e os três serviços e preços.
2. Entre em Meus agendamentos: deve aparecer a mensagem de lista vazia.
3. Escolha um serviço sem cadastrar o pet. Ao confirmar, o app deve pedir o cadastro.
4. Em Meu pet, tente salvar o nome vazio e depois o tutor vazio: o campo deve mostrar erro.
5. Cadastre Pipoca, Cachorro, tutor Ana. Salve, feche o app e abra novamente:
   o cadastro deve continuar preenchido.
6. Escolha Banho. Sem escolher a data, tente confirmar: deve aparecer uma orientação.
7. Escolha amanhã às 09h e confirme. A lista deve mostrar banho, Pipoca,
   a data escolhida, 09h e R$ 40,00.
8. Tente marcar Tosa na mesma data e hora: o aplicativo deve recusar o conflito.
9. Marque outro horário no mesmo dia: deve salvar e ordenar a lista.
10. Se ainda houver um horário de hoje que já passou, tente usá-lo: deve ser recusado.
    O calendário não deve permitir dias anteriores ao atual.
11. Feche o app e abra novamente: os agendamentos devem continuar na lista.
12. Altere o nome do pet. O cadastro deve mudar; os agendamentos anteriores
    devem conservar o nome que estava salvo quando foram criados.
13. Toque em Cancelar agendamento e escolha Manter: o registro deve continuar.
14. Cancele e confirme: o registro deve sumir. O mesmo horário poderá ser marcado novamente.
15. Gire a tela durante o cadastro e a escolha de data: os campos não devem se perder.
16. Confira também em tela pequena, na horizontal e com fonte maior:
    use a rolagem para alcançar os botões e confira se os textos estão legíveis.

## Testes automatizados

`RegrasAgendamentoTest.kt` possui 9 testes para data futura, horário futuro hoje,
dia passado, horário passado hoje, instante atual, data inválida/ausente,
horário fora das opções e conflitos na agenda.

Execute `./gradlew testDebugUnitTest` (ou `.\gradlew.bat testDebugUnitTest`).
O relatório HTML fica em `app/build/reports/tests/testDebugUnitTest/index.html`.
Esses testes verificam as regras; a sequência acima verifica a interface e a
persistência no aparelho.
