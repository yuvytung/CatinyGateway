#!/bin/bash
echo "start setup now"
echo "update"
apt-get update &&\
apt-get upgrade -y
echo "update successful"
echo "install expect and openjdk-11:lts"
apt install -y expect \
openjdk-11-jdk-headless
echo "install expect and openjdk-11:lts successful"
echo "install nodejs:lts"
curl -sL https://deb.nodesource.com/setup_lts.x | sudo -E bash -
sudo apt install -y nodejs
echo "install nodejs:lts successful"
echo "install docker"
sudo apt-get install -y \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg-agent \
    software-properties-common
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo apt-key fingerprint 0EBFCD88
sudo add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable"
sudo apt-get update
sudo apt-get install -y docker-ce docker-ce-cli containerd.io
echo "install docker successful"
echo "install docker-compose"
sudo curl -L "https://github.com/docker/compose/releases/download/1.26.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
echo "install docker-compose successful"
echo "install jenkins:lts"
wget -q -O - https://pkg.jenkins.io/debian-stable/jenkins.io.key | sudo apt-key add -
sudo sh -c 'echo deb https://pkg.jenkins.io/debian-stable binary/ > \
    /etc/apt/sources.list.d/jenkins.list'
sudo apt-get update
sudo apt-get install -y jenkins
echo "install jenkins:lts successful"
echo "*********************************************************"
echo "check version programs installed"
echo "*********************************************************"
echo "expect  : "
expect -v
echo "java    : "
java -version
echo "node    : "
node -v
echo "npm     : "
npm -v
echo "docker  : "
docker -v
echo "docker-compose : "
docker-compose -v
echo "*********************************************************"