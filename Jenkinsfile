pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'caianajsb/spring-boot-school-app'
        REPO_URL = 'https://github.com/caianajsb/SpringBootRESTAPI.git'
        app = ''
    }

    stages {
        stage('Clone repository') {
            steps {
                git branch: 'main', url: "${env.REPO_URL}"
            }
        }

        stage('Build with Maven') {
            steps {
           // linux    sh 'mvn clean package'
                bat 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    app = docker.build("${DOCKER_IMAGE}")
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-credentials') {
                        docker.image("${DOCKER_IMAGE}").push('latest')
                    }
                }
            }
        }
        
        stage('Deploy to Docker') {
            steps {
                script {
                    // Run a Docker container
                    app.run('-p 8081:8081')
                }
            }
        }

    }

    post {
        always {
            cleanWs()
        }
    }
}