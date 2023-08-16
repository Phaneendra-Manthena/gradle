pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
               git branch: 'main', url: 'https://github.com/Phaneendra-Manthena/gradle.git'
            }
        }
        stage('Clean') {
            steps {
                script {
                    // Run the Gradle clean command
                    bat 'gradlew clean' // Use 'sh' if on a Unix-like system
                  

                }
            }
        }
        stage('Build APK') {
            steps {
                script {
                    // Run the Gradle assembleDebug command
                    bat './gradlew assembleDebug' // Use 'sh' if on a Unix-like system
                }
            }
        }
    }
}
