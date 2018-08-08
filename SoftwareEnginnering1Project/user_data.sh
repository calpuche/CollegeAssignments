#!/bin/bash
#install updates, mysql, java, nginx, and other deps
apt-get update -y
apt-get upgrade -y
debconf-set-selections <<< 'mysql-server mysql-server/root_password password fv14ODy7$Me6a6!Y'
debconf-set-selections <<< 'mysql-server mysql-server/root_password_again password fv14ODy7$Me6a6!Y'
apt-get install mysql-server openjdk-8-jdk jq awscli build-essential nginx curl -y

export DOMAIN=$(aws ec2 --region us-east-1 describe-tags --filters Name=resource-id,Values=$(curl http://169.254.169.254/latest/meta-data/instance-id -s) | jq .[][0].Value | tr -d '"');
export ENV=$(aws ec2 --region us-east-1 describe-tags --filters Name=resource-id,Values=$(curl http://169.254.169.254/latest/meta-data/instance-id -s) | jq .[][1].Value | tr -d '"');

#create a service file for our java app
cat <<EOF | tee /etc/systemd/system/hilltop-online.service
[Unit]
Description=Hilltop Online
After=syslog.target

[Service]
ExecStart=/usr/bin/java -jar /home/ubuntu/hilltop-online.jar --spring.profiles.active=$ENV
Restart=always
RestartSec=30

[Install]
WantedBy=multi-user.target
EOF

#enable the java app
systemctl daemon-reload
systemctl enable hilltop-online.service
systemctl restart hilltop-online.service

#set up nginx well knwon
cat <<EOF |  tee /etc/nginx/nginx.conf
user www-data;
worker_processes auto;
pid /run/nginx.pid;
events {
    worker_connections 1024;
}

http {
    sendfile on;
    tcp_nopush on;
    tcp_nodelay on;
    keepalive_timeout 65;
    types_hash_max_size 2048;

    include /etc/nginx/mime.types;
    default_type application/octet-stream;

    access_log /var/log/nginx/access.log;
    error_log /var/log/nginx/error.log;

    gzip on;
    gzip_disable "msie6";

  server {
      listen 80;
      listen [::]:80;
      server_name _;

      root /var/www/html/;

      location ~ /.well-known {
        allow all;
      }
  }

}
EOF

service nginx restart

#setup nginx and openresty
rm -rf /etc/nginx/sites-available/ /etc/nginx/sites-enabled/ /etc/nginx/conf.d/
mkdir -p /etc/nginx/snippets/

#setup letsencrpy for DOMAIN using certbot
#from https://www.exratione.com/2016/06/a-simple-setup-and-installation-script-for-lets-encrypt-ssl-certificates/
# No package install yet.
wget https://dl.eff.org/certbot-auto
chmod a+x certbot-auto
mv certbot-auto /usr/local/bin

# Install the dependencies.
certbot-auto --noninteractive --os-packages-only

# Set up config file.
mkdir -p /etc/letsencrypt
cat <<EOF |  tee /etc/letsencrypt/cli.ini
# Uncomment to use the staging/testing server - avoids rate limiting.
#server = https://acme-staging.api.letsencrypt.org/directory

# Use a 4096 bit RSA key instead of 2048.
rsa-key-size = 4096

# Set email and domains.
email = jmorris@stedwards.edu
domains = $DOMAIN

# Text interface.
text = True
# No prompts.
non-interactive = True
# Suppress the Terms of Service agreement interaction.
agree-tos = True

# Use the webroot authenticator.
authenticator = webroot
webroot-path = /var/www/html
EOF

# Obtain cert.
certbot-auto certonly
openssl dhparam -out /etc/ssl/certs/dhparam.pem 2048

#generate ssl domain certs
cat <<EOF |  tee /etc/nginx/snippets/ssl-domain.conf
ssl_certificate /etc/letsencrypt/live/$DOMAIN/fullchain.pem;
ssl_certificate_key /etc/letsencrypt/live/$DOMAIN/privkey.pem;
EOF

#setup nginx ssl params
cat <<EOF |  tee /etc/nginx/snippets/ssl-params.conf
# from https://cipherli.st/
# and https://raymii.org/s/tutorials/Strong_SSL_Security_On_nginx.html

ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
ssl_prefer_server_ciphers on;
ssl_ciphers "EECDH+AESGCM:EDH+AESGCM:AES256+EECDH:AES256+EDH";
ssl_ecdh_curve secp384r1;
ssl_session_cache shared:SSL:10m;
ssl_session_tickets off;
ssl_stapling on;
ssl_stapling_verify on;
resolver 8.8.8.8 8.8.4.4 valid=300s;
resolver_timeout 5s;
# Disable preloading HSTS for now.  You can use the commented out header line that includes
# the "preload" directive if you understand the implications.
#add_header Strict-Transport-Security "max-age=63072000; includeSubdomains; preload";
add_header Strict-Transport-Security "max-age=63072000; includeSubdomains";
add_header X-Frame-Options DENY;
add_header X-Content-Type-Options nosniff;

ssl_dhparam /etc/ssl/certs/dhparam.pem;
EOF

#setup nginx conf
cat <<EOF |  tee /etc/nginx/nginx.conf
user www-data;
worker_processes auto;
pid /run/nginx.pid;
events {
    worker_connections 1024;
}

http {
    sendfile on;
    tcp_nopush on;
    tcp_nodelay on;
    keepalive_timeout 65;
    types_hash_max_size 2048;

    include /etc/nginx/mime.types;
    default_type application/octet-stream;

    ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
    ssl_prefer_server_ciphers on;

    access_log /var/log/nginx/access.log;
    error_log /var/log/nginx/error.log;

    gzip on;
    gzip_disable "msie6";

  server {
      listen 80;
      listen [::]:80;
      server_name $DOMAIN;
      return 301 https://$DOMAIN\$request_uri;
  }

  server {
      listen 443 ssl http2;
      listen [::]:443 ssl http2;
      server_name $DOMAIN;
      include snippets/ssl-domain.conf;
      include snippets/ssl-params.conf;

      proxy_set_header HOST \$host;
      proxy_set_header X-Forwarded-Proto \$scheme;
      proxy_set_header X-Real-IP \$remote_addr;
      proxy_set_header X-Forwarded-For \$proxy_add_x_forwarded_for;

      root /var/www/html;

      location / {
        proxy_pass http://localhost:8080;
      }

      location ~ /.well-known {
        allow all;
      }
  }

  server {
        listen       80  default_server;
        return       444;
    }
}
EOF

service nginx restart
