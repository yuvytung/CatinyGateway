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
		sh "docker service ls"
	}

	stage('clean')
	{
		sh "chmod +x gradlew"
		sh "./gradlew clean --no-daemon"
	}

	stage('nohttp')
	{
		sh "./gradlew checkstyleNohttp --no-daemon"
	}

	stage('check integration')
	{
		try
		{
			sh 'docker service inspect catiny-services_catinygateway-elasticsearch'
			sh 'docker service inspect catiny-services_catinygateway-mariadb'
			sh 'docker service inspect catiny-services_catinygateway-redis'
			sh "docker service inspect catiny-services_jhipster-registry"
			sh "docker service inspect catiny-services_zookeeper"
			sh "docker service inspect catiny-services_kafka"
		}
		catch (ignored)
		{
			echo 'the necessary services are not running . try start it'
			echo 'Sleep for 150 seconds to wait for the mariadb to be ready'
			sh "docker stack deploy -c /root/CatinyServer/swarm/catiny-services.yml catiny-services"
			sleep(150)
		}
		try
		{
			sh 'docker service inspect catinydev-services_catinydevgateway-elasticsearch'
			sh 'docker service inspect catinydev-services_catinydevgateway-mariadb'
			sh 'docker service inspect catinydev-services_catinydevgateway-redis'
			sh "docker service inspect catinydev-services_jhipster-registry-dev"
			sh "docker service inspect catinydev-services_zookeeper-dev"
			sh "docker service inspect catinydev-services_kafka-dev"
		}
		catch (ignored)
		{
			echo 'the necessary services are not running . try start it'
			echo 'Sleep for 150 seconds to wait for the mariadb to be ready'
			sh "docker stack deploy -c /root/CatinyServer/swarm/catiny-services.yml catiny-services"
			sleep(150)
		}
	}

	stage('check catiny-uaa')
	{
		try
		{
			sh "docker service inspect catiny-services_catinyuaa-app"
		}
		catch (ignored)
		{
			echo "Warning catiny-app-prod_catinyuaa is not running. try start catinyuaa"
		}
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
//			sh "./gradlew test integrationTest -PnodeInstall --no-daemon"
			sh "./gradlew build"
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

	stage('check catiny app is running')
	{
		try
		{
			sh "docker stack ls | grep catiny-app-dev"
		}
		catch (ignored)
		{
			echo "catiny app dev or prod isn't running"
			sh "docker stack deploy -c /root/CatinyServer/swarm/catiny-app-dev.yml catiny-app-dev"
			sleep(60)
		}
		try
		{
			sh "docker stack ls | grep catiny-app-prod"
		}
		catch (ignored)
		{
			echo "catiny app dev or prod isn't running . try start"
			sh "docker stack deploy -c /root/CatinyServer/swarm/catiny-app-prod.yml catiny-app-prod"
			sleep(60)
		}
	}

	stage('build docker catiny-gateway')
	{
		try
		{
			sh "./gradlew bootJar -Pprod jibDockerBuild --no-daemon"
			sh "docker tag catinygateway:latest yuvytung/catinygateway:latest"
			sh "docker push yuvytung/catinygateway:latest"
		}

		catch (exception)
		{
			throw exception
		}
	}

	stage('start docker catiny-gateway')
	{
		try
		{
			sh "docker service update --force catiny-app-dev_catinygateway"
		}
		catch (exception)
		{
			throw exception
		}
		sh "docker service update --force catiny-app-prod_catinygateway"
		echo "Successful deployment"
	}

	stage('Log display after 200 seconds from running')
	{
		sleep(60)
		sh "docker service logs --tail 500 catiny-app-dev_catinygateway"
		sh "docker service logs --tail 500 catiny-app-prod_catinygateway"
		echo "Done."
	}
}

//#!/usr/bin/env groovy
//
//node {
//	stage('checkout')
//	{
//		checkout scm
//	}
//
//	stage('check java , node , docker , docker-compose')
//	{
//		sh "pwd"
//		sh "java -version"
//		sh "node -v"
//		sh "npm -v"
//		sh "docker -v"
//		sh "docker-compose -v"
//	}
//
//	stage('clean')
//	{
//		sh "chmod +x gradlew"
//		sh "./gradlew clean --no-daemon"
//	}
//
//	stage('check integration')
//	{
//		try
//		{
//			sh 'docker container inspect docker_catinygateway-elasticsearch_1'
//			sh 'docker container inspect docker_catinygateway-mariadb_1'
//			sh 'docker container inspect docker_catinygateway-redis_1'
//			sh "docker container inspect docker_jhipster-registry_1"
//			sh "docker container inspect docker_zookeeper_1"
//			sh "docker container inspect docker_kafka_1"
//		}
//		catch (ignored)
//		{
//			echo 'the necessary services are not running . try start it'
//			sh "docker-compose -f src/main/docker/app-prod.yml up -d"
//			echo 'Sleep for 120 seconds to wait for the mariadb to be ready'
//			sleep(120)
//		}
//	}
//
//	stage('check catiny-uaa')
//	{
//		try
//		{
//			sh "docker container inspect docker_catinyuaa-app_1"
//		}
//		catch (err)
//		{
//			echo "docker_jhipster-registry_1 is not running. try start catinyuaa"
////			sh "docker-compose -f /var/lib/jenkins/workspace/CatinyUAA_master/src/main/docker/catiny-uaa.yml up -d"
//			throw err
//		}
//	}
//
//	stage('nohttp')
//	{
//		sh "./gradlew checkstyleNohttp --no-daemon"
//	}
//
//	stage('npm install')
//	{
//		sh "./gradlew npm_install -PnodeInstall --no-daemon"
//	}
//
//	stage('frontend tests')
//	{
//		try
//		{
//			sh "./gradlew npm_run_test-ci -PnodeInstall --no-daemon"
//		}
//		catch (err)
//		{
//			throw err
//		}
//		finally
//		{
//			junit '**/build/test-results/TESTS-*.xml'
//		}
//	}
//
//	stage('backend tests')
//	{
//		try
//		{
////			sh "./gradlew build --no-daemon"
//			sh "./gradlew test integrationTest -PnodeInstall --no-daemon"
//		}
//		catch (err)
//		{
//			throw err
//		}
//		finally
//		{
//			junit '**/build/**/TEST-*.xml'
//		}
//	}
//
//	stage('build docker catiny-gateway')
//	{
//		sh "./gradlew bootJar -Pprod jibDockerBuild --no-daemon"
//	}
//
//	stage('start docker catiny-gateway')
//	{
//		sh "docker-compose -f src/main/docker/catiny-gateway.yml up -d"
//		echo "Successful deployment"
//	}
//
//	stage( 'Log display after 200 seconds from running')
//	{
//		sleep(60)
//		sh "docker logs docker_catinygateway-app_1 --tail 1000"
//	}
//}
//
