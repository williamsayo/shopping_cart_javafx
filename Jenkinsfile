pipeline {
    agent any
    tools {
        maven "${params.MAVEN}"
    }

    environment {
        GITHUB_REPOSITORY= "${params.GITHUB_REPOSITORY}"
        SONARQUBE_SERVER = 'SonarQubeServer'
        SONAR_CREDENTIALS = "${params.SONAR_CRED}"
        DOCKERHUB_CREDENTIALS="${params.DOCKER_CRED}"
        DOCKERHUB_REPOSITORY="williamsayo/gui_localization"
        DOCKER_IMAGE_TAG="v1"
    }

    stages{
        stage('Check Java') {
            steps {
                bat 'java -version'
                bat 'echo %JAVA_HOME%'
                bat 'mvn -version'
            }
        }

        stage("check docker"){
            steps {
                bat "docker --version"
            }
        }

        stage("checkout"){
            steps {
                git branch: "${BRANCH}", url:"${GITHUB_REPOSITORY}"
            }
        }

        stage("test"){
            steps {
                bat "mvn clean test"
            }
        }

        stage("coverage report"){
            steps {
                bat "mvn jacoco:report"
            }
        }

        stage("publish test report"){
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage("publish coverage report"){
            steps {
                jacoco()
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQubeServer') {
                    bat "${tool 'SonarScanner'}\\bin\\sonar-scanner.bat"
                }
            }
        }

        stage("build docker image"){
            steps {
                bat "docker build -t ${DOCKERHUB_REPOSITORY}:${DOCKER_IMAGE_TAG} ."
            }
        }

        stage("Push Docker Image to Docker Hub"){
            steps {
                script{
                    docker.withRegistry('https://index.docker.io/v1/', env.DOCKER_CRED){
                        docker.image("${DOCKERHUB_REPOSITORY}:${DOCKER_IMAGE_TAG}").push()
                    }
                }
            }
        }
    }

    post {
        always {
            bat "docker logout"
        }
        success {
            echo "Image published to Docker Hub successfully."
        }
        failure {
            echo 'Pipeline failed. Check the logs above for details.'
        }
    }
}