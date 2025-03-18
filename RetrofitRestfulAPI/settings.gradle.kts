pluginManagement {
    repositories {
        google {
            // Configuração para incluir grupos específicos de dependências
            content {
                includeGroupByRegex("com\\.android.*") // Dependências relacionadas ao Android
                includeGroupByRegex("com\\.google.*") // Dependências relacionadas ao Google
                includeGroupByRegex("androidx.*") // Dependências relacionadas ao AndroidX
            }
        }
        mavenCentral() // Repositório Maven Central para dependências de terceiros
        gradlePluginPortal() // Repositório padrão para plugins do Gradle
    }
}

dependencyResolutionManagement {
    // Configuração para falhar caso dependências sejam resolvidas fora dos repositórios definidos
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google() // Repositório do Google para bibliotecas Android
        mavenCentral() // Repositório Maven Central para dependências de terceiros
    }
}

// Nome do projeto raiz
rootProject.name = "apisrestfulretrofit"

// Inclusão do módulo principal da aplicação
include(":app")
