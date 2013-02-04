BOX_URL = "http://files.vagrantup.com/precise64.box"

Vagrant::Config.run do |config|
  config.vm.box = "precise_64"
  config.vm.box_url = BOX_URL
  config.vm.customize ["modifyvm", :id, "--rtcuseutc", "on"]

  # You may want to up the memory / CPUs to get better performance in the VM.
  # Example given below to put in Vagrantfile.local if you want to do so.
  # config.vm.customize [ "modifyvm", :id, "--memory", "1024", "--cpus", "2" ]

  config.vm.host_name = 'vm'
end

