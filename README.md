# Nexus

Nexus e um aplicativo Android para gerar, salvar e consultar senhas. O app usa autenticacao Firebase, interface em Jetpack Compose, persistencia local com Room e protecao de dados sensiveis com Android Keystore e `EncryptedSharedPreferences`.

## Stack

- Kotlin
- Jetpack Compose + Material 3
- Navigation Compose
- Koin
- Firebase Auth
- Room
- Coroutines + Flow
- Android Keystore / Security Crypto
- Biometric API

## Estrutura principal

```text
app/src/main/java/com/example/nexus
├── framework
│   ├── di                  # Modulos Koin e Application
│   ├── service/local       # Room, DAO, entidade, migrations e repository
│   └── service/remote      # Firebase Auth
└── ui
    ├── ViewModels          # Estados e regras de tela
    ├── components          # Componentes Compose reutilizaveis
    ├── navigation          # Grafos de navegacao
    ├── screens             # Telas Compose
    ├── states              # Estados de UI
    └── until               # Utilitarios
```

## Configuracao local

1. Instale Android Studio ou configure o Android SDK.
2. Garanta que `ANDROID_HOME` aponte para o SDK Android.
3. Configure o Firebase do projeto e mantenha `app/google-services.json` atualizado para o ambiente local.
4. Rode:

```bash
./gradlew test
./gradlew assembleDebug
```

Se `gradlew` nao estiver executavel no sistema, rode:

```bash
bash gradlew test
```

## Seguranca

O armazenamento local de senhas passa pelo `PasswordRepository`, que criptografa os dados antes de persistir no Room usando AES/GCM com chave do Android Keystore.

Cuidados atuais:

- Senhas salvas sao criptografadas antes de irem para o banco local.
- Credenciais do recurso "lembrar-me" ficam em `EncryptedSharedPreferences`.
- Backup automatico da aplicacao esta desabilitado no manifesto.
- Regras de backup e device transfer excluem bancos, shared preferences e arquivos locais.
- Trafego HTTP sem TLS fica desabilitado por manifesto.
- Capturas de tela ficam bloqueadas via `FLAG_SECURE`.
- A exclusao de conta tambem limpa as senhas locais.
- Logs diretos de credenciais foram removidos.
- `app/google-services.json` nao deve ser versionado; use arquivo local ou o secret `GOOGLE_SERVICES_JSON` no CI.

Pontos que ainda merecem evolucao antes de producao:

- Validar Android Keystore e biometria em testes instrumentados.
- Definir politica de rotacao/recuperacao para dados locais protegidos por Keystore.
- Expandir testes instrumentados/Compose para validar UI em dispositivo ou emulador.

## CI

O workflow `.github/workflows/android-ci.yml` executa `./gradlew testDebugUnitTest` em pull requests e pushes para `master`.

Para builds que precisem do Firebase configurado, cadastre o conteudo do `google-services.json` no secret `GOOGLE_SERVICES_JSON`.

## Testes

Os testes unitarios ficam em:

```text
app/src/test
```

Cobertura atual da suite unitaria:

- validacao de campos de cadastro e senha;
- validacao de salvamento de senha gerada;
- geracao de senhas e regras do `PwdGeneratorViewModel`;
- fluxo de login, remember-me e credenciais salvas;
- fluxo de cadastro;
- fluxo de recuperacao de senha;
- repository local de senhas, incluindo criptografia na gravacao e leitura de dados atuais/legados;
- repository remoto de autenticacao Firebase com sucesso e principais erros mapeados;
- configuracoes de seguranca de manifesto e backup;
- mensagens globais de aviso.

Os testes instrumentados ficam em:

```text
app/src/androidTest
```

Ainda faltam testes instrumentados/Compose para validar a interface em dispositivo ou emulador.
