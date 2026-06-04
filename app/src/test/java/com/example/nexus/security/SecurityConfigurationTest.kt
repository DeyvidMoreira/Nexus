package com.example.nexus.security

import com.google.common.truth.Truth.assertThat
import java.io.File
import org.junit.Test

class SecurityConfigurationTest {

    @Test
    fun `manifest disables backups and cleartext traffic`() {
        val manifest = projectFile("src/main/AndroidManifest.xml").readText()

        assertThat(manifest).contains("android:allowBackup=\"false\"")
        assertThat(manifest).contains("android:usesCleartextTraffic=\"false\"")
    }

    @Test
    fun `legacy backup rules exclude sensitive storage domains`() {
        val backupRules = projectFile("src/main/res/xml/backup_rules.xml").readText()

        assertSensitiveDomainsAreExcluded(backupRules)
    }

    @Test
    fun `data extraction rules exclude sensitive storage domains`() {
        val extractionRules = projectFile("src/main/res/xml/data_extraction_rules.xml").readText()

        assertSensitiveDomainsAreExcluded(extractionRules)
    }

    private fun assertSensitiveDomainsAreExcluded(xml: String) {
        listOf("database", "sharedpref", "file", "external", "root").forEach { domain ->
            assertThat(xml).contains("""<exclude domain="$domain" path="." />""")
        }
    }

    private fun projectFile(relativePath: String): File {
        val workingDirectory = File(System.getProperty("user.dir"))
        val appDirectory = if (workingDirectory.name == "app") {
            workingDirectory
        } else {
            File(workingDirectory, "app")
        }

        return File(appDirectory, relativePath)
    }
}
