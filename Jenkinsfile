#!/usr/bin/env groovy

node {
    stage('checkout') {
        checkout scm
    }

    stage('check java , node , docker , docker-compose') {
        sh "java -version"
        sh "node -v"
        sh "npm -v"
        sh "docker -v"
        sh "docker-compose -v"
    }

    stage('clean') {
        sh "chmod +x gradlew"
        sh "./gradlew clean --no-daemon"
    }
    stage('nohttp') {
        sh "./gradlew checkstyleNohttp --no-daemon"
    }

    stage('npm install') {
        sh "./gradlew npm_install -PnodeInstall --no-daemon"
    }

//  todo
//    stage('backend tests') {
//        try {
//            sh "./gradlew test integrationTest -PnodeInstall --no-daemon"
//        } catch(err) {
//            throw err
//        } finally {
//            junit '**/build/**/TEST-*.xml'
//        }
//    }

    stage('frontend tests') {
        try {
            sh "./gradlew npm_run_test-ci -PnodeInstall --no-daemon"
        } catch(err) {
            throw err
        } finally {
            junit '**/build/test-results/TESTS-*.xml'
        }
    }

    stage('packaging') {
        sh "./gradlew bootJar -x test -Pprod -PnodeInstall --no-daemon"
        archiveArtifacts artifacts: '**/build/libs/*.jar', fingerprint: true
    }

//  todo
//    stage('quality analysis') {
//        withSonarQubeEnv('catiny-gateway-sonar') {
//            sh "./gradlew sonarqube --no-daemon"
//        }
//    }


    stage('check jhipster-registry'){
        try
        {
            sh "docker container inspect docker_jhipster-registry_1"
        }
        catch (err)
        {
            echo "docker_jhipster-registry_1 not running"
            sh "docker-compose -f src/main/docker/jhipster-registry-docker.yml up -d"
        }
    }

    stage('check catiny-uaa'){
        try
        {
            sh "docker container inspect docker_catinyuaa-app_1"
        }
        catch (err)
        {
            echo "docker_jhipster-registry_1 not running"
            throw err
        }
    }


    stage('build docker catiny-gateway') {
        sh "./gradlew bootJar -Pprod jibDockerBuild --no-daemon"
    }

    stage('start docker catiny-gateway') {
        sh "docker-compose -f src/main/docker/app.yml down"
        sh "docker-compose -f src/main/docker/app.yml up -d"
        echo "Successful deployment"
    }
}