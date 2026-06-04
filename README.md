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
- Logs diretos de credenciais foram removidos.

Pontos que ainda merecem evolucao antes de producao:

- Aumentar cobertura de testes para criptografia, repository e ViewModels.
- Revisar nomes de packages/pastas herdados de refatoracoes anteriores.
- Definir politica de rotacao/recuperacao para dados locais protegidos por Keystore.
- Criar pipeline de CI para build e testes.

## Testes

Os testes unitarios ficam em:

```text
app/src/test
```

Os testes instrumentados ficam em:

```text
app/src/androidTest
```

Atualmente a cobertura ainda e pequena e deve ser ampliada principalmente nas camadas de seguranca e persistencia.
