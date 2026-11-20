pipeline {
    agent any

    tools {
        maven 'Maven 3.6.3'
        jdk 'default'
    }

    stages {
        stage('init') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn -Dmaven.test.failure.ignore=true install'
            }
            post {
                success {
                   junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Publish to Nexus Repository Manager') {
            when { anyOf { branch 'develop'; branch 'master' } }
            steps {
                sh 'mvn jar:jar deploy:deploy'
            }
        }
    }
}
