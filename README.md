# Pata & Pelo

Aplicativo de PetShop feito em Kotlin e XML para a disciplina de desenvolvimento mobile. Nele, o tutor pode cadastrar um pet e marcar horários para banho e tosa.

## O que o aplicativo faz

- Cadastra um cachorro ou gato e o nome do tutor.
- Mostra os serviços e preços de banho, tosa e banho e tosa.
- Permite escolher uma data e um horário para atendimento.
- Mostra os agendamentos e permite cancelar um horário.

O cadastro guarda um pet por vez.

## Como abrir

1. No Android Studio, clique em **Open** e selecione a pasta do projeto.
2. Aguarde a sincronização do Gradle.
3. Escolha um emulador ou conecte um celular Android.
4. Selecione `app` e clique em **Run**.

O projeto usa Android SDK 34, Gradle 8.7 e Kotlin 1.9.24. A primeira sincronização pode precisar de internet para baixar as ferramentas.

## Como os dados são salvos

O cadastro do pet e os agendamentos usam `SharedPreferences`. As telas foram feitas em XML, e a navegação entre elas usa `Activity` e `Intent`.

## Integrantes

Preencher os nomes da dupla.

## Imagens

As fotos usadas no aplicativo são do Pexels:

- [Cachorro e gato](https://www.pexels.com/photo/dog-and-cat-sitting-together-16395150/), por Helena Jankovičová Kováčová.
- [Banho](https://www.pexels.com/photo/close-up-photo-of-bathing-of-dog-6131165/), por Tima Miroshnichenko.
- [Tosa](https://www.pexels.com/photo/close-up-photo-of-a-dog-being-groomed-6816850/), por Gustavo Fring.

[Licença das imagens](https://www.pexels.com/license/).
