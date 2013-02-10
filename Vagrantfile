require 'fileutils'
BOX_URL = "https://s3-eu-west-1.amazonaws.com/philandstuff/ubuntu-12.04-2013-02-07.box"

Vagrant::Config.run do |config|
  config.vm.box = "punch-precise-2013-02-07"
  config.vm.box_url = BOX_URL
  config.vm.customize ["modifyvm", :id, "--rtcuseutc", "on"]

  # You may want to up the memory / CPUs to get better performance in the VM.
  # Example given below to put in Vagrantfile.local if you want to do so.
  # config.vm.customize [ "modifyvm", :id, "--memory", "1024", "--cpus", "2" ]

  config.vm.host_name = 'vm'

  FileUtils.rm_rf 'catalogs'
  FileUtils.mkdir 'catalogs'
  system 'lein run nginx > catalogs/vm.json'
  config.vm.provision :puppet do |puppet|
    puppet.options = "--catalog" # hackity hack
    puppet.manifests_path = 'catalogs'
    puppet.manifest_file = 'vm.json'
  end

end

