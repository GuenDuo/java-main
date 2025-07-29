pipeline {
    agent any

    environment {
        GITHUB_REPO = 'https://github.com/GuenDuo/java-main.git'
        IMAGE_BACKEND = 'yourdockerhubuser/backend-auth'
        IMAGE_FRONTEND = 'yourdockerhubuser/frontend'
        SONARQUBE_SERVER = 'SonarQube'
        SONAR_PROJECT_KEY = 'java-main-backend'
        DOCKER_COMPOSE_PATH = './'   // Thư mục chứa docker-compose.yml
        VERSION = "v0.${BUILD_NUMBER}"
        DEPLOY_SSH_KEY = 'KeyCuoiKy'  // SSH key đã cấu hình trong Jenkins
        SERVER_USER = 'your-username'  // Tên người dùng trên server (ví dụ: ubuntu)
        SERVER_IP = 'your.server.ip'  // Địa chỉ IP của server
        REMOTE_PATH = '/path/to/project' // Đường dẫn dự án trên server
    }

    stages {
        stage('Checkout') {
            steps {
                echo '📥 Lấy source code từ GitHub'
                // Đảm bảo rằng refspec đúng và branch được chỉ định rõ ràng
                git branch: 'main', url: "${env.GITHUB_REPO}", refspec: '+refs/heads/main:refs/remotes/origin/main'
            }
        }

        stage('Build & Test Backend') {
            steps {
                dir('backend-auth') {
                    echo '🛠️ Build và kiểm tra Backend (Maven)'
                    sh 'mvn clean install'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                dir('backend-auth') {
                    withSonarQubeEnv("${env.SONARQUBE_SERVER}") {
                        sh 'mvn sonar:sonar'
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 3, unit: 'MINUTES') {
                    script {
                        def qg = waitForQualityGate()
                        if (qg.status != 'OK') {
                            error("❌ Quality Gate failed: ${qg.status}")
                        }
                    }
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                echo '🐳 Đóng gói Docker cho Backend và Frontend'
                script {
                    sh "docker build -t ${IMAGE_BACKEND}:${VERSION} backend-auth"
                    sh "docker build -t ${IMAGE_FRONTEND}:${VERSION} frontend"
                }
            }
        }

        stage('Deploy Local by Docker Compose') {
            steps {
                echo '🚀 Triển khai lên môi trường local bằng docker-compose'
                sh "docker-compose -f ${DOCKER_COMPOSE_PATH}/docker-compose.yml up -d --build"
            }
        }

        stage('Deploy on Remote Server') {
            steps {
                echo '🚀 Deploy lên server qua SSH'
                sshagent([DEPLOY_SSH_KEY]) {
                    sh """
                        ssh -o StrictHostKeyChecking=no ${SERVER_USER}@${SERVER_IP} 'cd ${REMOTE_PATH} && git pull && docker-compose up -d --build'
                    """
                }
            }
        }
    }
}
