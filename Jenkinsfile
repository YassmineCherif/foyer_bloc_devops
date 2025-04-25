
   pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub_credentials') // DockerHub credentials
        SONARQUBE_SERVER = 'SonarQube' // SonarQube server name
        SONARQUBE_TOKEN = credentials('sonarqube_token') // SonarQube token
    }

    stages {
        stage('Git Checkout') {
            steps {
                git branch: 'YassmineCherif-5SAE1-G3', credentialsId: 'YassmineCherif', url: 'https://github.com/karim9155/5SAE1-G3-Foyer.git'
            }
        }


        stage('Clean') {
            steps {
               sh 'mvn clean'
            }
        }

        stage('Compile') {
            steps {
             sh 'mvn compile'
            }
        }


        stage('Packages without tests') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }


     /*
        stage('Run Tests') {
            steps {
                echo "Running tests and generating JaCoCo report..."
                sh 'mvn test'
            }
        }

         stage('Generate JaCoCo Report') {
            steps {
                echo "Generating JaCoCo report..."
                 sh 'mvn jacoco:report'
            }
        }
        */


        stage('Run Tests') {
            steps {
                echo 'Running JUnit and Mockito tests...'
                sh 'mvn test'
                junit 'target/surefire-reports/*.xml'
            }
        }


        stage('SonarQube Analysis') {
            steps {
                script {
                    echo "Running SonarQube Analysis on project..."
                    withSonarQubeEnv("${SONARQUBE_SERVER}") {
                        sh """
                            mvn sonar:sonar \
                                -Dsonar.projectKey=5SAE1-G3-Foyer \
                                -Dsonar.login=${SONARQUBE_TOKEN}
                        """
                    }
                }
            }
        }
/*
        stage('Quality Gate') {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        */
        stage('Maven Deploy to Nexus') {
            steps {
                configFileProvider([configFile(fileId: '3d290ac1-a9e1-4294-ac7a-894a906f502b', variable: 'mavensettings')]) {
                    sh "mvn -s $mavensettings clean deploy -DskipTests=true"
                }
            }
        }

        stage('Archive Artifacts') {
            steps {
                echo 'Saving build artifacts...'
                archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: true
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building the Docker image...'
                script {
                    sh "docker build -t cherifyas/yassminecherif-g3-foyer:1.0.0 ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                echo 'Pushing the Docker image to DockerHub...'
                script {
                    sh "echo ${DOCKERHUB_CREDENTIALS_PSW} | docker login -u ${DOCKERHUB_CREDENTIALS_USR} --password-stdin"
                    sh "docker push cherifyas/yassminecherif-g3-foyer:1.0.0"
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                echo 'Pulling and deploying the latest Docker image using Docker Compose...'
                script {
                    // Pull the latest image and run docker-compose up
                    sh 'docker compose up -d'

                }
            }
        }
    }



post {
    always {
        emailext (
            subject: "Pipeline Status: ${currentBuild.currentResult}",
            body: """
                <html>
                    <body>
                        <p><strong>Build Status:</strong> ${currentBuild.currentResult}</p>
                        <p><strong>Build Number:</strong> ${env.BUILD_NUMBER}</p>
                        <p>Check the <a href="${env.BUILD_URL}">console output</a>.</p>
                    </body>
                </html>
            """,
            to: 'cherif.yasmine2000@gmail.com',
            from: 'jenkins@example.com',
            replyTo: 'jenkins@example.com',
            mimeType: 'text/html'
        )
    }
}




}