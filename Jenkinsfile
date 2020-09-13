#!/usr/bin/env groovy

node {
	stage('checkout')
	{
		checkout scm
	}

	stage('check java , node , docker , docker-compose')
	{
		sh "pwd"
		sh "java -version"
		sh "node -v"
		sh "npm -v"
		sh "docker -v"
		sh "docker-compose -v"
	}

	stage('clean')
	{
		sh "chmod +x gradlew"
		sh "./gradlew clean --no-daemon"
	}

	stage('check integration')
	{
		try
		{
			sh 'docker container inspect docker_catinygateway-elasticsearch_1'
			sh 'docker container inspect docker_catinygateway-mariadb_1'
			sh 'docker container inspect docker_catinygateway-redis_1'
			sh "docker container inspect docker_jhipster-registry_1"
			sh "docker container inspect docker_zookeeper_1"
			sh "docker container inspect docker_kafka_1"
		}
		catch (ignored)
		{
			echo 'the necessary services are not running . try start it'
			sh "docker-compose -f src/main/docker/app-prod.yml up -d"
		}
	}

	stage('check catiny-uaa')
	{
		try
		{
			sh "docker container inspect docker_catinyuaa-app_1"
		}
		catch (err)
		{
			echo "docker_jhipster-registry_1 is not running. try start catinyuaa"
//			sh "docker-compose -f /var/lib/jenkins/workspace/CatinyUAA_master/src/main/docker/catiny-uaa.yml up -d"
			throw err
		}
	}

	stage('nohttp')
	{
		sh "./gradlew checkstyleNohttp --no-daemon"
	}

	stage('npm install')
	{
		sh "./gradlew npm_install -PnodeInstall --no-daemon"
	}

	stage('frontend tests')
	{
		try
		{
			sh "./gradlew npm_run_test-ci -PnodeInstall --no-daemon"
		}
		catch (err)
		{
			throw err
		}
		finally
		{
			junit '**/build/test-results/TESTS-*.xml'
		}
	}

	stage('backend tests')
	{
		try
		{
//			sh "./gradlew build --no-daemon"
			sh "./gradlew test integrationTest -PnodeInstall --no-daemon"
		}
		catch (err)
		{
			throw err
		}
		finally
		{
			junit '**/build/**/TEST-*.xml'
		}
	}

	stage('build docker catiny-gateway')
	{
		sh "./gradlew bootJar -Pprod jibDockerBuild --no-daemon"
	}

	stage('start docker catiny-gateway')
	{
		sh "docker-compose -f src/main/docker/catiny-gateway.yml up -d"
		echo "Successful deployment"
	}

	stage( 'Log display after 200 seconds from running')
	{
		sleep(200)
		sh "docker logs docker_catinygateway-app_1 --tail 1000"
	}
}

