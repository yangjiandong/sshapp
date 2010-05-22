1.bin/active-admin: uncomment JMX options.
                    set max memory from 512M to 1024M 
2.conf/activemq.xml: set <system usage>, 1)memory from 64 to 128m, 2)disk from 100g to 10g, 3)temp from 10g to 1g
                     set <destinationPolicy> memory limit from 1m to 10m

3.conf/jetty.xml: remove demo,camel and fileserver application.
