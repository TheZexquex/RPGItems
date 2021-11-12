plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "com.github.thezexquex"
version = "1.0-SNAPSHOT"

val shadeBase = "$group.rpgitems.libs."
val mainClassPath = "$group.rpgitems.RPGItemsPlugin"

java.sourceCompatibility = JavaVersion.VERSION_16
java.targetCompatibility = JavaVersion.VERSION_16

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper","paper-api","1.17.1-R0.1-SNAPSHOT")
    implementation("org.spongepowered","configurate-yaml","4.0.0")
    implementation("net.kyori","adventure-text-minimessage","4.1.0-SNAPSHOT")
    implementation("me.clip","placeholderapi","2.10.10")
    implementation("cloud.commandframework", "cloud-paper", "1.5.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks {
    compileJava {
        options.encoding = "UFT-8"
    }

    shadowJar {
        relocate("org.spongepowered", shadeBase+ "configurate")
        relocate("net.kyori", shadeBase + "minimessage")
        relocate("me.clip", shadeBase + "papi")
        relocate("cloud.commandframework", shadeBase + "cloud")
    }

    processResources {
        from(sourceSets.main.get().resources.srcDirs) {
            filesMatching("plugin.yml") {
                expand(
                    "version" to project.version,
                    "name" to project.name,
                    "main" to mainClassPath
                )
            }
            duplicatesStrategy = DuplicatesStrategy.INCLUDE
        }
    }
}