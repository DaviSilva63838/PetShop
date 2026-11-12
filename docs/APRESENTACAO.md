# Roteiro de apresentação — aproximadamente 20 minutos

## 0–3 min: ideia e marca

Apresentem os integrantes e o aplicativo Pata & Pelo. Expliquem a ideia:
organizar banho e tosa, evitando esquecer horários ou marcar dois atendimentos
no mesmo horário. Descrevam o público: tutores de cães e gatos.

## 3–8 min: demonstração

Mostrem o catálogo, cadastrem um pet e marquem um serviço em data futura.
Fechem e abram o app para mostrar que os dados permanecem. Tentem repetir
um horário e mostrem a mensagem. Cancelem um atendimento e mostrem a lista.

Antes da apresentação, executem [o roteiro de testes](TESTES.md) no aparelho.

## 8–14 min: Kotlin e XML

Abram `activity_pet.xml` e `PetActivity.kt` lado a lado no Android Studio.
Mostrem a relação entre um `android:id` do XML e `findViewById` no Kotlin.
Expliquem `setOnClickListener`, a leitura de um `EditText`, o `if` que valida
o campo e o `Toast` de confirmação.

Depois mostrem `MainActivity.kt`: o `Intent` abre outra Activity.
Em `ServicosActivity.kt`, mostrem o laço `for`, o layout `item_servico.xml`
e o extra `servico_id` enviado para `AgendaActivity`.

## 14–17 min: dados e regras

Mostrem `PetStorage.kt`: `SharedPreferences` guarda pares de chave e valor no
aparelho; `commit()` informa se conseguiu gravar. Em `AgendaStorage.kt`,
expliquem que uma lista de agendamentos é transformada em texto JSON para
salvar e reconstruída ao carregar.

Mostrem `RegrasAgendamento.kt`: os horários estão em uma lista; a data/hora
escolhida precisa ser posterior ao momento atual; dois registros não podem
ocupar a mesma data e hora. O `UUID` identifica qual registro será cancelado.

## 17–20 min: repositório e conclusão

Abram o README e o histórico de commits. Cada integrante explica suas
contribuições e um trecho de código que entende. Mostrem como executar e
testar o projeto e mostrem os serviços disponíveis.

## Conceitos para revisar antes

| Conceito | Explicação curta |
| --- | --- |
| Activity | Uma tela do aplicativo, com um ciclo de vida. |
| XML | Define os componentes e a organização visual da tela. |
| Intent | Abre outra tela e pode levar um dado, como o ID de um serviço. |
| data class | Agrupa dados, como nome, espécie e tutor de um pet. |
| object | Mantém uma única instância, usada aqui para catálogo e regras. |
| SharedPreferences | Armazena pequenos dados locais em chaves e valores. |
| JSON | Formato de texto usado para guardar a lista de agendamentos. |
| onResume | Atualiza os dados quando a tela volta a aparecer. |
| Bundle | Guarda a data escolhida ao recriar a tela, por exemplo numa rotação. |
| LocalDate/LocalDateTime | Representam datas e horários para comparar e formatar. |

Confiram quais destes conceitos foram vistos em aula. O professor pede que
os recursos não vistos também sejam explicados; estudem o código e adaptem
o projeto ao documento de requisitos quando ele estiver disponível.
