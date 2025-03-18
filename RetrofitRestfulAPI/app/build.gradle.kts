plugins {
    alias(libs.plugins.android.application) // Plugin do Android Application
}

android {
    namespace = "com.example.apisrestfulretrofit" // Namespace do projeto
    compileSdk = 35 // Versão da API utilizada na compilação

    defaultConfig {
        applicationId = "com.example.apisrestfulretrofit" // ID do aplicativo
        minSdk = 27 // Versão mínima do Android suportada
        targetSdk = 35 // Versão alvo do Android
        versionCode = 1 // Código da versão do aplicativo
        versionName = "1.0" // Nome da versão do aplicativo

        // Configuração do runner para testes
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Não minimizar o código no build release
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11 // Compatibilidade com Java 11
        targetCompatibility = JavaVersion.VERSION_11 // Compatibilidade com Java 11
    }
}

dependencies {
    // Dependências principais
    implementation("androidx.appcompat:appcompat:1.6.1") // Suporte para compatibilidade com versões anteriores do Android
    implementation("com.google.android.material:material:1.9.0") // Material Design Components

    // Dependências para Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // Biblioteca principal Retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Conversor Gson para manipulação de JSON

    // Dependências adicionais
    implementation("androidx.recyclerview:recyclerview:1.3.0") // RecyclerView para listas
    implementation("com.google.code.gson:gson:2.9.0") // Biblioteca Gson para JSON

    // Dependências para testes
    testImplementation("junit:junit:4.13.2") // Testes unitários com JUnit
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // Extensões de teste para JUnit no Android
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1") // Testes de UI com Espresso
}