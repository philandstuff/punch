node default {
  package {'nginx':
    ensure => installed
  }

  file {'/etc/nginx/sites-available/hello':
    require => Package['nginx'],
    content => '
server {
	listen   80; ## listen for ipv4; this line is default and implied

	root /usr/share/nginx/www;
	index index.html index.htm;

	server_name localhost;

	location / {
		try_files $uri $uri/ /index.html;
	}

}

'
}

  file {'/etc/nginx/sites-enabled':
    ensure  => directory,
    recurse => true, # enable recursive directory management
    purge   => true, # purge all unmanaged junk
    force   => true, # also purge subdirs and links etc.
    require => Package['nginx'];
  }

  file {'/etc/nginx/sites-enabled/hello':
    ensure  => link,
    target  => '/etc/nginx/sites-available/hello',
    require => File['/etc/nginx/sites-available/hello'],
    notify  => Service['nginx'],
  }

  file {'/usr/share/nginx/www/index.html':
    content => '
<html>
<head>
<title>Hello world!</title>
</head>
<body>
<h1>Howdy!</h1>
</body>
</html>

',
  }
  Package['nginx'] -> File['/usr/share/nginx/www/index.html'] ~> Service['nginx']
  service {'nginx':
    ensure    => running,
    hasstatus => true,
    restart   => '/etc/init.d/nginx reload',
  }
}

