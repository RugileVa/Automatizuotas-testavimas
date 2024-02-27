pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                script {
                    sh './mvw test --stacktrace'
                }
            }
        }
    }
}